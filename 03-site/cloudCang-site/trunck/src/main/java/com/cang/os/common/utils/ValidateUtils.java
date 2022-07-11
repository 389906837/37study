package com.cang.os.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author rogerfan
 *
 */
public class ValidateUtils {
    static Pattern mobile_validator = Pattern.compile("^[1][3,4,5,6,7,8][0-9]{9}$");;

    public static boolean isMobile(String str) {
        Matcher m = mobile_validator.matcher(str);
        return m.matches();

    }
}
