package cn.iba8.module.generator.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.excel.MetaDatabaseTableDefinitionColumnExcel;
import cn.iba8.module.generator.common.response.BaseResponse;
import cn.iba8.module.generator.common.util.EasypoiUtil;
import cn.iba8.module.generator.service.MetaDatabaseService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class MetaDatabaseController {

    private final MetaDatabaseService metaDatabaseService;

    @GetMapping("/metadata/tables/getMyCatXml")
    public String getXml(@RequestParam(name = "codes") String codes) {
        return metaDatabaseService.getXml(codes);
    }

    @GetMapping("/metadata/tables/downloadMyCatXml")
    public String downloadXml(@RequestParam(name = "codes") String codes, String path) {
        String xml = metaDatabaseService.getXml(codes);
        try {
            if (StringUtils.isBlank(path)) {
                path = "D:mycat_schema.xml";
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(path)));
            bufferedWriter.write(xml);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return metaDatabaseService.getXml(codes);
    }

    @GetMapping("/metadata/tables/{code}")
    public BaseResponse<List<MetaDatabaseTableDefinition>> metaTables(@PathVariable(name = "code") String code) {
        return BaseResponse.success(metaDatabaseService.metaTables(code));
    }


    @GetMapping("/metadata/export/{code}")
    public BaseResponse<String> export(@PathVariable(name = "code") String code, Integer sheetName, HttpServletResponse response) {
        List<MetaDatabaseTableDefinition> definitions = metaDatabaseService.metaTables(code);
        List<Map<String, Object>> sheetsList = new ArrayList<>();
        definitions.forEach(r -> {
            String tableName = r.getTableName();
            String tableComment = StringUtils.isNotBlank(r.getTableComment()) ? r.getTableComment() : "";
            List<MetaDatabaseTableDefinition.MetaDatabaseTableDefinitionColumn> columns = r.getColumns();
            Map<String, Object> sheet = new HashMap<>();
            ExportParams params = new ExportParams();
            params.setSheetName(null == sheetName ? tableName : tableComment + "(" + tableName + ")");
            sheet.put("title", params);
            sheet.put("entity", MetaDatabaseTableDefinitionColumnExcel.class);
            sheet.put("data", MetaDatabaseTableDefinitionColumnExcel.of(columns));
            sheetsList.add(sheet);
        });
        Workbook workbook = ExcelExportUtil.exportExcel(sheetsList, ExcelType.HSSF);
        try {
            String[] split = code.split(":");
            String database = split[split.length - 1];
            EasypoiUtil.downLoadExcel(database + ".xls", response, workbook);
            return BaseResponse.success(database);
        } catch (Exception e) {
            throw BaseException.of(ResponseCode.BAD_REQUEST);
        }
    }

}
