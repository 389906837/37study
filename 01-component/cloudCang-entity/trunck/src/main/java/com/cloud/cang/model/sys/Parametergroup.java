package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 数据字典主表(SYS_PARAMETERGROUP) **/
public class Parametergroup extends GenericEntity  {

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
	
	/* 是否可修改:是否可供用户修改 */
	private Integer bisModify;
	
	public Integer getBisModify(){
		return bisModify;
	}
	
	public void setBisModify(Integer bisModify){
		this.bisModify= bisModify;
	}
	/* 组名称 */
	private String sgroupName;
	
	public String getSgroupName(){
		return sgroupName;
	}
	
	public void setSgroupName(String sgroupName){
		this.sgroupName= sgroupName;
	}
	/* 组编号 */
	private String sgroupNo;
	
	public String getSgroupNo(){
		return sgroupNo;
	}
	
	public void setSgroupNo(String sgroupNo){
		this.sgroupNo= sgroupNo;
	}
	/* 说明 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}

		
}