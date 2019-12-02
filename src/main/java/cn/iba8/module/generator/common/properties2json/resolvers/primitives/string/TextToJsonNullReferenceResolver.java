package cn.iba8.module.generator.common.properties2json.resolvers.primitives.string;

import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.Optional;

import static cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType.NULL_OBJECT;
import static cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType.NULL_VALUE;

public class TextToJsonNullReferenceResolver implements TextToConcreteObjectResolver<Object> {

    public final static TextToJsonNullReferenceResolver TEXT_TO_NULL_JSON_RESOLVER = new TextToJsonNullReferenceResolver();

    @Override
    public Optional<Object> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver, String propertyValue, String propertyKey) {
        if (propertyValue == null || propertyValue.equals(NULL_VALUE)) {
            return Optional.of(NULL_OBJECT);
        }
        return Optional.empty();
    }
}
