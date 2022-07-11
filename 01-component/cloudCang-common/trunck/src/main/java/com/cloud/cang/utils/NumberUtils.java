package com.cloud.cang.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * 数字值工具类
 * @version version1.0
 */
public class NumberUtils {

    public NumberUtils() {
        super();
    }

    /**
     * 返回[通用]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getGenericFormat() {
        return NumberFormat.getInstance();
    }

    /**
     * 根据Locale返回[通用的]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getGenericFormat(Locale locale) {
        return NumberFormat.getInstance(locale);
    }

    /**
     * 返回[货币]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getCurrencyFormat() {
        return NumberFormat.getCurrencyInstance();
    }

    /**
     * 根据Locale返回[货币]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getCurrencyFormat(Locale locale) {
        return NumberFormat.getCurrencyInstance(locale);
    }

    /**
     * 返回[整数]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getIntegerFormat() {
        return NumberFormat.getIntegerInstance();
    }

    /**
     * 根据Locale返回[整数]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getIntegerFormat(Locale locale) {
        return NumberFormat.getIntegerInstance(locale);
    }

    /**
     * 返回[数字]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getNumberFormat() {
        return NumberFormat.getNumberInstance();
    }

    /**
     * 根据Locale返回[数字]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getNumberFormat(Locale locale) {
        return NumberFormat.getNumberInstance(locale);
    }

    /**
     * 返回[百分比]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getPercentFormat() {
        return NumberFormat.getPercentInstance();
    }

    /**
     * 根据Locale返回[百分比]格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getPercentFormat(Locale locale) {
        return NumberFormat.getPercentInstance(locale);
    }

    /**
     * 自定义格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getCustomFormat(String pattern) {
        return new DecimalFormat(pattern);
    }

    /**
     * 自定义格式化对象
     * 
     * @return NumberFormat
     */
    public static NumberFormat getCustomFormat(String pattern, DecimalFormatSymbols symbols) {
        return new DecimalFormat(pattern, symbols);
    }

    /**
     * 以"##,###.00"格式，格式化数字值
     * 
     * @return String
     */
    public static String FormatFloat(String value) {
        if (value == null || value == "" || "0".equals(value))
            return "0.00";
        else
            return new DecimalFormat("##,###.00").format(Double.parseDouble(value));
    }

    /**
     * 以"##,###.00"格式，格式化数字值
     * 
     * @return String
     */
    public static String format(String value,String pattern) {
        if (value == null || value == "" || "0".equals(value))
        	value="0";
        return new DecimalFormat(pattern).format(Double.parseDouble(value));
    }
    /**
     * 格式化数字
     * @param value
     * @return
     */
    public static String FormatInt(String value) {
        if (value == null || value == "" || "0".equals(value))
            return "0";
        else
            return new DecimalFormat("##,###").format(Double.parseDouble(value));
    }

    /**
     * 以本地区货币数值形式，格式化数字值
     * 
     * @return String
     */
    public static String FormatCurrency(Locale locale, String value) {
        if (value == null || value == "")
            return NumberFormat.getCurrencyInstance(locale).format(0);
        else
            return NumberFormat.getCurrencyInstance(locale).format(Double.parseDouble(value));
    }

    /**
     * 格式化长整型数字值
     * 
     * @return String
     */
    public static String FormatNumber(Locale locale, String value) {
        if (value == null || value == "")
            return "0";
        else
            return NumberFormat.getNumberInstance(locale).format(Long.parseLong(value));
    }

    /**
     * 以"##,###.00"格式，格式化大数值型的数字值
     * 
     * @return String
     */
    public static String FormatDecimal(String value) {
        if (value == null || value == "" || "0".equals(value))
            return "0.00";
        else
            return new DecimalFormat("##,###.00").format(Double.parseDouble(value));
    }

    /**
     * 格式化为百分比的数字值
     * 
     * @return String
     */
    public static String FormatPercent(String value) {
        if (value == null || value == "")
            return "0%";
        else
            return NumberFormat.getPercentInstance().format(Double.parseDouble(value));
    }

    public static String FormatCust(String value, int fixNum) {
        String format = "##,###";
        String fixStr = "";
        if (fixNum > 0) {
            StringBuilder sb = new StringBuilder();
            int tempNum = fixNum;
            format += ".";
            while (tempNum > 0) {
                sb.append(0);
                tempNum--;

            }

            fixStr = sb.toString();
        }
        format = format + fixStr;

        if (value == null || value == "" || "0".equals(value)) {
            if (fixNum == 0) return "0";
            else
            return "0." + fixStr;
        }

        else
            return new DecimalFormat(format).format(Double.parseDouble(value));

    }

    /**
     * 字符串转换为数字
     * @param value
     * @return
     */
    public static int str2Int(String value) {

        if (StringUtils.isNotBlank(value)) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }

    }

}
