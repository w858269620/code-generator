package cn.iba8.module.generator.common.properties2json.resolvers.primitives.object;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

public class NullToJsonTypeConverter extends AbstractObjectToJsonTypeConverter<JsonNullReferenceType> {

    public static final NullToJsonTypeConverter NULL_TO_JSON_RESOLVER = new NullToJsonTypeConverter();

    @Override
    public Optional<AbstractJsonType> convertToJsonTypeOrEmpty(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                               JsonNullReferenceType convertedValue,
                                                               String propertyKey) {
        return Optional.of(convertedValue);
    }
}
