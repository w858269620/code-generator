package cn.iba8.module.generator.common.properties2json.resolvers.primitives;

import cn.iba8.module.generator.common.properties2json.object.JsonNullReferenceType;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.NullToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToJsonNullReferenceResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToJsonNullReferenceResolver;

@Deprecated
public class JsonNullReferenceTypeResolver extends PrimitiveJsonTypeDelegatorResolver<JsonNullReferenceType> {

    public JsonNullReferenceTypeResolver() {
        super(new TextToJsonNullReferenceResolver(), new NullToJsonTypeConverter());
    }
}
