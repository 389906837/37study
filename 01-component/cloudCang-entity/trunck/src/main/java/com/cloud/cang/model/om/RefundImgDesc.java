package com.cloud.cang.model.om;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 退款图片说明表(OM_REFUND_IMG_DESC) **/
public class RefundImgDesc extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键*/
	private String id;
	/*主键*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 显示顺序 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 图片路径 */
	private String simgPath;
	
	public String getSimgPath(){
		return simgPath;
	}
	
	public void setSimgPath(String simgPath){
		this.simgPath= simgPath;
	}
	/* 退款记录ID */
	private String srefundId;
	
	public String getSrefundId(){
		return srefundId;
	}
	
	public void setSrefundId(String srefundId){
		this.srefundId= srefundId;
	}

		
}