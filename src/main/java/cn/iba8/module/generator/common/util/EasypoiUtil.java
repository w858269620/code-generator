package cn.iba8.module.generator.common.util;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

public abstract class EasypoiUtil {

    /**
     * 模板导出excel
     *
     * @param templateExcel
     * @param dataMap  模板数据
     * @param fileName 导出文件名称
     * @param response
     */
    public static void templateExport(TemplateExportParams templateExcel,
                                      Map<String, Object> dataMap,
                                      String fileName,
                                      HttpServletResponse response) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(templateExcel, dataMap);
        if (workbook != null) {
            downLoadExcel(fileName, response, workbook);
        }
    }

    /**
     * 导出excel
     *
     * @param fileName
     * @param response
     * @param workbook
     * @throws IOException
     */
    public static void downLoadExcel(String fileName,
                                     HttpServletResponse response,
                                     Workbook workbook) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        workbook.write(response.getOutputStream());
    }
}
