package cn.iba8.module.generator.service.impl;

import cn.iba8.module.generator.service.LoadFileService;
import cn.iba8.module.generator.service.biz.I18nBizService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class LoadFileServiceImpl implements LoadFileService {

    private final I18nBizService i18NBizService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void loadI18n() {
        i18NBizService.loadI18n();
    }

}
