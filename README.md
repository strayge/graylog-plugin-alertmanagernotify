# AlertManagerNotify Plugin for Graylog

[![Build Status](https://github.com/strayge/graylog-plugin-alertmanagernotify/actions/workflows/build.yml/badge.svg)](https://github.com/strayge/graylog-plugin-alertmanagernotify/actions?query=branch%3Amaster+workflow%3Abuild)

This plugin can be used for connecting [Graylog](https://www.graylog.org/) alerts to the [Prometheus](https://prometheus.io/) [AlertManager](https://prometheus.io/docs/alerting/alertmanager/).

**Required Graylog version:** 4.0 and later

Installation
------------

[Download the plugin](https://github.com/strayge/graylog-plugin-alertmanagernotify/releases)
and place the `.jar` file in your Graylog plugin directory. The plugin directory
is the `plugins/` folder relative from your `graylog-server` directory by default
and can be configured in your `graylog.conf` file.

Restart `graylog-server` and you are done.

Development
-----------

Check [./development](./development/) for detailed build instructions.
