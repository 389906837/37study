package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备升级明细表(SB_DEVICE_UPGRADE_DETAILS) **/
public class DeviceUpgradeDetails extends GenericEntity  {

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
	
	/* 状态 
            10=待处理
            20=升级成功
            30=升级失败 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 设备地址 */
	private String sdeviceAddress;
	
	public String getSdeviceAddress(){
		return sdeviceAddress;
	}
	
	public void setSdeviceAddress(String sdeviceAddress){
		this.sdeviceAddress= sdeviceAddress;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 升级异常原因描述 */
	private String sexceptionDesc;
	
	public String getSexceptionDesc(){
		return sexceptionDesc;
	}
	
	public void setSexceptionDesc(String sexceptionDesc){
		this.sexceptionDesc= sexceptionDesc;
	}
	/* 升级主表ID */
	private String smainId;
	
	public String getSmainId(){
		return smainId;
	}
	
	public void setSmainId(String smainId){
		this.smainId= smainId;
	}
	/* 升级结束时间 */
	private Date tendTime;
	
	public Date getTendTime(){
		return tendTime;
	}
	
	public void setTendTime(Date tendTime){
		this.tendTime= tendTime;
	}
	/* 升级开始时间 */
	private Date tstartTime;
	
	public Date getTstartTime(){
		return tstartTime;
	}
	
	public void setTstartTime(Date tstartTime){
		this.tstartTime= tstartTime;
	}

		
}