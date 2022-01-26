package com.strayge;

import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.ServerStatus;
import org.graylog2.plugin.Version;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

/**
 * Implement the PluginMetaData interface here.
 */
public class AlertManagerNotifyMetaData implements PluginMetaData {
    private static final String PLUGIN_PROPERTIES = "com.strayge.graylog-plugin-alertmanagernotify/graylog-plugin.properties";

    @Override
    public String getUniqueId() {
        return "com.strayge.AlertManagerNotifyPlugin";
    }

    @Override
    public String getName() {
        return "AlertManagerNotify";
    }

    @Override
    public String getAuthor() {
        return "strayge";
    }

    @Override
    public URI getURL() {
        return URI.create("https://github.com/strayge/graylog-plugin-alertmanagernotify");
    }

    @Override
    public Version getVersion() {
        return Version.fromPluginProperties(getClass(), PLUGIN_PROPERTIES, "version", Version.from(0, 0, 0, "unknown"));
    }

    @Override
    public String getDescription() {
        return "Send alerts to the Prometheus AlertManager API.";
    }

    @Override
    public Version getRequiredVersion() {
        return Version.fromPluginProperties(getClass(), PLUGIN_PROPERTIES, "graylog.version", Version.from(0, 0, 0, "unknown"));
    }

    @Override
    public Set<ServerStatus.Capability> getRequiredCapabilities() {
        return Collections.emptySet();
    }
}
