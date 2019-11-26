package cn.iba8.module.generator.common.util;

import java.util.Map;

public abstract class Map2Properties {

    public static String convert(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        map.keySet().forEach(s -> {
            sb.append(s + "=" + map.get(s) + "\n");
        });
        return sb.toString();
    }

}
