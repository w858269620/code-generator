package cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.PrimitiveJsonTypeResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.AbstractObjectToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToConcreteObjectResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToConcreteObjectResolver;

import java.util.List;
import java.util.Optional;

import static cn.iba8.module.generator.common.properties2json.utils.reflection.InvokableReflectionUtils.setValueForField;

@SuppressWarnings("unchecked")
public class PrimitiveJsonTypeDelegatorResolver<T> extends PrimitiveJsonTypeResolver<T> {

    private final TextToConcreteObjectResolver toObjectResolver;
    private final AbstractObjectToJsonTypeConverter toJsonResolver;

    public PrimitiveJsonTypeDelegatorResolver(TextToConcreteObjectResolver toObjectResolver,
                                              AbstractObjectToJsonTypeConverter toJsonResolver) {
        this.toObjectResolver = toObjectResolver;
        this.toJsonResolver = toJsonResolver;
        setValueForField(this, "canResolveClass", resolveTypeOfResolver());
    }

    @Override
    public Class<?> resolveTypeOfResolver() {
        if (toJsonResolver != null) {
            return toJsonResolver.resolveTypeOfResolver();
        }
        return null;
    }

    @Override
    public AbstractJsonType returnConcreteJsonType(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                   T convertedValue,
                                                   String propertyKey) {
        Optional<AbstractJsonType> optional = toJsonResolver.convertToJsonTypeOrEmpty(primitiveJsonTypesResolver,
                                                                                      convertedValue,
                                                                                      propertyKey);
        return optional.get();
    }

    @Override
    protected Optional<T> returnConcreteValueWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                               String propertyValue,
                                                               String propertyKey) {
        Optional<Object> optionalObject = toObjectResolver.returnObjectWhenCanBeResolved(primitiveJsonTypesResolver,
                                                                            propertyValue, propertyKey);
        return optionalObject.map(o -> (T) o);
    }

    @Override
    public List<Class<?>> getClassesWhichCanResolve() {
        return toJsonResolver.getClassesWhichCanResolve();
    }
}
