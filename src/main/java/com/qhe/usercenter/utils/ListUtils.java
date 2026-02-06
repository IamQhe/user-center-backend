package com.qhe.usercenter.utils;

import java.util.List;
import java.util.Objects;

/**
 * List工具类
 *
 * @author IamQhe
 */
public class ListUtils {

    /**
     * 检查列表是否为null或为空
     * @param list 要检查的列表
     * @param <T> 列表元素类型
     * @return 如果列表为null或为空返回true，否则返回false
     */
    public static <T> boolean isNullOrEmpty(List<T> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 检查列表中是否存在null值
     * @param list 要检查的列表
     * @param <T> 列表元素类型
     * @return 如果列表为null或包含null元素返回true，否则返回false
     */
    public static <T> boolean isNullOrcontainsNull(List<T> list) {
        if (isNullOrEmpty(list)) {
            return true;
        }
        return list.stream().anyMatch(Objects::isNull);
    }
}
