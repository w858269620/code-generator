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
public enum TemplateTypeEnum {

    REPOSITORY_ENTITY(11, "数据库实体"),
    REPOSITORY_DAO(12, "数据库接口"),
    SERVICE(21, "业务层接口"),
    SERVICE_BIZ(22, "业务层细化"),
    SERVICE_IMPL(23, "业务层接口实现"),
    API(31, "REST接口"),
    API_IMPL(32, "REST接口实现"),
    API_REQUEST(33, "REST接口请求"),
    API_RESPONSE(34, "REST接口响应"),
    API_EXCEL(34, "Excel对象"),
    VUE_INDEX(41, "VUE列表"),
    VUE_ADD_OR_UPDATE(42, "VUE新增或修改"),
    ;

    private int code;

    private String name;

    public static TemplateTypeEnum of(int code) {
        for (TemplateTypeEnum templateTypeEnum : TemplateTypeEnum.values()) {
            if (code == templateTypeEnum.code) {
                return templateTypeEnum;
            }
        }
        return null;
    }

    public static String code2name(int code) {
        TemplateTypeEnum[] dataTypeMappingEnums = TemplateTypeEnum.values();
        for (TemplateTypeEnum logTypeEnum : dataTypeMappingEnums) {
            if (logTypeEnum.getCode() == code) {
                return logTypeEnum.getName();
            }
        }
        return "";
    }

}
