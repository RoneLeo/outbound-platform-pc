package com.chiyun.outboundplatform.utils;

public class StringUtil {

    public static boolean isNull(String str) {
        if ("".equals(str) || str == null) {
            return true;
        }
        return false;
    }
}
