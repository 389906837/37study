package com.cloud.cang.model.bpm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 图片信息管理表(BPM_PIC_INFO_MAN) **/
public class PicInfoMan extends GenericEntity  {

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
	
	/* 是否删除 0否  1是 */
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
	/* 业务编号 */
	private String sbatchNo;
	
	public String getSbatchNo(){
		return sbatchNo;
	}
	
	public void setSbatchNo(String sbatchNo){
		this.sbatchNo= sbatchNo;
	}
	/* 编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 文件存放路径 */
	private String sfileLocation;
	
	public String getSfileLocation(){
		return sfileLocation;
	}
	
	public void setSfileLocation(String sfileLocation){
		this.sfileLocation= sfileLocation;
	}
	/* 文件名 */
	private String sfileName;
	
	public String getSfileName(){
		return sfileName;
	}
	
	public void setSfileName(String sfileName){
		this.sfileName= sfileName;
	}
	/* 主机编号 */
	private String shostNo;
	
	public String getShostNo(){
		return shostNo;
	}
	
	public void setShostNo(String shostNo){
		this.shostNo= shostNo;
	}
	/* 商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 模型版本号 */
	private String smodelVersion;
	
	public String getSmodelVersion(){
		return smodelVersion;
	}
	
	public void setSmodelVersion(String smodelVersion){
		this.smodelVersion= smodelVersion;
	}
	/* 识别结果 */
	private String srecognitionResult;
	
	public String getSrecognitionResult(){
		return srecognitionResult;
	}
	
	public void setSrecognitionResult(String srecognitionResult){
		this.srecognitionResult= srecognitionResult;
	}
	/* 识别程序版本号 */
	private String sserverVersion;
	
	public String getSserverVersion(){
		return sserverVersion;
	}
	
	public void setSserverVersion(String sserverVersion){
		this.sserverVersion= sserverVersion;
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