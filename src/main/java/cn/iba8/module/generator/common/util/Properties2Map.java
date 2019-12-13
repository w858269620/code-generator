package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.CodeGenerateConstants;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Properties2Map {

    public static Map<String, String> converter(String properties) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] split = properties.split(CodeGenerateConstants.SPLITOR_PROPERTIES);
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (StringUtils.isNotBlank(s)) {
                int i1 = s.indexOf("=");
                if (i1 > 0) {
                    String key = s.substring(0, i1);
                    String value = "";
                    if (i1 < s.length() - 1) {
                        value = s.substring(i1 + 1);
                    }
                    map.put(key.trim(), value.trim());
                }
            }
        }
        return map;
    }

}
