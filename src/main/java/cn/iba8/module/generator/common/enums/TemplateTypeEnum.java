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

    REPOSITORY_ENTITY("Entity", "数据库实体"),
    REPOSITORY_DAO( "Repository", "数据库接口"),
    SERVICE("Service", "业务层接口"),
    SERVICE_BIZ("BizService", "业务层细化"),
    SERVICE_IMPL( "ServiceImpl", "业务层接口实现"),
    API("Api", "REST接口"),
    API_IMPL("ApiImpl", "REST接口实现"),
    API_SAVE_REQUEST("SaveRequest", "REST接口请求"),
    API_PAGE_REQUEST("PageRequest", "REST接口请求"),
    API_SAVE_RESPONSE("SaveResponse", "新增请求"),
    API_PAGE_RESPONSE("PageResponse", "分页响应"),
    API_DETAIL_RESPONSE("DetailResponse", "详情响应"),
    API_DELETE_RESPONSE("DeleteResponse", "删除响应"),
    API_EXCEL_IMPORT_REQUEST("ExcelImportRequest", "Excel导入请求"),
    API_EXCEL_EXPORT_RESPONSE("ExcelExportResponse", "Excel导出响应对象"),
    VUE_INDEX("VueList", "VUE列表"),
    VUE_ADD_OR_UPDATE("VueSave", "VUE新增或修改"),
    ;

    private String name;

    private String note;

    public static boolean contains(String name) {
        return null != ofName(name);
    }

    public static TemplateTypeEnum ofName(String name) {
        for (TemplateTypeEnum templateTypeEnum : TemplateTypeEnum.values()) {
            if (templateTypeEnum.name.equals(name)) {
                return templateTypeEnum;
            }
        }
        return null;
    }


    public static TemplateTypeEnum ofFilename(String filename) {
        int i = filename.lastIndexOf(".");
        String name = filename.substring(0, i);
        return ofName(name);
    }

}
