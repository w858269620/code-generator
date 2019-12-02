package cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils;

import cn.iba8.module.generator.common.properties2json.Constants;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.ArrayJsonType;
import cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType;
import cn.iba8.module.generator.common.properties2json.object.ObjectJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.BooleanToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.NumberToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToConcreteObjectResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToNumberResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToStringResolver;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.ArrayJsonType;
import cn.iba8.module.generator.common.properties2json.object.ObjectJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.BooleanToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.NumberToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.ObjectToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToBooleanResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToConcreteObjectResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToNumberResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static cn.iba8.module.generator.common.properties2json.Constants.ARRAY_END_SIGN;
import static cn.iba8.module.generator.common.properties2json.Constants.ARRAY_START_SIGN;
import static cn.iba8.module.generator.common.properties2json.Constants.JSON_OBJECT_END;
import static cn.iba8.module.generator.common.properties2json.Constants.JSON_OBJECT_START;
import static cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType.NULL_OBJECT;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.NullToJsonTypeConverter.NULL_TO_JSON_RESOLVER;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.StringToJsonTypeConverter.STRING_TO_JSON_RESOLVER;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToNumberResolver.convertToNumber;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToStringResolver.TO_STRING_RESOLVER;

public class JsonObjectHelper {

    private static final PrimitiveJsonTypesResolver primitiveJsonTypesResolver;
    private static final JsonParser jp = new JsonParser();
    private static final Gson gson = new Gson();

    static {
        List<ObjectToJsonTypeConverter> toJsonResolvers = new ArrayList<>();
        toJsonResolvers.add(new NumberToJsonTypeConverter());
        toJsonResolvers.add(new BooleanToJsonTypeConverter());
        toJsonResolvers.add(STRING_TO_JSON_RESOLVER);

        List<TextToConcreteObjectResolver> toObjectsResolvers = new ArrayList<>();
        toObjectsResolvers.add(new TextToNumberResolver());
        toObjectsResolvers.add(new TextToBooleanResolver());
        toObjectsResolvers.add(TextToStringResolver.TO_STRING_RESOLVER);
        primitiveJsonTypesResolver = new PrimitiveJsonTypesResolver(toObjectsResolvers, toJsonResolvers, false, NULL_TO_JSON_RESOLVER);
    }

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static JsonElement toJsonElement(String json) {
        return jp.parse(json);
    }

    public static ObjectJsonType createObjectJsonType(JsonElement parsedJson, String propertyKey) {
        ObjectJsonType objectJsonType = new ObjectJsonType();
        JsonObject asJsonObject = parsedJson.getAsJsonObject();
        for(Map.Entry<String, JsonElement> entry : asJsonObject.entrySet()) {
            JsonElement someField = entry.getValue();
            AbstractJsonType valueOfNextField = convertToAbstractJsonType(someField, propertyKey);
            objectJsonType.addField(entry.getKey(), valueOfNextField, null);
        }
        return objectJsonType;
    }

    public static AbstractJsonType convertToAbstractJsonType(JsonElement someField, String propertyKey) {
        AbstractJsonType valueOfNextField = null;
        if(someField.isJsonNull()) {
            valueOfNextField = JsonNullReferenceType.NULL_OBJECT;
        }
        if(someField.isJsonObject()) {
            valueOfNextField = createObjectJsonType(someField, propertyKey);
        }
        if(someField.isJsonArray()) {
            valueOfNextField = createArrayJsonType(someField, propertyKey);
        }
        if(someField.isJsonPrimitive()) {
            JsonPrimitive jsonPrimitive = someField.getAsJsonPrimitive();
            if(jsonPrimitive.isString()) {
                valueOfNextField = primitiveJsonTypesResolver.resolvePrimitiveTypeAndReturn(jsonPrimitive.getAsString(), propertyKey);
            } else if(jsonPrimitive.isNumber()) {
                String numberAsText = jsonPrimitive.getAsNumber().toString();
                valueOfNextField = primitiveJsonTypesResolver.resolvePrimitiveTypeAndReturn(TextToNumberResolver.convertToNumber(numberAsText), propertyKey);
            } else if(jsonPrimitive.isBoolean()) {
                valueOfNextField = primitiveJsonTypesResolver.resolvePrimitiveTypeAndReturn(jsonPrimitive.getAsBoolean(), propertyKey);
            }
        }
        return valueOfNextField;
    }

    public static ArrayJsonType createArrayJsonType(JsonElement parsedJson, String propertyKey) {
        ArrayJsonType arrayJsonType = new ArrayJsonType();
        JsonArray asJsonArray = parsedJson.getAsJsonArray();
        int index = 0;
        for(JsonElement element : asJsonArray) {
            arrayJsonType.addElement(index, convertToAbstractJsonType(element, propertyKey), null);
            index++;
        }
        return arrayJsonType;
    }

    public static boolean isValidJsonObjectOrArray(String propertyValue) {
        if(hasJsonObjectSignature(propertyValue) || hasJsonArraySignature(propertyValue)) {
            JsonParser jp = new JsonParser();
            try {
                jp.parse(propertyValue);
                return true;
            } catch(Exception ex) {
                return false;
            }
        }
        return false;
    }

    public static boolean hasJsonArraySignature(String propertyValue) {
        return hasJsonSignature(propertyValue.trim(), Constants.ARRAY_START_SIGN, Constants.ARRAY_END_SIGN);
    }

    public static boolean hasJsonObjectSignature(String propertyValue) {
        return hasJsonSignature(propertyValue.trim(), Constants.JSON_OBJECT_START, Constants.JSON_OBJECT_END);
    }

    private static boolean hasJsonSignature(String propertyValue, String startSign, String endSign) {
        return firsLetter(propertyValue).contains(startSign) && lastLetter(propertyValue).contains(endSign);
    }

    private static String firsLetter(String text) {
        return text.substring(0, 1);
    }

    private static String lastLetter(String text) {
        return text.substring(text.length() - 1);
    }
}
