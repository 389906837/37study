package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备注册信息表(SB_DEVICE_REGISTER) **/
public class DeviceRegister extends GenericEntity  {

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
	/* 状态： 10 待审核 20  审核通过  30 审核拒绝 40 已注册 */
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
	/* 审核人 */
	private String sauditUser;
	
	public String getSauditUser(){
		return sauditUser;
	}
	
	public void setSauditUser(String sauditUser){
		this.sauditUser= sauditUser;
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
	/* 注册IP */
	private String sregIp;
	
	public String getSregIp(){
		return sregIp;
	}
	
	public void setSregIp(String sregIp){
		this.sregIp= sregIp;
	}
	/* 注册端口 */
	private String sregPort;
	
	public String getSregPort(){
		return sregPort;
	}
	
	public void setSregPort(String sregPort){
		this.sregPort= sregPort;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 安全秘钥 */
	private String ssecurityKey;
	
	public String getSsecurityKey(){
		return ssecurityKey;
	}
	
	public void setSsecurityKey(String ssecurityKey){
		this.ssecurityKey= ssecurityKey;
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
	/* 审核时间 */
	private Date tauditTime;
	
	public Date getTauditTime(){
		return tauditTime;
	}
	
	public void setTauditTime(Date tauditTime){
		this.tauditTime= tauditTime;
	}
	/* 注册时间 */
	private Date tregTime;
	
	public Date getTregTime(){
		return tregTime;
	}
	
	public void setTregTime(Date tregTime){
		this.tregTime= tregTime;
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