package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.properties2json.util.PropertiesToJsonConverter;

import java.util.Properties;

public abstract class Properties2Json {

    public static String convert(Properties properties) {
        String json = new PropertiesToJsonConverter().convertToJson(properties);
        return json;
    }

}
