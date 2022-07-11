package com.cang.os.common.utils.id;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import com.cang.os.common.utils.id.generator.UUIDGenerator;


public class IdUtil {
	/**
	 * 初始化
	 * 
	 * @return
	 */
	public static IdUtil getInit() {
		return new IdUtil();
	}

	/**
	 * 获得随机数（纯数字）
	 * 
	 * @param num
	 * @return
	 */
	public String getRandomNumber(int num) {
		int codeCount = num;
		String str = "";
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9' };
		Random random = new Random();
		for (int i = 0; i < codeCount; i++) {
			str = str + codeSequence[random.nextInt(10)];
		}
		return str;
	}

	/**
	 * 拿 id 比如 xxxx-xxxx-xxxx-xxxx
	 * 
	 * @return
	 */
	public String getRandom() {
		String Random = "";
		int j = 4;
		for (int i = 0; i < 4; i++) {
			Random += this.getRandomNumberAndLetter(j);
			Random += "-";
		}
		String rand = Random.substring(0, Random.lastIndexOf("-"));
		return rand;
	}

	/**
	 * 获得随机数（数字加字母）
	 * 
	 * @param num
	 * @return
	 */
	public String getRandomNumberAndLetter(int num) {
		int codeCount = num; // 随机数的位数
		String str = "";
		char[] codeSequence = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'v', 'w', 'x', 'y', 'z' };
		Random random = new Random();
		for (int i = 0; i < codeCount; i++) {
			str = str + codeSequence[random.nextInt(62)];
		}
		return str;
	}

	/**
	 * 获取日期
	 * 
	 * @param type
	 * @return
	 */
	public String getDate(String type) {
		Date d = new Date();
		// String str = d.toString();
		// SimpleDateFormat sdf=new
		// SimpleDateFormat("yyyy-MM-dd  kk:mm:ss ");//其中yyyy-MM-dd是你要表示的格式
		SimpleDateFormat sdf = new SimpleDateFormat(type);
		// 可以任意组合，不限个数和次序；具体表示为：MM-month,dd-day,yyyy-year;kk-hour,mm-minute,ss-second;
		String str = sdf.format(d);
		return str;
	}

	/**
	 * 生成订单（时间+4位随机数）
	 * 
	 * @param str
	 * @return true表示是数字，false表示不是数字
	 */
	public String getOrderID() {
		return getDate("yyMMddHHmmss") + getRandomNumber(4);
	}

	/**
	 * 
	 * @function:
	 * @return
	 * @author wolf
	 * @QQ 405645010
	 * @date:2011-11-2 下午02:23:09
	 * @version :2.0
	 * @description :yyyyMMhh 年月日
	 */
	public String getDateyyyyMMdd() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date());
	}

	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * 
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if (null == date) {
			return 0;
		}

		return date.getTime() / 1000;
	}



	public long getLongId() {
		return Long.parseLong(getDate("yyMMddHHmmss") + getRandomNumber(7));
	}
	
	/**
	 * 获取唯一UUID
	 * @return
	 */
	public String getUuid() {

		UUIDGenerator uuid = new UUIDGenerator();
		return uuid.create();
	}
	
	


	public static void main(String[] args) {
		
		for(int i=0;i<200;i++){
			
			
		
			System.out.println(IdUtil.getInit().getUuid());
		}
			
		
		
		

	}
}
