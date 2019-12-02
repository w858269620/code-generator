package cn.iba8.module.generator.common.properties2json.resolvers.primitives.object;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.BooleanJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.BooleanJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

public class BooleanToJsonTypeConverter extends AbstractObjectToJsonTypeConverter<Boolean> {

    @Override
    public Optional<AbstractJsonType> convertToJsonTypeOrEmpty(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                               Boolean convertedValue,
                                                               String propertyKey) {
        return Optional.of(new BooleanJsonType(convertedValue));
    }
}
