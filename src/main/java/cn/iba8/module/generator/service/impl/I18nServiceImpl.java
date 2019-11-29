package cn.iba8.module.generator.service.impl;

import cn.iba8.module.generator.common.enums.FileSuffixEnum;
import cn.iba8.module.generator.common.properties2json.utils.collection.CollectionUtils;
import cn.iba8.module.generator.repository.dao.I18nFileOriginRepository;
import cn.iba8.module.generator.repository.entity.I18nFileOrigin;
import cn.iba8.module.generator.service.I18nService;
import cn.iba8.module.generator.service.biz.I18nBizService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class I18nServiceImpl implements I18nService {

    private final I18nBizService i18nBizService;

    private final I18nFileOriginRepository i18nFileOriginRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void loadI18n() {
        i18nBizService.loadI18n();
    }

    @Override
    public void processI18nFileOrigin() {
        List<I18nFileOrigin> i18nFileOrigins = i18nFileOriginRepository.findAllByProcessed(0);
        if (CollectionUtils.isEmpty(i18nFileOrigins)) {
            return;
        }
        for (I18nFileOrigin i18nFileOrigin : i18nFileOrigins) {
            String suffix = i18nFileOrigin.getSuffix();
            Integer type = i18nFileOrigin.getType();
            if (1 == type) {
                if (FileSuffixEnum.JSON.getName().equals(suffix)) {
                    i18nBizService.processI18nOriginForJsonBackend(i18nFileOrigin);
                }
            }
            if (2 == type) {
                if (FileSuffixEnum.JSON.getName().equals(suffix)) {
                    i18nBizService.processI18nOriginForJsonFrontend(i18nFileOrigin);
                }
            }
        }
    }

    @Override
    public void compensateToLanguage() {
        i18nBizService.compensateToLanguage();
    }

    @Override
    public void compensateToCode() {
        i18nBizService.compensateToCode();
    }

    @Override
    public void generateApp(String appCode) {
        i18nBizService.generateApp(appCode);
    }

    @Override
    public void generateLatestTargetFile(String appCode) {
        i18nBizService.generateLatestTargetFile(appCode);
    }

}
