package cn.iba8.module.generator.common.properties2json.object;

import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.ObjectToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToConcreteObjectResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToConcreteObjectResolver;

/**
 * This is object for notify that given reference will be converted as null in json.
 * It can be returned by {@link ObjectToJsonTypeConverter#convertToJsonTypeOrEmpty(PrimitiveJsonTypesResolver, Object, String)}
 * and can be returned by {@link TextToConcreteObjectResolver#returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver, String, String)}
 */
public final class JsonNullReferenceType extends AbstractJsonType {

    public final static JsonNullReferenceType NULL_OBJECT = new JsonNullReferenceType();

    public final static String NULL_VALUE = "null";

    @Override
    public String toStringJson() {
        return NULL_VALUE;
    }
}
