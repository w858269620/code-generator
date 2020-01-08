package cn.iba8.module.generator.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.excel.ResourceExcelDefinition;
import cn.iba8.module.generator.common.response.BaseResponse;
import cn.iba8.module.generator.common.util.JacksonUtil;
import cn.iba8.module.generator.service.ResourceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
public class ResourceController {

    private final ResourceService resourceService;

    @PostMapping("/resource/importExcel")
    public void importFile(@RequestParam("file")MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
            throw BaseException.of(ResponseCode.PARAMS_VALUE_ERROR);
        }
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        params.setNeedVerify(false);
        try {
            List<ResourceExcelDefinition> list = ExcelImportUtil.importExcel(file.getInputStream(), ResourceExcelDefinition.class, params);
            StringBuffer sb = new StringBuffer();
            list.forEach(r -> sb.append(r.validate()));
            if (StringUtils.isNotBlank(sb.toString())) {
                throw BaseException.of(ResponseCode.PARAMS_VALUE_ERROR.getCode(), sb.toString());
            }
            resourceService.importExcel(list);
        } catch (BaseException e) {
            throw e;
        } catch (Exception e) {
            throw BaseException.of(ResponseCode.SYSTEM_ERROR.getCode(), e.getMessage());
        }
    }

    @GetMapping("/resource/copyFromDatabase")
    public BaseResponse<Void> copyFromDatabase() {
        resourceService.copyFromDatabase();
        return BaseResponse.success(null);
    }

}
