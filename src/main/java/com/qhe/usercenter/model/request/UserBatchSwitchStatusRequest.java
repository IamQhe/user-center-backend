package com.qhe.usercenter.model.request;

import lombok.Data;

import java.util.List;

/**
 * 批量更新用户状态请求
 *
 * @author IamQhe
 */
@Data
public class UserBatchSwitchStatusRequest {
    private List<Long> userIdList;
    private Integer userStatus;
}
