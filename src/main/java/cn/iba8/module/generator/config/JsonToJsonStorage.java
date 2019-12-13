package cn.iba8.module.generator.config;

import cn.iba8.module.generator.common.util.Properties2Map;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JsonToJsonStorage {

    public static ConcurrentHashMap<String, Map<String, String>> json2jsonStorage = new ConcurrentHashMap<>();

    public static void load(String type, String properties) {
        if (StringUtils.isBlank(type)) {
            return;
        }
        if (null == properties) {
            return;
        }
        Map<String, String> converter = Properties2Map.converter(properties);
        json2jsonStorage.put(type, converter);
    }

}
