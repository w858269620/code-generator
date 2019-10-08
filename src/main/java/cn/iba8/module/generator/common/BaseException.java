package cn.iba8.module.generator.common;

import lombok.Data;

@Data
public class BaseException extends RuntimeException {

    private String code;

    public BaseException() {
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public static BaseException of(String code, String message) {
        return new BaseException(code, message);
    }

    public static BaseException of(ResponseCode responseCode) {
        return new BaseException(responseCode.getCode(), responseCode.getMessage());
    }

}
