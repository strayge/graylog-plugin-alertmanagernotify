package com.strayge;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import org.graylog.events.contentpack.entities.EventNotificationConfigEntity;
import org.graylog.events.notifications.EventNotificationConfig;
import org.graylog2.contentpacks.model.entities.EntityDescriptor;
import org.graylog2.contentpacks.model.entities.references.ValueReference;

@AutoValue
@JsonTypeName(AlertManagerNotifyConfigEntity.TYPE_NAME)
@JsonDeserialize(builder = AlertManagerNotifyConfigEntity.Builder.class)
public abstract class AlertManagerNotifyConfigEntity implements EventNotificationConfigEntity {
    public static final String TYPE_NAME = "alertmanagernotify-v1";

    public static Builder builder() {
        return Builder.create();
    }

    public abstract Builder toBuilder();

    @AutoValue.Builder
    public static abstract class Builder implements EventNotificationConfigEntity.Builder<Builder> {

        @JsonCreator
        public static Builder create() {
            return new AutoValue_AlertManagerNotifyConfigEntity.Builder().type(TYPE_NAME);
        }

        public abstract AlertManagerNotifyConfigEntity build();
    }

    @Override
    public EventNotificationConfig toNativeEntity(Map<String, ValueReference> parameters, Map<EntityDescriptor, Object> nativeEntities) {
        return AlertManagerNotifyConfig.builder().build();
    }
}
