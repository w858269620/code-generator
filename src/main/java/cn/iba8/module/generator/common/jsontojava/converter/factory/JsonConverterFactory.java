package cn.iba8.module.generator.common.jsontojava.converter.factory;

import cn.iba8.module.generator.common.jsontojava.converter.FileJsonConverter;
import cn.iba8.module.generator.common.jsontojava.converter.JsonConverter;
import cn.iba8.module.generator.common.jsontojava.converter.StringJsonConverter;
import cn.iba8.module.generator.common.jsontojava.file.FileReader;
import cn.iba8.module.generator.common.jsontojava.validator.JsonTypeChecker;
import cn.iba8.module.generator.common.jsontojava.validator.JsonValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JsonConverterFactory {

    private final FileReader fileReader;
    private final JsonValidator jsonValidator;
    private final JsonTypeChecker jsonTypeChecker;

    private Set<String> supportedExtensions = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("json", "txt")));

    public JsonConverterFactory(FileReader fileReader, JsonValidator jsonValidator, JsonTypeChecker jsonTypeChecker) {
        this.fileReader = fileReader;
        this.jsonValidator = jsonValidator;
        this.jsonTypeChecker = jsonTypeChecker;
    }
    
    public JsonConverter createJsonConverter(String json) {
        JsonConverter jsonConverter;

        if(hasExtension(json)) {
            jsonConverter = new FileJsonConverter(fileReader, jsonValidator, jsonTypeChecker);
        } else {
            jsonConverter = new StringJsonConverter(jsonValidator, jsonTypeChecker);
        }

        return jsonConverter;
    }

    private boolean hasExtension(String json) {
        String extension = json.substring(json.lastIndexOf('.') + 1);
        return !extension.isEmpty() && supportedExtensions.contains(extension);
    }
}
