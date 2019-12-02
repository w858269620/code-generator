package cn.iba8.module.generator.common.properties2json.resolvers.primitives.string;

import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.join;
import static cn.iba8.module.generator.common.properties2json.Constants.EMPTY_STRING;
import static cn.iba8.module.generator.common.properties2json.Constants.SIMPLE_ARRAY_DELIMITER;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.hasJsonArraySignature;
import static cn.iba8.module.generator.common.properties2json.resolvers.primitives.utils.JsonObjectHelper.isValidJsonObjectOrArray;

public class TextToElementsResolver implements TextToConcreteObjectResolver<List<?>> {

    private final String arrayElementSeparator;
    private final boolean resolveTypeOfEachElement;

    public TextToElementsResolver() {
        this(true);
    }

    public TextToElementsResolver(boolean resolveTypeOfEachElement) {
        this(resolveTypeOfEachElement, SIMPLE_ARRAY_DELIMITER);
    }

    public TextToElementsResolver(boolean resolveTypeOfEachElement, String arrayElementSeparator) {
        this.resolveTypeOfEachElement = resolveTypeOfEachElement;
        this.arrayElementSeparator = arrayElementSeparator;
    }

    @Override
    public Optional<List<?>> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver, String propertyValue, String propertyKey) {
        if(isSimpleArray(propertyValue) && !isValidJsonObjectOrArray(propertyValue)) {

            if(hasJsonArraySignature(propertyValue)) {
                propertyValue = propertyValue
                        .replaceAll("]\\s*$", EMPTY_STRING)
                        .replaceAll("^\\s*\\[\\s*", EMPTY_STRING);
                String[] elements = propertyValue.split(arrayElementSeparator);
                List<String> clearedElements = new ArrayList<>();
                for(String element : elements) {
                    element = element.trim();
                    clearedElements.add(element);
                }
                propertyValue = join(arrayElementSeparator, clearedElements);
            }

            List<Object> elements = new ArrayList<>();
            for(String element : propertyValue.split(arrayElementSeparator)) {
                if(resolveTypeOfEachElement) {
                    elements.add(primitiveJsonTypesResolver.getResolvedObject(element, propertyKey));
                } else {
                    elements.add(element);
                }
            }
            return Optional.of(elements);
        }
        return Optional.empty();
    }

    private boolean isSimpleArray(String propertyValue) {
        return propertyValue.contains(arrayElementSeparator) || hasJsonArraySignature(propertyValue);
    }
}
