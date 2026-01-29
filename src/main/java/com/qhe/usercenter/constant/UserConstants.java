package com.qhe.usercenter.constant;

public class UserConstants {
    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "IamQhe";

    /**
     * session键: 用户登录态
     */
    public static final String USER_LOGIN_STATE = "userLoginState";

    /**
     * 用户状态：禁用
     */
    public static final int USER_STATUS_DISABLE = 0;

    /**
     * 用户状态：启用
     */
    public static final int USER_STATUS_ENABLE = 1;

    /**
     * 用户身份：普通用户
     */
    public static final int USER_ROLE_NORMAL = 0;

    /**
     * 用户身份：管理员
     */
    public static final int USER_ROLE_ADMIN = 1;

    /**
     * 校验规则：用户账号最小长度
     */
    public static final int CHECK_USER_ACCOUNT_MIN_LENGTH = 3;

    /**
     * 校验规则：用户账号最大长度
     */
    public static final int CHECK_USER_ACCOUNT_MAX_LENGTH = 32;

    /**
     * 校验规则：用户密码最小长度
     */
    public static final int CHECK_USER_PASSWORD_MIN_LENGTH = 6;

    /**
     * 校验规则：用户密码最小长度
     */
    public static final int CHECK_USER_PASSWORD_MAX_LENGTH = 32;
}
