package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.enums.TemplateTypeEnum;
import cn.iba8.module.generator.common.ftl.TemplateDefinition;
import cn.iba8.module.generator.repository.entity.CodeTemplate;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTable;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTableColumn;
import cn.iba8.module.generator.repository.entity.Module;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.util.CollectionUtils;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TemplateDataUtil {

    public static List<TemplateDefinition.TemplateFileBean> getTemplateFileBeanList(Module module, MetaDatabaseTable metaDatabaseTable, List<MetaDatabaseTableColumn> metaDatabaseTableColumns, List<CodeTemplate> codeTemplates) {
        TemplateDefinition.TableColumnBean tableColumnBean = TemplateDefinition.TableColumnBean.of(module, metaDatabaseTable, metaDatabaseTableColumns);
        List<TemplateDefinition.TemplateFileBean> templateFileBeanList = TemplateDefinition.TemplateFileBean.of(tableColumnBean, codeTemplates);
        return templateFileBeanList;
    }

    public static String getContent(String packagePrefix, String template, TemplateDefinition.TableColumnBean tableColumnBean, TemplateTypeEnum templateTypeEnum) {
        if (TemplateTypeEnum.REPOSITORY_ENTITY.equals(templateTypeEnum)) {
            return getContentRepositoryEntity(packagePrefix, template, tableColumnBean);
        }
        if (TemplateTypeEnum.REPOSITORY_DAO.equals(templateTypeEnum)) {
            return getContentRepositoryDao(packagePrefix, template, tableColumnBean);
        }
        return null;
    }

    private static String getContentRepositoryDao(String packagePrefix, String template, TemplateDefinition.TableColumnBean tableColumnBean) {
        Map<String, Object> map = new HashMap<>();
        MetaDatabaseTable metaDatabaseTable = tableColumnBean.getMetaDatabaseTable();
        String entityName = FileNameUtil.getRepositoryEntityName(metaDatabaseTable.getTableName());
        map.put("entityName", entityName);
        map.put("clazzName", entityName + TemplateTypeEnum.REPOSITORY_DAO.getName());
        map.put("packagePrefix", packagePrefix);
        return getContent(template, map);
    }

    private static String getContentRepositoryEntity(String packagePrefix, String template, TemplateDefinition.TableColumnBean tableColumnBean) {
        Map<String, Object> map = new HashMap<>();
        MetaDatabaseTable metaDatabaseTable = tableColumnBean.getMetaDatabaseTable();
        map.put("columns", tableColumnBean.getMetaDatabaseTableColumns());
        map.put("clazzName", FileNameUtil.getRepositoryEntityName(metaDatabaseTable.getTableName()));
        map.put("tableName", metaDatabaseTable.getTableName());
        map.put("packagePrefix", packagePrefix);
        return getContent(template, map);
    }

    private static String getContent(String template, Map<String, Object> map) {
        VelocityEngine engine = new VelocityEngine();
        engine.init();
        VelocityContext context = new VelocityContext();
        if (null != map && !CollectionUtils.isEmpty(map.keySet())) {
            map.keySet().forEach(r -> context.put(r, map.get(r)));
        }
        StringWriter writer = new StringWriter();
        engine.evaluate(context, writer, "", template.replaceAll("[ ]*(#if|#else|#elseif|#end|#set|#foreach)", "$1"));
        return writer.toString();
    }

}
