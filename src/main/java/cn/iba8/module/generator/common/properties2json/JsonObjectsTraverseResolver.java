package cn.iba8.module.generator.common.properties2json;

import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.ObjectJsonType;
import cn.iba8.module.generator.common.properties2json.object.SkipJsonField;
import cn.iba8.module.generator.common.properties2json.path.PathMetadata;
import cn.iba8.module.generator.common.properties2json.resolvers.JsonTypeResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.transfer.DataForResolve;

import java.util.Map;

public class JsonObjectsTraverseResolver {

    private final Map<AlgorithmType, JsonTypeResolver> algorithms;
    private final PrimitiveJsonTypesResolver primitiveJsonTypesResolver;
    private Map<String, Object> properties;
    private String propertyKey;
    private PathMetadata rootPathMetaData;
    private ObjectJsonType currentObjectJsonType;

    public JsonObjectsTraverseResolver(Map<AlgorithmType, JsonTypeResolver> algorithms,
                                       Map<String, Object> properties, String propertyKey,
                                       PathMetadata rootPathMetaData, ObjectJsonType coreObjectJsonType) {
        this.properties = properties;
        this.propertyKey = propertyKey;
        this.rootPathMetaData = rootPathMetaData;
        this.currentObjectJsonType = coreObjectJsonType;
        this.algorithms = algorithms;
        this.primitiveJsonTypesResolver = (PrimitiveJsonTypesResolver) algorithms.get(AlgorithmType.PRIMITIVE);
    }

    public void initializeFieldsInJson() {
        PathMetadata currentPathMetaData = rootPathMetaData;
        Object valueFromProperties = properties.get(currentPathMetaData.getOriginalPropertyKey());
        if(valueFromProperties != null) {
            if(valueFromProperties instanceof SkipJsonField) {
                return;
            }
        }
        AbstractJsonType resolverJsonObject = primitiveJsonTypesResolver.resolvePrimitiveTypeAndReturn(valueFromProperties, currentPathMetaData.getOriginalPropertyKey());
        if (resolverJsonObject instanceof SkipJsonField && !rootPathMetaData.getLeaf().isArrayField()) {
            return;
        } else {
            rootPathMetaData.getLeaf().setJsonValue(resolverJsonObject);
        }
        rootPathMetaData.getLeaf().setRawValue(properties.get(propertyKey));

        while(currentPathMetaData != null) {
            DataForResolve dataForResolve = new DataForResolve(properties, propertyKey, currentObjectJsonType, currentPathMetaData);
            currentObjectJsonType = algorithms.get(resolveAlgorithm(currentPathMetaData))
                                              .traverseOnObjectAndInitByField(dataForResolve);
            currentPathMetaData = currentPathMetaData.getChild();
        }
    }

    private AlgorithmType resolveAlgorithm(PathMetadata currentPathMetaData) {
        if(isPrimitiveField(currentPathMetaData)) {
            return AlgorithmType.PRIMITIVE;
        }
        if(currentPathMetaData.isArrayField()) {
            return AlgorithmType.ARRAY;
        }
        return AlgorithmType.OBJECT;
    }


    private boolean isPrimitiveField(PathMetadata currentPathMetaData) {
        return currentPathMetaData.isLeaf();
    }

}
