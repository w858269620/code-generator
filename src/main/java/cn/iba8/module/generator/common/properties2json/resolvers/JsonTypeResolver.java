package cn.iba8.module.generator.common.properties2json.resolvers;


import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.ArrayJsonType;
import cn.iba8.module.generator.common.properties2json.object.ObjectJsonType;
import cn.iba8.module.generator.common.properties2json.path.PathMetadata;
import cn.iba8.module.generator.common.properties2json.resolvers.transfer.DataForResolve;

import java.util.Map;

public abstract class JsonTypeResolver {

    protected Map<String, Object> properties;
    protected String propertyKey;
    protected ObjectJsonType currentObjectJsonType;

	protected ArrayJsonType getArrayJsonWhenIsValid(PathMetadata currentPathMetaData) {
        AbstractJsonType jsonType = currentObjectJsonType.getField(currentPathMetaData.getFieldName());
        return (ArrayJsonType) jsonType;
    }

    public abstract ObjectJsonType traverse(PathMetadata currentPathMetaData);

    public final ObjectJsonType traverseOnObjectAndInitByField(DataForResolve dataForResolve) {
        properties = dataForResolve.getProperties();
        propertyKey = dataForResolve.getPropertiesKey();
        currentObjectJsonType = dataForResolve.getCurrentObjectJsonType();
        return traverse(dataForResolve.getCurrentPathMetaData());
    }
}
