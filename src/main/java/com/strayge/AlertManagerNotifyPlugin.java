package com.strayge;

import java.util.Collection;
import java.util.Collections;

import org.graylog2.plugin.Plugin;
import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.PluginModule;

/**
 * Implement the Plugin interface here.
 */
public class AlertManagerNotifyPlugin implements Plugin {
    @Override
    public PluginMetaData metadata() {
        return new AlertManagerNotifyMetaData();
    }

    @Override
    public Collection<PluginModule> modules () {
        return Collections.<PluginModule>singletonList(new AlertManagerNotifyModule());
    }
}
