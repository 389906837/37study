package com.cloud.cang.wap.common.utils;

import java.math.BigDecimal;

/**
 * BigDecimal计算类，约定为先做四舍五入再做计算
 * @Author: zengzexiong
 * @Date: 2017年12月29日09:36:11
 * @version 1.0
 */
public class PriceUtil {
    /**
     * 提供加法计算，约定为先做四舍五入再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static BigDecimal add(BigDecimal a1, BigDecimal a2){

        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).add(a2.setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
    }


    /**
     * 提供加法计算，约定为先做向下取再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static BigDecimal addDown(BigDecimal a1, BigDecimal a2){
        return a1.setScale(2,BigDecimal.ROUND_DOWN).add(a2.setScale(2,BigDecimal.ROUND_DOWN )).setScale(2,BigDecimal.ROUND_DOWN );
    }

    /**
     * 提供减法计算，约定为先做四舍五入再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static BigDecimal sub(BigDecimal a1,BigDecimal a2){
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).subtract(a2.setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
    }

    /**
     * 提供减法计算，约定为先做项上取再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static BigDecimal subUp(BigDecimal a1,BigDecimal a2){
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).subtract(a2.setScale(2,BigDecimal.ROUND_DOWN )).setScale(2,BigDecimal.ROUND_UP);
    }


    /**
     * 提供减法计算，约定为先做四舍五入再做计算
     * @param a1
     * @param count
     * @return
     */
    public static BigDecimal sub(BigDecimal a1,int  count){
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).subtract(new BigDecimal(count+"").setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
    }

    /**
     * 提供乘法计算，约定为先做四舍五入再做计算
     * @param a1
     * @param count
     * @return
     */
    public static BigDecimal multiply(BigDecimal a1,int count){
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(count+"").setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
    }

    /**
     * 提供乘法计算，约定为先做四舍五入再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static BigDecimal multiply(BigDecimal a1,BigDecimal a2){
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).multiply(a2.setScale(2,BigDecimal.ROUND_HALF_UP )).setScale(2,BigDecimal.ROUND_HALF_UP );
    }

    /**
     * 提供乘法计算，约定为先做向上再做计算
     * @return
     */
    public static BigDecimal multiplyRoundUp(BigDecimal a1,BigDecimal a2){
        return a1.setScale(2,BigDecimal.ROUND_UP).multiply(a2.setScale(2,BigDecimal.ROUND_UP )).setScale(2,BigDecimal.ROUND_UP);
    }

    /**
     * 提供除法计算，约定为先做四舍五入再做计算
     * @param a1
     * @param count
     * @return
     */
    public static BigDecimal divide(BigDecimal a1,int count){
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(count+"").setScale(2,BigDecimal.ROUND_HALF_UP ),2,BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 提供除法计算，向下取
     * @param a1
     * @param a2
     * @return
     */
    public static BigDecimal divideDown(BigDecimal a1,BigDecimal a2){
        return a1.divide(a2,2,BigDecimal.ROUND_DOWN);
    }
    /**
     * 提供除法计算，约定为先做四舍五入再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static BigDecimal divide(BigDecimal a1,BigDecimal a2){
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).divide(a2.setScale(2,BigDecimal.ROUND_HALF_UP),2,BigDecimal.ROUND_HALF_UP );
    }

    /**
     * 小于等于0，约定为先做四舍五入再做计算
     * @param a2
     * @return 小于等于0返回true，否则返回false
     */
    public static boolean ltOrEqZero(BigDecimal a2) {
        return new BigDecimal("0").compareTo(a2.setScale(2,BigDecimal.ROUND_HALF_UP)) >=0;
    }

    /**
     * 是否等于0，约定为先做四舍五入再做计算
     * @param a2
     * @return
     */
    public static boolean equalZero(BigDecimal a2) {
        return new BigDecimal("0").compareTo(a2.setScale(2,BigDecimal.ROUND_HALF_UP)) ==0;
    }

    /**
     * 大于等于0，约定为先做四舍五入再做计算
     * @param a2
     * @return
     */
    public static boolean gtOrEqZero(BigDecimal a2) {
        return new BigDecimal("0").compareTo(a2.setScale(2,BigDecimal.ROUND_HALF_UP)) <=0;
    }

    /**
     * 比较两个数的大小，约定为先做四舍五入再做计算
     * @param a
     * @param b
     * @return
     */
    public static boolean compareAgtB(BigDecimal a,BigDecimal b) {
        return a.setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(b.setScale(2,BigDecimal.ROUND_HALF_UP)) > 0;
    }

    /**
     * 比较a大于b是否成立，约定为先做四舍五入再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static boolean compareTo(BigDecimal a1,BigDecimal a2) {
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(a2.setScale(2,BigDecimal.ROUND_HALF_UP)) >= 0;
    }

    /**
     * 比较两个数的大小，约定为先做四舍五入再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static boolean compareTo(BigDecimal a1,int a2) {
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).compareTo(new BigDecimal(a2).setScale(2,BigDecimal.ROUND_HALF_UP)) >= 0;
    }


    /**
     * 除法，向下取整，约定为先做四舍五入再做计算
     * @param a1
     * @param a2
     * @return
     */
    public static BigDecimal divideToInt(BigDecimal a1,BigDecimal a2) {
        return a1.setScale(2,BigDecimal.ROUND_HALF_UP).divideToIntegralValue(a2.setScale(2,BigDecimal.ROUND_HALF_UP));
    }



}
