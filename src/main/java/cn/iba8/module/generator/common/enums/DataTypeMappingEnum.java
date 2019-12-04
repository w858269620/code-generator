package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

 /*
  * @Author sc.wan
  * @Description java与mysql数据映射
  * @Date 12:41 2019/7/4
  **/
@AllArgsConstructor
@Getter
public enum  DataTypeMappingEnum {

    TINYINT("TINYINT", "Integer"),
    SMALLINT("SMALLINT", "Integer"),
    INTEGER("INTEGER", "Integer"),
    INT("INT", "Integer"),
    BIGINT("BIGINT", "Long"),
    FLOAT("FLOAT", "Float"),
    DOUBLE("DOUBLE", "Double"),
    NUMERIC("NUMERIC", "java.math.BigDecimal"),
    DECIMAL("DECIMAL", "java.math.BigDecimal"),
    CHAR("CHAR", "Boolean"),
    VARCHAR("VARCHAR", "String"),
    LONGVARCHAR("LONGVARCHAR", "String"),
    DATE("DATE", "java.util.Date"),
    TIME("TIME", "java.util.Date"),
    TIMESTAMP("TIMESTAMP", "java.util.Date"),
    TEXT("TEXT", "String"),
    MEDIUMTEXT("MEDIUMTEXT", "String"),
    LONGTEXT("LONGTEXT", "String"),
    JSON("JSON", "String"),
    ;

    private String mysqlType;

    private String javaType;

    public static String mysqlTypeToJavaType(String mysqlType) {
        DataTypeMappingEnum[] dataTypeMappingEnums = DataTypeMappingEnum.values();
        for (DataTypeMappingEnum dataTypeMappingEnum : dataTypeMappingEnums) {
            if (dataTypeMappingEnum.getMysqlType().equals(mysqlType)) {
                return dataTypeMappingEnum.getJavaType();
            }
        }
        return DataTypeMappingEnum.VARCHAR.getJavaType();
    }

}
