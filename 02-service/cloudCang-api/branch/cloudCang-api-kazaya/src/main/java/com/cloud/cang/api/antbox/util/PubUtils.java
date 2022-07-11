package com.cloud.cang.api.antbox.util;


import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public abstract class PubUtils {
    private PubUtils() {

    }

    /**
     * 左补齐
     */
    public static String leftPad(String str, int len, char pad) {
        Assert.notNull(str, "str");
        if (str.length() >= len) {
            return str;
        }

        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < len - str.length(); i++) {
            buf.append(pad);
        }
        buf.append(str);
        return buf.toString();
    }

    public static String getURLParamValue(String qrcode, String param) {
        if (StringUtils.isEmpty(param)) {
            return "";
        }

        int start = qrcode.lastIndexOf(param + "=");
        if (start >= 0) {
            int end = qrcode.indexOf('&', start);
            return qrcode.substring(start + param.length() + 1, end > 0 ? end
                    : qrcode.length());
        }

        return "";
    }
}
