package com.qhe.usercenter.controller;

import com.qhe.usercenter.Exception.BusinessException;
import com.qhe.usercenter.common.BaseResponse;
import com.qhe.usercenter.common.BusinessCode;
import com.qhe.usercenter.common.ResponseUtils;
import com.qhe.usercenter.model.InvitationCode;
import com.qhe.usercenter.model.UserVO;
import com.qhe.usercenter.model.request.InvitationBatchRemoveRequest;
import com.qhe.usercenter.model.request.InvitationBatchSwitchStatusRequest;
import com.qhe.usercenter.model.request.InvitationCreateRequest;
import com.qhe.usercenter.model.request.InvitationQueryRequest;
import com.qhe.usercenter.service.InvitationCodeService;
import com.qhe.usercenter.service.UserService;
import com.qhe.usercenter.utils.ListUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 邀请码接口
 *
 * @author IamQhe
 */
@RestController
@RequestMapping("/invitation")
public class InvitationCodeController {
    @Resource
    UserService userService;

    @Resource
    InvitationCodeService invitationCodeService;

    @PostMapping("/admin/create")
    public BaseResponse<Boolean> create(@RequestBody InvitationCreateRequest invitationCode, HttpServletRequest request) {
        return ResponseUtils.success(invitationCodeService.createInvitation(invitationCode, request));
    }

    @PostMapping("/admin/search")
    public BaseResponse<List<InvitationCode>> search(@RequestBody InvitationQueryRequest invitationQueryRequest) {
        return ResponseUtils.success(invitationCodeService.search(invitationQueryRequest));
    }

    @PostMapping("/admin/update")
    public BaseResponse<Boolean> update(@RequestBody InvitationCode invitationCode, HttpServletRequest request) {
        if (invitationCode == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }
        Long invitationId = invitationCode.getInvitationId();
        if (invitationId == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "邀请码ID不能为空");
        }
        // 更新修改人信息
        UserVO user = userService.currentUser(request);
        invitationCode.setUpdateId(user.getUserId());
        boolean result = invitationCodeService.updateById(invitationCode);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "修改失败，请稍后再试");
        }
        return ResponseUtils.success(true);
    }

    @PostMapping("/admin/remove")
    public BaseResponse<Boolean> remove(@RequestBody InvitationCode invitationCode) {
        if (invitationCode == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }
        Long invitationId = invitationCode.getInvitationId();
        if (invitationId == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }
        boolean result = invitationCodeService.removeById(invitationId);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR);
        }
        return ResponseUtils.success(true);
    }

    @PostMapping("/admin/batchChangeStatus")
    public BaseResponse<Boolean> batchChangeStatus(@RequestBody InvitationBatchSwitchStatusRequest invitationBatchSwitchStatusRequest) {
        if (invitationBatchSwitchStatusRequest == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }
        // 非空校验
        List<Long> invitationIdList = invitationBatchSwitchStatusRequest.getInvitationIdList();
        Integer invitationStatus = invitationBatchSwitchStatusRequest.getInvitationStatus();
        if (invitationIdList == null || invitationStatus == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR);
        }
        if (ListUtils.isNullOrcontainsNull(invitationIdList)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "邀请码ID不能为空");
        }
        // 批量修改状态
        List<InvitationCode> invitationList = invitationIdList.stream().map(invitationId -> {
            InvitationCode invitationCode = new InvitationCode();
            invitationCode.setInvitationId(invitationId);
            invitationCode.setInvitationStatus(invitationStatus);
            return invitationCode;
        }).collect(Collectors.toList());
        boolean result = invitationCodeService.updateBatchById(invitationList);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR);
        }
        return ResponseUtils.success(true);
    }

    @PostMapping("/admin/batchRemove")
    public BaseResponse<Boolean> batchRemove(@RequestBody InvitationBatchRemoveRequest invitationBatchRemoveRequest) {
        if (invitationBatchRemoveRequest == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }
        // 非空校验
        List<Long> invitationIdList = invitationBatchRemoveRequest.getInvitationIdList();
        if (ListUtils.isNullOrcontainsNull(invitationIdList)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "邀请码ID不能为空");
        }
        // 批量删除
        List<InvitationCode> invitationList = invitationIdList.stream().map(invitationId -> {
            InvitationCode invitationCode = new InvitationCode();
            invitationCode.setInvitationId(invitationId);
            return invitationCode;
        }).collect(Collectors.toList());
        boolean result = invitationCodeService.removeBatchByIds(invitationList);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR);
        }
        return ResponseUtils.success(true);
    }
}
