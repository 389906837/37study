package com.cloud.cang.model.xx;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 站内信表阅读记录(XX_SITEMESSAGE_READ_RC) **/
public class SitemessageReadRc extends GenericEntity  {

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
	
	/* 是否删除 */
	private Integer bisDelete;
	
	public Integer getBisDelete(){
		return bisDelete;
	}
	
	public void setBisDelete(Integer bisDelete){
		this.bisDelete= bisDelete;
	}
	/* 信息ID */
	private String smsgId;
	
	public String getSmsgId(){
		return smsgId;
	}
	
	public void setSmsgId(String smsgId){
		this.smsgId= smsgId;
	}
	/* 接收人ID */
	private String sreceiveId;
	
	public String getSreceiveId(){
		return sreceiveId;
	}
	
	public void setSreceiveId(String sreceiveId){
		this.sreceiveId= sreceiveId;
	}
	/* 发送时间 */
	private Date treadDateTime;
	
	public Date getTreadDateTime(){
		return treadDateTime;
	}
	
	public void setTreadDateTime(Date treadDateTime){
		this.treadDateTime= treadDateTime;
	}

		
}