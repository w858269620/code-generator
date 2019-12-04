package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.enums.FileOpTypeEnum;
import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
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
@Service
@AllArgsConstructor
@Slf4j
public class LoadTemplateBizService implements CommandLineRunner {

    private final CodeTemplateBizService codeTemplateBizService;

    @Override
    public void run(String... args) throws Exception {
        File file = ResourceUtils.getFile("classpath:template/templates.json");
        InputStream is = new FileInputStream(file);
        byte[] bytes = new byte[is.available()];
        is.read(bytes);
        String json = new String(bytes);
        List<FileTemplateDefinition> fileTemplateDefinitions = FileTemplateDefinition.ofJson(json);
        if (!CollectionUtils.isEmpty(fileTemplateDefinitions)) {
            for (FileTemplateDefinition fileTemplateDefinition : fileTemplateDefinitions) {
                if (!fileTemplateDefinition.valid()) {
                    log.warn("invalid template.json item {}", fileTemplateDefinition);
                    continue;
                }
                if (FileOpTypeEnum.LOAD.getName().equalsIgnoreCase(fileTemplateDefinition.getFileOpType())) {
                    codeTemplateBizService.loadTemplate(fileTemplateDefinition);
                }
            }
        }
    }



}
