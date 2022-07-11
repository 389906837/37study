package com.cloud.cang.model.sl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 系统访问日志(SL_VIST_LOG) **/
public class VistLog extends GenericEntity  {

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
	
	/* 访问地址 */
	private String sactionpath;
	
	public String getSactionpath(){
		return sactionpath;
	}
	
	public void setSactionpath(String sactionpath){
		this.sactionpath= sactionpath;
	}
	/* 访问IP */
	private String sip;
	
	public String getSip(){
		return sip;
	}
	
	public void setSip(String sip){
		this.sip= sip;
	}
	/* 用户姓名 */
	private String srealName;
	
	public String getSrealName(){
		return srealName;
	}
	
	public void setSrealName(String srealName){
		this.srealName= srealName;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 来源系统 */
	private String ssourceSystem;
	
	public String getSsourceSystem(){
		return ssourceSystem;
	}
	
	public void setSsourceSystem(String ssourceSystem){
		this.ssourceSystem= ssourceSystem;
	}
	/* 用户ID */
	private String suerId;
	
	public String getSuerId(){
		return suerId;
	}
	
	public void setSuerId(String suerId){
		this.suerId= suerId;
	}
	/* 用户名 */
	private String susername;
	
	public String getSusername(){
		return susername;
	}
	
	public void setSusername(String susername){
		this.susername= susername;
	}
	/* 操作日期 */
	private Date toperateDate;
	
	public Date getToperateDate(){
		return toperateDate;
	}
	
	public void setToperateDate(Date toperateDate){
		this.toperateDate= toperateDate;
	}

		
}