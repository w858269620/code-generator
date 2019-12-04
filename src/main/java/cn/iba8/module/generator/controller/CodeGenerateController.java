package cn.iba8.module.generator.controller;

import cn.iba8.module.generator.common.ftl.TemplateDefinition;
import cn.iba8.module.generator.common.response.BaseResponse;
import cn.iba8.module.generator.common.util.FileNameUtil;
import cn.iba8.module.generator.common.util.SpringUtils;
import cn.iba8.module.generator.config.CodeGeneratorProperties;
import cn.iba8.module.generator.service.CodeGenerateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
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
    public BaseResponse<Void> generate(String moduleCode, String version, String typeGroup, HttpServletResponse response) {
        List<TemplateDefinition.TemplateFileBean> templateFileBeans = codeGenerateService.getCodeFiles(moduleCode, version, typeGroup);
        CodeGeneratorProperties codeGeneratorProperties = SpringUtils.getBean(CodeGeneratorProperties.class);
        String tmp = codeGeneratorProperties.getCodeOutputTmp() + "/" + System.currentTimeMillis();
        if (!CollectionUtils.isEmpty(templateFileBeans)) {
            for (TemplateDefinition.TemplateFileBean templateFileBean : templateFileBeans) {
                try {
                    String fileDir = templateFileBean.getFileDir();
                    String path = tmp + "/" + FileNameUtil.toPath(fileDir);
                    File parent = new File(path);
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    OutputStream os = new FileOutputStream(new File(path + "/" + templateFileBean.getFilename() + "." + typeGroup));
                    os.write(templateFileBean.getContent().getBytes());
                } catch (Exception e) {
                    log.error("生成临时文件失败");
                }
            }
        }

        return BaseResponse.success(null);
    }

}
