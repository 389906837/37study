package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 用户角色表(SYS_OPERATORROLE) **/
public class Operatorrole extends GenericEntity  {

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
	
	/* 操作员ID */
	private String soperatorId;
	
	public String getSoperatorId(){
		return soperatorId;
	}
	
	public void setSoperatorId(String soperatorId){
		this.soperatorId= soperatorId;
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