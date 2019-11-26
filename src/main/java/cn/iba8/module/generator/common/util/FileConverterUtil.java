package cn.iba8.module.generator.common.util;

import java.util.Map;

public abstract class FileConverterUtil {

    public static String yml2json(String yml) {
        String properties = yml2properties(yml);
        String json = Properties2Json.convert(properties);
        return json;
    }

    public static String yml2properties(String yml) {
        String properties = Yml2Properties.convert2properties(yml);
        return properties;
    }

    public static String properties2json(String properties) {
        String json = Properties2Json.convert(properties);
        return json;
    }

    public static String properties2yml(String properties) {
        String yml = Properties2Yml.convert(properties);
        return yml;
    }

    public static Map<String, String> properties2Map(String properties) {
        Map<String, String> stringMap = Properties2Map.converter(properties);
        return stringMap;
    }

    public static Map<String, Object> json2map(String json) {
        Map<String, Object> json2map = Json2Map.jsonToMap(json);
        return json2map;
    }

    public static String json2properties(String json) {
        Map<String, Object> stringObjectMap = json2map(json);
        String properties = Map2Properties.convert(stringObjectMap);
        return properties;
    }

    public static String json2yml(String json) {
        String properties = json2properties(json);
        String yml = properties2yml(properties);
        return yml;
    }

}
