package cn.iba8.module.generator.controller;

import cn.iba8.module.generator.common.ftl.TemplateDefinition;
import cn.iba8.module.generator.common.request.CodeTemplateGenerateRequest;
import cn.iba8.module.generator.common.response.BaseResponse;
import cn.iba8.module.generator.common.util.SpringUtils;
import cn.iba8.module.generator.common.util.TemplateUtil;
import cn.iba8.module.generator.config.CodeGeneratorProperties;
import cn.iba8.module.generator.service.CodeGenerateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class CodeGenerateController {

    private final CodeGenerateService codeGenerateService;

    @GetMapping("/codeGenerate/codes")
    public BaseResponse<Void> generate(@RequestParam String moduleCode,
                                       @RequestParam String version,
                                       @RequestParam(defaultValue = "java") String typeGroup,
                                       @RequestParam(defaultValue = "true") Boolean withMethod,
                                       @RequestParam(defaultValue = "false") Boolean withMethodExcel,
                                       @RequestParam(defaultValue = "default") String templateGroup,
                                       HttpServletResponse response) {
        CodeTemplateGenerateRequest request = new CodeTemplateGenerateRequest();
        request.setModuleCode(moduleCode);
        request.setWithMethod(withMethod);
        request.setTemplateGroup(templateGroup);
        request.setTypeGroup(typeGroup);
        request.setVersion(version);
        request.setWithMethodExcel(withMethodExcel);
        List<TemplateDefinition.TemplateFileBean> templateFileBeans = codeGenerateService.getCodeFiles(request);
        CodeGeneratorProperties codeGeneratorProperties = SpringUtils.getBean(CodeGeneratorProperties.class);
        String tmp = codeGeneratorProperties.getCodeOutputTmp() + "/" + System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(templateFileBeans)) {
            for (TemplateDefinition.TemplateFileBean templateFileBean : templateFileBeans) {
                OutputStream os = null;
                try {
                    String fileDir = templateFileBean.getFileDir();
                    String path = tmp + "/" + TemplateUtil.toPath(fileDir);
                    File parent = new File(path);
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    os = new FileOutputStream(new File(path + "/" + templateFileBean.getFilename() + "." + typeGroup));
                    os.write(templateFileBean.getContent().getBytes());
                } catch (Exception e) {
                    log.error("生成文件失败");
                } finally {
                    if (null != os) {
                        try {
                            os.close();
                        } catch (Exception e) {
                            log.error("关闭流失败 e {}", e);
                        }
                    }
                }
            }
        }

        return BaseResponse.success(null);
    }

}
