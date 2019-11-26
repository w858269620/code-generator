package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.properties2json.util.PropertiesToJsonConverter;

import java.util.Map;

public abstract class Properties2Json {

    public static String convert(String properties) {
        Map<String, String> converter = Properties2Map.converter(properties);
        String json = new PropertiesToJsonConverter().convertToJson(converter);
        return json;
    }

}
