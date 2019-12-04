package cn.iba8.module.generator.controller;

import cn.iba8.module.generator.common.ftl.TemplateDefinition;
import cn.iba8.module.generator.common.response.BaseResponse;
import cn.iba8.module.generator.common.util.ZipUtil;
import cn.iba8.module.generator.service.CodeGenerateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CodeGenerateController {

    private final CodeGenerateService codeGenerateService;

    @GetMapping("/codeGenerate/codes")
    public BaseResponse<Void> generate(String moduleCode, String version, String typeGroup, HttpServletResponse response) {
        List<TemplateDefinition.TemplateFileBean> templateFileBeans = codeGenerateService.getCodeFiles(moduleCode, version, typeGroup);
        ZipUtil.filePackage(templateFileBeans, moduleCode, version, typeGroup);
        return BaseResponse.success(null);
    }

}
