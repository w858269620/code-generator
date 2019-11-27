package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.i18n.I18nFileDefinition;
import cn.iba8.module.generator.common.util.CopyUtil;
import cn.iba8.module.generator.common.util.Json2Map;
import cn.iba8.module.generator.config.CodeGeneratorProperties;
import cn.iba8.module.generator.repository.dao.I18nFileOriginRepository;
import cn.iba8.module.generator.repository.dao.I18nLanguageRepository;
import cn.iba8.module.generator.repository.dao.ModuleRepository;
import cn.iba8.module.generator.repository.entity.I18nFileOrigin;
import cn.iba8.module.generator.repository.entity.I18nLanguage;
import cn.iba8.module.generator.repository.entity.Module;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class I18nBizService {

    private final CodeGeneratorProperties codeGeneratorProperties;

    private final I18nFileOriginRepository i18nFileOriginRepository;

    private final ModuleRepository moduleRepository;

    private final I18nLanguageRepository i18nLanguageRepository;

    @Transactional(rollbackFor = Exception.class)
    public void loadI18n() {
        String inputDir = codeGeneratorProperties.getInputDir();
        I18nFileDefinition i18nFileDefinition = I18nFileDefinition.of(inputDir);
        if (null == i18nFileDefinition || CollectionUtils.isEmpty(i18nFileDefinition.getModules())) {
            return;
        }
        List<I18nFileDefinition.I18nFileDefinitionModule> modules = i18nFileDefinition.getModules();
        List<Module> targetModules = new ArrayList<>();
        List<I18nFileOrigin> targetFileOrigins = new ArrayList<>();
        List<I18nLanguage> targetLanguages = new ArrayList<>();
        Set<String> languages = new HashSet<>();
        for (I18nFileDefinition.I18nFileDefinitionModule definitionModule : modules) {
            Module module = moduleRepository.findFirstByCodeAndVersion(definitionModule.getCode(), definitionModule.getVersion());
            if (null != module) {
                continue;
            }
            targetModules.add(CopyUtil.copy(definitionModule, Module.class));
            List<I18nFileDefinition.I18nFileDefinitionFile> moduleFiles = definitionModule.getFiles();
            if (!CollectionUtils.isEmpty(moduleFiles)) {
                for (I18nFileDefinition.I18nFileDefinitionFile i18nFileDefinitionFile : moduleFiles) {
                    languages.add(i18nFileDefinitionFile.getLanguage());
                    I18nFileOrigin i18nFileOrigin = CopyUtil.copy(i18nFileDefinitionFile, I18nFileOrigin.class);
                    i18nFileOrigin.setCreateTs(System.currentTimeMillis());
                    i18nFileOrigin.setModuleCode(definitionModule.getCode());
                    i18nFileOrigin.setModuleName(definitionModule.getName());
                    i18nFileOrigin.setProcessed(0);
                    i18nFileOrigin.setModifyTs(0L);
                    targetFileOrigins.add(i18nFileOrigin);
                }
            }
        }
        if (!CollectionUtils.isEmpty(languages)) {
            List<I18nLanguage> i18nLanguages = i18nLanguageRepository.findAllByCodeIn(languages);
            Set<String> languageExists = new HashSet<>();
            if (!CollectionUtils.isEmpty(i18nLanguages)) {
                i18nLanguages.forEach(r -> languageExists.add(r.getCode()));
            }
            languages.forEach(r -> {
                if (!languageExists.contains(r)) {
                    I18nLanguage i18nLanguage = new I18nLanguage();
                    i18nLanguage.setCode(r);
                    i18nLanguage.setName(r);
                    targetLanguages.add(i18nLanguage);
                }
            });
        }
        if (!CollectionUtils.isEmpty(targetModules)) {
            moduleRepository.saveAll(targetModules);
        }
        if (!CollectionUtils.isEmpty(targetLanguages)) {
            i18nLanguageRepository.saveAll(targetLanguages);
        }
        if (!CollectionUtils.isEmpty(targetFileOrigins)) {
            i18nFileOriginRepository.saveAll(targetFileOrigins);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void processI18nOrigin(I18nFileOrigin i18nFileOrigin) {
        if (null == i18nFileOrigin) {
            return;
        }
        I18nFileOrigin copy = CopyUtil.copy(i18nFileOrigin, I18nFileOrigin.class);
        copy.setProcessed(1);
        copy.setModifyTs(System.currentTimeMillis());
        String suffix = i18nFileOrigin.getSuffix();
        String content = i18nFileOrigin.getContent();
        if (!StringUtils.isBlank(content)) {
            if ("json".equals(suffix)) {
                Map<String, Object> stringObjectMap = Json2Map.jsonToMap(content);

            }
        }

    }


}
