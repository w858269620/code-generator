package cn.iba8.module.generator.common.properties2json.resolvers.primitives;

import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.ElementsToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToElementsResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToElementsResolver;
import cn.iba8.module.generator.common.properties2json.util.PropertiesToJsonConverter;

import java.util.Collection;

/**
 * When given text contains ',' or text starts with '[' and ends with ']' in text then it tries split by comma and remove '[]' signs and then
 * every separated text tries convert to json value.
 * It will try resolve every types by provided resolvers in {@link PropertiesToJsonConverter#PropertiesToJsonConverter(PrimitiveJsonTypeResolver...)}
 */
@Deprecated
public class PrimitiveArrayJsonTypeResolver extends PrimitiveJsonTypeDelegatorResolver<Collection<?>> {

    public PrimitiveArrayJsonTypeResolver() {
        super(new TextToElementsResolver(), new ElementsToJsonTypeConverter());
    }

    public PrimitiveArrayJsonTypeResolver(boolean resolveTypeOfEachElement) {
        super(new TextToElementsResolver(resolveTypeOfEachElement), new ElementsToJsonTypeConverter());
    }
}
