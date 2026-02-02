package com.qhe.usercenter.controller;

import com.qhe.usercenter.common.BaseResponse;
import com.qhe.usercenter.common.BusinessCode;
import com.qhe.usercenter.Exception.BusinessException;
import com.qhe.usercenter.common.ResponseUtils;
import com.qhe.usercenter.constant.UserConstants;
import com.qhe.usercenter.model.UserVO;
import com.qhe.usercenter.model.request.UserLoginRequest;
import com.qhe.usercenter.model.request.UserRegisterRequest;
import com.qhe.usercenter.service.UserService;
import com.qhe.usercenter.utils.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 *
 * @author IamQhe
 */
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = {"http://localhost:8000"}, allowCredentials = "true")
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
    public BaseResponse logout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR);
        }
        request.getSession().removeAttribute(UserConstants.USER_LOGIN_STATE);
        return ResponseUtils.success();
    }

    @PostMapping("/currentUser")
    public BaseResponse<UserVO> currentUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR);
        }
        UserVO userVO = userService.currentUser(request);
        return ResponseUtils.success(userVO);
    }
}
