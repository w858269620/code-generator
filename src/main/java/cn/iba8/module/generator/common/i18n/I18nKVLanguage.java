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
public class I18nKVLanguage {

    private String key;

    private String value;

    private String language;

    public static List<I18nKVLanguage> ofMap(Map<String, Object> stringObjectMap, String language) {
        List<I18nKVLanguage> target = new ArrayList<>();
        stringObjectMap.keySet().forEach(r -> {
            Object o = stringObjectMap.get(r);
            if (StringUtils.isNotBlank(r)) {
                target.add(new I18nKVLanguage(r, o != null ? o.toString() : "", language));
            }
        });
        return target;
    }

}
