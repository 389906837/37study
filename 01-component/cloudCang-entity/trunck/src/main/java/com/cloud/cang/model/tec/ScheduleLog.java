package com.cloud.cang.model.tec;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 任务中心日志表(TEC_SCHEDULE_LOG) **/
public class ScheduleLog extends GenericEntity  {

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
	
	/* 执行结果
            
            1=成功
            0=失败 */
	private Integer iresult;
	
	public Integer getIresult(){
		return iresult;
	}
	
	public void setIresult(Integer iresult){
		this.iresult= iresult;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 任务名 */
	private String staskName;
	
	public String getStaskName(){
		return staskName;
	}
	
	public void setStaskName(String staskName){
		this.staskName= staskName;
	}
	/* 执行时间 */
	private Date texecuteTime;
	
	public Date getTexecuteTime(){
		return texecuteTime;
	}
	
	public void setTexecuteTime(Date texecuteTime){
		this.texecuteTime= texecuteTime;
	}

		
}