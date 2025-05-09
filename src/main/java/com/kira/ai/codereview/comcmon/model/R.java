package com.kira.ai.codereview.comcmon.model;

import com.kira.ai.codereview.comcmon.constants.BaseEnum;
import com.kira.ai.codereview.comcmon.constants.ResultCode;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author: kira
 * @date: 2025/04/22 14:55
 **/
@Data
public class R<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String msg;

    private T result;

    public R() {

    }

    private R(T result, Integer code, String msg) {
        this.result = result;
        this.code = code;
        this.msg = msg;
    }

    private R(T result, BaseEnum resultCode) {
        this.result = result;
        this.msg = resultCode.getName();
        this.code = resultCode.getCode();
    }

    public static R<String> ok() {
        return new R<>(ResultCode.Success.getName(), ResultCode.Success);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(data, ResultCode.Success);
    }

    public static R<String> fail(BaseEnum resultCode) {
        return new R<>(null, resultCode);
    }

    public static R<String> fail(BaseEnum resultCode, String msg) {
        return new R<>(null, resultCode.getCode(), msg);
    }

    public boolean isSuccess() {
        return Objects.equals(ResultCode.Success.getCode(), this.getCode());
    }
}
