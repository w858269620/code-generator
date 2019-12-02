package cn.iba8.module.generator.common.properties2json.object;

import cn.iba8.module.generator.common.properties2json.path.PathMetadata;
import cn.iba8.module.generator.common.properties2json.util.StringToJsonStringWrapper;
import cn.iba8.module.generator.common.properties2json.util.exception.CannotOverrideFieldException;
import cn.iba8.module.generator.common.properties2json.Constants;
import cn.iba8.module.generator.common.properties2json.path.PathMetadata;

import java.util.HashMap;
import java.util.Map;

import static cn.iba8.module.generator.common.properties2json.Constants.EMPTY_STRING;
import static cn.iba8.module.generator.common.properties2json.Constants.JSON_OBJECT_END;
import static cn.iba8.module.generator.common.properties2json.Constants.JSON_OBJECT_START;
import static cn.iba8.module.generator.common.properties2json.Constants.NEW_LINE_SIGN;
import static cn.iba8.module.generator.common.properties2json.object.MergableObject.mergeObjectIfPossible;
import static cn.iba8.module.generator.common.properties2json.utils.collection.CollectionUtils.getLastIndex;

public class ObjectJsonType extends AbstractJsonType implements MergableObject<ObjectJsonType> {

    private Map<String, AbstractJsonType> fields = new HashMap<>();

    public void addField(final String field, final AbstractJsonType object, PathMetadata currentPathMetaData) {
        if(object instanceof SkipJsonField) {
            return;
        }

        AbstractJsonType oldFieldValue = fields.get(field);
        if(oldFieldValue != null) {
            if(oldFieldValue instanceof MergableObject && object instanceof MergableObject) {
                mergeObjectIfPossible(oldFieldValue, object, currentPathMetaData);
            } else {
                throw new CannotOverrideFieldException(currentPathMetaData.getCurrentFullPath(),
                                                       oldFieldValue,
                                                       currentPathMetaData.getOriginalPropertyKey());
            }
        } else {
            fields.put(field, object);
        }
    }

    public boolean containsField(String field) {
        return fields.containsKey(field);
    }

    public AbstractJsonType getField(String field) {
        return fields.get(field);
    }

    public ArrayJsonType getJsonArray(String field) {
        return (ArrayJsonType) fields.get(field);
    }

    @Override
    public String toStringJson() {
        StringBuilder result = new StringBuilder().append(Constants.JSON_OBJECT_START);
        int index = 0;
        int lastIndex = getLastIndex(fields.keySet());
        for(String fieldName : fields.keySet()) {
            AbstractJsonType object = fields.get(fieldName);
            String lastSign = index == lastIndex ? Constants.EMPTY_STRING : Constants.NEW_LINE_SIGN;
            result.append(StringToJsonStringWrapper.wrap(fieldName))
                  .append(":")
                  .append(object.toStringJson())
                  .append(lastSign);
            index++;
        }
        result.append(Constants.JSON_OBJECT_END);
        return result.toString();
    }

    @Override
    public void merge(ObjectJsonType mergeWith, PathMetadata currentPathMetadata) {
        for(String fieldName : mergeWith.fields.keySet()) {
            addField(fieldName, mergeWith.getField(fieldName), currentPathMetadata);
        }
    }
}
