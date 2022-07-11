package com.cloud.cang.model.tp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备管理信息模板表(TP_DEVICE_MANAGE_TEMPLATE) **/
public class DeviceManageTemplate extends GenericEntity  {

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
	/* 模板状态 10=启用 20=禁用 */
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
	/* 所属区域编号 数据字典配置 */
	private String sareaCode;
	
	public String getSareaCode(){
		return sareaCode;
	}
	
	public void setSareaCode(String sareaCode){
		this.sareaCode= sareaCode;
	}
	/* 生产厂商 */
	private String sareaPrincipal;
	
	public String getSareaPrincipal(){
		return sareaPrincipal;
	}
	
	public void setSareaPrincipal(String sareaPrincipal){
		this.sareaPrincipal= sareaPrincipal;
	}
	/* 区域负责人联系方式 */
	private String sareaPrincipalContact;
	
	public String getSareaPrincipalContact(){
		return sareaPrincipalContact;
	}
	
	public void setSareaPrincipalContact(String sareaPrincipalContact){
		this.sareaPrincipalContact= sareaPrincipalContact;
	}
	/* 模板编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 设备负责人 */
	private String sdevicePrincipal;
	
	public String getSdevicePrincipal(){
		return sdevicePrincipal;
	}
	
	public void setSdevicePrincipal(String sdevicePrincipal){
		this.sdevicePrincipal= sdevicePrincipal;
	}
	/* 设备负责人联系方式 */
	private String sdevicePrincipalContact;
	
	public String getSdevicePrincipalContact(){
		return sdevicePrincipalContact;
	}
	
	public void setSdevicePrincipalContact(String sdevicePrincipalContact){
		this.sdevicePrincipalContact= sdevicePrincipalContact;
	}
	/* 设备维护人姓名 */
	private String smaintain;
	
	public String getSmaintain(){
		return smaintain;
	}
	
	public void setSmaintain(String smaintain){
		this.smaintain= smaintain;
	}
	/* 维护人联系方式 */
	private String smaintainContact;
	
	public String getSmaintainContact(){
		return smaintainContact;
	}
	
	public void setSmaintainContact(String smaintainContact){
		this.smaintainContact= smaintainContact;
	}
	/* 商户编号 */
	private String smerchantCode;
	
	public String getSmerchantCode(){
		return smerchantCode;
	}
	
	public void setSmerchantCode(String smerchantCode){
		this.smerchantCode= smerchantCode;
	}
	/* 商户ID */
	private String smerchantId;
	
	public String getSmerchantId(){
		return smerchantId;
	}
	
	public void setSmerchantId(String smerchantId){
		this.smerchantId= smerchantId;
	}
	/* 模板名称 */
	private String sname;
	
	public String getSname(){
		return sname;
	}
	
	public void setSname(String sname){
		this.sname= sname;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 补货员姓名 */
	private String sreplenishment;
	
	public String getSreplenishment(){
		return sreplenishment;
	}
	
	public void setSreplenishment(String sreplenishment){
		this.sreplenishment= sreplenishment;
	}
	/* 补货员联系方式 */
	private String sreplenishmentContact;
	
	public String getSreplenishmentContact(){
		return sreplenishmentContact;
	}
	
	public void setSreplenishmentContact(String sreplenishmentContact){
		this.sreplenishmentContact= sreplenishmentContact;
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

	@Override
	public String toString() {
		return "DeviceManageTemplate{" +
				"id='" + id + '\'' +
				", idelFlag=" + idelFlag +
				", istatus=" + istatus +
				", saddUser='" + saddUser + '\'' +
				", sareaCode='" + sareaCode + '\'' +
				", sareaPrincipal='" + sareaPrincipal + '\'' +
				", sareaPrincipalContact='" + sareaPrincipalContact + '\'' +
				", scode='" + scode + '\'' +
				", sdevicePrincipal='" + sdevicePrincipal + '\'' +
				", sdevicePrincipalContact='" + sdevicePrincipalContact + '\'' +
				", smaintain='" + smaintain + '\'' +
				", smaintainContact='" + smaintainContact + '\'' +
				", smerchantCode='" + smerchantCode + '\'' +
				", smerchantId='" + smerchantId + '\'' +
				", sname='" + sname + '\'' +
				", sremark='" + sremark + '\'' +
				", sreplenishment='" + sreplenishment + '\'' +
				", sreplenishmentContact='" + sreplenishmentContact + '\'' +
				", supdateUser='" + supdateUser + '\'' +
				", taddTime=" + taddTime +
				", tupdateTime=" + tupdateTime +
				'}';
	}
}