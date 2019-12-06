package cn.iba8.module.generator.service.converter;

import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import cn.iba8.module.generator.common.util.MD5;
import cn.iba8.module.generator.repository.entity.CodeTemplate;

public abstract class CodeTemplateConverter {

    public static CodeTemplate toCodeTemplate(CodeTemplate origin, FileTemplateDefinition.FileTemplateClassDefinition fileTemplateDefinition, String content) {
        CodeTemplate target = new CodeTemplate();
        String path = fileTemplateDefinition.getFilePath();
        int i = path.lastIndexOf("/");
        String filename = path.substring(i + 1);
        if (null == origin) {
            target.setVersion(1L);
        } else {
            target.setVersion(origin.getVersion() + 1);
        }
        target.setCode(path);
        target.setTemplate(content);
        target.setName(filename);
        target.setMd5(MD5.getMD5Str(content));
        target.setLatest(1);
        target.setType(fileTemplateDefinition.getFileType());
        target.setNote(fileTemplateDefinition.getFileNote());
        target.setTypeGroup(fileTemplateDefinition.getFileTypeGroup());
        target.setKeyword(fileTemplateDefinition.getFileKeyword());
        target.setLevel(fileTemplateDefinition.getFileLevel());
        return target;
    }

}
