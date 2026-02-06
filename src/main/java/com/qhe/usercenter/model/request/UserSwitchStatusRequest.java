package com.qhe.usercenter.model.request;

import lombok.Data;

/**
 * 更新用户状态请求
 *
 * @author IamQhe
 */
@Data
public class UserSwitchStatusRequest {
    private Long userId;
    private Integer userStatus;
}
