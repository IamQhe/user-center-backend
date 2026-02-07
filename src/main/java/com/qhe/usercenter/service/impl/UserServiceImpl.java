package com.qhe.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qhe.usercenter.common.BusinessCode;
import com.qhe.usercenter.Exception.BusinessException;
import com.qhe.usercenter.constant.UserConstants;
import com.qhe.usercenter.model.User;
import com.qhe.usercenter.model.UserVO;
import com.qhe.usercenter.model.enums.UserStatusEnum;
import com.qhe.usercenter.model.request.UserQueryRequest;
import com.qhe.usercenter.model.request.UserUpdateRequest;
import com.qhe.usercenter.service.UserService;
import com.qhe.usercenter.mapper.UserMapper;
import com.qhe.usercenter.utils.ListUtils;
import com.qhe.usercenter.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserServiceImpl
 *
 * @author IamQhe
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    private final UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Long register(String userAccount, String userPassword, String checkPassword, String invitationCode) {
        // 非空校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, invitationCode) ) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL, "参数不允许为空");
        }

        // 用户账号校验
        if (!validateUserAccount(userAccount, UserConstants.CHECK_USER_ACCOUNT_MIN_LENGTH, UserConstants.CHECK_USER_ACCOUNT_MAX_LENGTH)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR,
                    "账号只允许由数字、字母和下划线组成，且不小于6位，不超过16位");
        }

        // 密码长度校验
        if (UserConstants.CHECK_USER_PASSWORD_MIN_LENGTH > userPassword.length() || userPassword.length() > UserConstants.CHECK_USER_PASSWORD_MAX_LENGTH) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR,
                    "密码不能小于6位，且不能超过16位");
        }

        // 密码和确认密码一致校验
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR,
                    "两次密码不一致");
        }

        // 用户账号查重校验
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        Long count = userMapper.selectCount(userQueryWrapper);
        if (count > 0) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR,
                    "账号已存在，再换一个吧");
        }

        // todo 邀请码校验


        // 密码盐值加密
        String saltPassword = DigestUtils.md5DigestAsHex((UserConstants.SALT + userPassword).getBytes());

        // 保存账号
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(saltPassword);
        user.setInvitationCode(invitationCode);
        boolean result = this.save(user);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "系统内部异常，请稍后重试");
        }
        return user.getUserId();
    }

    @Override
    public UserVO login(String userAccount, String userPassword, HttpServletRequest request) {
        // 参数非空校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL, "请求参数不允许为空");
        }

        // 用户账号校验
        if (!validateUserAccount(userAccount, UserConstants.CHECK_USER_ACCOUNT_MIN_LENGTH, UserConstants.CHECK_USER_ACCOUNT_MAX_LENGTH)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR,
                    "账号只允许由数字、字母和下划线组成，且不小于6位，不超过16位");
        }

        // 密码长度校验
        if (UserConstants.CHECK_USER_PASSWORD_MIN_LENGTH > userPassword.length() || userPassword.length() > UserConstants.CHECK_USER_PASSWORD_MAX_LENGTH) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "密码不能小于6位，且不能超过16位");
        }

        // 验证账户
        String saltPassword = DigestUtils.md5DigestAsHex((UserConstants.SALT + userPassword).getBytes());
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", saltPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "账号或密码错误");
        }

        // 判断用户状态
        if (user.getUserStatus() != UserConstants.USER_STATUS_ENABLE) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "账号被禁止登录");
        }

        // 用户信息脱敏
        UserVO userVO = getUserVO(user);

        // 服务器记录登录 session
        request.getSession().setAttribute(UserConstants.USER_LOGIN_STATE, userVO);

        // 返回脱敏用户信息
        return userVO;
    }

    @Override
    public UserVO currentUser(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR);
        }
        Object userObj = request.getSession().getAttribute(UserConstants.USER_LOGIN_STATE);
        if ( userObj == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_NOT_LOGIN, "账号未登录");
        }
        UserVO user = (UserVO) userObj;
        Long userId = user.getUserId();
        User currentUser = this.getById(userId);
        return getUserVO(currentUser);
    }

    @Override
    public List<UserVO> search(UserQueryRequest user) {
        return userMapper.searchUserList(user);
    }

    @Override
    public boolean updateUser(UserUpdateRequest user) {
        // 参数非空校验
        if (user == null || user.getUserId() == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户id不能为空");
        }
        if (StringUtils.isBlank(user.getUserAccount())) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户账号不能为空");
        }
        if (user.getUserRole() == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户身份不能为空");
        }
        if (user.getUserStatus() == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户状态不能为空");
        }

        // todo 身份枚举校验

        // 状态枚举校验
        if (!UserStatusEnum.containsStatus(user.getUserStatus())) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "非法状态参数：" + user.getUserStatus());
        }

        // todo 性别枚举校验

        // todo 邮箱正则校验

        // todo 手机号正则校验

        // 账号重复校验（除了用户本身）
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("user_id", user.getUserId())
                .eq("user_account", user.getUserAccount());
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR,
                    "账号已存在，再换一个吧");
        }

        boolean result = this.updateById(user);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "更新失败，请稍后尝试");
        }
        return result;
    }

    @Override
    public boolean switchStatus(Long userId, Integer userStatus) {
        if (userId == null ) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户id不能为空");
        }
        if (userStatus == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_NULL, "用户状态不能为空");
        }

        // 状态枚举校验
        if (!UserStatusEnum.containsStatus(userStatus)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "非法状态参数: " + userStatus);
        }

        User user = new User();
        user.setUserId(userId);
        user.setUserStatus(userStatus);
        boolean result = this.updateById(user);
        if (!result) {
            throw  new BusinessException(BusinessCode.SYSTEM_ERROR, "状态修改失败，请重试");
        }
        return result;
    }

    @Override
    public boolean batchSwitchStatus(List<Long> userIdList, Integer userStatus) {
        // 参数非空校验
        if (ListUtils.isNullOrEmpty(userIdList)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户id列表不能为空");
        }
        if (userStatus == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户状态不能为空");
        }
        // 用户列表空值校验
        if (ListUtils.isNullOrcontainsNull(userIdList)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户id列表不能存在空值");
        }

        // 更新用户状态
        List<User> list = userIdList.stream().map(userId -> {
            User u = new User();
            u.setUserId(userId);
            u.setUserStatus(userStatus);
            return u;
        }).collect(Collectors.toList());
        boolean result = this.updateBatchById(list);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "状态批量更新失败，请重试");
        }
        return result;
    }

    @Override
    public boolean resetPassword(Long userId, String password, String checkPassword, HttpServletRequest request) {
        if (userId == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "用户id不能为空");
        }
        if (password == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "新密码不能为空");
        }
        if (checkPassword == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "确认密码不能为空");
        }

        // 密码校验
        if (UserConstants.CHECK_USER_PASSWORD_MIN_LENGTH > password.length() || password.length() > UserConstants.CHECK_USER_PASSWORD_MAX_LENGTH) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "密码不能小于6位，且不能超过16位");
        }

        // 一致校验
        if (!password.equals(checkPassword)) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_PARAM_ERROR, "两次输入的密码不一致");
        }

        // 身份校验：仅允许用户自己或管理员操作
        if (request == null || request.getSession() == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_NO_AUTH, "操作非法");
        }
        Object o = request.getSession().getAttribute(UserConstants.USER_LOGIN_STATE);
        UserVO user = (UserVO) o;
        if (user == null) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_NOT_LOGIN);
        }
        if (user.getUserId().equals(userId) || user.getUserRole() != UserConstants.USER_ROLE_ADMIN) {
            throw new BusinessException(BusinessCode.BUSINESS_ERROR_NO_AUTH);
        }

        // 更新用户密码
        // 密码盐值加密
        String saltPassword = DigestUtils.md5DigestAsHex((UserConstants.SALT + password).getBytes());
        User updateUser = new User();
        updateUser.setUserId(userId);
        updateUser.setUserPassword(saltPassword);
        boolean result = updateById(updateUser);
        if (!result) {
            throw new BusinessException(BusinessCode.SYSTEM_ERROR, "更新密码失败，请稍后重试");
        }
        return result;
    }

    // 用户账号校验正则表达式
    public boolean validateUserAccount(String account, int min, int max) {
        // 允许数字、字母和下划线
        String pattern = "^[A-Za-z0-9_]{" + min + "," + max + "}$";
        return account.matches(pattern);
    }

    /**
     * User 转 UserVO
     *
     * @param user User
     * @return UserVO
     */
    public UserVO getUserVO(User user) {
        UserVO userVO = new UserVO();
        userVO.setUserId(user.getUserId());
        userVO.setUserName(user.getUserName());
        userVO.setUserAccount(user.getUserAccount());
        userVO.setUserAvatarUrl(user.getUserAvatarUrl());
        userVO.setUserRole(user.getUserRole());
        userVO.setUserGender(user.getUserGender());
        userVO.setUserEmail(user.getUserEmail());
        userVO.setUserPhone(user.getUserPhone());
        return userVO;
    }
}




