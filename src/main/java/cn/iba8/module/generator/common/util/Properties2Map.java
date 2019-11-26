package cn.iba8.module.generator.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Properties2Map {

    public static Map<String, String> converter(String properties) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] split = properties.split("\n");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (StringUtils.isNotBlank(s)) {
                String[] sp = s.split("=");
                String value = null;
                if (sp.length == 2) {
                    value = sp[1];
                }
                map.put(sp[0], value);
            }
        }
        return map;
    }

}
