package com.qhe.usercenter.model.enums;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户状态枚举类
 *
 * @author IamQhe
 */
public enum UserStatusEnum {
    USER_DISABLE(0, "禁用"),
    USER_ENABLE(1, "启用");

    private final Integer status;
    private final String description;

    UserStatusEnum(Integer status, String description) {
        this.status = status;
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据状态获取枚举实例
     *
     * @param status 状态
     * @return 枚举实例
     */
    public static Optional<UserStatusEnum> fromStatus(Integer status) {
        if (status == null) {
            return Optional.empty();
        }
        return Arrays.stream(values()).filter(enumValue -> Objects.equals(enumValue.getStatus(), status)).findFirst();
    }

    /**
     * 检查状态是否存在枚举实例
     *
     * @param status 状态
     * @return 是否包含：包含为 true；不包含为 false
     */
    public static boolean containsStatus(Integer status) {
        return fromStatus(status).isPresent();
    }
}
