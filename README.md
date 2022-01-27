# AlertManagerNotify Plugin for Graylog

[![Build Status](https://github.com/strayge/graylog-plugin-alertmanagernotify/actions/workflows/build.yml/badge.svg)](https://github.com/strayge/graylog-plugin-alertmanagernotify/actions?query=branch%3Amaster+workflow%3Abuild)

This plugin can be used for connecting [Graylog](https://www.graylog.org/) alerts to the [Prometheus](https://prometheus.io/) [AlertManager](https://prometheus.io/docs/alerting/alertmanager/).

Similar to [Graylog AlertManager Notification Plugin](https://github.com/GDATASoftwareAG/Graylog-Plugin-AlertManager-Callback), but uses new Graylog API for notifications.

**Required Graylog version:** 4.x (tested only on 4.2.5)

Installation
------------

[Download the plugin](https://github.com/strayge/graylog-plugin-alertmanagernotify/releases)
and place the `.jar` file in your Graylog plugin directory. The plugin directory
is the `plugins/` folder relative from your `graylog-server` directory by default
and can be configured in your `graylog.conf` file.

Restart `graylog-server` and you are done.

Screenshots
-----------
![image](https://user-images.githubusercontent.com/2664578/151272305-5699394c-89de-40c3-a240-32201a99bd5b.png)

![image](https://user-images.githubusercontent.com/2664578/151272408-8592e929-0ef0-4f84-b42a-f4c2a41b818a.png)


Custom variables
----------------

Options allow use JMTE Templates in labels & annotations.

Allowed ones:
```
# config - plugin configuration (AlertManagerNotifyConfig)
config.api_url
config.alert_name
config.labels
config.annotations
config.grace

# context - info about event definition (EventNotificationModelData)
context.event_definition_id
context.event_definition_type
context.event_definition_title
context.event_definition_description
context.job_definition_id
context.job_trigger_id

# event - info about current event (EventDto)
event.id
event.event_definition_type
event.event_definition_id
event.origin_context
event.timestamp
event.timestamp_processing
event.timerange_start
event.timerange_end
event.streams
event.source_streams
event.message
event.source
event.key_tuple
event.key
event.priority
event.alert
event.fields
event.group_by_fields

node_id
backlog - matched messages
backlog_size - amount of messages in backlog
message - event.message without context.event_definition_title prefix
```

Development
-----------

Check [./development](./development/) for detailed build instructions.
