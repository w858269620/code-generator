package cn.iba8.module.generator.service.converter;

import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import cn.iba8.module.generator.repository.entity.CodeTemplateCodeClass;

public abstract class CodeTemplateCodeClassConverter {

    public static CodeTemplateCodeClass toCodeTemplateCodeClass(FileTemplateDefinition.FileTemplateCodeClassDefinition fileTemplateDefinition) {
        CodeTemplateCodeClass target = new CodeTemplateCodeClass();
        target.setCodeClass(fileTemplateDefinition.getCodeClass());
        target.setType(fileTemplateDefinition.getFileType());
        target.setTypeGroup(fileTemplateDefinition.getFileTypeGroup());
        return target;
    }

}
