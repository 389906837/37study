package com.cloud.cang.model.om;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 申请退款操作日志表(OM_REFUND_OPERLOG) **/
public class RefundOperlog extends GenericEntity  {

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
	
	/* 操作内容 */
	private String scontent;
	
	public String getScontent(){
		return scontent;
	}
	
	public void setScontent(String scontent){
		this.scontent= scontent;
	}
	/* 操作IP */
	private String sip;
	
	public String getSip(){
		return sip;
	}
	
	public void setSip(String sip){
		this.sip= sip;
	}
	/* 操作用户名(后台用户显示真实用户名) */
	private String soperName;
	
	public String getSoperName(){
		return soperName;
	}
	
	public void setSoperName(String soperName){
		this.soperName= soperName;
	}
	/* 退款订单审核编号 */
	private String srefundCode;
	
	public String getSrefundCode(){
		return srefundCode;
	}
	
	public void setSrefundCode(String srefundCode){
		this.srefundCode= srefundCode;
	}
	/* 退款订单审核ID */
	private String srefundId;
	
	public String getSrefundId(){
		return srefundId;
	}
	
	public void setSrefundId(String srefundId){
		this.srefundId= srefundId;
	}
	/* 操作时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}

		
}