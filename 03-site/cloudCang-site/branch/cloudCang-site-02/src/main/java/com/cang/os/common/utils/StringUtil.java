/*
 * Copyright (C), 2002-2012, 苏宁易购电子商务有限公司
 * FileName: StringUtil.java
 * Author:   李强
 * Date:     2013-9-8
 * Description:  
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */

package com.cang.os.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang.StringUtils;

/**
 * String工具类
 * 
 * @author 无
 */
public class StringUtil {
    public static final int LENGTH_ONE = 1;
    public static final int LENGTH_TWO = 2;
    public static final int LENGTH_THREE = 3;
    public static final String STR_DOT = ".";
    public static final String MONEY_FEN = "00";
    public static final int CARD_LENGTH_14 = 14;
    public static final int CARD_LENGTH_16 = 14;

    public static boolean isNotNull(String s) {
        if (null != s && s.trim().length() != 0) {
            return true;
        }
        return false;
    }

    public static boolean isNull(String s) {
        if (null == s || s.trim().length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据要求字符串长度，数字左补零
     * 
     * @author YuanBing
     * @param data
     * @param length
     * @return
     */
    public static String leftFillZero(String str, int length) {
        if (null == str || "".equals(str.trim()) || length <= 0) {
            return "";
        }
        str = str.trim();
        if (str.length() == length) {
            return str;
        }
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while (i < length) {
            sb.append('0');
            i++;
        }
        sb.append(str);
        return sb.substring(sb.length() - length);
    }

    /**
     * 根据要求字符串长度，字符串右补空格
     * 
     * @author YuanBing
     * @param s
     * @param length
     * @return
     */
    public static String rightFillSpace(String s, int length) {
        int fillLength = 0;
        if (s != null) {
            if (s.trim().length() > length) {
                return s.trim().substring(0, length);
            } else {
                fillLength = length - s.trim().length();
            }
        } else {
            fillLength = length;
        }
        StringBuilder sb = new StringBuilder();
        if (s != null) {
            sb.append(s.trim());
        }
        for (int i = 0; i < fillLength; i++) {
            sb.append(" ");
        }
        if (sb.length() > length) {
            return sb.substring(0, length);
        }

        return sb.toString();
    }

    /**
     * 格式化钱（如：2000L--20.00）
     * 
     * @param money
     * @return
     */
    public static String formatMoney(Long money) {
        if (money == null || "".equals(String.valueOf(money).trim())) {
            return "0.00";
        }
        String m = money.toString();
        int len = m.trim().length();
        String result = "";
        switch (len) {
            case 1:
                result = "0.0" + m;
                break;
            case 2:
                result = "0." + m;
                break;
            default:
                result = m.substring(0, m.length() - LENGTH_TWO) + STR_DOT + m.substring(m.length() - LENGTH_TWO);
                break;
        }
        return result;
    }

    /**
     * 格式化钱的样式如输入：777777777777->"7,777,777,777.77"
     * 
     * @param money long型的钱
     * @return 格式化后的string 钱
     */
    public static String formatBankSpaceMoney(Long money) {
        if (money == null) {
            return null;
        }
        int mflag = 0;
        if (money < 0) {
            money = money * (-LENGTH_ONE);
            mflag = -1;
        }
        String m = money.toString();
        int len = m.trim().length();
        String result = "";
        if (len == 1) {
            result = "0.0" + m;
        } else if (len == 2) {
            result = "0." + m;
        } else {
            String s = m.substring(0, m.length() - 2);
            if (s.length() < LENGTH_THREE) {
                result = s + "." + m.substring(m.length() - 2);
            } else {
                result = "." + m.substring(m.length() - 2);
                int i = 0;
                String st = s;
                for (; st.length() >= LENGTH_THREE;) {
                    if (i == 0) {
                        result = st.substring(st.length() - LENGTH_THREE) + result;
                        i++;
                    } else {
                        result = st.substring(st.length() - LENGTH_THREE) + "," + result;
                    }
                    st = st.substring(0, st.length() - LENGTH_THREE);
                }
                if (st.length() > 0) {
                    result = st + "," + result;
                }
            }
        }
        if (mflag < 0) {
            result = "-" + result;
        }
        return result;
    }

    /**
     * 格式化钱（如："2000"--"20.00"）
     * 
     * @param money
     * @return
     */
    public static String formatMoney(String money) {
        if (money == null || "".equals(money.trim())) {
            return "0.00";
        }
        Double dTemp = Double.parseDouble(money);
        Long mon = dTemp.longValue();
        String m = mon.toString();
        int len = m.length();
        String result = "";
        switch (len) {
            case 1:
                result = "0.0" + m;
                break;
            case 2:
                result = "0." + m;
                break;
            default:
                result = m.substring(0, m.length() - 2) + "." + m.substring(m.length() - 2);
                break;
        }
        return result;
    }

    /**
     * 将string 格式的钱转换为long（如："20.00"--2000L） 输入格式必须为"20.00"
     * 
     * @param money
     * @return
     */
    public static Long moneyToLong(String money) {
        if (money == null) {
            return null;
        }
        money = money.trim();
        if (money.indexOf(".") > 0) {
            int index = money.indexOf(".");
            int len = money.length();
            if (len - index >= LENGTH_THREE) {
                money = money.substring(0, index) + money.substring(index + LENGTH_ONE, index + LENGTH_THREE);
            } else if (len - index == LENGTH_TWO) {
                money = money.substring(0, index) + money.substring(index + 1) + "0";
            } else {
                money = money.substring(0, index) + MONEY_FEN;
            }
        } else {
            money = money + MONEY_FEN;
        }
        return Long.valueOf(money);
    }

    /**
     * 将string 格式的钱转换为long（如："20.0或20.00"--2000L） 输入格式必须为
     * 
     * @param money
     * @return
     */
    public static Long money2Long(String money) {
        if (money == null) {
            return null;
        }
        money = money.trim();
        if (money.indexOf(".") > 0) {
            int index = money.indexOf(".");
            if (money.length() - index >= LENGTH_THREE) {
                money = money.substring(0, index) + money.substring(index + LENGTH_ONE, index + LENGTH_THREE);
            } else if (money.length() - index == 2) {
                money = money.substring(0, index) + money.substring(index + LENGTH_ONE) + "0";
            } else {
                money = money.substring(0, index) + MONEY_FEN;
            }
        } else {
            money = money + MONEY_FEN;
        }
        return Long.valueOf(money);
    }

    /**
     * 中间方法:例如20.00--2000 4r.5t--4r5t f57.t--f57t0
     * */
    public static String stringToString(String money) {
        if (money == null) {
            return null;
        }
        money = money.trim();
        if (money.indexOf(".") > 0) {
            int index = money.indexOf(".");
            int len = money.length();
            if (len - index >= 3) {
                money = money.substring(0, index) + money.substring(index + 1, index + 3);
            } else if (len - index == 2) {
                money = money.substring(0, index) + money.substring(index + 1) + "0";
            } else {
                money = money.substring(0, index) + MONEY_FEN;
            }
        } else {
            money = money + MONEY_FEN;
        }
        return money;
    }

    /**
     * 将string 格式的钱转换为long（如："20.00"--2000） 输入格式必须为"20.00"
     * 
     * @param money
     * @return
     */
    public static String moneyToString(String money) {
        if (money == null) {
            return null;
        }
        return moneyToLong(money).toString();
    }

    /**
     * 将string 格式的钱转换为string（如："20.0或20.00"--2000） 输入格式必须为
     * 
     * @param money
     * @return
     */
    public static String money2String(String money) {
        if (money == null) {
            return null;
        }
        return money2Long(money).toString();
    }

    /**
     * 去除字符串中的空格,换行,制表符
     * 
     * @param s
     * @return
     */
    public static String trim(String s) {
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        Matcher m = p.matcher(s);
        String after = m.replaceAll("");
        return after;
    }

    public static String[] split(String s, String regex) {
        String[] sa = s.split(regex);

        return sa;
    }

    /**
     * 把库号+内卡号转为14位的卡号给pos用
     */
    public static String cutString(String cardId) {
        if (null == cardId || "".equals(cardId.trim())) {
            return null;
        }
        if (cardId.length() <= CARD_LENGTH_14) {
            return cardId;
        } else {
            return cardId.substring(cardId.length() - CARD_LENGTH_14);
        }
    }

    /**
     * 卡号不足16位，在前面补0
     * 
     * @param cardNum
     * @return
     */
    public static String make16DigitCardNum(String cardNum) {
        String systemCardNum = cardNum;
        int cardLength = cardNum.length();
        if (cardLength < CARD_LENGTH_16) {
            for (int i = 0; i < CARD_LENGTH_16 - cardLength; i++) {
                systemCardNum = "0" + systemCardNum;
            }
        }

        return systemCardNum;
    }

    /**
     * 
     * 判断是否相等
     * 
     * @param obj1
     * @param obj2
     * @return
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean equals(Object obj1, Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    public static boolean equalsStrict(String str1, String str2) {
        if (null == str1 || null == str2) {
            return false;
        }
        String trimStr1 = StringUtil.trim(str1);
        String trimStr2 = StringUtil.trim(str2);
        if (StringUtils.isEmpty(trimStr1) || StringUtils.isEmpty(trimStr2)) {
            return false;
        }
        return trimStr1.equals(trimStr2);
    }

    /**
     * 判断输入的字符串是否匹配
     * 
     * @param inputStr 输入字符串
     * @param match 匹配模式
     * @return
     */
    public static boolean isStrMatches(String inputStr, String match) {
        boolean isMatch = false;
        if (isNull(inputStr) || isNull(match)) {
            return isMatch;
        } else {
            Pattern pattern = Pattern.compile(match);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isMatch = true;
            }
        }
        return isMatch;
    }
    
    /**
     * 截取字符串，长度大于maxLength的字符串，保留前maxLength位加"..."
     * 
     * @param s
     * @return
     */
    public static String cutString(String s, int maxLength) {
        if (maxLength > 0) {
            if (s != null && s.length() > maxLength) {
                return s.substring(0, maxLength) + "...";
            }
        }
        return s;
    }
    
    /**
     * 千分位加逗号
     */
    public static String toStrMicrometer(String str){
        BigDecimal b = new BigDecimal(str); 
        DecimalFormat d1 =new DecimalFormat("#,##0.######");
        str = d1.format(b);
        return str;
    }
    public static boolean isBlank(String str)
    {
        return str == null || str.trim().length() == 0;
    }

    public static boolean isNotBlank(String str)
    {
        return !isBlank(str);
    }
    
  //首字母转小写  
    public static String toLowerCaseFirstOne(String s)  
    {  
        if(Character.isLowerCase(s.charAt(0)))  
            return s;  
        else  
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();  
    }
    
    /**
	 * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母 注意：传入参数必须全部为中文，数字或者字母会被忽略
	 * 汉字拼音的首字母大写，不管多音字，取第一个
	 * 
	 * @param chinese
	 * @return
	 * @author liuwei
	 */
	private static String getFullSpellStr(String chinese) {
		if (chinese == null) {
			return null;
		}
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不标声调
		format.setVCharType(HanyuPinyinVCharType.WITH_V);// u:的声母替换为v
		try {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < chinese.length(); i++) {
				String[] array = PinyinHelper.toHanyuPinyinStringArray(
						chinese.charAt(i), format);
				if (array == null || array.length == 0) {
					continue;
				}
				String s = array[0];// 不管多音字,只取第一个
				String pinyin = String.valueOf(s.charAt(0)).toUpperCase()
						.concat(s.substring(1));
				sb.append(pinyin);
			}
			return sb.toString();
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取字符串内的所有汉字的汉语拼音并大写或者小写 注意：isUpperCase 为true 全部大写，false全部小写
	 * 
	 * @param chinese
	 *            <String>
	 * @param isUpperCase
	 *            <boolean>
	 * @return
	 * @author liuwei
	 */
	public static String getFullSpell(final String chinese,
			final boolean isUpperCase) {
		String str = null;
		if (isUpperCase)
			str = getFullSpellStr(chinese).toUpperCase();
		else
			str = getFullSpellStr(chinese).toLowerCase();
		return str;
	}

	/**
	 * 获取字符串内的所有汉字的汉语拼音并大写每个字的首字母 注意：传入参数必须全部为中文，数字或者字母会被忽略
	 * 汉字拼音的首字母大写，不管多音字，取第一个
	 * 
	 * @param chinese
	 * @return
	 * @author liuwei
	 */
	public static String getFullSpell(final String chinese) {
		return getFullSpellStr(chinese);
	}

	/**
	 * 获取字符串内的所有汉字的汉语拼音的首字母并大写 注意：传入参数必须全部为中文，数字或者字母会被忽略 汉字拼音的首字母并大写，不管多音字，取第一个
	 * 
	 * @param chinese
	 * @return
	 * @author liuwei
	 */
	public static String getFirstSpell(String chinese) {
		if (chinese == null) {
			return null;
		}
		try {
			HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
			format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 小写
			format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不标声调
			format.setVCharType(HanyuPinyinVCharType.WITH_V);// u:的声母替换为v

			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < chinese.length(); i++) {
				String[] array = PinyinHelper.toHanyuPinyinStringArray(
						chinese.charAt(i), format);
				if (array == null || array.length == 0) {
					continue;
				}
				String s = array[0];// 不管多音字,只取第一个
				String pinyin = String.valueOf(s.charAt(0)).toUpperCase();
				sb.append(pinyin);
			}
			return sb.toString();
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}
		return null;
	}

    
}
