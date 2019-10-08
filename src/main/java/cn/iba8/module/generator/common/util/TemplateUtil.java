package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.ResponseCode;
import cn.iba8.module.generator.common.ftl.TemplateDefinition;
import cn.iba8.module.generator.repository.entity.CodeTemplate;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTable;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTableColumn;
import cn.iba8.module.generator.repository.entity.Module;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public abstract class TemplateUtil {

    public static void main(String[] args) {
        String template = "#foreach ($column in $columns)\n" +
                "#if($column.columnName != 'id' && $column.columnName != 'creator' && $column.columnName != 'create_date')\n" +
                "    /**\n" +
                "     * $column.comments\n" +
                "     */\n" +
                "\tprivate $column.columnComment;\n" +
                "#end\n" +
                "#end";
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        VelocityContext context = new VelocityContext();
        List<MetaDatabaseTableColumn> columns = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MetaDatabaseTableColumn column = new MetaDatabaseTableColumn();
            column.setColumnName("name" + i);
            column.setColumnComment("comment" + i);
            columns.add(column);
        }
        context.put("columns", columns);
        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, "", template);
        System.out.println(writer.toString());
    }


    public byte[] generatorCode(List<CodeTemplate> codeTemplates, Module module, MetaDatabaseTable metaDatabaseTables, List<MetaDatabaseTableColumn> metaDatabaseTableColumns) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);

        List<TemplateDefinition.TemplateFileBean> templateFileBeans = TemplateDataUtil.getTemplateFileBeanList(module, metaDatabaseTables, metaDatabaseTableColumns, codeTemplates);
        for (TemplateDefinition.TemplateFileBean templateFileBean : templateFileBeans) {
            try {
                zip.putNextEntry(new ZipEntry(FileNameUtil.toPath(templateFileBean.getFileDir())));
                IOUtils.write(templateFileBean.getContent(), zip, "UTF-8");
                zip.closeEntry();
            } catch (IOException e) {
                throw BaseException.of(ResponseCode.SYSTEM_ERROR.getCode(), "渲染模板失败，表名：" + metaDatabaseTables.getTableName());
            }
        }
        IOUtils.closeQuietly(zip);
        return outputStream.toByteArray();
    }


}
