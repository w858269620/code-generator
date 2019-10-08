package cn.iba8.module.generator.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 *@description WebSocket响应数据
 *@author sc.wan
 *@datatime 2019/4/22 16:59
 */
@Data
public class WsResponse<T> implements Serializable {

    /** 数据类型 */
    private Integer type;

    /** clientId */
    private String clientId;

    /** 具体的数据 */
    private T data;

    public static <T> WsResponse<T> of(String clientId, Integer dataType, T data) {
        WsResponse<T> response = new WsResponse<>();
        response.setClientId(clientId);
        response.setType(dataType);
        response.setData(data);
        return response;
    }

}
