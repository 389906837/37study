/*
 * Copyright (C) 2016 37cang All rights reserved
 * Author: zhouhong
 * Date: 2016年3月30日
 * Description:ClientUtil.java 
 */
package com.cloud.cang.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 前置工具类
 * @author zhouhong
 * @version 1.0
 */
public class ClientUtil {
	
	
	
	
	/**
	 * 打招呼
	 * @return
	 */
	public  static String greeting(){
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		if (hour >= 6 && hour < 8) {
			return "早上好";
		} else if (hour >= 8 && hour < 11) {
			return "上午好";
		} else if (hour >= 11 && hour < 13) {
			return "中午好";
		} else if (hour >= 13 && hour < 18) {
			return "下午好";
		} else {
			return "晚上好";
		}
		
	}
	
	/**
	 * 返回当前日期为星期几 返回的星期为汉字。如：星期一
	 * 
	 * @return String
	 */
	public static String getWeekOfToday() {
		GregorianCalendar curDay = new GregorianCalendar();
		curDay.setFirstDayOfWeek(0);
		int week = curDay.get(Calendar.DAY_OF_WEEK); // 得到星期几
		String weekStr = "";
		switch (week - 1) {
		case 0:
			weekStr = "星期日";
			break;
		case 1:
			weekStr = "星期一";
			break;
		case 2:
			weekStr = "星期二";
			break;
		case 3:
			weekStr = "星期三";
			break;
		case 4:
			weekStr = "星期四";
			break;
		case 5:
			weekStr = "星期五";
			break;
		case 6:
			weekStr = "星期六";
			break;
		}
		return weekStr;
	}
	
	/**
	 * 获取今天是号
	 * 
	 * @return String
	 */
	public static String getDays() {
		return  new SimpleDateFormat("dd").format(new Date());
		
	}
	
	/**
	 * 获取月份
	 * 
	 * @return String
	 */
	public static String getMoth() {
		Calendar cal = Calendar.getInstance();
		return  String.valueOf(cal.get(Calendar.MONTH) + 1);
		
	}
	


}
