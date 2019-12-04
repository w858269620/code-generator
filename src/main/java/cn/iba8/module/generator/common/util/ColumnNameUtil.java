package cn.iba8.module.generator.common.util;

import com.google.common.base.CaseFormat;

public abstract class ColumnNameUtil {

    public static String toJavaField(String dbField) {
        String s = dbField.toLowerCase();
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, s);
    }

}
