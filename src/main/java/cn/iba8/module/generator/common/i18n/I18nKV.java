package cn.iba8.module.generator.common.i18n;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class I18nKV {

    private String code;

    private String message;

    public static List<I18nKV> ofMap(Map<String, Object> stringObjectMap) {
        List<I18nKV> target = new ArrayList<>();
        stringObjectMap.keySet().forEach(r -> {
            Object o = stringObjectMap.get(r);
            if (StringUtils.isNotBlank(r)) {
                target.add(new I18nKV(r, o != null ? o.toString() : ""));
            }
        });
        return target;
    }

}
