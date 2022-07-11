package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备搬迁记录(SB_DEVICE_MOVE_RECORD) **/
public class DeviceMoveRecord extends GenericEntity  {

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
	/* 状态 
            10=待审核
            20=已审核
            30=审核拒绝
            40=已完成
            50=已取消 */
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
	/* 区县ID */
	private String sareaId;
	
	public String getSareaId(){
		return sareaId;
	}
	
	public void setSareaId(String sareaId){
		this.sareaId= sareaId;
	}
	/* 投放区县 */
	private String sareaName;
	
	public String getSareaName(){
		return sareaName;
	}
	
	public void setSareaName(String sareaName){
		this.sareaName= sareaName;
	}
	/* 审核人 */
	private String sauditUser;
	
	public String getSauditUser(){
		return sauditUser;
	}
	
	public void setSauditUser(String sauditUser){
		this.sauditUser= sauditUser;
	}
	/* 城市ID */
	private String scityId;
	
	public String getScityId(){
		return scityId;
	}
	
	public void setScityId(String scityId){
		this.scityId= scityId;
	}
	/* 投放城市 */
	private String scityName;
	
	public String getScityName(){
		return scityName;
	}
	
	public void setScityName(String scityName){
		this.scityName= scityName;
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
	/* 设备新地址 */
	private String sdeviceNewAddress;
	
	public String getSdeviceNewAddress(){
		return sdeviceNewAddress;
	}
	
	public void setSdeviceNewAddress(String sdeviceNewAddress){
		this.sdeviceNewAddress= sdeviceNewAddress;
	}
	/* 设备原地址 */
	private String sdeviceOldAddress;
	
	public String getSdeviceOldAddress(){
		return sdeviceOldAddress;
	}
	
	public void setSdeviceOldAddress(String sdeviceOldAddress){
		this.sdeviceOldAddress= sdeviceOldAddress;
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
	/* 搬迁原因 */
	private String smoveReason;
	
	public String getSmoveReason(){
		return smoveReason;
	}
	
	public void setSmoveReason(String smoveReason){
		this.smoveReason= smoveReason;
	}
	/* 搬迁负责人 */
	private String sprincipal;
	
	public String getSprincipal(){
		return sprincipal;
	}
	
	public void setSprincipal(String sprincipal){
		this.sprincipal= sprincipal;
	}
	/* 省份ID */
	private String sprovinceId;
	
	public String getSprovinceId(){
		return sprovinceId;
	}
	
	public void setSprovinceId(String sprovinceId){
		this.sprovinceId= sprovinceId;
	}
	/* 投放省份 */
	private String sprovinceName;
	
	public String getSprovinceName(){
		return sprovinceName;
	}
	
	public void setSprovinceName(String sprovinceName){
		this.sprovinceName= sprovinceName;
	}
	/* 投放地址 */
	private String sputAddress;
	
	public String getSputAddress(){
		return sputAddress;
	}
	
	public void setSputAddress(String sputAddress){
		this.sputAddress= sputAddress;
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
	/* 申请时间 */
	private Date tapplyTime;
	
	public Date getTapplyTime(){
		return tapplyTime;
	}
	
	public void setTapplyTime(Date tapplyTime){
		this.tapplyTime= tapplyTime;
	}
	/* 审核意见 */
	private String tauditOpinion;
	
	public String getTauditOpinion(){
		return tauditOpinion;
	}
	
	public void setTauditOpinion(String tauditOpinion){
		this.tauditOpinion= tauditOpinion;
	}
	/* 审核时间 */
	private Date tauditTime;
	
	public Date getTauditTime(){
		return tauditTime;
	}
	
	public void setTauditTime(Date tauditTime){
		this.tauditTime= tauditTime;
	}
	/* 申报时间 */
	private Date tmoveTime;
	
	public Date getTmoveTime(){
		return tmoveTime;
	}
	
	public void setTmoveTime(Date tmoveTime){
		this.tmoveTime= tmoveTime;
	}
	/* 计划搬迁时间 */
	private Date tplanMoveTime;
	
	public Date getTplanMoveTime(){
		return tplanMoveTime;
	}
	
	public void setTplanMoveTime(Date tplanMoveTime){
		this.tplanMoveTime= tplanMoveTime;
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