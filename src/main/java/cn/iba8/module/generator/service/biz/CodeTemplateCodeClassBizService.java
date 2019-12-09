package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import cn.iba8.module.generator.repository.dao.CodeTemplateCodeClassRepository;
import cn.iba8.module.generator.repository.dao.CodeTemplateSuffixRepository;
import cn.iba8.module.generator.repository.entity.CodeTemplateCodeClass;
import cn.iba8.module.generator.repository.entity.CodeTemplateSuffix;
import cn.iba8.module.generator.service.converter.CodeTemplateCodeClassConverter;
import cn.iba8.module.generator.service.converter.CodeTemplateSuffixConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CodeTemplateCodeClassBizService {

    private final CodeTemplateCodeClassRepository codeTemplateCodeClassRepository;

    @Transactional
    public void loadTemplateCodeClass(FileTemplateDefinition.FileTemplateCodeClassDefinition fileTemplateDefinition, String templateGroup) {
        CodeTemplateCodeClass origin = codeTemplateCodeClassRepository.findFirstByTypeAndTypeGroup(fileTemplateDefinition.getFileType(), fileTemplateDefinition.getFileTypeGroup());
        if (null != origin && origin.getTemplateGroup().equals(templateGroup)) {
            if (!origin.getCodeClass().equals(fileTemplateDefinition.getCodeClass())) {
                origin.setCodeClass(fileTemplateDefinition.getCodeClass());
                codeTemplateCodeClassRepository.save(origin);
            }
            return;
        }
        CodeTemplateCodeClass codeTemplateCodeClass = CodeTemplateCodeClassConverter.toCodeTemplateCodeClass(fileTemplateDefinition, templateGroup);
        codeTemplateCodeClassRepository.save(codeTemplateCodeClass);
    }

}
