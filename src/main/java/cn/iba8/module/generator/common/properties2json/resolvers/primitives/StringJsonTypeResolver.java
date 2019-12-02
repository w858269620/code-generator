package cn.iba8.module.generator.common.properties2json.resolvers.primitives;

import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.StringToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToStringResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToStringResolver;

@Deprecated
public class StringJsonTypeResolver extends PrimitiveJsonTypeDelegatorResolver<String> {

    public StringJsonTypeResolver() {
        super(new TextToStringResolver(), new StringToJsonTypeConverter());
    }
}
