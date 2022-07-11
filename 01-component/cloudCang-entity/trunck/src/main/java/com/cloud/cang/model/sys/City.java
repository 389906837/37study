package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 城市表(SYS_CITY) **/
public class City extends GenericEntity  {

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
	/* 是否省会 */
	private Integer bisprovincecity;
	
	public Integer getBisprovincecity(){
		return bisprovincecity;
	}
	
	public void setBisprovincecity(Integer bisprovincecity){
		this.bisprovincecity= bisprovincecity;
	}
	/* 显示顺序 */
	private Integer isort;
	
	public Integer getIsort(){
		return isort;
	}
	
	public void setIsort(Integer isort){
		this.isort= isort;
	}
	/* 区号 */
	private String sareacode;
	
	public String getSareacode(){
		return sareacode;
	}
	
	public void setSareacode(String sareacode){
		this.sareacode= sareacode;
	}
	/* 城市对应省份证号代码 */
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
	/* 城市名 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 所属省份 */
	private String sprovinceid;
	
	public String getSprovinceid(){
		return sprovinceid;
	}
	
	public void setSprovinceid(String sprovinceid){
		this.sprovinceid= sprovinceid;
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