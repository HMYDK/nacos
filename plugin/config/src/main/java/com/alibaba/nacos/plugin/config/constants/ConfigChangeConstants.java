/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.plugin.config.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Config change plugin service constants.
 *
 * @author liyunfei
 */
public class ConfigChangeConstants {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigChangeConstants.class);
    
    /**
     * The relationship of  config change plugin service name and its pointcuts.
     */
    private static final Map<String, ConfigChangePointCutTypes[]> POINTCUTS_MAP = new ConcurrentHashMap<String, ConfigChangePointCutTypes[]>();
    
    public static final String NACOS_CORE_CONFIG_PLUGIN_PREFIX = "nacos.core.config.plugin.";
    
    public static final String POINT_CUT_NAME = "CONFIG_CHANGE_POINT_CUT_TYPES_TYPES";
    
    public static final String PLUGIN_PROPERTIES = "pluginProperties";
    
    /**
     * The actual config method args.
     */
    public static final String ORIGINAL_ARGS = "originalArgs";
    
    private static Integer pluginServiceCount = 0;
    
    // Load config pointcuts to each config change plugin services.
    static {
        Class[] innerClasses = ConfigChangeConstants.class.getDeclaredClasses();
        for (Class clazz : innerClasses) {
            try {
                POINTCUTS_MAP.put(clazz.getSimpleName().toLowerCase(Locale.ROOT),
                        (ConfigChangePointCutTypes[]) clazz.getField(POINT_CUT_NAME).get(null));
                pluginServiceCount++;
            } catch (NoSuchFieldException | IllegalAccessException e) {
                LOGGER.warn(
                        "[{}] Load config change plugin service to relevant pointcuts failed,please check {} at {} ",
                        ConfigChangeConstants.class, POINT_CUT_NAME, clazz);
            }
        }
    }
    
    public static ConfigChangePointCutTypes[] getPointcuts(String serviceType) {
        return POINTCUTS_MAP.get(serviceType);
    }
    
    public static Integer getPluginServiceCount() {
        return pluginServiceCount;
    }
}

