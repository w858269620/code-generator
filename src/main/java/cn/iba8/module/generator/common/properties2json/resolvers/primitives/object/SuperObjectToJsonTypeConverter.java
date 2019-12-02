package cn.iba8.module.generator.common.properties2json.resolvers.primitives.object;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType;
import cn.iba8.module.generator.common.properties2json.object.StringJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import com.google.gson.JsonElement;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.StringJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

import static cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType.NULL_OBJECT;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.createArrayJsonType;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.createObjectJsonType;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.toJson;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.toJsonElement;

public class SuperObjectToJsonTypeConverter extends AbstractObjectToJsonTypeConverter<Object> {

    /**
     * It return instance of ArrayJsonType or ObjectJsonType
     * if propertyValue is not one of above types then will convert it to gson JsonElement instance and then convert to ArrayJsonType, ObjectJsonType, StringJsonType
     *
     * @param primitiveJsonTypesResolver resolver which is main resolver for leaf value from property
     * @param propertyValue              property key.
     * @return instance of ArrayJsonType, ObjectJsonType or StringJsonType
     */

    @Override
    public Optional<AbstractJsonType> convertToJsonTypeOrEmpty(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                               Object propertyValue,
                                                               String propertyKey) {
        if(AbstractJsonType.class.isAssignableFrom(propertyValue.getClass())) {
            return Optional.of((AbstractJsonType) propertyValue);
        }
        return Optional.of(convertFromObjectToJson(propertyValue, propertyKey));
    }

    /**
     * It convert to implementation of AbstractJsonType through use of json for conversion from java object to raw json,
     * then raw json convert to com.google.gson.JsonElement, and this JsonElement to instance of AbstractJsonType (json object, array json, or simple text json)
     *
     * @param propertyValue java bean to convert to instance of AbstractJsonType.
     * @param propertyKey   currently processed propertyKey from properties.
     * @return instance of AbstractJsonType
     */
    public static AbstractJsonType convertFromObjectToJson(Object propertyValue, String propertyKey) {
        return convertToObjectArrayOrJsonText(toJsonElement(toJson(propertyValue)), propertyKey);
    }

    private static AbstractJsonType convertToObjectArrayOrJsonText(JsonElement someField, String propertyKey) {
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
            return new StringJsonType(someField.toString());
        }
        return valueOfNextField;
    }
}
