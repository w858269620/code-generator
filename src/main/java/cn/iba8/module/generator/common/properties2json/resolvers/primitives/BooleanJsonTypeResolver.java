package cn.iba8.module.generator.common.properties2json.resolvers.primitives;

import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.BooleanToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToBooleanResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;

@Deprecated
public class BooleanJsonTypeResolver extends PrimitiveJsonTypeDelegatorResolver<Boolean> {

    public BooleanJsonTypeResolver() {
        super(new TextToBooleanResolver(), new BooleanToJsonTypeConverter());
    }
}
