package cn.iba8.module.generator.common.properties2json.resolvers.primitives.object;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.StringJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.StringJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

public class StringToJsonTypeConverter extends AbstractObjectToJsonTypeConverter<String> {

    public static final StringToJsonTypeConverter STRING_TO_JSON_RESOLVER = new StringToJsonTypeConverter();

    @Override
    public Optional<AbstractJsonType> convertToJsonTypeOrEmpty(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                               String convertedValue,
                                                               String propertyKey) {
        return Optional.of(new StringJsonType(convertedValue));
    }
}
