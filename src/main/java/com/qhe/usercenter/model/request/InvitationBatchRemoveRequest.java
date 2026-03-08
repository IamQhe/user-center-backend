package com.qhe.usercenter.model.request;

import lombok.Data;

import java.util.List;

/**
 * 邀请码批量删除请求
 *
 * @author IamQhe
 */
@Data
public class InvitationBatchRemoveRequest {
    private List<Long> invitationIdList;
}
