package com.cang.os.common.utils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义java.util.Date 日期转换器
 * @author Sunny
 *
 */
public class DateConverter implements Converter<String, Date> {
	@Override
	public Date convert(String source) {
		if (StringUtils.isBlank(source)) {
			return null;
		}

		try {

			int count = 0;
			Pattern ptn = Pattern.compile(":");
			Matcher mat = ptn.matcher(source);
			while (mat.find()) {
				count++;
			}
			if (count == 2) {
				SimpleDateFormat datetimeFormat = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				datetimeFormat.setLenient(false);
				return datetimeFormat.parse(source);
			} else if (count == 0) {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				dateFormat.setLenient(false);
				return dateFormat.parse(source);
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException(
					"Could not parse date, date format is error ");
		}
		return null;
	}
}