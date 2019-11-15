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
public enum DdlOpTypeEnum {

    TABLE(1, "数据表"),
    TRIGGER(2, "触发器"),
    DDL(3, "数据库表和触发器"),
    ;

    private int code;

    private String name;

    public static DdlOpTypeEnum of(int code) {
        for (DdlOpTypeEnum templateTypeEnum : DdlOpTypeEnum.values()) {
            if (code == templateTypeEnum.code) {
                return templateTypeEnum;
            }
        }
        return null;
    }

    public static String code2name(int code) {
        DdlOpTypeEnum[] dataTypeMappingEnums = DdlOpTypeEnum.values();
        for (DdlOpTypeEnum logTypeEnum : dataTypeMappingEnums) {
            if (logTypeEnum.getCode() == code) {
                return logTypeEnum.getName();
            }
        }
        return "";
    }

}
