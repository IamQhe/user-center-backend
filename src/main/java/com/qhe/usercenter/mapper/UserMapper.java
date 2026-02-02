package com.qhe.usercenter.mapper;

import com.qhe.usercenter.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qhe.usercenter.model.UserVO;
import com.qhe.usercenter.model.request.UserQueryRequest;

import java.util.List;

/**
 * UserMapper
 *
 * @author IamQhe
 */
public interface UserMapper extends BaseMapper<User> {
    List<UserVO> searchUserList(UserQueryRequest user);
}




