package cn.iba8.module.generator.common.util;

import java.util.Map;
import java.util.Properties;

public abstract class Map2Properties {

    public static Properties convert(Map<String, Object> map) {
        Properties properties = new Properties();
        map.keySet().forEach(s -> properties.put(s, map.get(s)));
        return properties;
    }

    public static Properties convertString(Map<String, String> map) {
        Properties properties = new Properties();
        map.keySet().forEach(s -> properties.put(s, map.get(s)));
        return properties;
    }

}
