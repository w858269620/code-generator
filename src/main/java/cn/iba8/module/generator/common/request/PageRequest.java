package cn.iba8.module.generator.common.request;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/***
 * 分页请求参数
 * @Auther: wansc
 * @Date: 2018/10/16 10:21
 * @Description:
 */
@Data
public class PageRequest extends OrderByRequest {

    @NotNull(message = "pageNo不能为空")
    @Min(value = 1, message = "pageNo不能小于1")
    @Max(value = Integer.MAX_VALUE, message = "pageNo不能大于" + Integer.MAX_VALUE)
    private Integer pageNo = 1;

    @NotNull(message = "pageSize不能为空")
    @Min(value = 1, message = "pageSize不能小于1")
    @Max(value = Integer.MAX_VALUE, message = "pageSize不能大于" + Integer.MAX_VALUE)
    private Integer pageSize = 20;

    public Integer jpaPageNo() {
        if (null != pageNo) {
            return pageNo - 1;
        }
        return 0;
    }

    public static Integer toTotalPages(Long count, Integer pageSize) {
        if (null == pageSize || pageSize == 0) {
            pageSize = 50;
        }
        if (null == count) {
            return 0;
        }
        return count.intValue()/pageSize + (count % pageSize == 0 ? 0 : 1);
    }

}
