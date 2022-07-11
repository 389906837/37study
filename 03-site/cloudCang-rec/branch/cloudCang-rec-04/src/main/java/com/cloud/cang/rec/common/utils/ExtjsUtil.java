package com.cloud.cang.rec.common.utils;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;


public class ExtjsUtil {
	
	public static Date timeSplitJoin(Date d,String t){
		if(d==null) return null;
		String timeStr= DateUtils.dateParseShortString(d) +" "+t+":00";
		Date dresult=DateUtils.convertToDateTime(timeStr);
		if(null==dresult){
			throw new ServiceException("时间设置错误");
		}
		return dresult;
	}
	
	
	public static Integer confineInt(String c){
		if(StringUtils.isBlank(c) || c.equals("不限制") || c.equals("不限") || c.equals("0") ){
			return 0;
		}
		try{
			return Integer.parseInt(c);
		}catch(Exception e){
			return 0;
		}
	}

}
