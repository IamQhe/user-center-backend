package com.qhe.usercenter.model.request;

import lombok.Data;

import java.util.List;

/**
 * 批量删除用户请求
 *
 * @author IamQhe
 */
@Data
public class UserBatchDeleteRequest {
    private List<Long> userIdList;
}
