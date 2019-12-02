package cn.iba8.module.generator.common.properties2json.resolvers.primitives;

import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.NumberToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToNumberResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToNumberResolver;

@Deprecated
public class NumberJsonTypeResolver extends PrimitiveJsonTypeDelegatorResolver<Number> {

    public NumberJsonTypeResolver() {
        super(new TextToNumberResolver(), new NumberToJsonTypeConverter());
    }
}
