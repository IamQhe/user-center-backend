package com.qhe.usercenter.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qhe.usercenter.model.User;
import lombok.Data;

import java.util.Date;

/**
 * 用户查询类
 *
 * @author IamQhe
 */
@Data
public class UserQueryRequest extends User{

    /**
     * 创建起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date startCreateTime;

    /**
     * 创建结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date endCreateTime;

    /**
     * 更新起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date startUpdateTime;

    /**
     * 更新结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date endUpdateTime;
}
