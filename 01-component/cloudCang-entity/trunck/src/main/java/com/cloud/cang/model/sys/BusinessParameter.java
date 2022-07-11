package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 运营参数表(SYS_BUSINESS_PARAMETER) **/
public class BusinessParameter extends GenericEntity  {

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
	
	/* 参数名称 */
	private String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name= name;
	}
	/* 参数说明 */
	private String remark;
	
	public String getRemark(){
		return remark;
	}
	
	public void setRemark(String remark){
		this.remark= remark;
	}
	/* 排序号 */
	private Integer sort;
	
	public Integer getSort(){
		return sort;
	}
	
	public void setSort(Integer sort){
		this.sort= sort;
	}
	/* 参数值 */
	private String value;
	
	public String getValue(){
		return value;
	}
	
	public void setValue(String value){
		this.value= value;
	}

		
}