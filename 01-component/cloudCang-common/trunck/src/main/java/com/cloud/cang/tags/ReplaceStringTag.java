/** 
 * Select.java
 * create on 2014-7-15
 * Copyright 2020 XYR All Rights Reserved.
 */
package com.cloud.cang.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;


/**
 * 把字符串替换为指定的截取格式
 * 如：value=13588882222,beforesize=3,aftersize=4变成135****2222 <br/>
 * 如：value=13588882222,before=3变成***88882222 <br/>
 * 如：value=13588882222,beforesize=3变成135*** <br/>
 * 如：value=13588882222,aftersize=3变成***222 <br/>
 * @since version1.0
 *   
 */
public class ReplaceStringTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 404287123436653073L;
	// 数据属性
	private String value;// 当前列表值

	private String replaceStr="***";
	private int beforesize = -1;//前面留的字符大小
	private int aftersize = -1;//后面留的字符大小
    private int before = -1;//前面要隐藏的字符大小

	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		String content = "";
		if (StringUtils.isNotBlank(value) && value.length() > beforesize) {
			if (before == -1) {
				if (beforesize == -1 && aftersize == -1) {
					content = this.replaceStr;
				} else if (beforesize == -1) {
					content += replaceStr;
					if (value.length() > this.aftersize)
						content += value.substring(value.length()
								- this.aftersize, value.length());
				} else if (aftersize == -1) {
					if (value.length() > this.beforesize)
						content += value.substring(0, beforesize);
					content += replaceStr;
				} else {
					if (value.length() > this.beforesize)
						content += value.substring(0, beforesize);
					content += replaceStr;
					if (value.length() > this.aftersize)
						content += value.substring(value.length()
								- this.aftersize, value.length());
				}
			} else {
				content += replaceStr + value.substring(before);
			}
		}
		try {
			out.write(content.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}


	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}


	/**
	 * @return the replaceStr
	 */
	public String getReplaceStr() {
		return replaceStr;
	}


	/**
	 * @param replaceStr the replaceStr to set
	 */
	public void setReplaceStr(String replaceStr) {
		this.replaceStr = replaceStr;
	}


	/**
	 * @return the beforesize
	 */
	public int getBeforesize() {
		return beforesize;
	}


	/**
	 * @param beforesize the beforesize to set
	 */
	public void setBeforesize(int beforesize) {
		this.beforesize = beforesize;
	}


	/**
	 * @return the aftersize
	 */
	public int getAftersize() {
		return aftersize;
	}


	/**
	 * @param aftersize the aftersize to set
	 */
	public void setAftersize(int aftersize) {
		this.aftersize = aftersize;
	}


	public int getBefore() {
		return before;
	}


	public void setBefore(int before) {
		this.before = before;
	}


	
}