package cn.iba8.module.generator.common.jsontojava.validator;

public interface JsonTypeChecker {

    boolean isObject(String json);

    boolean isArray(String json);
}
