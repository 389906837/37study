package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 国家表(SYS_COUNTRY) **/
public class Country extends GenericEntity  {

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
	
	/* 显示顺序 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 简拼名 */
	private String sjpname;
	
	public String getSjpname(){
		return sjpname;
	}
	
	public void setSjpname(String sjpname){
		this.sjpname= sjpname;
	}
	/* 国家名 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 全拼名 */
	private String spyname;
	
	public String getSpyname(){
		return spyname;
	}
	
	public void setSpyname(String spyname){
		this.spyname= spyname;
	}

		
}