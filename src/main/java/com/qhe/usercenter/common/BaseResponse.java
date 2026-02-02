package com.qhe.usercenter.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用响应封装
 *
 * @author IamQhe
 */
@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 实际响应
     */
    private T data;

    /**
     * 响应状态
     */
    private int code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应描述
     */
    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(BusinessCode businessCode) {
        this(businessCode.getCode(), null, businessCode.getMessage(), businessCode.getDescription());
    }
}
