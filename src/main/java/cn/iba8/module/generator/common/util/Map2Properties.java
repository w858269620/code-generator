package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.CodeGenerateConstants;

import java.util.Map;

public abstract class Map2Properties {

    public static String convert(Map<String, Object> map) {
        StringBuffer sb = new StringBuffer();
        map.keySet().forEach(s -> sb.append(s + "=" + map.get(s) + CodeGenerateConstants.SPLITOR_PROPERTIES));
        return sb.toString();
    }

    public static String convertString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        map.keySet().forEach(s -> sb.append(s + "=" + map.get(s) + CodeGenerateConstants.SPLITOR_PROPERTIES));
        return sb.toString();
    }

}
