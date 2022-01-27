package com.strayge;

import java.util.Collections;
import java.util.Set;

import org.graylog2.plugin.PluginConfigBean;
import org.graylog2.plugin.PluginModule;

/**
 * Extend the PluginModule abstract class here to add you plugin to the system.
 */
public class AlertManagerNotifyModule extends PluginModule {
    /**
     * Returns all configuration beans required by this plugin.
     *
     * Implementing this method is optional. The default method returns an empty {@link Set}.
     */
    @Override
    public Set<? extends PluginConfigBean> getConfigBeans() {
        return Collections.emptySet();
    }

    @Override
    protected void configure() {
        /*
         * Register your plugin types here.
         *
         */
        addNotificationType(
            AlertManagerNotifyConfig.TYPE_NAME,
            AlertManagerNotifyConfig.class,
            AlertManagerNotify.class,
            AlertManagerNotify.Factory.class
        );
    }
}
