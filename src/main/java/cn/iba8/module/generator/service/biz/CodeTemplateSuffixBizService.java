package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import cn.iba8.module.generator.repository.dao.CodeTemplateSuffixRepository;
import cn.iba8.module.generator.repository.entity.CodeTemplateSuffix;
import cn.iba8.module.generator.service.converter.CodeTemplateSuffixConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CodeTemplateSuffixBizService {

    private final CodeTemplateSuffixRepository codeTemplateSuffixRepository;

    @Transactional
    public void loadTemplateSuffix(FileTemplateDefinition.FileTemplateSuffixDefinition fileTemplateDefinition, String templateGroup) {
        CodeTemplateSuffix origin = codeTemplateSuffixRepository.findFirstByTypeAndTypeGroup(fileTemplateDefinition.getFileType(), fileTemplateDefinition.getFileTypeGroup());
        if (null != origin && origin.getTemplateGroup().equals(templateGroup)) {
            return;
        }
        CodeTemplateSuffix codeTemplateSuffix = CodeTemplateSuffixConverter.toCodeTemplateSuffix(fileTemplateDefinition, templateGroup);
        codeTemplateSuffixRepository.save(codeTemplateSuffix);
    }

}
