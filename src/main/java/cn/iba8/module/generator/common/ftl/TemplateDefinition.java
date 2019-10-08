package cn.iba8.module.generator.common.ftl;

import cn.iba8.module.generator.common.enums.TemplateTypeEnum;
import cn.iba8.module.generator.common.util.FileNameUtil;
import cn.iba8.module.generator.common.util.TemplateDataUtil;
import cn.iba8.module.generator.repository.entity.CodeTemplate;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTable;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTableColumn;
import cn.iba8.module.generator.repository.entity.Module;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class TemplateDefinition {

    @Data
    public static class TemplateFileBean implements Serializable {

        private String filename;

        private String fileDir;

        private String content;

        public static List<TemplateFileBean> of(TableColumnBean tableColumnBean, List<CodeTemplate> codeTemplates) {
            List<TemplateFileBean> target = new ArrayList<>();
            Module module = tableColumnBean.getModule();
            String packagePrefix = module.getPackageName();
            for (CodeTemplate r : codeTemplates) {
                TemplateTypeEnum templateTypeEnum = TemplateTypeEnum.of(r.getType());
                String template = r.getTemplate();
                switch (templateTypeEnum) {
                    case REPOSITORY_ENTITY:
                        target.add(TemplateFileBean.ofRepositoryEntity(packagePrefix, template, tableColumnBean));
                        break;
                    case REPOSITORY_DAO:
                        target.add(TemplateFileBean.ofRepositoryDao(packagePrefix, template, tableColumnBean));
                        break;
                    case SERVICE:
                        target.add(TemplateFileBean.ofServic(packagePrefix, template, tableColumnBean));
                        break;
                    case SERVICE_BIZ:
                        target.add(TemplateFileBean.ofServiceBiz(packagePrefix, template, tableColumnBean));
                        break;
                    case SERVICE_IMPL:
                        target.add(TemplateFileBean.ofServiceImpl(packagePrefix, template, tableColumnBean));
                        break;
                    case API:
                        target.add(TemplateFileBean.ofApi(packagePrefix, template, tableColumnBean));
                        break;
                    case API_IMPL:
                        target.add(TemplateFileBean.ofApiImpl(packagePrefix, template, tableColumnBean));
                        break;
                    case API_REQUEST:
                        target.add(TemplateFileBean.ofApiRequest(packagePrefix, template, tableColumnBean));
                        break;
                    case API_RESPONSE:
                        target.add(TemplateFileBean.ofApiResponse(packagePrefix, template, tableColumnBean));
                        break;
                    case API_EXCEL:
                        target.add(TemplateFileBean.ofApiExcel(packagePrefix, template, tableColumnBean));
                        break;
                    case VUE_INDEX:
                        target.add(TemplateFileBean.ofApiVueIndex(packagePrefix, template, tableColumnBean));
                        break;
                    case VUE_ADD_OR_UPDATE:
                        target.add(TemplateFileBean.ofApiVueAddOrUpdate(packagePrefix, template, tableColumnBean));
                        break;
                    default:
                        break;
                }
            }
            return target;
        }

        public static TemplateFileBean ofRepositoryEntity(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            MetaDatabaseTable metaDatabaseTable = tableColumnBean.getMetaDatabaseTable();
            templateFileBean.setFilename(FileNameUtil.getRepositoryEntityName(metaDatabaseTable.getTableName()));
            templateFileBean.setFileDir(packagePrefix + ".repository.entity");
            templateFileBean.setContent(TemplateDataUtil.getContent(packagePrefix, template, tableColumnBean, TemplateTypeEnum.REPOSITORY_ENTITY));
            return templateFileBean;
        }

        public static TemplateFileBean ofRepositoryDao(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofServiceBiz(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofServic(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofServiceImpl(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofApi(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofApiImpl(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofApiRequest(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofApiResponse(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofApiExcel(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofApiVueIndex(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }

        public static TemplateFileBean ofApiVueAddOrUpdate(String packagePrefix, String template, TableColumnBean tableColumnBean) {
            TemplateFileBean templateFileBean = new TemplateFileBean();
            return templateFileBean;
        }
    }

    @Data
    public static class TableColumnBean implements Serializable {
        private Module module;
        private MetaDatabaseTable metaDatabaseTable;
        private List<MetaDatabaseTableColumn> metaDatabaseTableColumns = new ArrayList<>();

        public static TableColumnBean of(Module module, MetaDatabaseTable metaDatabaseTable, List<MetaDatabaseTableColumn> metaDatabaseTableColumns) {
            TableColumnBean tableColumnBean = new TableColumnBean();
            tableColumnBean.setMetaDatabaseTable(metaDatabaseTable);
            tableColumnBean.setModule(module);
            tableColumnBean.setMetaDatabaseTableColumns(metaDatabaseTableColumns);
            return tableColumnBean;
        }
    }

}
