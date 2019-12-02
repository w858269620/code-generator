package cn.iba8.module.generator.common.properties2json.object;

import cn.iba8.module.generator.common.properties2json.path.PathMetadata;
import cn.iba8.module.generator.common.properties2json.util.exception.MergeObjectException;
import cn.iba8.module.generator.common.properties2json.path.PathMetadata;
import cn.iba8.module.generator.common.properties2json.util.exception.MergeObjectException;

@SuppressWarnings("unchecked")
public interface MergableObject<T extends AbstractJsonType> {
    void merge(T mergeWith, PathMetadata currentPathMetadata);

    static void mergeObjectIfPossible(AbstractJsonType oldJsonElement, AbstractJsonType elementToAdd, PathMetadata currentPathMetadata) {
        MergableObject oldObject = (MergableObject) oldJsonElement;
        if (oldObject.getClass().isAssignableFrom(elementToAdd.getClass())) {
            oldObject.merge(elementToAdd, currentPathMetadata);
        } else {
            throw new MergeObjectException(oldJsonElement, elementToAdd, currentPathMetadata);
        }
    }
}
