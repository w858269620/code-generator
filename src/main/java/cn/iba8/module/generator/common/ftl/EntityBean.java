package cn.iba8.module.generator.common.ftl;

import cn.iba8.module.generator.common.enums.DataTypeMappingEnum;
import cn.iba8.module.generator.common.util.ClazzNameUtil;
import cn.iba8.module.generator.common.util.NameConvertUtil;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTable;
import cn.iba8.module.generator.repository.entity.MetaDatabaseTableColumn;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class EntityBean implements Serializable {

    private String packagePre;

    private String clazzName;

    private String tableName;

    private String idTypeName = "Long";

    private int idStrategy = 1;

    private String idStrategyClazz = "com.chitic.module.core.constant.ChiticCoreConstant.ID_GENERATOR_COMMON";

    private List<EntityColumn> columns;

    public static EntityBean of(MetaDatabaseTable table, List<MetaDatabaseTableColumn> tableColumns) {
        EntityBean bean = new EntityBean();
        bean.setClazzName(ClazzNameUtil.entityName(table.getTableName()));
        if (StringUtils.isNotBlank(table.getIdStrategy())) {
            bean.setIdStrategyClazz(table.getIdStrategy());
        }
        List<EntityColumn> columns = new ArrayList<>();
        bean.setColumns(columns);
        tableColumns.stream().forEach(r -> {
            EntityColumn entityColumn = new EntityColumn();
            columns.add(entityColumn);
            entityColumn.setColumnComment(r.getColumnComment());
            entityColumn.setColumnName(r.getColumnName());
            entityColumn.setId(r.isPrimaryKey());
            entityColumn.setJavaName(NameConvertUtil.toCamelCase(r.getColumnName()));
            entityColumn.setLength(r.getColumnSize());
            entityColumn.setJavaType(DataTypeMappingEnum.mysqlTypeToJavaType(r.getColumnType()));
            entityColumn.setNullable(r.getNullable() == 1);
            if (r.isPrimaryKey()) {
                bean.setIdTypeName(entityColumn.getJavaType());
            }
        });
        return bean;
    }

    @Data
    public static class EntityColumn implements Serializable {

        private String columnName;

        private String columnComment;

        private String javaType;

        private String javaName;

        private boolean nullable = true;

        private int length = 255;

        private boolean id = false;

    }

}
