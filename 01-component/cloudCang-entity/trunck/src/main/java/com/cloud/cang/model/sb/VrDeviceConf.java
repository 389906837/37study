package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 视觉设备配置(SB_VR_DEVICE_CONF) **/
public class VrDeviceConf extends GenericEntity  {

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
	
	/* 设备层数 */
	private Integer ilayerNum;
	
	public Integer getIlayerNum(){
		return ilayerNum;
	}
	
	public void setIlayerNum(Integer ilayerNum){
		this.ilayerNum= ilayerNum;
	}
	/* 通道序号 */
	private Integer iserialNumber;
	
	public Integer getIserialNumber(){
		return iserialNumber;
	}
	
	public void setIserialNumber(Integer iserialNumber){
		this.iserialNumber= iserialNumber;
	}
	/* 状态10=开启 20=关闭 30=废弃 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 设备主板ID */
	private String sboardId;
	
	public String getSboardId(){
		return sboardId;
	}
	
	public void setSboardId(String sboardId){
		this.sboardId= sboardId;
	}
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 通道唯一编号 */
	private String suniqueCode;
	
	public String getSuniqueCode(){
		return suniqueCode;
	}
	
	public void setSuniqueCode(String suniqueCode){
		this.suniqueCode= suniqueCode;
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