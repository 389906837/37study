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

import org.apache.commons.lang3.StringUtils;

/**

 * @author zhouhong  把图片路径转为缩略图片地址
 * @version 1.0
 */
public class ImagePathSize extends TagSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -201308090000231L;
	private String srcPath; 
	/*值*/
	private String size;
	
	@Override
	public int doStartTag() throws JspException {
		try {
		    if(StringUtils.isBlank(size)){
		        pageContext.getOut().write(srcPath);
		    }else{
		        int point=srcPath.lastIndexOf(".");
	            pageContext.getOut().write(srcPath.substring(0, point)+"_"+size+srcPath.substring(point));
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
	    size = "";
        srcPath = "";
	}
	
	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

    public String getSrcPath() {
        return srcPath;
    }

    public void setSrcPath(String srcPath) {
        this.srcPath = srcPath;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

}
