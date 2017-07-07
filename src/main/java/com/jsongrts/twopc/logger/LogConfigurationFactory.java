package com.jsongrts.twopc.logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.json.JSONObject;

import java.net.URI;

/**
 */
class LogConfigurationFactory extends ConfigurationFactory {
    private static final String _ROOT_LOGGER = "root";

    private static final String _DEFAULT_CONF = "{\n" +
            "    \"root\": {\n" +
            "        \"level\": \"info\"\n" +
            "    }\n" +
            "}";

    private static JSONObject _conf = null;

    LogConfigurationFactory() {
        _conf = new JSONObject(_DEFAULT_CONF);
    }

    LogConfigurationFactory(JSONObject jconf) {
        _conf = jconf;
        if (_conf.has(_ROOT_LOGGER)) {
            JSONObject rootLogger = new JSONObject();
            rootLogger.put("level", "info");
            _conf.put(_ROOT_LOGGER, rootLogger);
        }
    }

    static Configuration createConfiguration(final String name,
                                             ConfigurationBuilder<BuiltConfiguration> builder) {
        builder.setConfigurationName(name);
        builder.setStatusLevel(Level.ERROR);

        AppenderComponentBuilder appenderBuilder = builder.newAppender("Stdout", ConsoleAppender.PLUGIN_NAME).
                addAttribute("target", ConsoleAppender.Target.SYSTEM_OUT);
        appenderBuilder.add(builder.newLayout("PatternLayout").
                addAttribute("pattern", "%d{DEFAULT} [%p] [%c{1}] [%F:%M:%L] [%t:%T] %X %m%n"));
        builder.add(appenderBuilder);

        // create loggers for each logger defined in _conf
        for (Object logger : _conf.keySet()) {
            builder.add(builder.newRootLogger(_getLevel((String)logger)).add(builder.newAppenderRef("Stdout")));
        }

        return builder.build();
    }


    static private Level _getLevel(String logger) {
        JSONObject j = _conf.getJSONObject(logger);
        if (!j.has("level"))
            return Level.INFO;
        switch (j.getString("level")) {
            case "info":
                return Level.INFO;
            case "debug":
                return Level.DEBUG;
            case "warn":
                return Level.WARN;
            case "error":
                return Level.ERROR;
            case "fatal":
                return Level.FATAL;
            default:
                throw new IllegalArgumentException("Unknown logging level - " + j.getString("level"));
        }
    }

    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext, final ConfigurationSource source) {
        return getConfiguration(loggerContext, source.toString(), null);
    }


    @Override
    public Configuration getConfiguration(final LoggerContext loggerContext,
                                          final String name,
                                          final URI configLocation) {
        ConfigurationBuilder<BuiltConfiguration> builder = newConfigurationBuilder();
        return createConfiguration(name, builder);
    }


    @Override
    protected String[] getSupportedTypes() {
        return new String[] {"*"};
    }
}