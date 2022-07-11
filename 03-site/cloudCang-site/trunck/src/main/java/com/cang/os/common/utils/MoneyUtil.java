package com.cang.os.common.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 〈一句话功能简述〉<br>
 * 〈功能详细描述〉
 * 
 * @author Administrator
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class MoneyUtil {
	
	/**
     * 数字:100
     */
    public static final int ONE_HUNDRED = 100;
	
    /**
     * 
     * 功能描述: 份额转金额<br>
     * 〈功能详细描述〉
     * 
     * @param share
     * @return
     */
    public static Long shareToAmount(BigDecimal share) {
        return share.multiply(BigDecimal.valueOf(ONE_HUNDRED)).longValue();
    }

    /**
     * 
     * 功能描述: 金额转份额<br>
     * 〈功能详细描述〉
     * 
     * @param share
     * @return
     */
    public static BigDecimal amountToShare(Long amount) {
        return BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(ONE_HUNDRED));
    }
    
    /**
     * 功能描述: <br>
     * 获取银行每日收益
     *
     * @param rate 利率
     * @param amount 金额
     * @return
     */
    public static BigDecimal getBankDayIncome(String rate,long amount){
        return new BigDecimal(rate).multiply(BigDecimal.valueOf(amount)).
            divide(BigDecimal.valueOf(365),4);
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param fen
     * @return
     */
    public static String strFenToStrYuan(String fen) {
        return String.valueOf(BigDecimal.valueOf(Long.parseLong(fen)).divide(
                BigDecimal.valueOf(ONE_HUNDRED)));
    }

    /**
     *  分转元 保留２位小数
     */
    public static String castFentoYuan(String fen){
        return BigDecimal.valueOf(Double.valueOf(fen)).divide(
                BigDecimal.valueOf(ONE_HUNDRED)).setScale(2, RoundingMode.HALF_UP).toString();
    }
    
    /**
     * @title 添加会计标识：','
     * @param money 待处理的人民币
     * @return
     */
    public static String getAccountantMoney(BigDecimal money) {
        String disposeMoneyStr = money.setScale(2, RoundingMode.HALF_UP).toString();
        // 小数点处理
        int dotPosition = disposeMoneyStr.indexOf(".");
        String exceptDotMoeny = null;// 小数点之前的字符串
        String dotMeony = null;// 小数点之后的字符串
        if (dotPosition > 0) {
            exceptDotMoeny = disposeMoneyStr.substring(0, dotPosition);
            dotMeony = disposeMoneyStr.substring(dotPosition);
        } else {
            exceptDotMoeny = disposeMoneyStr;
        }
        // 负数处理
        int negativePosition = exceptDotMoeny.indexOf("-");
        if (negativePosition == 0) {
            exceptDotMoeny = exceptDotMoeny.substring(1);
        }
        StringBuffer reverseExceptDotMoney = new StringBuffer(exceptDotMoeny);
        reverseExceptDotMoney.reverse();// 字符串倒转
        char[] moneyChar = reverseExceptDotMoney.toString().toCharArray();
        StringBuffer returnMeony = new StringBuffer();// 返回值
        for (int i = 0; i < moneyChar.length; i++) {
            if (i != 0 && i % 3 == 0) {
                returnMeony.append(",");// 每隔3位加','
            }
            returnMeony.append(moneyChar[i]);
        }
        returnMeony.reverse();// 字符串倒转
        if (dotPosition > 0) {
            returnMeony.append(dotMeony);
        }
        if (negativePosition == 0) {
            return "-" + returnMeony.toString();
        } else {
            return returnMeony.toString();
        }
    }
    
    public static String convert2ChineseMoney(BigDecimal money){
        if(money == null){
            return null;
        }
        MoneyToChinese mc = new MoneyToChinese();
       return mc.NumToCNMoney(money.doubleValue());
        
    }
    public static String convert2ChineseMoneyDouble(Double money){
        if(money == null){
            return null;
        }
        MoneyToChinese mc = new MoneyToChinese();
       return mc.NumToCNMoney(money.doubleValue());
        
    }
}
