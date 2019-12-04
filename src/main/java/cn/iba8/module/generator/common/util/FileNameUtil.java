package cn.iba8.module.generator.common.util;

import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;

public abstract class FileNameUtil {

    public static String getRepositoryEntityName(String tableName) {
        String str = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, tableName);
        return str.substring(0,1).toUpperCase().concat(str.substring(1).toLowerCase());
    }

    public static String getRepositoryDaoName(String tableName) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, tableName) + "Repository.java";
    }

    public static String toPath(String packageDir) {
        String[] split = packageDir.split("\\.");
        return StringUtils.join(split, "/");
    }

}
