package com.cloud.cang.message;


import com.cloud.cang.common.SuperDto;

import java.util.Map;


/**
 * 生成协议参数
 * @author zhouhong
 * @version 1.0
 */
public class ProtocolBuildDto extends SuperDto {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 模板ID
     */
    private String templateId;
    
    /**
     * 模板映射内容
     */
    private Map<String,Object> values;
    
    /**
     * 是否是协议
     */
    private boolean isProtocol=false;

    private String url;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Map<String, Object> getValues() {
		return values;
	}

	public void setValues(Map<String, Object> values) {
		this.values = values;
	}

	public boolean isProtocol() {
		return isProtocol;
	}

	public void setProtocol(boolean isProtocol) {
		this.isProtocol = isProtocol;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
	
  
}
