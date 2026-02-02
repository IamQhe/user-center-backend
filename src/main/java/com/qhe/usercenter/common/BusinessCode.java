package com.qhe.usercenter.common;

/**
 * 业务响应枚举
 *
 * @author IamQhe
 */
public enum BusinessCode {

    BUSINESS_SUCCESS(200, "ok", ""),
    BUSINESS_ERROR_PARAM_ERROR(40000, "请求参数错误", ""),
    BUSINESS_ERROR_PARAM_NULL(40001, "请求数据为空", ""),
    BUSINESS_ERROR_NOT_LOGIN(40100, "未登录", ""),
    BUSINESS_ERROR_NO_AUTH(40101, "无权限", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 响应信息
     */
    private final String message;

    /**
     * 响应信息描述
     */
    private final String description;

    BusinessCode(int code, String message, String description){
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
