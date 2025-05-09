package com.kira.ai.codereview.comcmon.exception;

import com.kira.ai.codereview.comcmon.constants.BaseEnum;
import com.kira.ai.codereview.comcmon.constants.ResultCode;
import lombok.Data;

/**
 * @ClassName BusinessException
 * @Autohor Kira
 * @Date 2025/3/17 22:57
 * @Description
 **/

@Data
public class BusinessException extends RuntimeException {
    BaseEnum resultCode;

    public BusinessException(String msg) {
        super(msg);
        this.resultCode = ResultCode.Fail;
    }

    public BusinessException(BaseEnum resultCode) {
        super(resultCode.getName());
        this.resultCode = resultCode;
    }

    public BusinessException(BaseEnum resultCode, String msg) {
        super(msg);
        this.resultCode = resultCode;
    }
}
