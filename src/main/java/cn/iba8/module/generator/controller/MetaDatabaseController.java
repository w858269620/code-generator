package cn.iba8.module.generator.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.enums.DdlOpTypeEnum;
import cn.iba8.module.generator.common.excel.MetaDatabaseTableDefinitionColumnExcel;
import cn.iba8.module.generator.common.request.MetaDatabaseDdlRequest;
import cn.iba8.module.generator.common.request.MetaDatabaseGenerateDdlRequest;
import cn.iba8.module.generator.common.response.BaseResponse;
import cn.iba8.module.generator.common.response.MetaDatabaseDdlResponse;
import cn.iba8.module.generator.common.response.MetaDatabaseGenerateDdlResponse;
import cn.iba8.module.generator.common.util.EasypoiUtil;
import cn.iba8.module.generator.service.MetaDatabaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@AllArgsConstructor
@Slf4j
public class MetaDatabaseController {

    private final MetaDatabaseService metaDatabaseService;

    @GetMapping("/metadata/tables/generateTableDdl")
    public List<MetaDatabaseGenerateDdlResponse> generateTableDdl(String codes,
                                                                  String filterNote,
                                                                  String replacement,
                                                                  String filterExcludeNote
                                                                  ) {
        MetaDatabaseGenerateDdlRequest request = new MetaDatabaseGenerateDdlRequest();
        request.setCodes(codes);
        request.setFilterExcludeNote(filterExcludeNote);
        request.setFilterNote(filterNote);
        request.setReplacement(replacement);
        return metaDatabaseService.doTableDdl(request);
    }

    @GetMapping("/metadata/tables/downloadDdl")
    public void downloadAllDdl(String codes,
                               @RequestParam(name = "replacement") String replacement,
                               @RequestParam(name = "type", required = false, defaultValue = "3") Integer type,
                               HttpServletResponse response) {
        MetaDatabaseDdlRequest request = new MetaDatabaseDdlRequest();
        request.setCodes(codes);
        request.setReplacement(replacement);
        List<MetaDatabaseDdlResponse> allDdl = metaDatabaseService.getTableDdl(request);
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ZipOutputStream zip = new ZipOutputStream(outputStream);
            for (MetaDatabaseDdlResponse metaDatabaseDdlResponse : allDdl) {
                StringWriter sw = new StringWriter();
                String data;
                if (DdlOpTypeEnum.TABLE.getCode() == type) {
                    data = metaDatabaseDdlResponse.getCreateTable();
                } else if (DdlOpTypeEnum.TRIGGER.getCode() == type) {
                    data = metaDatabaseDdlResponse.getCreateTriggers();
                } else {
                    data = metaDatabaseDdlResponse.getCreateDdl();
                }
                if (data == null) {
                    data = "";
                }
                sw.append(data);
                zip.putNextEntry(new ZipEntry(metaDatabaseDdlResponse.getNote() + "_" + replacement + ".sql"));
                IOUtils.write(sw.toString(), zip, "UTF-8");
                IOUtils.closeQuietly(sw);
                zip.closeEntry();
            }
            IOUtils.closeQuietly(zip);
            byte[] data = outputStream.toByteArray();
            response.reset();
            response.setHeader("Content-Disposition", "attachment; filename=\"sql_" + replacement + "_" + type + ".zip\"");
            response.addHeader("Content-Length", "" + data.length);
            response.setContentType("application/octet-stream; charset=UTF-8");
            IOUtils.write(data, response.getOutputStream());
            IOUtils.closeQuietly(zip);
        } catch (Exception e) {
            log.error("下载文件失败 e {}", e);
        }
    }

    @GetMapping("/metadata/tables/getMyCatXml")
    public String getMyCatXml(@RequestParam(name = "codes") String codes) {
        return metaDatabaseService.getXml(codes);
    }

    @GetMapping("/metadata/tables/getTables")
    public String getTables(@RequestParam(name = "code") String code) {
        return metaDatabaseService.getTables(code);
    }

    @GetMapping("/metadata/tables/downloadMyCatXml")
    public String downloadXml(@RequestParam(name = "codes") String codes, HttpServletResponse response, String path) {
        String xml;
        ServletOutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            xml = metaDatabaseService.getXml(codes);
            os = response.getOutputStream();
            bos = new BufferedOutputStream(os);
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment; filename=mycat_schema.xml");
            bos.write(xml.getBytes());
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != bos) {
                try {
                    bos.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }

    @GetMapping("/metadata/tables/byCode")
    public BaseResponse<List<MetaDatabaseTableDefinition>> metaTables(@RequestParam(name = "code") String code) {
        return BaseResponse.success(metaDatabaseService.metaTables(code));
    }

    @GetMapping("/metadata/export/byCode")
    public BaseResponse<String> export(@RequestParam(name = "code") String code, Integer sheetName, HttpServletResponse response) {
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
