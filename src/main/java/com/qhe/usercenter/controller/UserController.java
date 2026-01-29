package com.qhe.usercenter.controller;

import com.qhe.usercenter.constant.UserConstants;
import com.qhe.usercenter.model.User;
import com.qhe.usercenter.model.UserVO;
import com.qhe.usercenter.model.request.UserLoginRequest;
import com.qhe.usercenter.model.request.UserRegisterRequest;
import com.qhe.usercenter.service.UserService;
import com.qhe.usercenter.utils.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    UserService userService;

    @PostMapping("/register")
    public Long register(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return -1L;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String invitationCode = userRegisterRequest.getInvitationCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, invitationCode)) {
            return -1L;
        }
        return userService.register(userAccount, userPassword, checkPassword, invitationCode);
    }

    @PostMapping("/login")
    public UserVO login(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null || request == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        return userService.login(userAccount, userPassword, request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        if (request == null) {
            return;
        }
        request.getSession().removeAttribute(UserConstants.USER_LOGIN_STATE);
    }
}
