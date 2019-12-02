package cn.iba8.module.generator.common.properties2json.resolvers.primitives;

import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.CharacterToJsonTypeConverter;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToCharacterResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.delegator.PrimitiveJsonTypeDelegatorResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.string.TextToCharacterResolver;

@Deprecated
public class CharacterJsonTypeResolver extends PrimitiveJsonTypeDelegatorResolver<Character> {

    public CharacterJsonTypeResolver() {
        super(new TextToCharacterResolver(), new CharacterToJsonTypeConverter());
    }
}