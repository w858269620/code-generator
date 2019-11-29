package cn.iba8.module.generator.service.impl;

import cn.iba8.module.generator.common.enums.FileSuffixEnum;
import cn.iba8.module.generator.common.properties2json.utils.collection.CollectionUtils;
import cn.iba8.module.generator.repository.dao.I18nFileOriginRepository;
import cn.iba8.module.generator.repository.entity.I18nFileOrigin;
import cn.iba8.module.generator.service.LoadFileService;
import cn.iba8.module.generator.service.biz.I18nBizService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class LoadFileServiceImpl implements LoadFileService {

    private final I18nBizService i18NBizService;

    private final I18nFileOriginRepository i18nFileOriginRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void loadI18n() {
        i18NBizService.loadI18n();
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
                    i18NBizService.processI18nOriginForJsonBackend(i18nFileOrigin);
                }
            }
            if (2 == type) {
                if (FileSuffixEnum.JSON.getName().equals(suffix)) {
                    i18NBizService.processI18nOriginForJsonFrontend(i18nFileOrigin);
                }
            }
        }
    }

    @Override
    public void compensateToLanguage() {
        i18NBizService.compensateToLanguage();
    }
}
