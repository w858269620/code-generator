package cn.iba8.module.generator.common.jsontojava.tosingleclass;

import cn.iba8.module.generator.common.jsontojava.constants.JsonToJavaConstants;
import cn.iba8.module.generator.common.jsontojava.converter.builder.JavaClassBuilder;
import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.ComplexPropertyType;
import cn.iba8.module.generator.common.jsontojava.exception.JsonToJavaException;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class ToSingleJsonMeta implements Serializable {

    private String packageName;

    private String rootClazz;

    private Set<String> imports = new LinkedHashSet<>();

    public static class ToSingleJsonMetaClazzBuilder implements Serializable {
        private static final char END_STATEMENT = ';';
        private static final String NEW_LINE = System.lineSeparator();
        private static final String SPACE = " ";
        private static final String BIG_SPACE = "    ";
        private static final String DOUBLE_NEW_LINE = System.lineSeparator() + System.lineSeparator();
        private static final char BLOCK_OPEN = '{';
        private static final char BLOCK_CLOSED = '}';
        private static final String METHOD_NO_ARGS = "()";
        private static final char METHOD_OPEN = '(';
        private static final char METHOD_CLOSED = ')';

        private static final String JSONPROPERTY_IMPORT_STATEMENT = "com.fasterxml.jackson.annotation.JsonProperty";
    }

    @Data
    public static class ToSingleJsonMetaClazz implements Serializable {


        private String javaClassDeclaration;

        private StringBuilder properties;
        private StringBuilder gettersAndSetters;
        private StringBuilder importStatements;

        private HashSet<String> importedClasses;

        private HashSet<String> propertyKeyNames;

        private String className;

        private boolean withAnnotations = JsonToJavaConstants.DEFAULT_FOR_WITH_ANNOTATIONS;


    }

}
