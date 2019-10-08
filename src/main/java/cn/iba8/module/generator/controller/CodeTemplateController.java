package cn.iba8.module.generator.controller;

import cn.iba8.module.generator.common.request.CodeRequest;
import cn.iba8.module.generator.common.request.CodeTemplatePageRequest;
import cn.iba8.module.generator.common.request.CodeTemplateSaveRequest;
import cn.iba8.module.generator.common.request.IdRequest;
import cn.iba8.module.generator.common.response.*;
import cn.iba8.module.generator.service.CodeTemplateService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class CodeTemplateController {

    private final CodeTemplateService codeTemplateService;

     /*
      * @Author sc.wan
      * @Description 分页
      * @Date 17:43 2019/8/26
      * @Param
      * @return
      **/
    @PostMapping("/codeTemplate/page")
    public BaseResponse<PageResponse<CodeTemplatePageResponse>> page(@RequestBody @Valid CodeTemplatePageRequest request) {
        return BaseResponse.success(codeTemplateService.page(request));
    }

     /*
      * @Author sc.wan
      * @Description 模板新增
      * @Date 17:34 2019/8/26
      * @Param
      * @return
      **/
    @PostMapping("/codeTemplate/save")
    public BaseResponse<CodeTemplateSaveResponse> save(@RequestBody @Valid CodeTemplateSaveRequest request) {
        return BaseResponse.success(codeTemplateService.save(request));
    }

     /*
      * @Author sc.wan
      * @Description 通过id获取详情
      * @Date 17:41 2019/8/26
      * @Param
      * @return
      **/
    @PostMapping("/codeTemplate/detailById")
    public BaseResponse<CodeTemplateDetailResponse> detailById(@RequestBody @Valid IdRequest request) {
        return BaseResponse.success(codeTemplateService.detailById(request));
    }

    /*
     * @Author sc.wan
     * @Description 通过code获取详情
     * @Date 17:41 2019/8/26
     * @Param
     * @return
     **/
    @PostMapping("/codeTemplate/detailByCode")
    public BaseResponse<CodeTemplateDetailResponse> detailByCode(@RequestBody @Valid CodeRequest request) {
        return BaseResponse.success(codeTemplateService.detailByCode(request));
    }

}
