package com.kira.ai.codereview.config;

import com.kira.ai.codereview.comcmon.constants.ResultCode;
import com.kira.ai.codereview.comcmon.exception.BusinessException;
import com.kira.ai.codereview.comcmon.exception.SystemException;
import com.kira.ai.codereview.comcmon.model.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;

/**
 * @author: kira
 * @date: 2025/04/24 09:26
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<R<String>> handleBusinessException(BusinessException e) {
        log.error("业务异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(R.fail(e.getResultCode(), e.getMessage()));
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<R<String>> handleSystemException(SystemException e) {
        log.error("系统异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.fail(ResultCode.SystemError));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<R<String>> handleException(Exception e) {
        log.error("系统异常", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(R.fail(ResultCode.SystemError));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<R<String>> handleValidateException(ConstraintViolationException e) {
        log.warn("校验异常", e);
        // 不合格的字段，可能有多个，只需要返回其中一个提示用户就行
        // 比如密码为空
        ArrayList<ConstraintViolation<?>> constraintViolations = new ArrayList<>(
                e.getConstraintViolations());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(R.fail(ResultCode.ValidateError,
                        constraintViolations.get(0).getMessage()));

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<R<String>> handleValidateExceptionForSpring(
            MethodArgumentNotValidException e) {
        log.warn("校验异常", e);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(R.fail(ResultCode.ValidateError,
                        e.getBindingResult().getAllErrors().get(0)
                                .getDefaultMessage()));
    }


}
