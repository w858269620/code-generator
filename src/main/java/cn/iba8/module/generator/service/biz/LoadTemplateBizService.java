package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * @Author sc.wan
 * @Description 加载模板配置文件
 * @Date 2019/12/4 10:09
 */
@Component
@AllArgsConstructor
@Slf4j
public class LoadTemplateBizService implements CommandLineRunner {

    private final CodeTemplateBizService codeTemplateBizService;

    private final CodeTemplateSuffixBizService codeTemplateSuffixBizService;

    private final CodeTemplateCodeClassBizService codeTemplateCodeClassBizService;

    @Override
    public void run(String... args) throws Exception {
        File file = ResourceUtils.getFile("classpath:template/templates.json");
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        String json = new String(bytes);
        FileTemplateDefinition fileTemplateDefinition = FileTemplateDefinition.ofJson(json);
        if (null == fileTemplateDefinition) {
            return;
        }
        List<FileTemplateDefinition.FileTemplateSuffixDefinition> loadCodeSuffix = fileTemplateDefinition.getLoadCodeSuffix();
        List<FileTemplateDefinition.FileTemplateClassDefinition> loadCodeTemplate = fileTemplateDefinition.getLoadCodeTemplate();
        List<FileTemplateDefinition.FileTemplateCodeClassDefinition> loadCodeClass = fileTemplateDefinition.getLoadCodeClass();
        if (!CollectionUtils.isEmpty(loadCodeSuffix)) {
            for (FileTemplateDefinition.FileTemplateSuffixDefinition fileTemplateSuffixDefinition : loadCodeSuffix) {
                codeTemplateSuffixBizService.loadTemplateSuffix(fileTemplateSuffixDefinition);
            }
        }
        if (!CollectionUtils.isEmpty(loadCodeTemplate)) {
            for (FileTemplateDefinition.FileTemplateClassDefinition fileTemplateClassDefinition : loadCodeTemplate) {
                codeTemplateBizService.loadTemplate(fileTemplateClassDefinition);
            }
        }
        if (!CollectionUtils.isEmpty(loadCodeClass)) {
            for (FileTemplateDefinition.FileTemplateCodeClassDefinition fileTemplateCodeClassDefinition : loadCodeClass) {
                codeTemplateCodeClassBizService.loadTemplateCodeClass(fileTemplateCodeClassDefinition);
            }
        }
    }



}
