package cn.iba8.module.generator.service.converter;

import cn.iba8.module.generator.common.enums.FileSuffixEnum;
import cn.iba8.module.generator.common.util.FileConverterUtil;
import cn.iba8.module.generator.common.util.MD5;
import cn.iba8.module.generator.repository.entity.App;
import cn.iba8.module.generator.repository.entity.I18nCodeLanguage;
import cn.iba8.module.generator.repository.entity.I18nFileTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class I18nConverter {

    public static List<I18nFileTarget> toI18nFileTargetJson(App app, Map<String, List<I18nCodeLanguage>> i18nCodeMap, Set<String> notes) {
        List<I18nFileTarget> target = new ArrayList<>();
        i18nCodeMap.keySet().forEach(r -> {
            List<I18nCodeLanguage> i18nCodeLanguages = i18nCodeMap.get(r);
            I18nFileTarget i18nFileTarget = new I18nFileTarget();
            i18nFileTarget.setAppCode(app.getCode());
            i18nFileTarget.setAppName(app.getName());
            i18nFileTarget.setVersion(app.getVersion());
            i18nFileTarget.setCreateTs(System.currentTimeMillis());
            i18nFileTarget.setSuffix(FileSuffixEnum.JSON.getName());
            i18nFileTarget.setName(FileNameEnum.JSON.getName());
            i18nFileTarget.setLanguage(r);
            i18nFileTarget.setContent(buildJson(i18nCodeLanguages));
            i18nFileTarget.setMd5(MD5.getMD5Str(i18nFileTarget.getContent() + i18nFileTarget.getLanguage()));
            i18nFileTarget.setNote(StringUtils.join(notes, ","));
            target.add(i18nFileTarget);
        });
        return target;
    }

    private static String buildJson(List<I18nCodeLanguage> i18nCodeLanguages) {
        Map<String, String> map = i18nCodeLanguages.stream().collect(Collectors.toMap(I18nCodeLanguage::getCode, I18nCodeLanguage::getMessage, (k1, k2) -> k2));
        String json = FileConverterUtil.map2json(map);
        return json;
    }

    @AllArgsConstructor
    @Getter
    enum FileNameEnum {
        JSON("locale.constant-zh_CN.json"),
        ;
        private String name;
    }

}
