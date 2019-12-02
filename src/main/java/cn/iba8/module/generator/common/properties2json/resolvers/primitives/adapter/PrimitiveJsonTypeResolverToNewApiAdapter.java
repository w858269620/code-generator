package cn.iba8.module.generator.common.properties2json.resolvers.primitives.adapter;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.PrimitiveJsonTypeResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.ObjectToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToConcreteObjectResolver;

import java.util.List;
import java.util.Optional;

import static java.util.Arrays.asList;
import static cn.iba8.module.generator.common.properties2json.utils.reflection.InvokableReflectionUtils.invokeMethod;
import static cn.iba8.module.generator.common.properties2json.utils.reflection.InvokableReflectionUtils.setValueForField;

@SuppressWarnings("unchecked")
public final class PrimitiveJsonTypeResolverToNewApiAdapter extends PrimitiveJsonTypeResolver
        implements TextToConcreteObjectResolver, ObjectToJsonTypeConverter {

    private final PrimitiveJsonTypeResolver oldImplementation;

    public PrimitiveJsonTypeResolverToNewApiAdapter(PrimitiveJsonTypeResolver oldImplementation) {
        this.oldImplementation = oldImplementation;
        setValueForField(this, "canResolveClass", resolveTypeOfResolver());
    }

    @Override // from PrimitiveJsonTypeResolver and ObjectToJsonTypeConverter
    public Class<?> resolveTypeOfResolver() {
        if (oldImplementation != null) {
            return oldImplementation.resolveTypeOfResolver();
        }
        return null;
    }

    @Override // from PrimitiveJsonTypeResolver and ObjectToJsonTypeConverter
    public AbstractJsonType returnJsonType(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                           Object propertyValue,
                                           String propertyKey) {
        return oldImplementation.returnJsonType(primitiveJsonTypesResolver,
                                                propertyValue,
                                                propertyKey);
    }

    @Override // from PrimitiveJsonTypeResolver
    protected Optional<Object> returnConcreteValueWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                                    String propertyValue,
                                                                    String propertyKey) {
        return invokeMethod(oldImplementation, "returnConcreteValueWhenCanBeResolved",
                                                     asList(PrimitiveJsonTypesResolver.class, String.class, String.class),
                                                     asList(primitiveJsonTypesResolver, propertyValue, propertyKey));
    }

    @Override // from PrimitiveJsonTypeResolver
    public AbstractJsonType returnConcreteJsonType(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                   Object convertedValue,
                                                   String propertyKey) {
        return oldImplementation.returnConcreteJsonType(primitiveJsonTypesResolver, convertedValue, propertyKey);
    }


    @Override // from TextToConcreteObjectResolver and PrimitiveJsonTypeResolver
    public Optional<Object> returnConvertedValueForClearedText(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                               String propertyValue,
                                                               String propertyKey) {
        Optional<?> optional = oldImplementation.returnConvertedValueForClearedText(primitiveJsonTypesResolver, propertyValue, propertyKey);
        return Optional.ofNullable(optional.orElse(null));
    }

    @Override // from TextToConcreteObjectResolver
    public Optional<Object> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver,
                                                          String propertyValue,
                                                          String propertyKey) {
        Optional<?> optional = returnConcreteValueWhenCanBeResolved(primitiveJsonTypesResolver, propertyValue, propertyKey);
        return Optional.ofNullable(optional.orElse(null));
    }

    @Override // from ObjectToJsonTypeConverter
    public Optional<AbstractJsonType> convertToJsonTypeOrEmpty(PrimitiveJsonTypesResolver primitiveJsonTypesResolver, Object convertedValue, String propertyKey) {
        return Optional.of(oldImplementation.returnJsonType(primitiveJsonTypesResolver, convertedValue, propertyKey));
    }

    @Override // from ObjectToJsonTypeConverter and PrimitiveJsonTypeResolver
    public List<Class<?>> getClassesWhichCanResolve() {
        return oldImplementation.getClassesWhichCanResolve();
    }

    public PrimitiveJsonTypeResolver getOldImplementation() {
        return oldImplementation;
    }
}
