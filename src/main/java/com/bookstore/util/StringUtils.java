package com.bookstore.util;

public class StringUtils {

    private StringUtils() {

    }

    public static boolean isEmpty(String s) {
        return s == null || s.length() == 0;
    }

    public static boolean notEmpty(String s) {
        return s != null && s.length() > 0;
    }
}
