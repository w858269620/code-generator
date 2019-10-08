package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

 /*
  * @Author sc.wan
  * @Description /日志变动分类
  * @Date 12:41 2019/7/4
  **/
@AllArgsConstructor
@Getter
public enum MetaDatabaseChangeLogTypeEnum {
    DATABASE(0, "数据库"),
    TABLE(1, "数据库表"),
    FIELD(2, "字段"),
    ;

    private int code;

    private String name;

    public static String code2name(int code) {
        MetaDatabaseChangeLogTypeEnum[] dataTypeMappingEnums = MetaDatabaseChangeLogTypeEnum.values();
        for (MetaDatabaseChangeLogTypeEnum logTypeEnum : dataTypeMappingEnums) {
            if (logTypeEnum.getCode() == code) {
                return logTypeEnum.getName();
            }
        }
        return "";
    }

}
