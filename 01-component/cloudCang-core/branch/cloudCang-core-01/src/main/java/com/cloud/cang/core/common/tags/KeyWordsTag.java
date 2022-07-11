package com.cloud.cang.core.common.tags;

import com.cloud.cang.core.wz.service.KeyWordsService;

import com.cloud.cang.model.wz.KeyWords;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * keyWordsTag
 * @author zhouhong
 * @version 2.0
 *
 */
public class KeyWordsTag extends TagSupport {

	private static final long serialVersionUID = 6796793313583121264L;
	
	private static Logger logger = LoggerFactory.getLogger(KeyWordsTag.class);
	
    private String url; 
	
	private String title;
	
	private String keywords;

	private String description;
	
	@Override
	public int doStartTag() throws JspException {
		

		JspWriter out = this.pageContext.getOut();
		try {
			KeyWordsService service = SpringContext.getBean(KeyWordsService.class);
			KeyWords KeyWords=service.getKeyWords(url);
			
			if(StringUtil.isNotNull(title)){
				out.println("<title>"+KeyWords.getStitle()+"</title>");
			}
			
			if(StringUtil.isNotNull(keywords)){
				out.println("<meta name=\"keywords\" content=\""+KeyWords.getSkeyWord()+"\" />");
			}
			
			if(StringUtil.isNotNull(description)){
				out.println("<meta name=\"description\" content=\""+KeyWords.getSdescription()+"\" />");
			}
			
			if(StringUtil.isNull(title)&&StringUtil.isNull(keywords)&&StringUtil.isNull(description)){
				out.println("<title>"+KeyWords.getStitle()+"</title>");
				out.println("<meta  charset=\"utf-8\" />");
				out.println("<meta name=\"renderer\" content=\"webkit\" />");
				out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=Edge\" />");
				out.println("<meta name=\"format-detection\" content=\"telephone=no\" />");
				out.println("<meta name=\"keywords\" content=\""+KeyWords.getSkeyWord()+"\" />");
				out.println("<meta name=\"description\" content=\""+KeyWords.getSdescription()+"\" />");
			}
			
		} catch (IOException e) {
			logger.error("",e);
		}
		return super.doStartTag();
	}
	
	
	@Override
	public void release() {
		super.release();
		url = "";
		title = "";
		keywords = "";
		description = "";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
