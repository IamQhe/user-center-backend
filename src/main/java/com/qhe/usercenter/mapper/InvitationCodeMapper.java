package com.qhe.usercenter.mapper;

import com.qhe.usercenter.model.InvitationCode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * InvitationCodeMapper
 *
 * @author IamQhe
 */
public interface InvitationCodeMapper extends BaseMapper<InvitationCode> {
    List<InvitationCode> search(InvitationCode invitationCode);
}




