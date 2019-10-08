package cn.iba8.module.generator.common.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
public class StatusRequest implements Serializable {

    @ApiModelProperty(value = "ids")
    @NotNull(message = "id不能为空")
    @Size(min = 1, message = "至少选择一个")
    private Set<Long> ids;

}
