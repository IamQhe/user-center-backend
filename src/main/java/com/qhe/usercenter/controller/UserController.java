package com.qhe.usercenter.controller;

import com.qhe.usercenter.common.BaseResponse;
import com.qhe.usercenter.common.BusinessCode;
import com.qhe.usercenter.Exception.BusinessException;
import com.qhe.usercenter.common.ResponseUtils;
import com.qhe.usercenter.constant.UserConstants;
import com.qhe.usercenter.model.UserVO;
import com.qhe.usercenter.model.request.*;
import com.qhe.usercenter.service.UserService;
import com.qhe.usercenter.utils.ListUtils;
import com.qhe.usercenter.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 *
 * @author IamQhe
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String invitationCode = userRegisterRequest.getInvitationCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, invitationCode)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }
        Long userId = userService.register(userAccount, userPassword, checkPassword, invitationCode);
        return ResponseUtils.success(userId);
    }

    @PostMapping("/login")
    public BaseResponse<UserVO> login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null || request == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        UserVO user = userService.login(userAccount, userPassword, request);
        return ResponseUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Boolean> logout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR);
        }
        request.getSession().removeAttribute(UserConstants.USER_LOGIN_STATE);
        return ResponseUtils.success(true);
    }

    @PostMapping("/currentUser")
    public BaseResponse<UserVO> currentUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR);
        }
        UserVO userVO = userService.currentUser(request);
        return ResponseUtils.success(userVO);
    }

    @PostMapping("/admin/search")
    public BaseResponse<List<UserVO>> search(@RequestBody(required = false) UserQueryRequest user) {
        return ResponseUtils.success(userService.search(user));
    }

    @PostMapping("/admin/update")
    public BaseResponse<Boolean> update(@RequestBody UserUpdateRequest user) {
        if (user == null || user.getUserId() == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户id不能为空");
        }

        boolean result = userService.updateUser(user);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "更新失败，请稍后重试");
        }
        return ResponseUtils.success(result);
    }

    @PostMapping("/admin/delete")
    public BaseResponse<Boolean> delete(@RequestBody UserDeleteRequest userDeleteRequest) {
        if (userDeleteRequest == null || userDeleteRequest.getUserId() == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }

        // todo 权限校验

        // todo 不可删除自身

        Long userId = userDeleteRequest.getUserId();
        boolean result = userService.removeById(userId);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR);
        }
        return ResponseUtils.success(true);
    }

    @PostMapping("/admin/batchDelete")
    public BaseResponse<Boolean> batchDelete(@RequestBody UserBatchDeleteRequest userBatchDeleteRequest) {
        if (userBatchDeleteRequest == null || userBatchDeleteRequest.getUserIdList() == null
                || userBatchDeleteRequest.getUserIdList().isEmpty()) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }

        List<Long> userIdList = userBatchDeleteRequest.getUserIdList();
        if (ListUtils.isNullOrcontainsNull(userIdList)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户id列表不能存在空值");
        }

        boolean result = userService.removeBatchByIds(userIdList);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "批量操作失败，请稍后重试");
        }
        return ResponseUtils.success(true);
    }

    @PostMapping("/admin/switchStatus")
    public BaseResponse<Boolean> switchStatus(@RequestBody UserSwitchStatusRequest userSwitchStatusRequest) {
        if (userSwitchStatusRequest == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }

        Long userId = userSwitchStatusRequest.getUserId();
        Integer userStatus = userSwitchStatusRequest.getUserStatus();
        if (userId == null || userStatus == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户id和状态不能为空");
        }
        boolean result = userService.switchStatus(userId, userStatus);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR);
        }
        return ResponseUtils.success(result);
    }

    @PostMapping("/admin/batchSwitchStatus")
    public BaseResponse<Boolean> batchSwitchStatus(@RequestBody UserBatchSwitchStatusRequest userBatchSwitchStatusRequest) {
        if (userBatchSwitchStatusRequest == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }

        List<Long> userIdList = userBatchSwitchStatusRequest.getUserIdList();
        Integer userStatus = userBatchSwitchStatusRequest.getUserStatus();

        boolean result = userService.batchSwitchStatus(userIdList, userStatus);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "状态批量更新失败，请重试");
        }
        return ResponseUtils.success(result);
    }

    @PostMapping("/resetPassword")
    public BaseResponse<Boolean> resetPassword(@RequestBody UserResetPasswordRequest userResetPasswordRequest, HttpServletRequest request) {
        if (userResetPasswordRequest == null || request == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL);
        }

        Long userId = userResetPasswordRequest.getUserId();
        String password = userResetPasswordRequest.getPassword();
        String checkPassword = userResetPasswordRequest.getCheckPassword();

        boolean result = userService.resetPassword(userId, password, checkPassword, request);
        return ResponseUtils.success(result);
    }
}
