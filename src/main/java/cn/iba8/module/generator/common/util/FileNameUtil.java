package cn.iba8.module.generator.common.util;

import com.google.common.base.CaseFormat;

import java.io.File;

public abstract class FileNameUtil {

    public static String getRepositoryEntityName(String tableName) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, tableName);
    }

    public static String getRepositoryDaoName(String tableName) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, tableName) + "Repository.java";
    }

    public static String toPath(String packageDir) {
        String separator = File.separator;
        return packageDir.replaceAll(packageDir, separator);
    }

}
