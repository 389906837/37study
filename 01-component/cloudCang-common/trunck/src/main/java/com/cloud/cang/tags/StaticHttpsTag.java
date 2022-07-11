/*
 * Copyright (C) 2015 37cang All rights reserved
 * Author: 【zhouhong】
 * Date: 2015年6月12日
 * Description:formatTag.java 
 */
package com.cloud.cang.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**

 * @author zhouhong  把图片路径转为缩略图片地址
 * @version 1.0
 */
public class StaticHttpsTag extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -201308090000231L;
	private String path; 
	/*值*/
	
	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
		    if(path==null || path.equals("")){
		    	pageContext.getOut().write("");
		    }else if(path.startsWith("http")){
		    	pageContext.getOut().write(path.replace("http:", ""));
		    }else if(path.startsWith("https")){
		    	pageContext.getOut().write(path.replace("https:", ""));
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public static void main(String[] args) {
	    int point="aaaa.pgn".lastIndexOf(".");
        
       System.out.println("aaaa.pgn".substring(0, point)+"_"+"10X10"+"aaaa.pgn".substring(point));
    }
	
	@Override
	public void release() {
	    super.release();
	    path = null;
	}
	
	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

   

}
