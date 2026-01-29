package com.qhe.usercenter.model;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户头像路径
     */
    private String userAvatarUrl;

    /**
     * 用户身份
     */
    private Integer userRole;

    /**
     * 用户性别
     */
    private Integer userGender;

    /**
     * 用户邮箱
     */
    private String userEmail;
}
