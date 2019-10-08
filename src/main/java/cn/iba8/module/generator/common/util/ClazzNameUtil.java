package cn.iba8.module.generator.common.util;

public abstract class ClazzNameUtil {

    public static String entityName(String table) {
        return NameConvertUtil.toCapitalizeCamelCase(table);
    }

    public static String repositoryName(String column) {
        return NameConvertUtil.toCapitalizeCamelCase(column) + "Repository";
    }

    public static String serviceBizName(String column) {
        return NameConvertUtil.toCapitalizeCamelCase(column) + "BizService";
    }

}
