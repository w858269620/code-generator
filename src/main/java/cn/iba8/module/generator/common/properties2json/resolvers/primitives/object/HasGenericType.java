package cn.iba8.module.generator.common.properties2json.resolvers.primitives.object;

import cn.iba8.module.generator.common.properties2json.resolvers.primitives.adapter.InvokedFromAdapter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.InvokedFromDelegator;
import cn.iba8.module.generator.common.properties2json.util.exception.ParsePropertiesException;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.adapter.InvokedFromAdapter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.InvokedFromDelegator;
import cn.iba8.module.generator.common.properties2json.util.exception.ParsePropertiesException;

import java.lang.reflect.ParameterizedType;

public interface HasGenericType<T> {

    @SuppressWarnings("unchecked")
    @InvokedFromAdapter
    @InvokedFromDelegator
    default Class<?> resolveTypeOfResolver() {
        Class<?> currentClass = getClass();
        while(currentClass != null) {
            try {
                return (Class<T>) ((ParameterizedType) currentClass
                        .getGenericSuperclass()).getActualTypeArguments()[0];
            } catch(Exception ccx) {
                currentClass = currentClass.getSuperclass();
            }
        }
        throw new ParsePropertiesException("Cannot find generic type for resolver: " + getClass() + " You can resolve it by one of below:"
                                           + "\n 1. override method resolveTypeOfResolver() for provide explicit class type " +
                                           "\n 2. add generic type during extension of PrimitiveJsonTypeResolver "
                                           + "'class " + getClass().getSimpleName() + " extends PrimitiveJsonTypeResolver<GIVEN_TYPE>'");
    }
}