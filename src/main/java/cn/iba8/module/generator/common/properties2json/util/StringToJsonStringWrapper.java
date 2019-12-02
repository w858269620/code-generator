package cn.iba8.module.generator.common.properties2json.util;

public class StringToJsonStringWrapper {

    private static final String JSON_STRING_SCHEMA = "\"%s\"";

    public static String wrap(String textToWrap) {
        return String.format(JSON_STRING_SCHEMA, textToWrap.replace("\"","\\\""));
    }

}
