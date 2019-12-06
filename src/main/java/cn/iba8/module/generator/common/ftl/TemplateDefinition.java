package cn.iba8.module.generator.common.ftl;

import cn.iba8.module.generator.common.enums.DataTypeMappingEnum;
import cn.iba8.module.generator.common.util.CopyUtil;
import cn.iba8.module.generator.common.util.TemplateUtil;
import cn.iba8.module.generator.repository.entity.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class TemplateDefinition {

    @Data
    public static class TemplateFileBean implements Serializable {

        private String filename;

        private String fileDir;

        private String content;

        public static List<TemplateFileBean> of(TableColumnBean tableColumnBean, List<CodeTemplate> codeTemplates, Map<String, CodeTemplateSuffix> codeTemplateSuffixMap, Map<String, CodeTemplateCodeClass> codeTemplateCodeClassMap) {
            List<TemplateFileBean> target = new ArrayList<>();
            Module module = tableColumnBean.getModule();
            String packagePrefix = module.getPackageName();
            MetaDatabaseTable metaDatabaseTable = tableColumnBean.getMetaDatabaseTable();
            String tableComment = metaDatabaseTable.getTableComment();
            if (StringUtils.isNotBlank(tableComment)) {
                int i = tableComment.lastIndexOf("表");
                if (i > 0) {
                    tableComment = tableComment.substring(0, i);
                    metaDatabaseTable.setTableComment(tableComment);
                }
            }
            for (CodeTemplate r : codeTemplates) {
                CodeTemplateSuffix codeTemplateSuffix = codeTemplateSuffixMap.get(r.getType());
                String content = TemplateUtil.getContent(packagePrefix, r, codeTemplateSuffixMap, codeTemplateCodeClassMap, tableColumnBean);
                TemplateFileBean templateFileBean = new TemplateFileBean();
                templateFileBean.setContent(content);
                templateFileBean.setFileDir(packagePrefix + codeTemplateSuffix.getPackageSuffix());
                templateFileBean.setFilename(TemplateUtil.toClassName(metaDatabaseTable.getTableName()) + codeTemplateSuffix.getFileSuffix());
                target.add(templateFileBean);
            }
            return target;
        }

    }

    @Data
    public static class TableColumnBean implements Serializable {
        private Module module;
        private MetaDatabaseTable metaDatabaseTable;
        private List<TableColumnJavaBean> metaDatabaseTableColumns = new ArrayList<>();

        public static TableColumnBean of(Module module, MetaDatabaseTable metaDatabaseTable, List<MetaDatabaseTableColumn> metaDatabaseTableColumns) {
            TableColumnBean tableColumnBean = new TableColumnBean();
            tableColumnBean.setMetaDatabaseTable(metaDatabaseTable);
            tableColumnBean.setModule(module);
            List<TableColumnJavaBean> tableColumnJavaBeans = TableColumnJavaBean.ofList(metaDatabaseTableColumns);
            tableColumnBean.setMetaDatabaseTableColumns(tableColumnJavaBeans);
            return tableColumnBean;
        }

    }

    @Data
    public static class TableColumnJavaBean implements Serializable {
        private Long id;
        private Long metaDatabaseTableId;
        private String columnName;
        private String columnType;
        private String columnComment;
        private int columnSize;
        private int digits;
        private int nullable;
        private boolean primaryKey;
        private String javaName;
        private String javaType;

        public static List<TableColumnJavaBean> ofList(List<MetaDatabaseTableColumn> metaDatabaseTableColumns) {
            List<TableColumnJavaBean> idList = new ArrayList<>();
            List<TableColumnJavaBean> otherList = new ArrayList<>();
            for (MetaDatabaseTableColumn metaDatabaseTableColumn : metaDatabaseTableColumns) {
                TableColumnJavaBean tableColumnJavaBean = TableColumnJavaBean.of(metaDatabaseTableColumn);
                if (tableColumnJavaBean.isPrimaryKey()) {
                    idList.add(tableColumnJavaBean);
                } else {
                    otherList.add(tableColumnJavaBean);
                }
            }
            idList.addAll(otherList);
            return idList;
        }

        public static TableColumnJavaBean of(MetaDatabaseTableColumn metaDatabaseTableColumn) {
            TableColumnJavaBean copy = CopyUtil.copy(metaDatabaseTableColumn, TableColumnJavaBean.class);
            copy.setJavaName(TemplateUtil.toJavaField(metaDatabaseTableColumn.getColumnName()));
            copy.setJavaType(DataTypeMappingEnum.mysqlTypeToJavaType(metaDatabaseTableColumn.getColumnType()));
            if (copy.isPrimaryKey() && !"String".equals(copy.getJavaType())) {
                copy.setJavaType("Long");
            }
            return copy;
        }
    }

}
