package cn.iba8.module.generator.common.properties2json.resolvers.primitives.string;

import cn.iba8.module.generator.common.properties2json.resolvers.PrimitiveJsonTypesResolver;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

public class TextToNumberResolver implements TextToConcreteObjectResolver<Number> {

    @Override
    public Optional<Number> returnObjectWhenCanBeResolved(PrimitiveJsonTypesResolver primitiveJsonTypesResolver, String propertyValue, String propertyKey) {
        return Optional.ofNullable(convertToNumber(propertyValue));
    }

    public static Number convertToNumber(String propertyValue) {
        Number number = convertToNumberFromText(propertyValue);
        if (number!= null && number.toString().equals(propertyValue)) {
            return number;
        }
        return null;
    }

    private static Number convertToNumberFromText(String propertyValue) {

        try {
            return getIntegerNumber(propertyValue);
        } catch (NumberFormatException exc) {
        }
        try {
            return getDoubleNumber(propertyValue);
        } catch (NumberFormatException exc) {
        }
        return null;
    }


    private static BigInteger getIntegerNumber(String toParse) {
        return new BigInteger(toParse);
    }

    private static BigDecimal getDoubleNumber(String toParse) {
        return new BigDecimal(toParse);
    }
}
