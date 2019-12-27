package cn.iba8.module.generator.common.jsontojava.tosingleclass;

import cn.iba8.module.generator.common.jsontojava.converter.builder.enums.ComplexPropertyType;
import cn.iba8.module.generator.common.jsontojava.exception.JsonToJavaException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;

@Data
public class ToSingleJsonClassBuilder {

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

    private HashSet<PropertyField> properties;

    private HashSet<String> importedClasses;

    private HashSet<String> propertyKeyNames;

    private String className;

    private boolean withAnnotations = true;


    public ToSingleJsonClassBuilder(String className, String packageName, boolean withAnnotations) {
        validClassNameAndPackageName(className, packageName);
        declareClass(ToSingleJsonClassBuilder.firstCharToUpperCase(className), packageName);
        this.withAnnotations = withAnnotations;
        properties = new HashSet();
        importedClasses = new HashSet<>();
        propertyKeyNames = new HashSet<>();

        addImportStatement("lombok.Data");
        addImportStatement("java.io.Serializable");
        if(this.withAnnotations)
           addImportStatement(JSONPROPERTY_IMPORT_STATEMENT);
    }

    private void declareClass(String className, String packagename) {
        this.className = removeUnwantedCharacters(className);
    }

    private void validClassNameAndPackageName(String className, String packagename) {
        if(className == null || className.isEmpty() || packagename == null || packagename.isEmpty())
            throw new JsonToJavaException("Class name or package name is empty");
    }

    public String getClassName() {
        return className;
    }

    public void addProperty(String originalPropertyName, String declareName) {
        String propertyName = removeUnwantedCharacters(originalPropertyName);
        if(!propertyKeyNames.contains(propertyName)) {
            StringBuilder anno = new StringBuilder();
            StringBuilder field = new StringBuilder();
            if (withAnnotations) {
                anno.append("@JsonProperty(\"").append(originalPropertyName).append("\"").append(METHOD_CLOSED).append(NEW_LINE);
            }
            field
                .append("private ")
                .append(declareName)
                .append(SPACE)
                .append(propertyName)
                .append(END_STATEMENT)
                .append(NEW_LINE);
            properties.add(new PropertyField(anno.toString(), field.toString()));
            propertyKeyNames.add(propertyName);
        }
    }

    public void addProperty(String originalPropertyName, ComplexPropertyType complexPropertyType, String genericType) {
        String propertyName = removeUnwantedCharacters(originalPropertyName);
        if(!propertyKeyNames.contains(propertyName)) {
            String declareName = String.format(complexPropertyType.getDeclareName(), genericType);
            addProperty(originalPropertyName, declareName);
        }
    }

    public void addImportStatement(String importStatement) {
        if(!importedClasses.contains(importStatement)) {
            importedClasses.add(importStatement);
        }
    }

    public boolean hasProperty(String propertyName) {
        return propertyKeyNames.contains(propertyName);
    }

    public static String firstCharToUpperCase(String propertyName) {
        return propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
    }

    private String removeUnwantedCharacters(String javaIdentifier){
        StringBuilder validJavaIdentifier = new StringBuilder();

        char[] givenClassChars = javaIdentifier.toCharArray();
        for(int i = 0; i < givenClassChars.length; i++){
            char character = givenClassChars[i];
            if(Character.isJavaIdentifierPart(character)) {
                if(i == 0) {
                    if(Character.isJavaIdentifierStart(character)) {
                        validJavaIdentifier.append(character);
                    }
                } else {
                    validJavaIdentifier.append(character);
                }
            }
        }

        if(validJavaIdentifier.length() == 0){
            throw new JsonToJavaException("No valid characters in class name or property name");
        }
        return validJavaIdentifier.toString();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PropertyField implements Serializable {
        private String jsonAnnotation;
        private String fieldStatement;
    }

}
