package cn.iba8.module.generator.common.jsontojava.converter;

import cn.iba8.module.generator.common.jsontojava.constants.JsonToJavaConstants;
import cn.iba8.module.generator.common.jsontojava.validator.InputJsonValidator;
import cn.iba8.module.generator.common.jsontojava.validator.JsonType;
import cn.iba8.module.generator.common.jsontojava.validator.JsonTypeChecker;
import cn.iba8.module.generator.common.jsontojava.validator.JsonValidator;

import java.util.List;

public class StringJsonConverter extends AbstractJsonConverter{

    private final JsonValidator jsonValidator;
    private final JsonTypeChecker jsonTypeChecker;

    /**
     *
     * @param jsonValidator default: {@link InputJsonValidator}
     * @param jsonTypeChecker default: {@link JsonType}
     */
    public StringJsonConverter(JsonValidator jsonValidator, JsonTypeChecker jsonTypeChecker) {
        this.jsonValidator = jsonValidator;
        this.jsonTypeChecker = jsonTypeChecker;
    }

    public StringJsonConverter() {
        jsonValidator = new InputJsonValidator();
        jsonTypeChecker = new JsonType();
    }

    /**
     * converts a json string to a java class (and child classes)
     * given a json that contains an array the first item in the array will be parsed
     * @param json json string to be converted into java classes
     * @param objectName name of the root class name
     * @param withAnnotations specify weather class should be generated with json annotations.
     * @return a list of {@link JsonClassResult} objects
     */
    @Override
    public List<JsonClassResult> convertToJava(String json, String objectName, String packageName, boolean withAnnotations) {
        return convertJsonToJava(json, objectName, packageName, withAnnotations);
    }

    @Override
    public List<JsonClassResult> convertToJava(String json, String objectName, String packageName) {
        return convertToJava(json, objectName, packageName, JsonToJavaConstants.DEFAULT_FOR_WITH_ANNOTATIONS);
    }

    @Override
    protected JsonValidator jsonValidator() {
        return jsonValidator;
    }

    @Override
    protected JsonTypeChecker jsonTypeChecker() {
        return jsonTypeChecker;
    }
}
