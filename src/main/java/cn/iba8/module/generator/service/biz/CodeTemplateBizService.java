package cn.iba8.module.generator.service.biz;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.repository.dao.CodeTemplateRepository;
import cn.iba8.module.generator.repository.entity.CodeTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        CodeTemplate codeTemplate = codeTemplateRepository.findFirstByCode(code);
        if (null == codeTemplate) {
            throw BaseException.of(ResponseCode.CODE_TEMPLATE_CODE_NOT_EXIST);
        }
        return codeTemplate.getTemplate();
    }



}
