package com.strayge;

import org.graylog.events.notifications.EventNotification;
import org.graylog.events.notifications.EventNotificationContext;
// import org.graylog.events.notifications.EventNotificationModelData;
// import org.graylog.events.notifications.EventNotificationService;
import org.graylog.events.notifications.PermanentEventNotificationException;
import org.graylog.events.notifications.TemporaryEventNotificationException;
// import org.graylog2.plugin.MessageSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// import com.google.common.collect.ImmutableList;

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
    // private final EventNotificationService notificationCallbackService;

    @Override
    public void execute(EventNotificationContext ctx) throws TemporaryEventNotificationException, PermanentEventNotificationException {
        LOG.info("AlertManagerNotify.execute called");

        final AlertManagerNotifyConfig config = (AlertManagerNotifyConfig) ctx.notificationConfig();
        // config.url()

        // ImmutableList<MessageSummary> backlog = notificationCallbackService.getBacklogForEvent(ctx);
        // final EventNotificationModelData model = EventNotificationModelData.of(ctx, backlog);

        // final EventNotificationModelData model = getModel(ctx, backlog);
        // model.eventDefinitionTitle()
        // ctx.notificationId()

        // final Request request = new Request.Builder()
        //         .url(httpUrl)
        //         .post(RequestBody.create(CONTENT_TYPE, body))
        //         .build();

        // try (final Response r = httpClient.newCall(request).execute()) {
        //     if (!r.isSuccessful()) {
        //         throw new PermanentEventNotificationException(
        //                 "Expected successful HTTP response [2xx] but got [" + r.code() + "]. " + config.url());
        //     }
        // } catch (IOException e) {
        //     throw new PermanentEventNotificationException(e.getMessage());
        // }
    }

    // private EventNotificationModelData getModel(EventNotificationContext ctx, ImmutableList<MessageSummary> backlog) {
    //     final Optional<EventDefinitionDto> definitionDto = ctx.eventDefinition();
    //     final Optional<JobTriggerDto> jobTriggerDto = ctx.jobTrigger();
    //     return EventNotificationModelData.builder()
    //             // .eventDefinition(definitionDto)
    //             .eventDefinitionId(definitionDto.map(EventDefinitionDto::id).orElse(UNKNOWN))
    //             .eventDefinitionType(definitionDto.map(d -> d.config().type()).orElse(UNKNOWN))
    //             .eventDefinitionTitle(definitionDto.map(EventDefinitionDto::title).orElse(UNKNOWN))
    //             .eventDefinitionDescription(definitionDto.map(EventDefinitionDto::description).orElse(UNKNOWN))
    //             .jobDefinitionId(jobTriggerDto.map(JobTriggerDto::jobDefinitionId).orElse(UNKNOWN))
    //             .jobTriggerId(jobTriggerDto.map(JobTriggerDto::id).orElse(UNKNOWN))
    //             .event(ctx.event())
    //             .backlog(backlog)
    //             // .backlogSize(backlog.size())
    //             .build();
    // }

}
