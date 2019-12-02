package cn.iba8.module.generator.common.properties2json.resolvers.primitives.string;

import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

public class TextToBooleanResolver implements TextToConcreteObjectResolver<Boolean> {

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    @Override
    public Optional<Boolean> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver, String propertyValue, String propertyKey) {
        if (TRUE.equalsIgnoreCase(propertyValue) || FALSE.equalsIgnoreCase(propertyValue)){
            return Optional.of(getBoolean(propertyValue));
        }
        return Optional.empty();
    }

    private static Boolean getBoolean(String value) {
        return Boolean.valueOf(value);
    }
}
