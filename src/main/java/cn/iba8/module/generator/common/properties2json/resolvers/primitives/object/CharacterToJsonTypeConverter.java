package cn.iba8.module.generator.common.properties2json.resolvers.primitives.object;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.StringJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.StringJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

public class CharacterToJsonTypeConverter extends AbstractObjectToJsonTypeConverter<Character> {

    @Override
    public Optional<AbstractJsonType> convertToJsonTypeOrEmpty(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                               Character convertedValue,
                                                               String propertyKey) {
        return Optional.of(new StringJsonType(convertedValue.toString()));
    }
}
