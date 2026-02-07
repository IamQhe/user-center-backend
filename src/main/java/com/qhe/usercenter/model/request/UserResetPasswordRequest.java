package com.qhe.usercenter.model.request;

import lombok.Data;

/**
 * 重置用户密码请求
 *
 * @author IamQhe
 */
@Data
public class UserResetPasswordRequest {
    private Long userId;
    private String password;
    private String checkPassword;
}
