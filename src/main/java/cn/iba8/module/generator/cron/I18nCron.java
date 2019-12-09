package cn.iba8.module.generator.cron;

import cn.iba8.module.generator.service.I18nService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class I18nCron {

    private final I18nService i18nService;

    @Scheduled(cron = "${cn.iba8.module.cron.i18n-load-origin}")
    public void loadI18nOrigin() {
        i18nService.loadI18n();
    }

    @Scheduled(cron = "${cn.iba8.module.cron.i18n-generate-code}")
    public void generateCode() {
        //读入的国际化文件处理
        i18nService.processI18nFileOrigin();
        //转化为全局国际化编码
        i18nService.compensateToCode();
        //转换其他语言
        i18nService.compensateToLanguage();
    }

}
