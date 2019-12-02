package cn.iba8.module.generator.common.properties2json.resolvers.primitives.string;

import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

public class TextToCharacterResolver implements TextToConcreteObjectResolver<Character> {

    @Override
    public Optional<Character> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver, String propertyValue, String propertyKey) {
        if(propertyValue.length() == 1) {
            return Optional.of(propertyValue.charAt(0));
        }
        return Optional.empty();
    }
}
