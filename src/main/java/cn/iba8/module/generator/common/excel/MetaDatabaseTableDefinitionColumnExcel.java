package cn.iba8.module.generator.common.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.iba8.module.generator.common.MetaDatabaseTableDefinition;
import cn.iba8.module.generator.common.util.CopyUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class MetaDatabaseTableDefinitionColumnExcel implements Serializable {

    @Excel(name = "列名", width = 20)
    private String columnName;

    @Excel(name = "数据类型", width = 20)
    private String columnType;

    @Excel(name = "长度", width = 20)
    private int columnSize;

    @Excel(name = "小数", width = 20)
    private int digits;

    @Excel(name = "是否允许为空", width = 20)
    private String nullable;

    @Excel(name = "是否主键", width = 20)
    private String primaryKey;

    @Excel(name = "备注", width = 20)
    private String columnComment;

    public static MetaDatabaseTableDefinitionColumnExcel of(MetaDatabaseTableDefinition.MetaDatabaseTableDefinitionColumn column) {
        MetaDatabaseTableDefinitionColumnExcel copy = CopyUtil.copy(column, MetaDatabaseTableDefinitionColumnExcel.class);
        if (1 == column.getNullable()) {
            copy.setNullable("是");
        } else {
            copy.setNullable("否");
        }
        if (column.isPrimaryKey()) {
            copy.setPrimaryKey("是");
        } else {
            copy.setPrimaryKey("否");
        }
        return copy;
    }

    public static List<MetaDatabaseTableDefinitionColumnExcel> of(List<MetaDatabaseTableDefinition.MetaDatabaseTableDefinitionColumn> columns) {
        List<MetaDatabaseTableDefinitionColumnExcel> target = new ArrayList<>();
        columns.forEach(r -> target.add(of(r)));
        return target;
    }

}
