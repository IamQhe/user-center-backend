package com.qhe.usercenter.utils;

public class StringUtils {

    public static boolean isAnyBlank(String... args) {
        for (String arg : args) {
            if (isBlank(arg)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlank(String s) {
        return s == null || "".equals(s);
    }

}
