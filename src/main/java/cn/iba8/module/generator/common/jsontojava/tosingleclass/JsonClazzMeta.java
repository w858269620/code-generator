package cn.iba8.module.generator.common.jsontojava.tosingleclass;

import cn.iba8.module.generator.common.jsontojava.converter.JsonClassResult;
import cn.iba8.module.generator.common.jsontojava.converter.builder.JavaClassBuilder;
import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.PropertyType;
import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.SinglePropertyType;
import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.util.PropertyTypeFinder;
import cn.iba8.module.generator.common.jsontojava.exception.JsonToJavaException;
import cn.iba8.module.generator.common.jsontojava.validator.InputJsonValidator;
import cn.iba8.module.generator.common.jsontojava.validator.JsonType;
import cn.iba8.module.generator.common.jsontojava.validator.JsonValidator;
import lombok.Data;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class JsonClazzMeta {

    private String fieldName;

    private PropertyType propertyType;

    private List<JsonClazzMeta> children = new ArrayList<>();

    public static JsonClazzMeta ofJson(String json) {
        return null;
    }

    private final JsonType jsonType = new JsonType();

    private final JsonValidator jsonValidator = new InputJsonValidator();

    public void parseJson(String json, boolean isRoot) {
        if(jsonValidator.isValidJson(json)){
            if (isRoot) {
                JsonClazzMeta jsonClazzMeta = new JsonClazzMeta();
            }
            JSONObject jsonObject = new JSONObject(json);
            for(String key : jsonObject.keySet()) {
                Object value = jsonObject.get(key);
                if(value != null && !(!(value instanceof String) && "null".equals(value.toString()))) {
                    PropertyType propertyType = PropertyTypeFinder.getPropertyType(value, jsonType);
                    if(!SinglePropertyType.NEW.equals(propertyType)) {

                    } else {

                    }
                } else {

                }
            }
        } else {
            throw new JsonToJavaException("JSON Schema is not valid");
        }

    }

}
