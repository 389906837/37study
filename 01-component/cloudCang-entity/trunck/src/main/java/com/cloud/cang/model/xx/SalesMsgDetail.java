package com.cloud.cang.model.xx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 营销短信明细表(XX_SALES_MSG_DETAIL) **/
public class SalesMsgDetail extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键ID*/
	private String id;
	/*主键ID*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 接收人类型 1：会员  2：非会员 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 消息内容 */
	private String scontext;
	
	public String getScontext(){
		return scontext;
	}
	
	public void setScontext(String scontext){
		this.scontext= scontext;
	}
	/* 营销主表ID */
	private String smainId;
	
	public String getSmainId(){
		return smainId;
	}
	
	public void setSmainId(String smainId){
		this.smainId= smainId;
	}
	/* 手机号 */
	private String smobile;
	
	public String getSmobile(){
		return smobile;
	}
	
	public void setSmobile(String smobile){
		this.smobile= smobile;
	}

		
}