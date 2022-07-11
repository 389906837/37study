package com.cloud.cang.model.bpm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 识别结果表(BPM_IDENTIFICATION_RESULTS) **/
public class IdentificationResults extends GenericEntity  {

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
	/* 识别程序版本号 */
	private String sappversionNo;
	
	public String getSappversionNo(){
		return sappversionNo;
	}
	
	public void setSappversionNo(String sappversionNo){
		this.sappversionNo= sappversionNo;
	}
	/* 版本号STR */
	private String sappversionStr;
	
	public String getSappversionStr(){
		return sappversionStr;
	}
	
	public void setSappversionStr(String sappversionStr){
		this.sappversionStr= sappversionStr;
	}
	/* 版本号 */
	private String sbatchNo;
	
	public String getSbatchNo(){
		return sbatchNo;
	}
	
	public void setSbatchNo(String sbatchNo){
		this.sbatchNo= sbatchNo;
	}
	/* 公司名 */
	private String scompanyName;
	
	public String getScompanyName(){
		return scompanyName;
	}
	
	public void setScompanyName(String scompanyName){
		this.scompanyName= scompanyName;
	}
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 设备号 */
	private String sdeviceNo;
	
	public String getSdeviceNo(){
		return sdeviceNo;
	}
	
	public void setSdeviceNo(String sdeviceNo){
		this.sdeviceNo= sdeviceNo;
	}
	/* 图片地址 */
	private String simgUrl;
	
	public String getSimgUrl(){
		return simgUrl;
	}
	
	public void setSimgUrl(String simgUrl){
		this.simgUrl= simgUrl;
	}
	/* 识别结果 */
	private String srecognitionResult;
	
	public String getSrecognitionResult(){
		return srecognitionResult;
	}
	
	public void setSrecognitionResult(String srecognitionResult){
		this.srecognitionResult= srecognitionResult;
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