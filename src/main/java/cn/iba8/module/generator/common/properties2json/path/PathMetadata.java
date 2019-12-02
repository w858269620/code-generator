package cn.iba8.module.generator.common.properties2json.path;

import cn.iba8.module.generator.common.properties2json.Constants;
import cn.iba8.module.generator.common.properties2json.PropertyArrayHelper;
import lombok.Data;
import cn.iba8.module.generator.common.properties2json.PropertyArrayHelper;
import cn.iba8.module.generator.common.properties2json.object.AbstractJsonType;

import static cn.iba8.module.generator.common.properties2json.Constants.EMPTY_STRING;
import static cn.iba8.module.generator.common.properties2json.Constants.NORMAL_DOT;


@Data
public class PathMetadata {

    private static final String NUMBER_PATTERN = "([1-9]\\d*)|0";
    public static final String INDEXES_PATTERN = "\\s*(\\[\\s*((" + NUMBER_PATTERN + ")|\\*)\\s*]\\s*)+";

    private static final String WORD_PATTERN = "(.)*";

    private final String originalPropertyKey;
    private PathMetadata parent;
    private String fieldName;
    private String originalFieldName;
    private PathMetadata child;
    private PropertyArrayHelper propertyArrayHelper;
    private Object rawValue;
    private AbstractJsonType jsonValue;

    public boolean isLeaf() {
        return child == null;
    }

    public boolean isRoot() {
        return parent == null;
    }

    public String getCurrentFullPath() {
        return parent == null ? originalFieldName : parent.getCurrentFullPath() + Constants.NORMAL_DOT + originalFieldName;
    }

    public PathMetadata getLeaf() {
        PathMetadata current = this;
        while (current.getChild() != null) {
            current = current.getChild();
        }
        return current;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
        if (fieldName.matches(WORD_PATTERN + INDEXES_PATTERN)) {
            propertyArrayHelper = new PropertyArrayHelper(fieldName);
            this.fieldName = propertyArrayHelper.getArrayFieldName();
        }
    }

    public void setRawValue(Object rawValue) {
        if (!isLeaf()) {
            throw new RuntimeException("Cannot set value for not leaf: " + getCurrentFullPath());
        }
        this.rawValue = rawValue;
    }

    public String getOriginalPropertyKey() {
        return originalPropertyKey;
    }

    public PathMetadata getRoot() {
        PathMetadata current = this;
        while (current.getParent() != null) {
            current = current.getParent();
        }
        return current;
    }

    public String getCurrentFullPathWithoutIndexes() {
        String parentFullPath = isRoot() ? Constants.EMPTY_STRING : getParent().getCurrentFullPath() + Constants.NORMAL_DOT;
        return parentFullPath + getFieldName();
    }

    public void setJsonValue(AbstractJsonType jsonValue) {
        if (!isLeaf()) {
            throw new RuntimeException("Cannot set value for not leaf: " + getCurrentFullPath());
        }
        this.jsonValue = jsonValue;
    }

    public AbstractJsonType getJsonValue() {
        return jsonValue;
    }

    @Override
    public String toString() {
        return "field='" + fieldName + '\'' +
               ", rawValue=" + rawValue +
               ", fullPath='" + getCurrentFullPath() + '}';
    }

    public boolean isArrayField() {
        return propertyArrayHelper != null;
    }
}
