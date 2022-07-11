package com.cloud.cang.model.sys;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** IP地址管理(SYS_IP_AREA) **/
public class IpArea extends GenericEntity  {

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
	
	/* 国家 */
	private String country;
	
	public String getCountry(){
		return country;
	}
	
	public void setCountry(String country){
		this.country= country;
	}
	/* 状态 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 城市 */
	private String scity;
	
	public String getScity(){
		return scity;
	}
	
	public void setScity(String scity){
		this.scity= scity;
	}
	/* IP */
	private String sip;
	
	public String getSip(){
		return sip;
	}
	
	public void setSip(String sip){
		this.sip= sip;
	}
	/* 备注 */
	private String smemo;
	
	public String getSmemo(){
		return smemo;
	}
	
	public void setSmemo(String smemo){
		this.smemo= smemo;
	}
	/* 省份 */
	private String sprovince;
	
	public String getSprovince(){
		return sprovince;
	}
	
	public void setSprovince(String sprovince){
		this.sprovince= sprovince;
	}
	/* 添加时间 */
	private Date taddtime;
	
	public Date getTaddtime(){
		return taddtime;
	}
	
	public void setTaddtime(Date taddtime){
		this.taddtime= taddtime;
	}
	/* 修改时间 */
	private Date tupdatetime;
	
	public Date getTupdatetime(){
		return tupdatetime;
	}
	
	public void setTupdatetime(Date tupdatetime){
		this.tupdatetime= tupdatetime;
	}

		
}