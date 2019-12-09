package cn.iba8.module.generator.service.impl;

import cn.iba8.module.generator.common.ftl.TemplateDefinition;
import cn.iba8.module.generator.service.CodeGenerateService;
import cn.iba8.module.generator.service.biz.CodeGenerateBizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CodeGenerateServiceImpl implements CodeGenerateService {

    private final CodeGenerateBizService codeGenerateBizService;

    @Override
    public List<TemplateDefinition.TemplateFileBean> getCodeFiles(String moduleCode, String version, String typeGroup, String templateGroup) {
        return codeGenerateBizService.getCodeFiles(moduleCode, version, typeGroup, templateGroup);
    }

}
