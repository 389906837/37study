package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 县镇区域表(SYS_TOWN) **/
public class Town extends GenericEntity  {

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
	
	/* 是否常用 */
	private Integer biscommon;
	
	public Integer getBiscommon(){
		return biscommon;
	}
	
	public void setBiscommon(Integer biscommon){
		this.biscommon= biscommon;
	}
	/* 显示顺序 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 所属城市 */
	private String scityid;
	
	public String getScityid(){
		return scityid;
	}
	
	public void setScityid(String scityid){
		this.scityid= scityid;
	}
	/*  */
	private String sidcode;
	
	public String getSidcode(){
		return sidcode;
	}
	
	public void setSidcode(String sidcode){
		this.sidcode= sidcode;
	}
	/* 简拼名 */
	private String sjpname;
	
	public String getSjpname(){
		return sjpname;
	}
	
	public void setSjpname(String sjpname){
		this.sjpname= sjpname;
	}
	/* 县镇区域名 */
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