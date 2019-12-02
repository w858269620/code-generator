package cn.iba8.module.generator.common.properties2json.resolvers;

import cn.iba8.module.generator.common.properties2json.JsonObjectFieldsValidator;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;
import cn.iba8.module.generator.common.properties2json.object.ObjectJsonType;
import cn.iba8.module.generator.common.properties2json.path.PathMetadata;

public class ObjectJsonTypeResolver extends JsonTypeResolver {


    @Override
    public ObjectJsonType traverse(PathMetadata currentPathMetaData) {
        fetchJsonObjectOrCreate(currentPathMetaData);
        return currentObjectJsonType;
    }

    private void fetchJsonObjectOrCreate(PathMetadata currentPathMetaData) {
        if (currentObjectJsonType.containsField(currentPathMetaData.getFieldName())) {
            fetchJsonObjectWhenIsNotPrimitive(currentPathMetaData);
        } else {
            createNewJsonObjectAndAssignToCurrent(currentPathMetaData);
        }
    }

    private void createNewJsonObjectAndAssignToCurrent(PathMetadata currentPathMetaData) {
        ObjectJsonType nextObjectJsonType = new ObjectJsonType();
        currentObjectJsonType.addField(currentPathMetaData.getFieldName(), nextObjectJsonType, currentPathMetaData);
        currentObjectJsonType = nextObjectJsonType;
    }

    private void fetchJsonObjectWhenIsNotPrimitive(PathMetadata currentPathMetaData) {
        AbstractJsonType jsonType = currentObjectJsonType.getField(currentPathMetaData.getFieldName());
        JsonObjectFieldsValidator.checkEarlierWasJsonObject(propertyKey, currentPathMetaData, jsonType);
        currentObjectJsonType = (ObjectJsonType) jsonType;
    }
}
