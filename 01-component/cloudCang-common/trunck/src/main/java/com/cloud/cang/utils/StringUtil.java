
package com.cloud.cang.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;


//import com.fgoods.etradefront.services.common.hotkey.po.Hotkey;

/**
 * String辅助类
 * 
 * @version V1.0, 2015-1-15
 */
public class StringUtil {

	
	/**
	 * 将字符以4个为单位增加空格
	 * @param bankId
	 * @return
	 */
	public static String formartSpace(String bankId){
		String val="";
		//Math.floor(a);
		int len=4;
		int a=bankId.length()/len;
		if(bankId.length()%len>0){
			 a++;
		}
		for(int i=0;i<a;i++){
			int last=(i+1)<<2;
			if(last>bankId.length()){
				val+=bankId.substring(i<<2);
			}else if(a==(i+1)){
				val+=bankId.substring(i<<2, last);
			}else{
				val+=bankId.substring(i<<2, last)+" ";
			}
		}
		return val;
	}

	public static boolean isNotNull(String s) {
        return null != s && s.trim().length() != 0;
    }

    public static boolean isNull(String s) {
        return null == s || s.trim().length() == 0;
    }
    
	private static MessageDigest digest = null; // 用于创建MD5加密对象
	/**
	 * 字符串转为时间类型
	 * 
	 * @param dateStr
	 * @return Date
	 */
	public static final Date parseDate(String dateStr) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 把String[] 转成 List<String[]>
	 * 
	 * @param list
	 * @param pageSize
	 *            ,每个数组的大小
	 * @return
	 */
	public static List<String[]> splitArray(String[] strs, int pageSize) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < strs.length; i++) {
			list.add(strs[i]);
		}

		List<String[]> sList = new ArrayList<String[]>();
		int count = list.size();
		int size;
		if (count % pageSize == 0) {
			size = count / pageSize;
		} else {
			size = count / pageSize + 1;
		}
		for (int i = 0; i < size; i++) {
			List<String> tempList;
			if (i == size - 1) {
				tempList = list.subList(i * pageSize, list.size());
			} else {
				tempList = list.subList(i * pageSize, (i + 1) * pageSize);
			}
			String[] mobiles = new String[tempList.size()];
			for (int j = 0; j < mobiles.length; j++) {
				mobiles[j] = tempList.get(j);
			}
			sList.add(mobiles);
		}
		return sList;
	}

	/**
	 * 对指定的字符串做替换功能(大小写区别)
	 * 
	 * @param s
	 *            <String>(指定的字符串),s1<String>(要替换的字符串),s2<String>(新的字符串)
	 * @return String
	 */
	public static final String replace(String s, String s1, String s2) {

		if (s == null)
			return null;
		int i = 0;
		if ((i = s.indexOf(s1, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s.indexOf(s1, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}

			stringbuffer.append(ac, k, ac.length - k);
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	/**
	 * 对指定的字符串做替换功能(大小写不区别)
	 * 
	 * @param s
	 *            <String>(指定的字符串),s1<String>(要替换的字符串),s2<String>(新的字符串)
	 * @return String
	 */
	public static final String replaceIgnoreCase(String s, String s1, String s2) {

		if (s == null)
			return null;
		String s3 = s.toLowerCase();
		String s4 = s1.toLowerCase();
		int i = 0;
		if ((i = s3.indexOf(s4, i)) >= 0) {
			char ac[] = s.toCharArray();
			char ac1[] = s2.toCharArray();
			int j = s1.length();
			StringBuffer stringbuffer = new StringBuffer(ac.length);
			stringbuffer.append(ac, 0, i).append(ac1);
			i += j;
			int k;
			for (k = i; (i = s3.indexOf(s4, i)) > 0; k = i) {
				stringbuffer.append(ac, k, i - k).append(ac1);
				i += j;
			}

			stringbuffer.append(ac, k, ac.length - k);
			return stringbuffer.toString();
		} else {
			return s;
		}
	}

	/**
	 * 将字符中的"<"和">"替换为html中的标签格式"&lt;"和"&gt;"
	 * 
	 * @param s
	 *            <String>(指定的字符串)
	 * @return String
	 */
	public static final String escapeHTMLTags(String s) {

		if (s == null || s.length() == 0)
			return s;
		StringBuffer stringbuffer = new StringBuffer(s.length());

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c == '<')
				stringbuffer.append("&lt;");
			else if (c == '>')
				stringbuffer.append("&gt;");
			else
				stringbuffer.append(c);
		}

		return stringbuffer.toString();
	}

	/**
	 * 将字符中转换为数字类型(int)
	 * 
	 * @param str
	 *            <String>(指定的字符串)
	 * @return int
	 */
	public static final int toNumber(final String str) {

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * 将字符中转换为数字类型(int),可以设置缺省值
	 * 
	 * @param str
	 *            <String>(指定的字符串)，defaultValue<int>(缺省值)
	 * @return int
	 */
	public static final int toNumber(final String str, int defaultValue) {

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	// 格式化小数对象
	private static DecimalFormat df = new DecimalFormat("##,###.00");

	// 格式化整数为默认格式对象
	private static NumberFormat nf = NumberFormat.getInstance();

	/**
	 * 格式化浮点型的值的小数点后为两位小数
	 * 
	 * @param d
	 *            <double>（浮点型值）
	 * @return String（格式化后的字符串）
	 */
	public static String formatDouble(double d) {
		String data = df.format(d);
		if (data.startsWith("."))
			data = "0" + data;
		return data;
	}

	/**
	 * 格式化整数为默认格式
	 * 
	 * @param d
	 *            <int>（整型值）
	 * @return String（格式化后的字符串）
	 */
	public static String formatInt(int d) {

		return nf.format(d);
	}

	/**
	 * 格式化整数为默认格式
	 * 
	 * @param d
	 *            <double>（浮点型值）
	 * @return String（格式化后的字符串）
	 */
	public static String formatInt(double d) {

		return nf.format(d);
	}

	/**
	 * 将字符中转换为小写的数组
	 * 
	 * @param s
	 *            <String>(指定的字符串)
	 * @return String[]
	 */
	public static final String[] toLowerCaseWordArray(String s) {

		if (s == null || s.length() == 0)
			return new String[0];
		StringTokenizer stringtokenizer = new StringTokenizer(s, " ,\r\n.:/\\+");
		String as[] = new String[stringtokenizer.countTokens()];
		for (int i = 0; i < as.length; i++)
			as[i] = stringtokenizer.nextToken().toLowerCase();

		return as;
	}

	/**
	 * 将gb格式的字符转换为utf-8格式字符
	 * 
	 * @param src
	 *            <String>
	 * @return String
	 */
	public static String gbToUtf8(String src) {

		byte b[] = src.getBytes();
		char c[] = new char[b.length];
		for (int i = 0; i < b.length; i++)
			c[i] = (char) (b[i] & 0xff);
		return new String(c);
	}

	/**
	 * 将字符串转换为GBK格式字符串
	 * 
	 * @param s
	 *            <String>
	 * @return String
	 */
	public static final String ConvertGBK(String s) {

		String s1 = "";
		if (s == null || s.trim().length() == 0)
			return "";
		try {
			s1 = new String(s.getBytes("ISO-8859-1"), "GBK");
		} catch (Exception exception) {
		}
		return s1;
	}

	/**
	 * 将null转换为""空字符串
	 * 
	 * @param src
	 *            <String>
	 * @return String
	 */
	public static final String NULLToSpace(String s) {

		if (s == null)
			return "";
		else
			return s.trim();
	}

	/**
	 * 验证字符串是否为空
	 * 
	 * @param str
	 * @return boolean
	 */
	public final static boolean isEmpty(String str) {

        return str == null || str.trim().length() == 0;
	}

	/**
	 * 验证是否为整数
	 * 
	 * @param str
	 * @return boolean
	 */
	public final static boolean isNumber(String str) {

		try {
			Integer.parseInt(str);
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * 验证是否为Float类型
	 * 
	 * @param str
	 * @return boolean
	 */
	public final boolean isFloat(String str) {

		try {
			Float.parseFloat(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 验证是否为Double类型
	 * 
	 * @param str
	 * @return boolean
	 */
	public final boolean isDouble(String str) {

		try {
			Double.parseDouble(str);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 验证是否为email
	 * 
	 * @param str
	 * @return boolean
	 */
	public final boolean isEmail(String str) {
		if (str.trim().length() != 0) {
			int first = str.indexOf("@");
			int last = str.lastIndexOf("@");

            return !(first != last || first == -1 || str.endsWith("@") == true);
		} else {
			return false;
		}
	}

	/**
	 * 验证是否为日期字符串(格式:yyMMdd)
	 * 
	 * @param str
	 * @return boolean
	 */
	public static boolean isDate(String str) {

		boolean bValid = true;
		boolean bl = false;

		if (str != null) {
			try {
				if (str.length() == 10) {
					for (int i = 0; i < 10; i++) {
						String sTem = Integer.toString(i);
						if (str.endsWith(sTem)) {
							bl = true;
						}
					}

					if (bl) {
						DateFormat formatter = DateFormat.getDateInstance(
								DateFormat.SHORT, Locale.getDefault());
						formatter.setLenient(false);
						formatter.parse(str);
					} else {
						return false;
					}
				} else {
					return false;
				}
			} catch (ParseException e) {
				bValid = false;
			}
		} else {
			bValid = false;
		}

		return bValid;
	}

	/**
	 * 比较两日期(字符)的大小,日期格式为yyMMdd.
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return boolean
	 */
	public final boolean compareDate(String beginDate, String endDate) {

		long begin = Integer.parseInt(beginDate.substring(0, 4)
				+ beginDate.substring(5, 7) + beginDate.substring(8, 10));
		long end = Integer.parseInt(endDate.substring(0, 4)
				+ endDate.substring(5, 7) + endDate.substring(8, 10));
        return begin <= end;
	}

	/**
	 * todaysteel字符串加密算法
	 * 
	 * @param sConst
	 *            原字符串
	 * @param key
	 *            密钥
	 * @return String
	 */
	public static String encryptWord(String sConst, int key) {

		char r[] = new char[sConst.length()];
		char a[] = sConst.toCharArray();

		for (int i = 0; i < sConst.length(); i++) {
			r[i] = (char) (a[i] ^ key >> 8);
			key = (byte) r[i] + key;
		}
		return new String(r);
	}

	/**
	 * 采用MD5加密算法，加密字符串
	 * 
	 * @param s
	 *            <String>（被加密源字符串）
	 * @return String（加密后的字符串）
	 */
	public static final synchronized String hash(String s) {

		if (digest == null)
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException nosuchalgorithmexception) {
				System.err
						.println("Failed to load the MD5 MessageDigest. Jive will be unable to function normally.");
				nosuchalgorithmexception.printStackTrace();
			}
		digest.update(s.getBytes());
		return toHex(digest.digest());
	}

	/**
	 * 将byte类型的数据转为字符串
	 * 
	 * @param abyte0
	 *            []<byte>
	 * @return String（转换后的字符串）
	 */
	public static final String toHex(byte abyte0[]) {

		StringBuffer stringbuffer = new StringBuffer(abyte0.length * 2);
		for (int i = 0; i < abyte0.length; i++) {
			if ((abyte0[i] & 0xff) < 16)
				stringbuffer.append("0");
			stringbuffer.append(Long.toString(abyte0[i] & 0xff, 16));
		}

		return stringbuffer.toString();
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

	/***
	 * 判断字符串中是否包含中文 boolean
	 * 
	 * @author liuwei
	 */
	public static boolean hasCn(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		} else {
            return str.getBytes().length > str.length();
		}
	}

	public static String numToChinese(int number) {
		String[] chinese = new String[] { "", "一", "二", "三", "四", "五", "六",
				"七", "八", "九" };
		return chinese[number];
	}

	/**
	 * 格式化收款银行帐号
	 * 
	 * @param sreceiveaccount
	 *            收款帐号字符串(id:name@id:name@)
	 * @return
	 */
	public static String formatBankAccount(String sreceiveaccount) {
		String bankAccount = "";
		if (StringUtils.isNotBlank(sreceiveaccount)) {
			String[] sreceiveaccounts = sreceiveaccount.substring(0,
					sreceiveaccount.length() - 1).split("@");
			for (int v = 0; v < sreceiveaccounts.length; v++) {
				String[] temp = sreceiveaccounts[v].split(":");
				bankAccount += temp[1] + "\n";
			}
		}
		return bankAccount;
	}

	/**
	 * 取得收款银行帐号的ID
	 * 
	 * @param serceiveraccount
	 *            收款帐号字符串(id:name@id:name@)
	 * @return
	 */
	public static List<String> getBankAccountId(String serceiveraccount) {
		List<String> list = new ArrayList<String>();
		if (StringUtils.isNotBlank(serceiveraccount)) {
			String[] temp = serceiveraccount.split("@");
			if (null != temp && temp.length > 0) {
				for (String tmp : temp) {
					list.add(tmp.split(":")[0]);
				}
			}
		}
		return list;
	}

	/**
	 * 按给定字节数取字串
	 * 
	 * @param str
	 *            原串
	 * @startIndex 起始位置
	 * @param byteNum
	 *            要取的字节数
	 * @return
	 * @throws Exception
	 */
	public static String getSubStr(String str, int startIndex, int byteNum)
			throws Exception {
		try {
			if (str == null) {
				return "";
			}
			byte[] b = str.getBytes("GBK");
			if (startIndex >= b.length) {
				return "";
			}
			int index = 0;
			int n = 0;// 用于记录取了多少个字节
			for (; n < byteNum && index < b.length; index++) {
				if (b[index] != 0) {
					n++;
				}
			}

			// 避免出现最后是半个汉字而乱码
			// if (b[index] < 0) {//
			// 说明最后为汉字，这里可以自己定义，如果最后半个汉字不要则index--,如果补全则index++
			// index++;
			// }
			return new String(b, startIndex, index, "GBK");
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

    /**
     * 降字符串转换成给定长度的字符串，如超出的话截断，并在最后以三个点结尾
     * 
     * @param str
     *            String
     * @param length
     *            int
     * @return String
     */
    public static String toLengthForIntroduce(String str, int length) {
        str = delTag(str);

        byte[] strByte = str.getBytes();
        int byteLength = strByte.length;
        char[] charArray;
        StringBuffer buff = new StringBuffer();
        if (byteLength > (length * 2)) {
            charArray = str.toCharArray();
            int resultLength = 0;
            for (int i = 0; i < charArray.length; i++) {
                resultLength += String.valueOf(charArray[i]).getBytes().length;
                if (resultLength > (length * 2)) {
                    break;
                }
                buff.append(charArray[i]);

            }
            buff.append("...");
            str = buff.toString();
        }

        str = replace(str, "\"", "\\\"");
        str = replace(str, "，", ",");
        return str;

    }

    public static String delTag(String str) {
        str = str + "<>";
        StringBuffer buff = new StringBuffer();
        int start = 0;
        int end = 0;

        while (str.length() > 0) {
            start = str.indexOf("<");
            end = str.indexOf(">");
            if (start > 0) {
                buff.append(str.substring(0, start));
            }
            if (end > 0 && end <= str.length()) {
                str = str.substring(end + 1, str.length());
            } else {
                str = "";
            }

        }
        String result = buff.toString();

        while (result.startsWith(" ")) {

            result = result.substring(result.indexOf(" ") + 1, result.length());

        }
        return result;

    }

	/**
	 * 根据传入的分割符号和分隔符出现的次数获取需要截取的字节数/字符数，仅仅限于处理字母和数字组合
	 * 
	 * @param msg
	 *            , 待处理的字符串
	 * @param split
	 *            , 分隔符
	 * @param count
	 *            , 出现的次数
	 * @return
	 */
	public static int getBodySplitLength(String msg, String split, int count) {
		int length = 0;
		for (int i = 0; i < count; i++) {
			length += msg.indexOf(split) + 1;
			msg = msg.substring(msg.indexOf(split) + 1);
		}
		return length;
	}

	/**
	 * 如果字段为空，则返回空的字符串
	 * 
	 * @param da
	 * @return
	 */
	public static String getEmptyIfBlank(String da) {
		return StringUtils.isNotBlank(da) ? da : "";
	}

	
	/**
	 * 判断是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumberAndInteger(String str) {
		Pattern pattern = Pattern.compile("^\\d+$|^\\d+\\.\\d+$|-\\d+$");
		Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

	public static boolean isPositiveNumber(String str) {
		Pattern pattern = Pattern.compile("^\\+?[1-9]\\d*$");
		Matcher isNum = pattern.matcher(str);
        return isNum.matches();
    }

	/**
	 * 获取日期格式为yyMMdd的字符串
	 * 
	 * @return
	 */
	public static String getTodayDate() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	/**
	 * 提供字符数组转换，只针对于数据库字段解析
	 * 
	 * @param array
	 * @return 类似('1','2','3')的结果
	 */
	public static String arrayConvertion(String[] array) {
		String singular = "";
		for (String str : array) {
			singular += "'" + str + "',";
		}
		singular = singular.substring(0, singular.lastIndexOf(","));
		return singular;
	}

	 public static boolean isBlank(String str)
	    {
	        return str == null || str.trim().length() == 0;
	    }

	    public static boolean isNotBlank(String str)
	    {
	        return !isBlank(str);
	    }
	   
	
	/**
	 * 字符串替换为指定字符
	 * @param source 源字符
	 * @param startIndex 开始索引
	 * @param endIndex 结束索引
	 * @param replaceStr 替换的字符
	 * @return
	 */
    public static String replaceStr(String source, Integer startIndex,
	    Integer endIndex, String replaceStr) {
	startIndex = startIndex -1;
	String _source = source;
	if (isNull(source))
	    return "";
	if (isNull(replaceStr))
	    replaceStr = "****";
	if (startIndex == null || startIndex <= 0
		|| startIndex > source.length())
	    return _source;

	if (endIndex == null || endIndex <= startIndex) {
	    return _source = _source.substring(0, startIndex) + replaceStr;
	}

	if (endIndex > source.length()) {
	    endIndex = source.length();
	}

	StringBuffer sbstr = new StringBuffer(source.length());
	// startStr
	sbstr.append(_source.substring(0, startIndex));
	// replaceStr
	sbstr.append(replaceStr);
	sbstr.append(_source.substring(endIndex, source.length()));

	return sbstr.toString();

    }
    
    /**
	 * 将16进制转换为二进制
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * 将二进制转换成16进制
	 * 
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex);
		}
		return sb.toString();
	}
	
	 
	public static String replaceEmail(String email){
		if (StringUtils.isNotBlank(email)){
			if (email.indexOf("@") > 0){
				String [] emailArray = email.split("@");
				if (emailArray.length == 2) {
					String ename = emailArray[0];
					if(ename.length() <5){
						return "***@"+emailArray[1];
					}else{
						return ename.substring(0, 3)+"***"+ename.substring(ename.length() -1)+"@"+emailArray[1];
					}
				}
				
			}
		}
		return email;
	}
	
	 public static void main(String[] args) {
	    System.out.println(replaceEmail("85865514@qq.com"));
	}
}