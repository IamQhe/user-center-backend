create table user
(
    user_id         bigint            not null comment '用户id'
        primary key,
    user_name       varchar(255)      null comment '用户姓名',
    user_account    varchar(255)      not null comment '用户账号',
    user_password   varchar(255)      not null comment '用户密码',
    user_role       tinyint default 0 not null comment '用户身份',
    user_status     tinyint default 0 not null comment '用户状态',
    user_gender     tinyint           null comment '用户性别',
    user_email      varchar(255)      null comment '用户邮箱',
    invitation_code varchar(255)      null comment '邀请码',
    is_delete       tinyint default 0 null
)
    comment '用户';

