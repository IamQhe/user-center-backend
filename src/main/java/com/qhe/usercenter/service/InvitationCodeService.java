package com.qhe.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qhe.usercenter.model.InvitationCode;
import com.qhe.usercenter.model.request.InvitationQueryRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* InvitationCodeService
 *
 * @author IamQhe
*/
public interface InvitationCodeService extends IService<InvitationCode> {
    boolean createInvitation(InvitationCode invitationCode, HttpServletRequest request);

    boolean checkInvitationCode(String invitationCode);

    boolean updateInvitationCode(InvitationCode invitationCode);

    boolean removeInvitationCode(Long invitationId);

    List<InvitationCode> search(InvitationQueryRequest invitationQueryRequest);
}
