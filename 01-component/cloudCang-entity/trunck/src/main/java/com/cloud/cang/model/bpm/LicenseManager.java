package com.cloud.cang.model.bpm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** LICENSE管理表(BPM_LICENSE_MANAGER) **/
public class LicenseManager extends GenericEntity  {

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
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 构建时间 */
	private String screateTime;
	
	public String getScreateTime(){
		return screateTime;
	}
	
	public void setScreateTime(String screateTime){
		this.screateTime= screateTime;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* License过期时间 */
	private String sexpiredDay;
	
	public String getSexpiredDay(){
		return sexpiredDay;
	}
	
	public void setSexpiredDay(String sexpiredDay){
		this.sexpiredDay= sexpiredDay;
	}
	/* 授权类型 */
	private String sfileType;
	
	public String getSfileType(){
		return sfileType;
	}
	
	public void setSfileType(String sfileType){
		this.sfileType= sfileType;
	}
	/* 有效时间 */
	private String slicenseExpiredDays;
	
	public String getSlicenseExpiredDays(){
		return slicenseExpiredDays;
	}
	
	public void setSlicenseExpiredDays(String slicenseExpiredDays){
		this.slicenseExpiredDays= slicenseExpiredDays;
	}
	/* License文件地址 */
	private String slicenseFileUrl;
	
	public String getSlicenseFileUrl(){
		return slicenseFileUrl;
	}
	
	public void setSlicenseFileUrl(String slicenseFileUrl){
		this.slicenseFileUrl= slicenseFileUrl;
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