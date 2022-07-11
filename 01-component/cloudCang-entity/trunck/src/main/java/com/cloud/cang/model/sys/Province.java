package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 省份表(SYS_PROVINCE) **/
public class Province extends GenericEntity  {

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
	
	/* 显示顺序 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 所属国家 */
	private String scountryid;
	
	public String getScountryid(){
		return scountryid;
	}
	
	public void setScountryid(String scountryid){
		this.scountryid= scountryid;
	}
	/* 省份对应省份证号代码 */
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
	/* 省份名 */
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