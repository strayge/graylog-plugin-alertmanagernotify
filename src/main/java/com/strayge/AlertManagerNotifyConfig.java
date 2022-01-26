package com.strayge;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;
import org.graylog.events.event.EventDto;
import org.graylog.events.notifications.EventNotificationConfig;
import org.graylog.events.notifications.EventNotificationExecutionJob;
import org.graylog.scheduler.JobTriggerData;
import org.graylog2.contentpacks.EntityDescriptorIds;
import org.graylog2.plugin.rest.ValidationResult;

@AutoValue
@JsonTypeName(AlertManagerNotifyConfig.TYPE_NAME)
@JsonDeserialize(builder = AlertManagerNotifyConfig.Builder.class)
public abstract class AlertManagerNotifyConfig implements EventNotificationConfig {
    public static final String TYPE_NAME = "alertmanagernotify-v1";

    private static final String FIELD_API_URL = "api_url";
    private static final String FIELD_ALERT_NAME = "alert_name";
    private static final String FIELD_LABELS = "labels";
    private static final String FIELD_ANNOTATIONS = "annotations";
    private static final String FIELD_GRACE = "grace";

    @JsonProperty(FIELD_API_URL)
    public abstract String apiUrl();

    @JsonProperty(FIELD_ALERT_NAME)
    public abstract String alertName();

    @JsonProperty(FIELD_LABELS)
    public abstract String labels();

    @JsonProperty(FIELD_ANNOTATIONS)
    public abstract String annotations();

    @JsonProperty(FIELD_GRACE)
    public abstract String grace();

    @Override @JsonIgnore
    public JobTriggerData toJobTriggerData(EventDto dto) {
        return EventNotificationExecutionJob.Data.builder().eventDto(dto).build();
    }

    public static Builder builder() {
        return Builder.create();
    }

    @Override @JsonIgnore
    public ValidationResult validate() {
        final ValidationResult validation = new ValidationResult();
        return validation;
    }

    @AutoValue.Builder
    public static abstract class Builder implements EventNotificationConfig.Builder<Builder> {
        @JsonCreator
        public static Builder create() {
            return new AutoValue_AlertManagerNotifyConfig.Builder().type(TYPE_NAME);
        }

        @JsonProperty(FIELD_API_URL)
        public abstract Builder apiUrl(String apiUrl);

        @JsonProperty(FIELD_ALERT_NAME)
        public abstract Builder alertName(String alertName);

        @JsonProperty(FIELD_LABELS)
        public abstract Builder labels(String labels);

        @JsonProperty(FIELD_ANNOTATIONS)
        public abstract Builder annotations(String annotations);

        @JsonProperty(FIELD_GRACE)
        public abstract Builder grace(String grace);

        public abstract AlertManagerNotifyConfig build();
    }

    @Override
    public AlertManagerNotifyConfigEntity toContentPackEntity(EntityDescriptorIds entityDescriptorIds) {
        return AlertManagerNotifyConfigEntity.builder().build();
    }

}
