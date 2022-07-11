package com.cloud.cang.model.wz;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 访问来源表(WZ_VISITSOURCE) **/
public class Visitsource extends GenericEntity  {

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
	
	/* 访问引用页 */
	private String srefererPath;
	
	public String getSrefererPath(){
		return srefererPath;
	}
	
	public void setSrefererPath(String srefererPath){
		this.srefererPath= srefererPath;
	}
	/* 预留字段 */
	private String sreserve;
	
	public String getSreserve(){
		return sreserve;
	}
	
	public void setSreserve(String sreserve){
		this.sreserve= sreserve;
	}
	/* 所属系统 */
	private String ssystem;
	
	public String getSsystem(){
		return ssystem;
	}
	
	public void setSsystem(String ssystem){
		this.ssystem= ssystem;
	}
	/* 访问Ip */
	private String svisitIp;
	
	public String getSvisitIp(){
		return svisitIp;
	}
	
	public void setSvisitIp(String svisitIp){
		this.svisitIp= svisitIp;
	}
	/* 访问路径 */
	private String svisitPath;
	
	public String getSvisitPath(){
		return svisitPath;
	}
	
	public void setSvisitPath(String svisitPath){
		this.svisitPath= svisitPath;
	}
	/* 访问来源 */
	private String svisitSource;
	
	public String getSvisitSource(){
		return svisitSource;
	}
	
	public void setSvisitSource(String svisitSource){
		this.svisitSource= svisitSource;
	}
	/* 访问时间 */
	private Date svisitTime;
	
	public Date getSvisitTime(){
		return svisitTime;
	}
	
	public void setSvisitTime(Date svisitTime){
		this.svisitTime= svisitTime;
	}

		
}