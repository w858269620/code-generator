package cn.iba8.module.generator.common.properties2json.resolvers.primitives.string;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.convertToAbstractJsonType;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.hasJsonArraySignature;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.hasJsonObjectSignature;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.toJsonElement;

public class TextToObjectResolver implements TextToConcreteObjectResolver<AbstractJsonType> {

    @Override
    public Optional<AbstractJsonType> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver, String propertyValue, String propertyKey) {
        if(hasJsonObjectSignature(propertyValue) || hasJsonArraySignature(propertyValue)) {
            try {
                return Optional.ofNullable(convertToAbstractJsonType(toJsonElement(propertyValue), propertyKey));
            } catch(Exception exception) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}
