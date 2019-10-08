package cn.iba8.module.generator.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CodeTemplateSaveRequest implements Serializable {

    private Long id;

    @NotBlank(message = "code不能为空")
    private String code;

    @NotBlank(message = "name不能为空")
    private String name;

    @NotBlank(message = "note不能为空")
    private String note;

    @NotBlank(message = "template不能为空")
    private String template;

    @NotNull(message = "type不能为空")
    private Integer type;

}
