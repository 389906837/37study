package com.cloud.cang.model.sp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 商品标签表(SP_COMMODITY_RFID) **/
public class CommodityRfid extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/**/
	private String id;
	/**/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 一体机标签 */
	private String rfid;
	
	public String getRfid(){
		return rfid;
	}
	
	public void setRfid(String rfid){
		this.rfid= rfid;
	}
	/* 商品ID */
	private String scommodityCode;
	
	public String getScommodityCode(){
		return scommodityCode;
	}
	
	public void setScommodityCode(String scommodityCode){
		this.scommodityCode= scommodityCode;
	}
	/* 商品名称 */
	private String scommodityName;
	
	public String getScommodityName(){
		return scommodityName;
	}
	
	public void setScommodityName(String scommodityName){
		this.scommodityName= scommodityName;
	}
	/* 商户ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 操作用户ID */
	private String soperatorId;
	
	public String getSoperatorId(){
		return soperatorId;
	}
	
	public void setSoperatorId(String soperatorId){
		this.soperatorId= soperatorId;
	}
	/*  */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}

		
}