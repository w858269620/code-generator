package cn.iba8.module.generator.common.properties2json.resolvers.primitives.object;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractObjectToJsonTypeConverter<T> implements ObjectToJsonTypeConverter<T> {

    protected final Class<?> canResolveClass = resolveTypeOfResolver();

    /**
     * Inform about that certain converter can convert from generic type.
     * @return list of classes from which can convert to json object/element.
     */
    @Override
    public List<Class<?>> getClassesWhichCanResolve() {
        return Collections.singletonList(canResolveClass);
    }
}
