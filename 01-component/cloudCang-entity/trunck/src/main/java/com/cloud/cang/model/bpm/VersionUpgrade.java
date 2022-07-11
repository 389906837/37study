package com.cloud.cang.model.bpm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 版本更新数据表(BPM_VERSION_UPGRADE) **/
public class VersionUpgrade extends GenericEntity  {

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
	/* 升级次数 */
	private Integer iupgradeCount;
	
	public Integer getIupgradeCount(){
		return iupgradeCount;
	}
	
	public void setIupgradeCount(Integer iupgradeCount){
		this.iupgradeCount= iupgradeCount;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 修改人 */
	private String supdateUser;
	
	public String getSupdateUser(){
		return supdateUser;
	}
	
	public void setSupdateUser(String supdateUser){
		this.supdateUser= supdateUser;
	}
	/* 版本号(NO) */
	private String sversionNo;
	
	public String getSversionNo(){
		return sversionNo;
	}
	
	public void setSversionNo(String sversionNo){
		this.sversionNo= sversionNo;
	}
	/* 版本号(STR) */
	private String sversionStr;
	
	public String getSversionStr(){
		return sversionStr;
	}
	
	public void setSversionStr(String sversionStr){
		this.sversionStr= sversionStr;
	}
	/* 文件地址 */
	private String szipFileUrl;
	
	public String getSzipFileUrl(){
		return szipFileUrl;
	}
	
	public void setSzipFileUrl(String szipFileUrl){
		this.szipFileUrl= szipFileUrl;
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