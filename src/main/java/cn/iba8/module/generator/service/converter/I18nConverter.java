package cn.iba8.module.generator.service.converter;

import cn.iba8.module.generator.common.enums.FileSuffixEnum;
import cn.iba8.module.generator.common.enums.I18nFileTypeEnum;
import cn.iba8.module.generator.common.util.*;
import cn.iba8.module.generator.config.CodeGeneratorProperties;
import cn.iba8.module.generator.repository.entity.App;
import cn.iba8.module.generator.repository.entity.I18nCodeLanguage;
import cn.iba8.module.generator.repository.entity.I18nFileTarget;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public abstract class I18nConverter {

    public static List<I18nFileTarget> toI18nFileTargetJson(App app, Map<String, List<I18nCodeLanguage>> i18nCodeMap, Map<String, I18nFileTarget> lanTargetFileMap, Set<String> notes) {
        List<I18nFileTarget> target = new ArrayList<>();
        i18nCodeMap.keySet().forEach(r -> {
            List<I18nCodeLanguage> i18nCodeLanguages = i18nCodeMap.get(r);
            I18nFileTarget existFileTarget = lanTargetFileMap.get(r);
            Map<String, Object> existMap = new HashMap<>();
            List<I18nCodeLanguage> incrI18nCodeLanguages = new ArrayList<>();
            if (null != existFileTarget) {
                String content = existFileTarget.getContent();
                existMap = Json2Map.jsonToMap(content);
            }
            for (I18nCodeLanguage fileTarget : i18nCodeLanguages) {
                if (null == existMap.get(fileTarget.getCode())) {
                    incrI18nCodeLanguages.add(fileTarget);
                }
            }
            target.add(buildI18nFileTarget(app, r, i18nCodeLanguages, notes, I18nFileTypeEnum.ALL.getCode()));
            if (!CollectionUtils.isEmpty(incrI18nCodeLanguages)) {
                target.add(buildI18nFileTarget(app, r, incrI18nCodeLanguages, notes, I18nFileTypeEnum.INCR.getCode()));
            }
        });
        return target;
    }

    private static I18nFileTarget buildI18nFileTarget(App app, String r, List<I18nCodeLanguage> i18nCodeLanguages, Set<String> notes, Integer type) {
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
        i18nFileTarget.setType(type);
        return i18nFileTarget;
    }

    public static void transferFiles(Set<String> filePaths) {
        try {
            CodeGeneratorProperties bean = SpringUtils.getBean(CodeGeneratorProperties.class);
            for (String filePath : filePaths) {
                File origin = new File(filePath);
                String originRelativePath = filePath.substring(bean.getInputDir().length());
                String targetPath = bean.getInputHistory() + originRelativePath;
                File target = new File(targetPath);
                String parent = target.getParent();
                File f = new File(parent);
                if (!f.exists()) {
                    f.mkdirs();
                }
                String name = target.getName();
                FileUtils.moveFile(origin, new File(f.getPath() + "/" + System.currentTimeMillis() + "_" + name));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String buildJson(List<I18nCodeLanguage> i18nCodeLanguages) {
        Map<String, String> map = i18nCodeLanguages.stream().collect(Collectors.toMap(I18nCodeLanguage::getCode, I18nCodeLanguage::getMessage, (k1, k2) -> k2));
        Properties properties = Map2Properties.convertString(map);
        String yml = Properties2Yml.convert(properties);
        String json = Yml2Json.converter(yml);
        return json;
    }

    @AllArgsConstructor
    @Getter
    enum FileNameEnum {
        JSON("locale.constant-zh_CN.json"),;
        private String name;
    }

}
