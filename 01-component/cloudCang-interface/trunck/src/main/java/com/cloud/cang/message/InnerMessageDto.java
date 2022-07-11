package com.cloud.cang.message;

/**
 * 发送给内部人
 * 
 * @author zhouhong
 * @version 1.0
 */
@SuppressWarnings("serial")
public class InnerMessageDto extends MessageDto {

    /**
     * 权限编号
     */
    private String purviewCode;


    public String getPurviewCode() {
	return purviewCode;
    }

    public void setPurviewCode(String purviewCode) {
	this.purviewCode = purviewCode;
    }

}
