package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.ftl.FileModuleDefinition;
import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class LoadTemplateBizService {

    private final CodeTemplateBizService codeTemplateBizService;

    private final CodeTemplateSuffixBizService codeTemplateSuffixBizService;

    private final CodeTemplateCodeClassBizService codeTemplateCodeClassBizService;

    /*
     * @Author sc.wan
     * @Description 加载模板
     * @Date 16:40 2019/12/9
     * @Param
     * @return
     **/
    public void loadTemplates(FileModuleDefinition.FileModuleTemplateDefinition templateRoot) {
        String templatesRoot = templateRoot.getTemplateRoot();
        String templateGroup = templateRoot.getTemplateGroup();
        InputStream is = null;
        try {
            File file = ResourceUtils.getFile(templatesRoot);
            is = new FileInputStream(file);
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
                    codeTemplateSuffixBizService.loadTemplateSuffix(fileTemplateSuffixDefinition, templateGroup);
                }
            }
            if (!CollectionUtils.isEmpty(loadCodeTemplate)) {
                for (FileTemplateDefinition.FileTemplateClassDefinition fileTemplateClassDefinition : loadCodeTemplate) {
                    codeTemplateBizService.loadTemplate(fileTemplateClassDefinition, templateGroup);
                }
            }
            if (!CollectionUtils.isEmpty(loadCodeClass)) {
                for (FileTemplateDefinition.FileTemplateCodeClassDefinition fileTemplateCodeClassDefinition : loadCodeClass) {
                    codeTemplateCodeClassBizService.loadTemplateCodeClass(fileTemplateCodeClassDefinition, templateGroup);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
