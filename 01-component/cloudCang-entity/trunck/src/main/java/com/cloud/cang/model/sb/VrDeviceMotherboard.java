package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 视觉设备主板配置(SB_VR_DEVICE_MOTHERBOARD) **/
public class VrDeviceMotherboard extends GenericEntity  {

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
	
	/* 最大通道数（摄像头个数） */
	private Integer imaxChannel;
	
	public Integer getImaxChannel(){
		return imaxChannel;
	}
	
	public void setImaxChannel(Integer imaxChannel){
		this.imaxChannel= imaxChannel;
	}
	/* 注册端口 */
	private Integer iport;
	
	public Integer getIport(){
		return iport;
	}
	
	public void setIport(Integer iport){
		this.iport= iport;
	}
	/* 主板状态 10=正常 20=异常 30 =废弃 */
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
	/* 主板型号 */
	private String sboardModel;
	
	public String getSboardModel(){
		return sboardModel;
	}
	
	public void setSboardModel(String sboardModel){
		this.sboardModel= sboardModel;
	}
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 注册IP */
	private String sregisterIp;
	
	public String getSregisterIp(){
		return sregisterIp;
	}
	
	public void setSregisterIp(String sregisterIp){
		this.sregisterIp= sregisterIp;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
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