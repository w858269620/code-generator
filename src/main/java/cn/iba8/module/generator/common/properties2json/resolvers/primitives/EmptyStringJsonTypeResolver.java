package cn.iba8.module.generator.common.properties2json.resolvers.primitives;

import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.StringToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToEmptyStringResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;

@Deprecated
public class EmptyStringJsonTypeResolver extends PrimitiveJsonTypeDelegatorResolver<String> {

    public EmptyStringJsonTypeResolver() {
        super(new TextToEmptyStringResolver(), new StringToJsonTypeConverter());
    }
}
