package com.qhe.usercenter.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qhe.usercenter.model.InvitationCode;

import java.util.Date;

/**
 * 邀请码查询请求
 *
 * @author IamQhe
 */
public class InvitationQueryRequest extends InvitationCode {
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
