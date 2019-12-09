package cn.iba8.module.generator.common.ftl;

import cn.iba8.module.generator.common.enums.DataTypeMappingEnum;
import cn.iba8.module.generator.common.enums.TemplateLevelEnum;
import cn.iba8.module.generator.common.util.CopyUtil;
import cn.iba8.module.generator.common.util.TemplateUtil;
import cn.iba8.module.generator.repository.entity.*;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

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
            Map<String, List<CodeTemplate>> levelTemplateMap = codeTemplates.stream().collect(Collectors.groupingBy(CodeTemplate::getLevel));
            List<CodeTemplate> tableTemplates = levelTemplateMap.get(TemplateLevelEnum.TABLE.getName());
            List<CodeTemplate> moduleTemplates = levelTemplateMap.get(TemplateLevelEnum.MODULE.getName());
            Map<String, String> constantMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(tableTemplates)) {
                for (CodeTemplate r : tableTemplates) {
                    CodeTemplateSuffix codeTemplateSuffix = codeTemplateSuffixMap.get(r.getType());
                    TemplateContent templateContent = TemplateContent.of(module, r, codeTemplateSuffixMap, codeTemplateCodeClassMap, tableColumnBean);
                    String content = TemplateUtil.getContent(templateContent);
                    TemplateFileBean templateFileBean = new TemplateFileBean();
                    templateFileBean.setContent(content);
                    templateFileBean.setFileDir(packagePrefix + codeTemplateSuffix.getPackageSuffix());
                    templateFileBean.setFilename(TemplateUtil.toClassName(metaDatabaseTable.getTableName()) + codeTemplateSuffix.getFileSuffix());
                    target.add(templateFileBean);
                }
            }
            if (!CollectionUtils.isEmpty(moduleTemplates)) {
                for (CodeTemplate r : moduleTemplates) {
                    CodeTemplateSuffix codeTemplateSuffix = codeTemplateSuffixMap.get(r.getType());
                    TemplateContent templateContent = TemplateContent.of(module, r, codeTemplateSuffixMap, codeTemplateCodeClassMap, tableColumnBean);
                    String content = TemplateUtil.getContent(templateContent);
                    TemplateFileBean templateFileBean = new TemplateFileBean();
                    templateFileBean.setContent(content);
                    templateFileBean.setFileDir(packagePrefix + codeTemplateSuffix.getPackageSuffix());
                    templateFileBean.setFilename(TemplateUtil.toClassName(metaDatabaseTable.getTableName()) + codeTemplateSuffix.getFileSuffix());
                    target.add(templateFileBean);
                }
            }
            return target;
        }
    }



    @Data
    public static class TemplateContent implements Serializable {
        private Module module;
        private CodeTemplate codeTemplate;
        private Map<String, CodeTemplateSuffix> codeTemplateSuffixMap;
        private Map<String, CodeTemplateCodeClass> codeTemplateCodeClassMap;
        private TemplateDefinition.TableColumnBean tableColumnBean;

        public static TemplateContent of(Module module, CodeTemplate codeTemplate, Map<String, CodeTemplateSuffix> codeTemplateSuffixMap, Map<String, CodeTemplateCodeClass> codeTemplateCodeClassMap, TemplateDefinition.TableColumnBean tableColumnBean) {
            TemplateContent templateContent = new TemplateContent();
            templateContent.setCodeTemplate(codeTemplate);
            templateContent.setModule(module);
            templateContent.setCodeTemplateCodeClassMap(codeTemplateCodeClassMap);
            templateContent.setCodeTemplateSuffixMap(codeTemplateSuffixMap);
            tableColumnBean.constrains();
            templateContent.setTableColumnBean(tableColumnBean);
            return templateContent;
        }

    }

    @Data
    public static class TableColumnBean implements Serializable {
        private Module module;
        private MetaDatabaseTable metaDatabaseTable;
        private List<TableColumnJavaBean> metaDatabaseTableColumns = new ArrayList<>();
        private List<String> constrainImports = new ArrayList<>();

        public void constrains() {
            Set<String> importSet = new HashSet<>();
            for (TableColumnJavaBean tableColumnJavaBean : metaDatabaseTableColumns) {
                Set<String> annos = new HashSet<>();
                if (tableColumnJavaBean.getColumnSize() > 0 && "String".equals(tableColumnJavaBean.getJavaType())) {
                    importSet.add("import org.hibernate.validator.constraints.Length;");
                    annos.add("@Length(max = " + tableColumnJavaBean.getColumnSize() + ")");
                }
                if (tableColumnJavaBean.getNullable() == 0 && !"id".equals(tableColumnJavaBean.getJavaName())) {
                    if ("String".equals(tableColumnJavaBean.getJavaType())) {
                        importSet.add("import javax.validation.constraints.NotBlank;");
                        annos.add("@NotBlank(message = \"\")");
                    } else {
                        importSet.add("import javax.validation.constraints.NotNull;");
                        annos.add("@NotNull(message = \"\")");
                    }
                }
                if (!CollectionUtils.isEmpty(annos)) {
                    tableColumnJavaBean.setConstrains(new ArrayList<>(annos));
                }
            }
            if (!CollectionUtils.isEmpty(importSet)) {
                constrainImports = new ArrayList<>(importSet);
            }
        }

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
        private List<String> constrains = new ArrayList<>();



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
            copy.setColumnName(copy.getColumnName().toLowerCase());
            return copy;
        }
    }

}
