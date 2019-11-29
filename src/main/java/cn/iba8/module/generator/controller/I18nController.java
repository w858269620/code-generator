package cn.iba8.module.generator.controller;

import cn.iba8.module.generator.common.response.BaseResponse;
import cn.iba8.module.generator.service.I18nService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class I18nController {

    private final I18nService i18nService;

    @GetMapping("/i18n/i18n")
    public BaseResponse<Void> loadI18n() {
        i18nService.loadI18n();
        return BaseResponse.success(null);
    }

    @GetMapping("/i18n/originToCodeLanguage")
    public BaseResponse<Void> originToCodeLanguage() {
        i18nService.processI18nFileOrigin();
        return BaseResponse.success(null);
    }

    @GetMapping("/i18n/compensateToLanguage")
    public BaseResponse<Void> compensateToLanguage() {
        i18nService.compensateToLanguage();
        return BaseResponse.success(null);
    }

    @GetMapping("/i18n/compensateToCode")
    public BaseResponse<Void> compensateToCode() {
        i18nService.compensateToCode();
        return BaseResponse.success(null);
    }

    @GetMapping("/i18n/generateApp")
    public BaseResponse<Void> generateApp() {
        i18nService.compensateToCode();
        return BaseResponse.success(null);
    }

}
