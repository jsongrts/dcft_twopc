package com.jsongrts.twopc.logger;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.json.JSONObject;

/**
 * LOG is a thin wrapper of Log4j2.
 */
public class LOG {
    private static boolean _inited = false;

    public static void init(JSONObject conf) {
        ConfigurationFactory.setConfigurationFactory(new LogConfigurationFactory(conf));
        _inited = true;
    }

    public static void init() {
        ConfigurationFactory.setConfigurationFactory(new LogConfigurationFactory());
        _inited = true;
    }

    public static void setContext(Object... params) {
        if (!_inited)
            init();

        ThreadContext.clearAll();
        addIntoContext(params);
    }

    public static void removeFromContext(String key) {
        if (!_inited)
            init();

        ThreadContext.remove(key);
    }

    public static void addIntoContext(Object... params) {
        if (!_inited)
            init();

        for (int i = 0; i < params.length / 2; i++) {
            String k = params[2 * i].toString();
            String v = params[2 * i + 1].toString();
            ThreadContext.put(k, v);
        }
    }

    public static Logger logger() {
        if (!_inited)
            init();

        return LogManager.getLogger(ThreadContext.get("component"));
    }
}