package com.qhe.usercenter.service;

import com.qhe.usercenter.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qhe.usercenter.model.UserVO;
import com.qhe.usercenter.model.request.UserQueryRequest;
import com.qhe.usercenter.model.request.UserUpdateRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * UserService
 *
 * @author IamQhe
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 确认密码
     * @param invitationCode 邀请码
     * @return 用户id
     */
    Long register(String userAccount, String userPassword, String checkPassword, String invitationCode);

    /**
     * 用户登录
     *
     * @param userAccount 账号
     * @param userPassword 密码
     * @param request servlet请求
     * @return 用户VO
     */
    UserVO login(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request servlet请求
     * @return 当前用户VO
     */
    UserVO currentUser(HttpServletRequest request);

    /**
     * 获取用户列表
     *
     * @param user 查询参数
     * @return 用户列表
     */
    List<UserVO> search(UserQueryRequest user);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 影响记录数量
     */
    boolean updateUser(UserUpdateRequest user);

    /**
     * 更改用户状态
     *
     * @param userId 用户id
     * @param userStatus 用户状态
     * @return 更新结果
     */
    boolean switchStatus(Long userId, Integer userStatus);

    /**
     * 批量更新用户状态
     *
     * @param userIdList 用户id列表
     * @param userStatus 用户状态
     * @return 更新结果
     */
    boolean batchSwitchStatus(List<Long> userIdList, Integer userStatus);

    /**
     * 重置用户密码
     *
     * @param userId 用户id
     * @param password 用户密码
     * @param checkPassword 确认密码
     * @param request 用于获取当前用户
     * @return 更新结果
     */
    boolean resetPassword(Long userId, String password, String checkPassword, HttpServletRequest request);
}
