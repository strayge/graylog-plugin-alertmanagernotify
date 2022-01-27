package com.strayge;

import static java.util.Objects.requireNonNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.floreysoft.jmte.Engine;
import com.google.common.collect.ImmutableList;

import org.graylog.events.notifications.EventNotification;
import org.graylog.events.notifications.EventNotificationContext;
import org.graylog.events.notifications.EventNotificationModelData;
import org.graylog.events.notifications.EventNotificationService;
import org.graylog.events.notifications.PermanentEventNotificationException;
import org.graylog.events.notifications.TemporaryEventNotificationException;
import org.graylog2.jackson.TypeReferences;
import org.graylog2.notifications.NotificationService;
import org.graylog2.plugin.MessageSummary;
import org.graylog2.plugin.system.NodeId;
import org.graylog2.streams.StreamService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the plugin. Your class should implement one of the existing plugin
 * interfaces. (i.e. AlarmCallback, MessageInput, MessageOutput)
 */
public class AlertManagerNotify implements EventNotification {
    public interface Factory extends EventNotification.Factory {
        @Override
        AlertManagerNotify create();
    }

    private static final Logger LOG = LoggerFactory.getLogger(AlertManagerNotify.class);
    private final EventNotificationService notificationCallbackService;
    private final NodeId nodeId;
    private final ObjectMapper objectMapper;

    @Inject
    public AlertManagerNotify(
        EventNotificationService notificationCallbackService,
        StreamService streamService,
        NotificationService notificationService,
        NodeId nodeId,
        ObjectMapper objectMapper
    ) {
        this.notificationCallbackService = notificationCallbackService;
        this.nodeId = requireNonNull(nodeId, "nodeId");
        this.objectMapper = requireNonNull(objectMapper, "objectMapper");
    }

    @Override
    public void execute(EventNotificationContext ctx) throws TemporaryEventNotificationException, PermanentEventNotificationException {
        AlertManagerNotifyConfig config = (AlertManagerNotifyConfig) ctx.notificationConfig();
        ImmutableList<MessageSummary> backlog = notificationCallbackService.getBacklogForEvent(ctx);
        EventNotificationModelData model = EventNotificationModelData.of(ctx, backlog);

        Engine templateEngine = Engine.createEngine();
        Map<String, Object> templateModel = createTemplateModel(config, backlog, model);

        Map<String, String> annotations = extractKeyValuePairsFromField(config.annotations());
        Map<String, Object> resolvedAnnotations = transformTemplateValues(templateEngine, templateModel, annotations);

        Map<String, String> labels = extractKeyValuePairsFromField(config.labels());
        labels.put("alertname", config.alertName());
        Map<String, Object> resolvedLabels = transformTemplateValues(templateEngine, templateModel, labels);

        int grace_period = 1;
        if (!"".equals(config.grace())) {
            try {
                grace_period = Integer.parseInt(config.grace());
            } catch (NumberFormatException e) {
                LOG.error("AlertManagerNotify: invalid grace period");
            }
        }
        DateTime startAt = new DateTime();
        DateTime endsAt = startAt.plusMinutes(grace_period).plusSeconds(20);

        AlertManagerPayload payloadObject = new AlertManagerPayload();
        payloadObject.annotations = resolvedAnnotations;
        payloadObject.labels = resolvedLabels;
        payloadObject.generatorURL = model.eventDefinitionId();
        payloadObject.startsAt = startAt.toString();
        payloadObject.endsAt = endsAt.toString();

        try {
            String payload = this.objectMapper.writeValueAsString(payloadObject);
            sendToAlertManager(config.apiUrl(), payload);
        } catch (JsonProcessingException e) {
            throw new PermanentEventNotificationException(e.getMessage());
        }
    }

    private Map<String, Object> createTemplateModel(
        AlertManagerNotifyConfig config,
        ImmutableList<MessageSummary> backlog,
        EventNotificationModelData model
    ) {

        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("node_id", this.nodeId);
        templateModel.put("config", objectToJson(config));
        templateModel.put("backlog", backlog);
        templateModel.put("backlog_size", backlog.size());
        templateModel.put("context", objectToJson(model));
        templateModel.put("event", objectToJson(model.event()));

        String message = model.event().message();
        String messagePrefix = model.eventDefinitionTitle() + ": ";
        if (message.startsWith(messagePrefix)) {
            message = message.substring(messagePrefix.length());
        }
        templateModel.put("message", message);
        return templateModel;
    }

    private Map<String, Object> transformTemplateValues(
        Engine templateEngine,
        Map<String, Object> templateModel,
        Map<String, String> customValueMap
    ) {
        final Map<String, Object> transformedCustomValueMap = new HashMap<>();
        customValueMap.forEach((key, value) -> {
            if (value instanceof String) {
                transformedCustomValueMap.put(key, templateEngine.transform((String) value, templateModel));
            } else {
                transformedCustomValueMap.put(key, value);
            }
        });
        return transformedCustomValueMap;
    }

    private Map<String, String> extractKeyValuePairsFromField(String textFieldValue) {
        Map<String, String> extractedPairs = new HashMap<>();

        if (textFieldValue != null && !"".equals(textFieldValue)) {
            final String preparedTextFieldValue = textFieldValue.replaceAll(";", "\n");
            Properties properties = new Properties();
            InputStream stringInputStream = new ByteArrayInputStream(preparedTextFieldValue.getBytes(StandardCharsets.UTF_8));
            try {
                properties.load(stringInputStream);
                properties.forEach((key, value) -> extractedPairs.put((String) key, (String) value));
            } catch (IOException e) {
                LOG.error("AlertManagerNotify: parse property failed " + e.getMessage());
            }
        }
        return extractedPairs;
    }

    private boolean sendToAlertManager(String url, String payload) {
        try {
            payload = "[" + payload + "]";
            URL alertManagerUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) alertManagerUrl.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.setRequestProperty("Content-Type", "application/json;");
            connection.setRequestProperty("Accept", "application/json,text/plain");
            connection.setRequestProperty("Method", "POST");
            try (OutputStream os = connection.getOutputStream()) {
                os.write(payload.getBytes(StandardCharsets.UTF_8));
            }
            int HttpResult = connection.getResponseCode();
            connection.disconnect();
            if (HttpResult != HttpURLConnection.HTTP_OK) {
                LOG.error("AlertManagerNotify: AlertManager returned bad code: " + HttpResult);
            }
            return HttpResult == HttpURLConnection.HTTP_OK;
        } catch (IOException e) {
            LOG.error("AlertManagerNotify: request failed " + e.getMessage());
            return false;
        }
    }

    private Object objectToJson(Object object) {
        return this.objectMapper.convertValue(object, TypeReferences.MAP_STRING_OBJECT);
    }

}
