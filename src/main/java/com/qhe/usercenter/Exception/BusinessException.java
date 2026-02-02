package com.qhe.usercenter.Exception;

import com.qhe.usercenter.common.BusinessCode;

/**
 * 自定义业务异常
 *
 * @author IamQhe
 */
public class BusinessException extends RuntimeException{
    private final int code;
    private final String description;

    public BusinessException(int code, String message, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(BusinessCode errCode) {
        super(errCode.getMessage());
        this.code = errCode.getCode();
        this.description = errCode.getDescription();
    }

    public BusinessException(BusinessCode errCode, String description) {
        super(errCode.getMessage());
        this.code = errCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
