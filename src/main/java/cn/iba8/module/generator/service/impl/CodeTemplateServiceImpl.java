package cn.iba8.module.generator.service.impl;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.request.CodeRequest;
import cn.iba8.module.generator.common.request.CodeTemplatePageRequest;
import cn.iba8.module.generator.common.request.CodeTemplateSaveRequest;
import cn.iba8.module.generator.common.request.IdRequest;
import cn.iba8.module.generator.common.response.CodeTemplateDetailResponse;
import cn.iba8.module.generator.common.response.CodeTemplatePageResponse;
import cn.iba8.module.generator.common.response.CodeTemplateSaveResponse;
import cn.iba8.module.generator.common.response.PageResponse;
import cn.iba8.module.generator.common.util.CopyUtil;
import cn.iba8.module.generator.repository.dao.CodeTemplateRepository;
import cn.iba8.module.generator.repository.entity.CodeTemplate;
import cn.iba8.module.generator.service.CodeTemplateService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CodeTemplateServiceImpl implements CodeTemplateService {

    private final CodeTemplateRepository codeTemplateRepository;

    @Override
    public PageResponse<CodeTemplatePageResponse> page(CodeTemplatePageRequest request) {
        Page<CodeTemplate> page = codeTemplateRepository.findAll(((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(request.getCodeLike())) {
                predicates.add(cb.like(root.get("code"), "%" + request.getCodeLike() + "%"));
            }
            if (StringUtils.isNotBlank(request.getNameLike())) {
                predicates.add(cb.like(root.get("name"), "%" + request.getNameLike() + "%"));
            }
            return query.getRestriction();
        }), PageRequest.of(request.jpaPageNo(), request.getPageSize()));
        return PageResponse.of(CopyUtil.copyList(page.getContent(), CodeTemplatePageResponse.class), page.getTotalElements(), page.getTotalPages());
    }

    @Override
    public CodeTemplateDetailResponse detailById(IdRequest request) {
        Optional<CodeTemplate> optionalCodeTemplate = codeTemplateRepository.findById(request.getId());
        if (!optionalCodeTemplate.isPresent()) {
            throw BaseException.of(ResponseCode.CODE_TEMPLATE_NOT_EXIST);
        }
        return CopyUtil.copy(optionalCodeTemplate.get(), CodeTemplateDetailResponse.class);
    }

    @Override
    public CodeTemplateDetailResponse detailByCode(CodeRequest request) {
        CodeTemplate codeTemplate = codeTemplateRepository.findFirstByCode(request.getCode());
        if (null == codeTemplate) {
            throw BaseException.of(ResponseCode.CODE_TEMPLATE_NOT_EXIST);
        }
        return CopyUtil.copy(codeTemplate, CodeTemplateDetailResponse.class);
    }

    @Override
    public CodeTemplateSaveResponse save(@Valid CodeTemplateSaveRequest request) {
        CodeTemplate codeTemplate;
        boolean checkCode = true;
        if (null != request.getId()) {
            Optional<CodeTemplate> optionalCodeTemplate = codeTemplateRepository.findById(request.getId());
            if (!optionalCodeTemplate.isPresent()) {
                throw BaseException.of(ResponseCode.CODE_TEMPLATE_NOT_EXIST);
            }
            codeTemplate = optionalCodeTemplate.get();
            checkCode = !codeTemplate.getCode().equals(request.getCode());
            if (checkCode) {
                codeTemplate.setCode(request.getCode());
            }
        } else {
            codeTemplate = CopyUtil.copy(request, CodeTemplate.class);
        }
        if (checkCode) {
            CodeTemplate firstByCode = codeTemplateRepository.findFirstByCode(request.getCode());
            if (null != firstByCode) {
                throw BaseException.of(ResponseCode.CODE_TEMPLATE_CODE_ALREADY_EXIST);
            }
        }
        CodeTemplate save = codeTemplateRepository.save(codeTemplate);
        return CopyUtil.copy(save, CodeTemplateSaveResponse.class);
    }

}
