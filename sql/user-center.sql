create table user
(
    user_id         bigint                             not null comment '用户id'
        primary key,
    user_name       varchar(255)                       null comment '用户姓名',
    user_account    varchar(255)                       null comment '用户账号',
    user_password   varchar(255)                       not null comment '用户密码',
    user_avatar_url varchar(512)                       null comment '用户头像',
    user_role       tinyint  default 0                 not null comment '用户身份',
    user_status     int      default 1                 not null comment '用户状态 0：禁用；1：启用',
    user_gender     tinyint                            null comment '用户性别',
    user_email      varchar(255)                       null comment '用户邮箱',
    user_phone      varchar(255)                       null comment '用户手机号',
    invitation_code varchar(255)                       not null comment '邀请码',
    create_time     datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time     datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete       tinyint  default 0                 null comment '逻辑删除'
)
    comment '用户';


create table invitation_code
(
    invitation_id     bigint                             not null comment '邀请码id'
        primary key,
    invitation_code   varchar(255)                       not null comment '邀请码',
    invitation_type   int                                not null comment '邀请码类型 0：单次；1：多次',
    invitation_status tinyint  default 1                 not null comment '邀请码状态 0：禁用；1：启用',
    expire_time       datetime                           null comment '到期时间',
    creator_id        bigint                             null comment '创建人id',
    invitation_note   varchar(512)                       null comment '备注',
    create_time       datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time       datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete         tinyint  default 0                 not null comment '逻辑删除',
    constraint invitation_code_pk_2
        unique (invitation_code)
)
    comment '邀请码';

