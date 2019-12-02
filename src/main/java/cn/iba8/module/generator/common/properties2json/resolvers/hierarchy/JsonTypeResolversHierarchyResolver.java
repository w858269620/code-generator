package cn.iba8.module.generator.common.properties2json.resolvers.hierarchy;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.PrimitiveJsonTypeResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.adapter.PrimitiveJsonTypeResolverToNewApiAdapter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.ObjectToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.util.exception.ParsePropertiesException;

import java.util.*;

import static java.util.stream.Collectors.toList;

/**
 * It looks for sufficient resolver, firstly will looks for exactly match class type provided by method {@link PrimitiveJsonTypeResolver#getClassesWhichCanResolve()}
 * if not then will looks for closets parent class or parent interface.
 * If will find resolver for parent class or parent interface at the same level, then will get parent super class as first.
 * If will find only closets super interfaces (at the same level) then will throw exception...
 */
public class JsonTypeResolversHierarchyResolver {

    private final Map<Class<?>, List<ObjectToJsonTypeConverter<?>>> resolversByType = new HashMap<>();
    private final HierarchyClassResolver hierarchyClassResolver;

    public JsonTypeResolversHierarchyResolver(List<ObjectToJsonTypeConverter> resolvers) {
        for(ObjectToJsonTypeConverter<?> resolver : resolvers) {
            for(Class<?> canResolveType : resolver.getClassesWhichCanResolve()) {
                List<ObjectToJsonTypeConverter<?>> resolversByClass = resolversByType.get(canResolveType);
                if(resolversByClass == null) {
                    List<ObjectToJsonTypeConverter<?>> newResolvers = new ArrayList<>();
                    newResolvers.add(resolver);
                    resolversByType.put(canResolveType, newResolvers);
                } else {
                    resolversByClass.add(resolver);
                }
            }
        }
        List<Class<?>> typesWhichCanResolve = new ArrayList<>();
        for(ObjectToJsonTypeConverter<?> resolver : resolvers) {
            typesWhichCanResolve.addAll(resolver.getClassesWhichCanResolve());
        }
        hierarchyClassResolver = new HierarchyClassResolver(typesWhichCanResolve);
    }

    public AbstractJsonType returnConcreteJsonTypeObject(PrimitiveJsonTypesResolver mainResolver,
                                                         Object instance,
                                                         String propertyKey) {
        Objects.requireNonNull(instance);
        Class<?> instanceClass = instance.getClass();
        List<ObjectToJsonTypeConverter<?>> resolvers = resolversByType.get(instanceClass);
        if(resolvers == null) {
            Class<?> typeWhichCanResolve = hierarchyClassResolver.searchResolverClass(instance);
            if(typeWhichCanResolve == null) {
                throw new ParsePropertiesException(String.format(ParsePropertiesException.CANNOT_FIND_TYPE_RESOLVER_MSG, instanceClass));
            }
            resolvers = resolversByType.get(typeWhichCanResolve);
        }

        if(!resolvers.isEmpty()) {
            if(instanceClass != String.class && resolvers.size() > 1 &&
               resolvers.stream().anyMatch(resolver -> resolver instanceof PrimitiveJsonTypeResolverToNewApiAdapter)) {
                List<Class<?>> resolversClasses = resolvers.stream()
                                                           .map(resolver -> {
                                                               if(resolver instanceof PrimitiveJsonTypeResolverToNewApiAdapter) {
                                                                   PrimitiveJsonTypeResolverToNewApiAdapter adapter = (PrimitiveJsonTypeResolverToNewApiAdapter) resolver;
                                                                   PrimitiveJsonTypeResolver oldImplementation = adapter.getOldImplementation();
                                                                   return oldImplementation.getClass();
                                                               }
                                                               return resolver.getClass();
                                                           }).collect(toList());
                throw new ParsePropertiesException("Found: " + new ArrayList<>(resolversClasses) + " for type" + instanceClass + " expected only one!");
            }

            for(ObjectToJsonTypeConverter<?> resolver : resolvers) {
                Optional<AbstractJsonType> abstractJsonType = resolver.returnOptionalJsonType(mainResolver, instance, propertyKey);
                if(abstractJsonType.isPresent()) {
                    return abstractJsonType.get();
                }
            }
        }

        throw new ParsePropertiesException(String.format(ParsePropertiesException.CANNOT_FIND_JSON_TYPE_OBJ, instanceClass, propertyKey, instance));
    }
}
