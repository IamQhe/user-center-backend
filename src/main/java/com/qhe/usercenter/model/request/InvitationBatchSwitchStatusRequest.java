package com.qhe.usercenter.model.request;

import lombok.Data;

import java.util.List;

/**
 * 邀请码批量修改状态请求
 *
 * @author IamQhe
 */
@Data
public class InvitationBatchSwitchStatusRequest {
    private List<Long> invitationIdList;
    private Integer invitationStatus;
}
