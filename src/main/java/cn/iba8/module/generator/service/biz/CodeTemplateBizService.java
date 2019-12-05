package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.ftl.FileTemplateDefinition;
import cn.iba8.module.generator.repository.dao.CodeTemplateRepository;
import cn.iba8.module.generator.repository.entity.CodeTemplate;
import cn.iba8.module.generator.service.converter.CodeTemplateConverter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CodeTemplateBizService {

    private final CodeTemplateRepository codeTemplateRepository;

     /*
      * @Author sc.wan
      * @Description 获取模板内容
      * @Date 16:35 2019/8/26
      * @Param
      * @return
      **/
    public String getTemplate(String code) {
        CodeTemplate codeTemplate = codeTemplateRepository.findFirstByCodeOrderByVersionDesc(code);
        if (null == codeTemplate) {
            throw BaseException.of(ResponseCode.CODE_TEMPLATE_CODE_NOT_EXIST);
        }
        return codeTemplate.getTemplate();
    }

    @Transactional
    public void loadTemplate(FileTemplateDefinition.FileTemplateClassDefinition fileTemplateDefinition) {
        String content = loadContent(fileTemplateDefinition.getFilePath());
        if (StringUtils.isBlank(content)) {
            return;
        }
        CodeTemplate origin = codeTemplateRepository.findFirstByCodeOrderByVersionDesc(fileTemplateDefinition.getFilePath());
        if (null != origin) {
            if (origin.getMd5().equals(fileTemplateDefinition.md5())) {
                return;
            }
        }
        CodeTemplate codeTemplate = CodeTemplateConverter.toCodeTemplate(origin, fileTemplateDefinition, content);
        codeTemplateRepository.save(codeTemplate);
        if (null != origin) {
            origin.setLatest(0);
            codeTemplateRepository.save(origin);
        }
    }

    private String loadContent(String path) {
        try {
            File file = ResourceUtils.getFile(path);
            InputStream is = new FileInputStream(file);
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return new String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("fail to load file path={}", path);
        }
        return null;
    }

}
