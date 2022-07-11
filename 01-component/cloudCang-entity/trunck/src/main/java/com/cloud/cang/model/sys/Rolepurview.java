package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 后台角色权限分配(SYS_ROLEPURVIEW) **/
public class Rolepurview extends GenericEntity  {

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
	
	/* 权限码ID */
	private String spurviewId;
	
	public String getSpurviewId(){
		return spurviewId;
	}
	
	public void setSpurviewId(String spurviewId){
		this.spurviewId= spurviewId;
	}
	/* 角色ID */
	private String sroleId;
	
	public String getSroleId(){
		return sroleId;
	}
	
	public void setSroleId(String sroleId){
		this.sroleId= sroleId;
	}

		
}