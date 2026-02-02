package com.qhe.usercenter.common;

/**
 * 通用响应工具类
 *
 * @author IamQhe
 */
public class ResponseUtils {
    /**
     * 请求成功，无返回值
     *
     * @return 响应
     */
    public static BaseResponse success() {
        return new BaseResponse<>(BusinessCode.BUSINESS_SUCCESS.getCode() , null, BusinessCode.BUSINESS_SUCCESS.getMessage());
    }

    /**
     * 请求成功，有返回值
     *
     * @param data 响应结果
     * @return 响应
     * @param <T> 响应数据类型
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(BusinessCode.BUSINESS_SUCCESS.getCode(), data, BusinessCode.BUSINESS_SUCCESS.getMessage());
    }

    /**
     * 请求失败
     *
     * @param code 响应码
     * @param message 响应信息
     * @param description 响应描述
     * @return 响应
     */
    public static BaseResponse fail(int code, String message, String description) {
        return new BaseResponse<>(code, null, message, description);
    }

    /**
     * 请求失败
     *
     * @param businessCode 业务状态码
     * @return 响应
     */
    public static BaseResponse fail(BusinessCode businessCode) {
        return new BaseResponse<>(businessCode);
    }

    /**
     * 请求失败
     *
     * @param businessCode 业务状态码
     * @param message 错误信息
     * @param description 错误描述
     * @return 响应
     */
    public static BaseResponse fail(BusinessCode businessCode, String message, String description) {
        return new BaseResponse<>(businessCode.getCode(), null, message, description);
    }

    /**
     * 请求失败
     *
     * @param businessCode 业务状态码
     * @param description 错误描述
     * @return 响应
     */
    public static BaseResponse fail(BusinessCode businessCode, String description) {
        return new BaseResponse<>(businessCode.getCode(), null, businessCode.getMessage(), description);
    }
}
