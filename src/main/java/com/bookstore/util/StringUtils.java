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

    public static Long[] convertStringToLong(String[] strings) {
        Long[] longs = new Long[strings.length];
        for (int i = 0; i < strings.length; i++) {
            longs[i] = Long.valueOf(strings[i]);
        }
        return longs;
    }
}
