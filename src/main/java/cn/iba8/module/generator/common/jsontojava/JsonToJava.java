package cn.iba8.module.generator.common.jsontojava;

import cn.iba8.module.generator.common.jsontojava.constants.JsonToJavaConstants;
import cn.iba8.module.generator.common.jsontojava.converter.JsonConverter;
import cn.iba8.module.generator.common.jsontojava.converter.factory.JsonConverterFactory;
import cn.iba8.module.generator.common.jsontojava.converter.JsonClassResult;
import cn.iba8.module.generator.common.jsontojava.exception.JsonToJavaException;
import cn.iba8.module.generator.common.jsontojava.file.FileReader;
import cn.iba8.module.generator.common.jsontojava.file.FileSaver;
import cn.iba8.module.generator.common.jsontojava.file.JavaFileSaver;
import cn.iba8.module.generator.common.jsontojava.file.JsonFileReader;
import cn.iba8.module.generator.common.jsontojava.validator.InputJsonValidator;
import cn.iba8.module.generator.common.jsontojava.validator.JsonType;
import cn.iba8.module.generator.common.jsontojava.validator.JsonTypeChecker;
import cn.iba8.module.generator.common.jsontojava.validator.JsonValidator;

import java.util.List;

public class JsonToJava {

    private final JsonConverterFactory jsonConverterFactory;
    private final FileSaver fileSaver;
    private JsonConverter jsonConverter;

    public JsonToJava() {
        this.jsonConverterFactory = new JsonConverterFactory(new JsonFileReader(), new InputJsonValidator(new JsonType()), new JsonType());
        this.fileSaver = new JavaFileSaver();
    }

    /**
     *
     * @param fileReader default {@link JsonFileReader}
     * @param jsonValidator default {@link InputJsonValidator}
     * @param typeChecker default {@link JsonType}
     * @param fileSaver default {@link JavaFileSaver}
     */
    public JsonToJava(FileReader fileReader, JsonValidator jsonValidator, JsonTypeChecker typeChecker, FileSaver fileSaver) {
        this.jsonConverterFactory = new JsonConverterFactory(fileReader, jsonValidator, typeChecker);
        this.fileSaver = fileSaver;
    }

    public JsonToJava(FileReader fileReader, JsonValidator jsonValidator, JsonTypeChecker jsonTypeChecker) {
        this.jsonConverterFactory = new JsonConverterFactory(fileReader, jsonValidator, jsonTypeChecker);
        this.fileSaver = null;
    }

    public void jsonToJava(String json, String objectName, String packageName, String outputDir) {
        jsonToJava(json, objectName, packageName, outputDir, JsonToJavaConstants.DEFAULT_FOR_WITH_ANNOTATIONS);
    }

    public List<JsonClassResult> jsonToJava(String json, String objectName, String packageName){
        return jsonToJava(json, objectName, packageName, JsonToJavaConstants.DEFAULT_FOR_WITH_ANNOTATIONS);
    }

     /***
     *
     * @param json json file of json string to be processed
     * @param objectName root class name
     * @param packageName package of generated classes
     * @param outputDir directory to save json files
     * @param withAnnotations turn off/on jackson annotations. This is enabled by default.
     */
    public void jsonToJava(String json, String objectName, String packageName, String outputDir, boolean withAnnotations) {
        initializeJsonConverter(json);

        List<JsonClassResult> javaClassResult = jsonConverter.convertToJava(json, objectName, packageName, withAnnotations);
        if(fileSaver != null){
            javaClassResult
                    .parallelStream()
                    .forEach(classResult -> fileSaver.saveJavaFile(classResult.getClassDeclaration(), classResult.getClassName(), outputDir));
        } else {
            throw new JsonToJavaException("No instance of FileSaver found");
        }
    }

    /***
     *
     * @param json json file of json string to be processed
     * @param objectName root class name
     * @param packageName package of generated classes
     * @param withAnnotations turn off/on jackson annotations. This is enabled by default.
     * @return list of {@link JsonClassResult} objects
     */
    public List<JsonClassResult> jsonToJava(String json, String objectName, String packageName, boolean withAnnotations){
        initializeJsonConverter(json);
        return jsonConverter.convertToJava(json, objectName, packageName, withAnnotations);
    }

    private synchronized void initializeJsonConverter(String json) {
        if(jsonConverter ==  null) {
            jsonConverter = jsonConverterFactory.createJsonConverter(json);
        }
    }
}
