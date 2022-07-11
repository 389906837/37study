package com.cloud.cang.core.common.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * 
 * 
 * 类功能描述: URL解码标签
 * @author  zhouhong
 * @version V1.0, 2014-4-24
 */
public class URLDecodeTag extends TagSupport {
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -7686694628583315127L;
    private String data;
    
    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.print(URLDecoder.decode(data, "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
   
}
