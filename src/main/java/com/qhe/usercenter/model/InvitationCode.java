package com.qhe.usercenter.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 邀请码
 *
 * @author IamQhe
 */
@TableName(value ="invitation_code")
@Data
public class InvitationCode implements Serializable {
    /**
     * 邀请码id
     */
    @TableId
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long invitationId;

    /**
     * 邀请码
     */
    private String invitationCode;

    /**
     * 邀请码类型 0：单次；1：多次
     */
    private Integer invitationType;

    /**
     * 邀请码状态 0：禁用；1：启用
     */
    private Integer invitationStatus;

    /**
     * 到期时间
     */
    private Date expireTime;

    /**
     * 创建人id
     */
    private Long creatorId;

    /**
     * 修改人id
     */
    private Long updateId;

    /**
     * 备注
     */
    private String invitationNote;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}