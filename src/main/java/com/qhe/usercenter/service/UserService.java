package com.qhe.usercenter.service;

import com.qhe.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qhe.usercenter.model.UserVO;

import javax.servlet.http.HttpServletRequest;

/**
 * UserService
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @param invitationCode 邀请码
     * @return
     */
    public Long register(String userAccount, String userPassword, String checkPassword, String invitationCode);

    /**
     * 用户登录
     *
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request servlet请求
     * @return
     */
    public UserVO login(String userAccount, String userPassword, HttpServletRequest request);
}
