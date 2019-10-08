package cn.iba8.module.generator.common.response;

import cn.iba8.module.generator.common.BaseException;
import cn.iba8.module.generator.common.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> implements Serializable {

    private String code;

    private String message;

    private T data;

    private boolean success;

    public boolean isSuccess() {
        return ResponseCode.SUCCESS.getCode().equals(this.code);
    }

    public T whenSuccess() throws BaseException {
        if (!this.isSuccess()) {
            throw BaseException.of(this.code, this.message);
        }
        return this.data;
    }

    public static <T> BaseResponse<T> success(T t) {
        return success(t, ResponseCode.SUCCESS.getMessage());
    }

    public static <T> BaseResponse<T> success(T t, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setMessage(message);
        response.setData(t);
        return response;
    }

    public static <T> BaseResponse<T> fail(String code, String message) {
        BaseResponse<T> response = new BaseResponse<>();
        response.setCode(code);
        response.setMessage(message);
        return response;
    }

}
