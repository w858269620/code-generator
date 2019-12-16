package cn.iba8.module.generator.common.jsontojava.converter;

import cn.iba8.module.generator.common.jsontojava.constants.JsonToJavaConstants;
import cn.iba8.module.generator.common.jsontojava.file.FileReader;
import cn.iba8.module.generator.common.jsontojava.file.JsonFileReader;
import cn.iba8.module.generator.common.jsontojava.validator.InputJsonValidator;
import cn.iba8.module.generator.common.jsontojava.validator.JsonType;
import cn.iba8.module.generator.common.jsontojava.validator.JsonTypeChecker;
import cn.iba8.module.generator.common.jsontojava.validator.JsonValidator;

import java.util.List;

public class FileJsonConverter extends AbstractJsonConverter{

    private final FileReader fileReader;
    private final JsonValidator jsonValidator;
    private final JsonTypeChecker jsonTypeChecker;

    /***
     * Defaults
     * @param fileReader default: {@link JsonFileReader}
     * @param jsonValidator default: {@link InputJsonValidator}
     * @param jsonTypeChecker default: {@link JsonType}
     */
    public FileJsonConverter(FileReader fileReader, JsonValidator jsonValidator, JsonTypeChecker jsonTypeChecker) {
        this.fileReader = fileReader;
        this.jsonValidator = jsonValidator;
        this.jsonTypeChecker = jsonTypeChecker;
    }

    public FileJsonConverter() {
        this.fileReader = new JsonFileReader();
        this.jsonValidator = new InputJsonValidator();
        this.jsonTypeChecker = new JsonType();
    }

    /**
     * converts a json path (json in given filepath) to a java class (and child classes)
     * given a json that contains an array the first item in the array will be parsed
     * @param jsonPath path to jsonFile
     * @param objectName name of the root class name
     * @param withAnnotations specify weather class should be generated with json annotations.
     * @return a list of {@link JsonClassResult} objects
     */
    @Override
    public List<JsonClassResult> convertToJava(String jsonPath, String objectName, String packageName, boolean withAnnotations) {
        String jsonString = fileReader.readJsonFromFile(jsonPath);
        return convertJsonToJava(jsonString, objectName, packageName, withAnnotations);
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
