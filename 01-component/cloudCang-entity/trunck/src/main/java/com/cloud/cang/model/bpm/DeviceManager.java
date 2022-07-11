package com.cloud.cang.model.bpm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备管理表(BPM_DEVICE_MANAGER) **/
public class DeviceManager extends GenericEntity  {

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
	
	/* 是否删除0否1是 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* License授权次数 */
	private Integer igrantAuthorizationCount;
	
	public Integer getIgrantAuthorizationCount(){
		return igrantAuthorizationCount;
	}
	
	public void setIgrantAuthorizationCount(Integer igrantAuthorizationCount){
		this.igrantAuthorizationCount= igrantAuthorizationCount;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 渠道号 */
	private String schannelNo;
	
	public String getSchannelNo(){
		return schannelNo;
	}
	
	public void setSchannelNo(String schannelNo){
		this.schannelNo= schannelNo;
	}
	/* 公司名称 */
	private String scompanyName;
	
	public String getScompanyName(){
		return scompanyName;
	}
	
	public void setScompanyName(String scompanyName){
		this.scompanyName= scompanyName;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 最大过期时间 */
	private String sexpiredDayMax;
	
	public String getSexpiredDayMax(){
		return sexpiredDayMax;
	}
	
	public void setSexpiredDayMax(String sexpiredDayMax){
		this.sexpiredDayMax= sexpiredDayMax;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 修改日期 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}

		
}