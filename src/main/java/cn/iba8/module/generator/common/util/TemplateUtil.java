package cn.iba8.module.generator.common.util;

import cn.iba8.module.generator.common.ftl.TemplateDefinition;
import cn.iba8.module.generator.repository.entity.*;
import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.util.CollectionUtils;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public abstract class TemplateUtil {

    public static String toPath(String packageDir) {
        String[] split = packageDir.split("\\.");
        return StringUtils.join(split, "/");
    }

    public static String toClassName(String tableName) {
        String str = CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, tableName);
        return str.substring(0,1).toUpperCase().concat(str.substring(1).toLowerCase());
    }

    public static String toJavaField(String dbField) {
        String s = dbField.toLowerCase();
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.UPPER_CAMEL, s);
    }

    public static String entityName(String table) {
        return NameConvertUtil.toCapitalizeCamelCase(table);
    }

    public static String getContent(TemplateDefinition.TemplateContent templateContent) {
        Module module = templateContent.getModule();
        CodeTemplate codeTemplate = templateContent.getCodeTemplate();
        Map<String, CodeTemplateSuffix> codeTemplateSuffixMap = templateContent.getCodeTemplateSuffixMap();
        Map<String, CodeTemplateCodeClass> codeTemplateCodeClassMap = templateContent.getCodeTemplateCodeClassMap();
        TemplateDefinition.TableColumnBean tableColumnBean = templateContent.getTableColumnBean();
        Map<String, Object> map = new HashMap<>();
        MetaDatabaseTable metaDatabaseTable = tableColumnBean.getMetaDatabaseTable();
        String entityName = toClassName(metaDatabaseTable.getTableName());
        CodeTemplateSuffix codeTemplateSuffix = codeTemplateSuffixMap.get(codeTemplate.getType());
        map.put("entityName", entityName);
        map.put("fieldEntityName", toJavaField(entityName));
        map.put("clazzName", entityName + codeTemplateSuffix.getFileSuffix());
        map.put("packagePrefix", module.getPackageName());
        map.put("module", module);
        map.put("packageSuffix", codeTemplateSuffix.getPackageSuffix());
        map.put("columns", tableColumnBean.getMetaDatabaseTableColumns());
        map.put("tableName", metaDatabaseTable.getTableName());
        map.put("table", metaDatabaseTable);
        map.put("codeTemplateSuffix", codeTemplateSuffixMap);
        map.put("codeTemplateClass", codeTemplateCodeClassMap);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        map.put("datetime", sdf.format(new Date()));
        return getContent(codeTemplate.getTemplate(), map);
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
