package com.cloud.cang.core.common.tags;


import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;


/**
 * Str replace Tag 
 * @author zhouhong
 * @version 1.0
 * @eg: 15292248762  -> 152****8762
 */
public class ReplaceStrTag extends TagSupport {

    private static final long serialVersionUID = 2687826239258299582L;

    private static Logger logger = LoggerFactory.getLogger(ReplaceStrTag.class);

    private String value;

    private Integer beginIndex;

    private Integer endIndex;

    private String replaceStr;

    @Override
    public int doStartTag() throws JspException {

	JspWriter out = this.pageContext.getOut();

	try {
	    out.write(StringUtil.replaceStr(value, beginIndex, endIndex,
		    replaceStr));
	} catch (IOException e) {
	    logger.error("", e);
	}

	return super.doStartTag();
    }

    @Override
    public void release() {
	super.release();
	value = "";
	beginIndex = null;
	endIndex = null;
	replaceStr = "";
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public Integer getBeginIndex() {
	return beginIndex;
    }

    public void setBeginIndex(Integer beginIndex) {
	this.beginIndex = beginIndex;
    }

    public Integer getEndIndex() {
	return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
	this.endIndex = endIndex;
    }

    public String getReplaceStr() {
	return replaceStr;
    }

    public void setReplaceStr(String replaceStr) {
	this.replaceStr = replaceStr;
    }

}
