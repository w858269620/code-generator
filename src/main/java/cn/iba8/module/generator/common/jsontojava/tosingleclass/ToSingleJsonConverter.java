package cn.iba8.module.generator.common.jsontojava.tosingleclass;

import cn.iba8.module.generator.common.jsontojava.converter.builder.JavaClassBuilder;
import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.ComplexPropertyType;
import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.PropertyType;
import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.SinglePropertyType;
import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.util.PropertyTypeFinder;
import cn.iba8.module.generator.common.jsontojava.exception.JsonToJavaException;
import cn.iba8.module.generator.common.jsontojava.validator.InputJsonValidator;
import cn.iba8.module.generator.common.jsontojava.validator.JsonType;
import cn.iba8.module.generator.common.jsontojava.validator.JsonTypeChecker;
import cn.iba8.module.generator.common.jsontojava.validator.JsonValidator;
import com.alibaba.fastjson.JSON;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.*;

public class ToSingleJsonConverter {

    private static JsonValidator jsonValidator = new InputJsonValidator();

    private static JsonTypeChecker jsonTypeChecker = new JsonType();

    public static void main(String[] args) throws Exception {
        InputStream originJsonIs = new FileInputStream(new File("D:\\tmp\\test\\origin.json"));
        byte[] originJsonBytes = new byte[originJsonIs.available()];
        originJsonIs.read(originJsonBytes);
        String originJson = new String(originJsonBytes);
        Map<String, ToSingleJsonClassBuilder> test = convertJsonToJava(originJson, "Test", "cn.iba8", true);
        ToSingleJsonClassBuilder root = test.get("Test");
        List<ToSingleJsonClassBuilder> innerBean = new ArrayList<>();
        Set<String> imports = new HashSet<>();
        test.keySet().forEach(r -> {
            ToSingleJsonClassBuilder toSingleJsonClassBuilder = test.get(r);
            if (!"Test".equals(r)) {
                innerBean.add(toSingleJsonClassBuilder);
            }
            imports.addAll(toSingleJsonClassBuilder.getImportedClasses());
        });
        String packageName = "cn.iba8";
        StringBuffer sb = new StringBuffer();
        sb.append("package ").append(packageName).append(JavaClassBuilder.END_STATEMENT)
                .append(JavaClassBuilder.DOUBLE_NEW_LINE);
        imports.forEach(s -> sb.append("import " + s + JavaClassBuilder.END_STATEMENT).append(JavaClassBuilder.NEW_LINE));
        System.out.println(JSON.toJSONString(sb));

    }

    protected static Map<String, ToSingleJsonClassBuilder> convertJsonToJava(String json, String objectName, String packageName, boolean withAnnotations) {
        if (jsonValidator.isValidJson(json)) {
            Map<String, ToSingleJsonClassBuilder> javaClasses = new HashMap<>();

            convert(javaClasses, json, objectName, packageName, withAnnotations);

            return javaClasses;
        } else {
            throw new JsonToJavaException("JSON Schema is not valid");
        }
    }

    private static ToSingleJsonClassBuilder convert(Map<String, ToSingleJsonClassBuilder> javaClasses, String json, String objectName, String packageName, boolean withAnnotations) {
        ToSingleJsonClassBuilder javaClassBuilder = null;
        if (jsonTypeChecker.isObject(json)) {
            javaClassBuilder = convertObject(javaClasses, new JSONObject(json), objectName, packageName, withAnnotations);
        } else if (jsonTypeChecker.isArray(json)) {
            javaClassBuilder = convertArray(javaClasses, new JSONArray(json), objectName, packageName, withAnnotations);
        }
        return javaClassBuilder;
    }

    private static ToSingleJsonClassBuilder convertObject(Map<String, ToSingleJsonClassBuilder> javaClasses, JSONObject jsonObject, String objectName, String packageName, boolean withAnnotations) {
        ToSingleJsonClassBuilder javaClassBuilder = new ToSingleJsonClassBuilder(objectName, packageName, withAnnotations);
        javaClasses.put(objectName, javaClassBuilder);

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value != null && !(!(value instanceof String) && "null".equals(value.toString()))) {
                PropertyType propertyType = PropertyTypeFinder.getPropertyType(value, jsonTypeChecker);
                if (!SinglePropertyType.NEW.equals(propertyType)) {
                    if (!javaClassBuilder.hasProperty(key)) {
                        addProperty(javaClasses, javaClassBuilder, key, propertyType, value, packageName, withAnnotations);
                    }
                } else {
                    ToSingleJsonClassBuilder propertyJavaNewClass = convert(javaClasses, value.toString(), objectName + ToSingleJsonClassBuilder.firstCharToUpperCase(key), packageName, withAnnotations);
                    javaClassBuilder.addProperty(key, propertyJavaNewClass.getClassName());
                }
            } else {
                javaClassBuilder.addProperty(key, SinglePropertyType.OBJECT.getDeclareName());
            }
        }
        return javaClassBuilder;
    }

    private static ToSingleJsonClassBuilder convertArray(Map<String, ToSingleJsonClassBuilder> javaClasses, JSONArray jsonArray, String objectName, String packageName, boolean withAnnotations) {
        ToSingleJsonClassBuilder javaClassBuilder = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            javaClassBuilder = convert(javaClasses, jsonArray.get(i).toString(), objectName, packageName, withAnnotations);
        }

        return javaClassBuilder;
    }

    private static void addProperty(Map<String, ToSingleJsonClassBuilder> javaClasses, ToSingleJsonClassBuilder javaClassBuilder, String key, PropertyType propertyType, Object value, String packageName, boolean withAnnotations) {
        if (propertyType instanceof ComplexPropertyType) {
            ComplexPropertyType complexPropertyType = (ComplexPropertyType) propertyType;
            javaClassBuilder.addProperty(key, complexPropertyType, findGenericForList(javaClasses, key, value, packageName, withAnnotations));
            if (complexPropertyType.hasToImport()) {
                javaClassBuilder.addImportStatement(complexPropertyType.getFqName());
            }
        } else {
            javaClassBuilder.addProperty(key, propertyType.getDeclareName());
        }
    }

    protected static String findGenericForList(Map<String, ToSingleJsonClassBuilder> javaClasses, String key, Object value, String packageName, boolean withAnnotations) {
        String type = SinglePropertyType.OBJECT.getDeclareName();

        HashSet<String> types = new HashSet<>();

        JSONArray jsonArray = new JSONArray(value.toString());
        for (int i = 0; i < jsonArray.length(); i++) {
            Object valueInList = jsonArray.get(i);
            PropertyType propertyType = PropertyTypeFinder.getPropertyType(valueInList, jsonTypeChecker);
            if (SinglePropertyType.NEW.equals(propertyType)) {
                String className = ToSingleJsonClassBuilder.firstCharToUpperCase(key);
                convert(javaClasses, value.toString(), className, packageName, withAnnotations);
                types.add(className);
            } else if (propertyType instanceof ComplexPropertyType) {
                types.add(String.format(propertyType.getDeclareName(), findGenericForList(javaClasses, key, valueInList, packageName, withAnnotations)));
            } else {
                types.add(propertyType.getDeclareName());
                if (types.size() > 1) {
                    return type;
                }
            }
        }

        if (types.size() == 1) {
            type = types.iterator().next();
        }
        return type;
    }

}
