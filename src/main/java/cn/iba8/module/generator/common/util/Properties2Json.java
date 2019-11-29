package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.properties2json.util.PropertiesToJsonConverter;

import java.util.Map;

public abstract class Properties2Json {

    private static final String BRACE_LEFT = "\\{";
    private static final String BRACE_RIGHT = "}";
    private static final String COMMA = ",";
    private static final String BRACE_LEFT_REPLACEMENT = "AAAA555";
    private static final String BRACE_RIGHT_REPLACEMENT = "BBBB333";
    private static final String COMMA_REPLACEMENT = "CCCC666";

    public static String convert(String properties) {
        properties = properties.replaceAll(BRACE_LEFT, BRACE_LEFT_REPLACEMENT);
        properties = properties.replaceAll(BRACE_RIGHT, BRACE_RIGHT_REPLACEMENT);
        properties = properties.replaceAll(COMMA, COMMA_REPLACEMENT);
        Map<String, String> converter = Properties2Map.converter(properties);
        String json = new PropertiesToJsonConverter().convertToJson(converter);
        json = json.replaceAll(BRACE_LEFT_REPLACEMENT, BRACE_LEFT);
        json = json.replaceAll(BRACE_RIGHT_REPLACEMENT, BRACE_RIGHT);
        json = json.replaceAll(COMMA_REPLACEMENT, COMMA);
        return json;
    }

}
