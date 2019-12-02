package cn.iba8.module.generator.common.properties2json.resolvers.primitives.string;

import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

public class TextToEmptyStringResolver implements TextToConcreteObjectResolver<String> {

    public static final TextToEmptyStringResolver EMPTY_TEXT_RESOLVER = new TextToEmptyStringResolver();
    private final static String EMPTY_VALUE = "";

    @Override
    public Optional<String> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                          String propertyValue,
                                                          String propertyKey) {
        String text = propertyValue.equals(EMPTY_VALUE) ? EMPTY_VALUE : null;
        return Optional.ofNullable(text);
    }
}
