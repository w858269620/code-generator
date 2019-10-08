package cn.iba8.module.generator.common.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CodeNotNullRequest implements Serializable {

    @NotBlank(message = "编号不能为空")
    private String code;

}
