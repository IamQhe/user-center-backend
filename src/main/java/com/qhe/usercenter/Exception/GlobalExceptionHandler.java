package com.qhe.usercenter.Exception;

import com.qhe.usercenter.common.BaseResponse;
import com.qhe.usercenter.common.BusinessCode;
import com.qhe.usercenter.common.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author IamQhe
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException" + e.getMessage(), e);
        return ResponseUtils.fail(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse businessExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResponseUtils.fail(BusinessCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
