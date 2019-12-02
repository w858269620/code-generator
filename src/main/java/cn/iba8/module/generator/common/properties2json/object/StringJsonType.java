package cn.iba8.module.generator.common.properties2json.object;

import cn.iba8.module.generator.common.properties2json.util.StringToJsonStringWrapper;
import cn.iba8.module.generator.common.properties2json.util.StringToJsonStringWrapper;

public class StringJsonType extends PrimitiveJsonType<String> {

    public StringJsonType(String value) {
        super(value);
    }

    @Override
    public String toStringJson() {
        return StringToJsonStringWrapper.wrap(value);
    }
}
