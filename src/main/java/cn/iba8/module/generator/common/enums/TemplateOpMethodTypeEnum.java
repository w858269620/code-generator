package cn.iba8.module.generator.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TemplateOpMethodTypeEnum {

    SAVE("save", "新增或修改"),
    DETAIL("detail", "详情"),
    PAGE("page", "分页"),
    EXCEL_EXPORT("excelExport", "excel导出"),
    DELETE("delete", "删除"),
    ;

    private String name;

    private String note;

    public static boolean contains(String name) {
        return null != ofName(name);
    }

    public static TemplateOpMethodTypeEnum ofName(String name) {
        for (TemplateOpMethodTypeEnum templateTypeEnum : TemplateOpMethodTypeEnum.values()) {
            if (templateTypeEnum.name.equals(name)) {
                return templateTypeEnum;
            }
        }
        return null;
    }

    public static TemplateOpMethodTypeEnum ofFilename(String filename) {
        int i = filename.lastIndexOf(".");
        String name = filename.substring(0, i);
        return ofName(name);
    }

}
