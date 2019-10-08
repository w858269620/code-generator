package cn.iba8.module.generator.service;

import cn.iba8.module.generator.common.request.CodeRequest;
import cn.iba8.module.generator.common.request.CodeTemplatePageRequest;
import cn.iba8.module.generator.common.request.CodeTemplateSaveRequest;
import cn.iba8.module.generator.common.request.IdRequest;
import cn.iba8.module.generator.common.response.CodeTemplateDetailResponse;
import cn.iba8.module.generator.common.response.CodeTemplatePageResponse;
import cn.iba8.module.generator.common.response.CodeTemplateSaveResponse;
import cn.iba8.module.generator.common.response.PageResponse;

public interface CodeTemplateService {

    CodeTemplateSaveResponse save(CodeTemplateSaveRequest request);

    CodeTemplateDetailResponse detailById(IdRequest request);

    CodeTemplateDetailResponse detailByCode(CodeRequest request);

    PageResponse<CodeTemplatePageResponse> page(CodeTemplatePageRequest request);

}
