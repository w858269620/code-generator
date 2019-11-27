package cn.iba8.module.generator.controller;

import cn.iba8.module.generator.common.response.BaseResponse;
import cn.iba8.module.generator.service.LoadFileService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class LoadFileController {

    private final LoadFileService loadFileService;

    @GetMapping("/loadFile/i18n")
    public BaseResponse<Void> loadI18n() {
        loadFileService.loadI18n();
        return BaseResponse.success(null);
    }

}
