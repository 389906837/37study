package com.cloud.cang.core.common.tags;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
/**
 * 
 * 
 * 类功能描述: URL编码标签
 * @author  zhouhong
 * @version V1.0, 2014-4-24
 */
public class URLEncodeTag extends TagSupport {
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -7686694628583315127L;
    private String data;
    public String getData() {
        return data;
    }

    @Override
	public int doEndTag() throws JspException {
    	try {
            if(StringUtils.isBlank(data))return SKIP_BODY;
            JspWriter out = pageContext.getOut();
            out.print(URLEncoder.encode(data, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
	}

    public void setData(String data) {
        this.data = data;
    }
    public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("/111/333", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
