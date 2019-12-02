package cn.iba8.module.generator.common.properties2json.util.exception;

import cn.iba8.module.generator.common.properties2json.resolvers.primitives.StringJsonTypeResolver;
import cn.iba8.module.generator.common.properties2json.resolvers.primitives.object.StringToJsonTypeConverter;

public class ParsePropertiesException extends RuntimeException {

    public static final String STRING_RESOLVER_AS_NOT_LAST = "Added some type resolver after " + StringJsonTypeResolver.class.getCanonicalName() +
                                                             ". This type resolver always should be last when is in configuration of resolvers";

    public static final String STRING__TO_JSON_RESOLVER_AS_NOT_LAST = "Added some type resolver after " + StringToJsonTypeConverter.class.getCanonicalName() +
                                                                      ". This type resolver always should be last when is in configuration of resolvers";

    public static final String PROPERTY_KEY_NEEDS_TO_BE_STRING_TYPE = "Unsupported property key type: %s for key: %s, Property key needs to be a string type";
    public static final String CANNOT_FIND_TYPE_RESOLVER_MSG = "Cannot find valid JSON type resolver for class: '%s'. \n" +
            "Please consider add sufficient resolver to your resolvers.";


    public static final String CANNOT_FIND_JSON_TYPE_OBJ = "Cannot find valid JSON type resolver for class: '%s'. \n" +
                                                               " for property: %s, and object value: %s \n" +
                                                               "Please consider add sufficient resolver to your resolvers.";
    public ParsePropertiesException(String message) {
        super(message);
    }
}
