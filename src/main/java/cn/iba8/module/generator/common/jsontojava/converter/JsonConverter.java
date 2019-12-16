package cn.iba8.module.generator.common.jsontojava.converter;

import java.util.List;

public interface JsonConverter {

    List<JsonClassResult> convertToJava(String json, String objectName, String packageName, boolean withAnnotations);

    List<JsonClassResult> convertToJava(String json, String objectName, String packageName);
}
