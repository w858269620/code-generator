package cn.iba8.module.generator.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> implements Serializable {

    /**
     * 数据
     */
    private List<T> list;

    /**
     * 总条数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Integer totalPages;

    public static <T> PageResponse<T> of(List<T> list, Long totalCount, Integer totalPages) {
        return new PageResponse<>(list, totalCount, totalPages);
    }

    public static PageResponse empty() {
        PageResponse response = new PageResponse();
        response.setTotalCount(0L);
        response.setTotalPages(0);
        response.setList(new ArrayList<>());
        return response;
    }

}
