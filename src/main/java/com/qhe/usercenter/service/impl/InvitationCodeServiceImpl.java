package com.qhe.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhe.usercenter.Exception.BusinessException;
import com.qhe.usercenter.common.BusinessCode;
import com.qhe.usercenter.constant.UserConstants;
import com.qhe.usercenter.model.InvitationCode;
import com.qhe.usercenter.model.UserVO;
import com.qhe.usercenter.model.request.InvitationQueryRequest;
import com.qhe.usercenter.service.InvitationCodeService;
import com.qhe.usercenter.mapper.InvitationCodeMapper;
import com.qhe.usercenter.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * InvitationCodeServiceImpl
 *
 * @author IamQhe
 */
@Service
public class InvitationCodeServiceImpl extends ServiceImpl<InvitationCodeMapper, InvitationCode>
    implements InvitationCodeService{
    @Resource
    InvitationCodeMapper invitationCodeMapper;

    @Override
    public boolean createInvitation(InvitationCode invitationCode, HttpServletRequest request) {
        // 校验
        if (invitationCode == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }

        // 邀请码类型校验
        Integer invitationType = invitationCode.getInvitationType();
        if (invitationType == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }

        // 生成邀请码
        invitationCode.setInvitationCode(UUID.randomUUID().toString());

        // 设置创建人id
        Object userObj = request.getSession().getAttribute(UserConstants.USER_LOGIN_STATE);
        UserVO user = (UserVO) userObj;
        if (user == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_NOT_LOGIN);
        }

        // 保存邀请码
        invitationCode.setCreatorId(user.getUserId());
        boolean result = save(invitationCode);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR);
        }
        return true;
    }

    @Override
    public boolean checkInvitationCode(String invitationCode) {
        if (StringUtils.isBlank(invitationCode)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL, "邀请码不能为空");
        }

        // 根据邀请码获取邀请信息
        InvitationCode invitation = selectByInvitationCode(invitationCode);
        if (invitation == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "邀请码不存在");
        }

        // 邀请码是否有效
        Integer invitationStatus = invitation.getInvitationStatus();
        if (Integer.valueOf(0).equals(invitationStatus)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "邀请码已失效");
        }

        // 邀请码是否到期
        Long invitationId = invitation.getInvitationId();
        Date expireTime = invitation.getExpireTime();
        if (expireTime != null && expireTime.before(new Date())) {
            // 更新邀请码状态
            updateInvitationStatus(invitationId, 0);
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "邀请码已失效");
        }

        // 若为单次邀请码，更新邀请码状态
        Integer invitationType = invitation.getInvitationType();
        if (Integer.valueOf(0).equals(invitationType)) {
            updateInvitationStatus(invitationId, 0);
        }
        return true;
    }

    @Override
    public boolean updateInvitationCode(InvitationCode invitationCode) {
        boolean result = updateById(invitationCode);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "更新失败，请稍后重试");
        }
        return true;
    }

    @Override
    public boolean removeInvitationCode(Long invitationId) {
        boolean result = removeById(invitationId);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "删除失败，请稍后重试");
        }
        return true;
    }

    @Override
    public List<InvitationCode> search(InvitationQueryRequest invitationQueryRequest) {
        return invitationCodeMapper.search(invitationQueryRequest);
    }

    private InvitationCode selectByInvitationCode(String invitationCode) {
        if (StringUtils.isBlank(invitationCode)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL, "邀请码不能为空");
        }

        // 检验邀请码是否存在
        QueryWrapper<InvitationCode> invitationCodeQueryWrapper = new QueryWrapper<>();
        invitationCodeQueryWrapper.eq("invitation_code", invitationCode);
        return getOne(invitationCodeQueryWrapper);
    }

    private void updateInvitationStatus(Long invitationId, Integer status) {
        InvitationCode updateInvitationCode = new InvitationCode();
        updateInvitationCode.setInvitationId(invitationId);
        updateInvitationCode.setInvitationStatus(status);
        updateInvitationCode(updateInvitationCode);
    }
}




