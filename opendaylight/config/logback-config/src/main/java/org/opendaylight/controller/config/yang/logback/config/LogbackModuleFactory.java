/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

/**
 * Generated file

 * Generated by: org.opendaylight.controller.config.yangjmxgenerator.plugin.JMXGenerator
 * Generated at: Wed Jul 17 15:26:45 CEST 2013
 *
 * Do not modifiy this file unless it is present under src/main directory
 */
package org.opendaylight.controller.config.yang.logback.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.LoggerComparator;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy;
import ch.qos.logback.core.rolling.TimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.opendaylight.controller.config.api.DependencyResolver;
import org.opendaylight.controller.config.api.DependencyResolverFactory;
import org.opendaylight.controller.config.api.ModuleIdentifier;
import org.osgi.framework.BundleContext;
import org.slf4j.LoggerFactory;

/**
*
*/
public class LogbackModuleFactory extends
        org.opendaylight.controller.config.yang.logback.config.AbstractLogbackModuleFactory {

    public static final String INSTANCE_NAME = "singleton";
    private Map<String, LoggerTO> loggersDTOs;
    private Map<String, RollingFileAppenderTO> rollingDTOs;
    private Map<String, ConsoleAppenderTO> consoleDTOs;
    private Map<String, FileAppenderTO> fileDTOs;

    @Override
    public LogbackModule instantiateModule(final String instanceName, final DependencyResolver dependencyResolver,
            final BundleContext bundleContext) {
        Preconditions.checkArgument(instanceName.equals(INSTANCE_NAME),
                "There should be just one instance of logback, named " + INSTANCE_NAME);
        prepareDTOs();
        LogbackModule module = new LogbackModule(new ModuleIdentifier(getImplementationName(), INSTANCE_NAME),
                dependencyResolver);
        module.setFileAppenderTO(Lists.newArrayList(fileDTOs.values()));
        module.setConsoleAppenderTO(Lists.newArrayList(consoleDTOs.values()));
        module.setRollingFileAppenderTO(Lists.newArrayList(rollingDTOs.values()));
        module.setLoggerTO(Lists.newArrayList(loggersDTOs.values()));
        return module;
    }

    @Override
    public LogbackModule instantiateModule(final String instanceName, final DependencyResolver dependencyResolver,
            final LogbackModule oldModule, final AutoCloseable oldInstance, final BundleContext bundleContext) {
        Preconditions.checkArgument(instanceName.equals(INSTANCE_NAME),
                "There should be just one instance of logback, named " + INSTANCE_NAME);
        prepareDTOs();
        LogbackModule module = new LogbackModule(new ModuleIdentifier(getImplementationName(), INSTANCE_NAME),
                dependencyResolver, oldModule, oldInstance);
        module.setConsoleAppenderTO(Lists.newArrayList(consoleDTOs.values()));
        /*
         * module.setJCloudsAppender(Lists.newArrayList(jcloudsDTOs.values()));
         */
        module.setFileAppenderTO(Lists.newArrayList(fileDTOs.values()));
        module.setRollingFileAppenderTO(Lists.newArrayList(rollingDTOs.values()));
        module.setLoggerTO(Lists.newArrayList(loggersDTOs.values()));
        return module;
    }

    private void prepareDTOs() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
        this.loggersDTOs = prepareLoggersDTOs(context);
        prepareAppendersDTOs(context);
    }

    private void prepareAppendersDTOs(final LoggerContext context) {
        this.rollingDTOs = new HashMap<>();
        this.consoleDTOs = new HashMap<>();
        this.fileDTOs = new HashMap<>();
        ch.qos.logback.core.rolling.RollingFileAppender<ILoggingEvent> rollingApp;
        ch.qos.logback.core.ConsoleAppender<ILoggingEvent> consoleApp;
        ch.qos.logback.core.FileAppender<ILoggingEvent> fileApp;
        Map<Logger, List<Appender<ILoggingEvent>>> appendersAll = new HashMap<>();
        for (Logger log : context.getLoggerList()) {
            List<Appender<ILoggingEvent>> appenders = new ArrayList<>();
            Iterator<Appender<ILoggingEvent>> iter = log.iteratorForAppenders();
            while (iter.hasNext()) {
                Appender<ILoggingEvent> element = iter.next();
                appenders.add(element);
            }
            appendersAll.put(log, appenders);
        }
        for (List<ch.qos.logback.core.Appender<ILoggingEvent>> appEntry : appendersAll.values()) {
            for (ch.qos.logback.core.Appender<ILoggingEvent> appender : appEntry) {
                if (appender instanceof ch.qos.logback.core.rolling.RollingFileAppender<?>) {
                    RollingFileAppenderTO app = new RollingFileAppenderTO();
                    rollingApp = (ch.qos.logback.core.rolling.RollingFileAppender<ILoggingEvent>) appender;
                    app.setAppend(rollingApp.isAppend());
                    PatternLayoutEncoder encoder = (PatternLayoutEncoder) rollingApp.getEncoder();
                    app.setEncoderPattern(encoder.getPattern());
                    app.setFileName(rollingApp.getFile());
                    if (rollingApp.getRollingPolicy() instanceof FixedWindowRollingPolicy) {
                        FixedWindowRollingPolicy rollingPolicy = (FixedWindowRollingPolicy) rollingApp
                                .getRollingPolicy();
                        app.setMaxIndex(rollingPolicy.getMaxIndex());
                        app.setMinIndex(rollingPolicy.getMinIndex());
                        app.setFileNamePattern(rollingPolicy.getFileNamePattern());
                        app.setRollingPolicyType("FixedWindowRollingPolicy");
                    } else if (rollingApp.getRollingPolicy() instanceof TimeBasedRollingPolicy<?>) {
                        TimeBasedRollingPolicy<ILoggingEvent> rollingPolicy = (TimeBasedRollingPolicy<ILoggingEvent>) rollingApp.getRollingPolicy();
                        app.setRollingPolicyType("TimeBasedRollingPolicy");
                        app.setFileNamePattern(rollingPolicy.getFileNamePattern());
                        app.setMaxHistory(rollingPolicy.getMaxHistory());
                        app.setCleanHistoryOnStart(rollingPolicy.isCleanHistoryOnStart());
                    }
                    SizeBasedTriggeringPolicy<ILoggingEvent> triggeringPolicy = (SizeBasedTriggeringPolicy<ILoggingEvent>) rollingApp
                            .getTriggeringPolicy();
                    app.setMaxFileSize(getMaxFileSize(triggeringPolicy).toString());
                    app.setName(rollingApp.getName());
                    this.rollingDTOs.put(rollingApp.getName(), app);
                } else if (appender instanceof ch.qos.logback.core.FileAppender<?>) {
                    FileAppenderTO app = new FileAppenderTO();
                    fileApp = (ch.qos.logback.core.FileAppender<ILoggingEvent>) appender;
                    app.setName(fileApp.getName());
                    app.setAppend(fileApp.isAppend());
                    app.setFileName(fileApp.getFile());
                    PatternLayoutEncoder encoder = (PatternLayoutEncoder) fileApp.getEncoder();
                    app.setEncoderPattern(encoder.getPattern());
                    this.fileDTOs.put(fileApp.getName(), app);
                }
                if (appender instanceof ch.qos.logback.core.ConsoleAppender<?>) {
                    ConsoleAppenderTO app = new ConsoleAppenderTO();
                    consoleApp = (ch.qos.logback.core.ConsoleAppender<ILoggingEvent>) appender;
                    consoleApp.getCopyOfAttachedFiltersList();
                    PatternLayoutEncoder encoder = (PatternLayoutEncoder) consoleApp.getEncoder();
                    app.setEncoderPattern(encoder.getPattern());
                    app.setName(consoleApp.getName());
                    app.setThresholdFilter(context.getLogger(Logger.ROOT_LOGGER_NAME).getEffectiveLevel().levelStr);
                    this.consoleDTOs.put(consoleApp.getName(), app);
                }
            }
        }
    }

    private Map<String, LoggerTO> prepareLoggersDTOs(final LoggerContext context) {
        Map<String, LoggerTO> DTOs = new HashMap<>();
        List<String> appenders = new ArrayList<>();
        List<org.slf4j.Logger> loggersToBeAdd = removeUnusableLoggers(context.getLoggerList(),
                context.getLogger(Logger.ROOT_LOGGER_NAME));
        for (org.slf4j.Logger log : loggersToBeAdd) {
            LoggerTO logger = new LoggerTO();
            if (((Logger) log).getLevel() != null) {
                logger.setLevel(((Logger) log).getLevel().levelStr);
            } else {
                logger.setLevel(((Logger) log).getEffectiveLevel().levelStr);
            }
            logger.setLoggerName(log.getName());
            Iterator<Appender<ILoggingEvent>> iter = ((Logger) log).iteratorForAppenders();
            while (iter.hasNext()) {
                Appender<ILoggingEvent> element = iter.next();
                appenders.add(element.getName());
            }
            logger.setAppenders(appenders);
            DTOs.put(log.getName(), logger);
            appenders = new ArrayList<>();

        }
        return DTOs;
    }

    private List<org.slf4j.Logger> removeUnusableLoggers(final List<Logger> loggerList, final Logger rootLogger) {
        Collections.sort(loggerList, new LoggerComparator());
        Map<String, org.slf4j.Logger> loggersToReturn = new HashMap<>();

        for (org.slf4j.Logger log : loggerList) {
            boolean shouldAdd = true;
            for (Entry<String, org.slf4j.Logger> entry : loggersToReturn.entrySet()) {
                if (StringUtils.contains(log.getName(), entry.getKey())) {
                    if (((Logger) log).getLevel() != null
                            && ((Logger) log).getLevel().equals(((Logger) entry.getValue()).getLevel())
                            && !((Logger) log).iteratorForAppenders().hasNext()) {
                        shouldAdd = false;
                        break;
                    }
                    if (((Logger) log).getLevel() == null
                            && ((Logger) log).getEffectiveLevel().equals(
                                    ((Logger) entry.getValue()).getEffectiveLevel())
                            && !((Logger) log).iteratorForAppenders().hasNext()) {
                        shouldAdd = false;
                        break;
                    }
                }
                if (((Logger) log).getLevel() != null && ((Logger) log).getLevel().equals(rootLogger.getLevel())
                        && !((Logger) log).iteratorForAppenders().hasNext()) {
                    shouldAdd = false;
                    break;
                }
                if (((Logger) log).getLevel() == null
                        && ((Logger) log).getEffectiveLevel().equals(rootLogger.getEffectiveLevel())
                        && !((Logger) log).iteratorForAppenders().hasNext()) {
                    shouldAdd = false;
                    break;
                }
            }
            if (shouldAdd) {
                loggersToReturn.put(log.getName(), log);
            }
        }
        return Lists.newArrayList(loggersToReturn.values());
    }

    @Override
    public Set<LogbackModule> getDefaultModules(final DependencyResolverFactory dependencyResolverFactory,
            final BundleContext bundleContext) {
        DependencyResolver resolver = dependencyResolverFactory.createDependencyResolver(new ModuleIdentifier(
                getImplementationName(), INSTANCE_NAME));
        LogbackModule defaultLogback = instantiateModule(INSTANCE_NAME, resolver, bundleContext);
        Set<LogbackModule> defaultModules = Sets.newHashSet(defaultLogback);
        return defaultModules;
    }

    // Ugly hack to deal with logback changing its api
    private static final Field MAX_FILE_SIZE_FIELD;
    static {
        Field f;
        try {
            f = SizeBasedTriggeringPolicy.class.getDeclaredField("maxFileSize");
            f.setAccessible(true);
        } catch (NoSuchFieldException | SecurityException e) {
            throw new ExceptionInInitializerError(e);
        }

        MAX_FILE_SIZE_FIELD = f;
    }

    @VisibleForTesting
    static FileSize getMaxFileSize(SizeBasedTriggeringPolicy<?> policy) {
        try {
            return (FileSize) MAX_FILE_SIZE_FIELD.get(policy);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot get maxFileSize field", e);
        }
    }
}
