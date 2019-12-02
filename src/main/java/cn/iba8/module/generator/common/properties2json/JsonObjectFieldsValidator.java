package cn.iba8.module.generator.common.properties2json;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.ArrayJsonType;
import cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType;
import cn.iba8.module.generator.common.properties2json.object.MergableObject;
import cn.iba8.module.generator.common.properties2json.object.ObjectJsonType;
import cn.iba8.module.generator.common.properties2json.object.PrimitiveJsonType;
import cn.iba8.module.generator.common.properties2json.path.PathMetadata;
import cn.iba8.module.generator.common.properties2json.util.exception.CannotOverrideFieldException;

public class JsonObjectFieldsValidator {

    public static void checkThatFieldCanBeSet(ObjectJsonType currentObjectJson, PathMetadata currentPathMetaData, String propertyKey) {
        if(currentObjectJson.containsField(currentPathMetaData.getFieldName())) {
            AbstractJsonType abstractJsonType = currentObjectJson.getField(currentPathMetaData.getFieldName());
            if(currentPathMetaData.isArrayField()) {
                if(isArrayJson(abstractJsonType)) {
                    ArrayJsonType jsonArray = currentObjectJson.getJsonArray(currentPathMetaData.getFieldName());
                    AbstractJsonType elementByDimArray = jsonArray.getElementByGivenDimIndexes(currentPathMetaData);
                    if(elementByDimArray != null) {
                        throwErrorWhenCannotMerge(currentPathMetaData, propertyKey, elementByDimArray);
                    }
                } else {
                    throw new CannotOverrideFieldException(currentPathMetaData.getCurrentFullPathWithoutIndexes(), abstractJsonType, propertyKey);
                }
            } else {
                throwErrorWhenCannotMerge(currentPathMetaData, propertyKey, abstractJsonType);
            }
        }
    }

    private static void throwErrorWhenCannotMerge(PathMetadata currentPathMetaData, String propertyKey, AbstractJsonType oldJsonValue) {
        if (!isMergableJsonType(oldJsonValue) ) {
            throw new CannotOverrideFieldException(currentPathMetaData.getCurrentFullPath(), oldJsonValue, propertyKey);
        }
    }

    public static void checkEarlierWasJsonObject(String propertyKey, PathMetadata currentPathMetaData, AbstractJsonType jsonType) {
         if (!isObjectJson(jsonType)) {
             throw new CannotOverrideFieldException(currentPathMetaData.getCurrentFullPath(), jsonType, propertyKey);
         }
    }

    public static boolean isObjectJson(AbstractJsonType jsonType) {
        return ObjectJsonType.class.isAssignableFrom(jsonType.getClass());
    }

    public static boolean isPrimitiveValue(AbstractJsonType jsonType) {
        return PrimitiveJsonType.class.isAssignableFrom(jsonType.getClass()) || JsonNullReferenceType.class.isAssignableFrom(jsonType.getClass());
    }

    public static boolean isArrayJson(AbstractJsonType jsonType) {
        return ArrayJsonType.class.isAssignableFrom(jsonType.getClass());
    }

    public static boolean isMergableJsonType(Object jsonType) {
        return MergableObject.class.isAssignableFrom(jsonType.getClass());
    }
}
