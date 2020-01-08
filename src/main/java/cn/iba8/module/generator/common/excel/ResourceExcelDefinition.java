package cn.iba8.module.generator.common.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.iba8.module.generator.common.util.ValidatorUtil;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
public class ResourceExcelDefinition implements Serializable {

    @NotBlank(message = "系统编号必须填写")
    @Excel(name = "系统", needMerge = true)
    private String system;

    @Valid
    @NotNull(message = "资源不能为空")
    @Size(min = 1, message = "资源必须大于1")
    @ExcelCollection(name = "资源")
    private List<ResourcePoolExcelRequest> list;

    public String validate() {
        StringBuffer s = new StringBuffer();
        String d = ValidatorUtil.validate2Msg(this).toString();
        StringBuffer vs = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            vs.append(ValidatorUtil.validate2Msg(list.get(i)).toString());
        }
        if (StringUtils.isNotBlank(d)) {
            s.append(d);
        }
        if (StringUtils.isNotBlank(vs)) {
            s.append("系统【" + this.system + "】：" + vs);
        }
        if (StringUtils.isNotBlank(s.toString())) {
            s.append(";\r\n");
        }
        return s.toString();

    }

    @Data
    public static class ResourcePoolExcelRequest implements Serializable {

        @Excel(name = "唯一标记", width = 50)
        @NotBlank
        private String sign;

        @Excel(name = "类目")
        @NotNull
        @Min(0)
        @Max(3)
        private Integer category;

        @NotBlank(message = "名称不能为空")
        @Excel(name = "名称", width = 50)
        private String name;

        @Excel(name = "启用状态")
        private Integer enabled;

        @Excel(name = "页面连接", width = 50)
        private String href;

        @Excel(name = "接口权限", width = 50)
        private String perms;

        @Excel(name = "排序")
        private Integer sort;

        @Excel(name = "页面打开方式")
        private String target;

        @Excel(name = "图标")
        private String icon;

        @Excel(name = "背景颜色")
        private String bgColor;

        @Excel(name = "备注")
        private String note;


    }


}
