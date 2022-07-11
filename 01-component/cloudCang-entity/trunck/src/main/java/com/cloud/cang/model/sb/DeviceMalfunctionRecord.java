package com.cloud.cang.model.sb;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 设备故障信息记录(SB_DEVICE_MALFUNCTION_RECORD) **/
public class DeviceMalfunctionRecord extends GenericEntity  {

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
            10=待处理
            20=已处理
            30=已废弃 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 故障申报类型
            10=系统警报-长连接
            20=系统警报-短连接
            30=手动申报 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 处理人 */
	private String sdealwithMan;
	
	public String getSdealwithMan(){
		return sdealwithMan;
	}
	
	public void setSdealwithMan(String sdealwithMan){
		this.sdealwithMan= sdealwithMan;
	}
	/* 处理时间 */
	private Date sdealwithTime;
	
	public Date getSdealwithTime(){
		return sdealwithTime;
	}
	
	public void setSdealwithTime(Date sdealwithTime){
		this.sdealwithTime= sdealwithTime;
	}
	/* 申报人 */
	private String sdeclareMan;
	
	public String getSdeclareMan(){
		return sdeclareMan;
	}
	
	public void setSdeclareMan(String sdeclareMan){
		this.sdeclareMan= sdeclareMan;
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
	/* 故障错误代码 */
	private String serrorCode;
	
	public String getSerrorCode(){
		return serrorCode;
	}
	
	public void setSerrorCode(String serrorCode){
		this.serrorCode= serrorCode;
	}
	/* 故障等级 */
	private String slevel;
	
	public String getSlevel(){
		return slevel;
	}
	
	public void setSlevel(String slevel){
		this.slevel= slevel;
	}
	/* 故障问题描叙 */
	private String smalfunctionDesc;
	
	public String getSmalfunctionDesc(){
		return smalfunctionDesc;
	}
	
	public void setSmalfunctionDesc(String smalfunctionDesc){
		this.smalfunctionDesc= smalfunctionDesc;
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
	/* 处理备注 */
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
	/* 申报时间 */
	private Date tdeclareTime;
	
	public Date getTdeclareTime(){
		return tdeclareTime;
	}
	
	public void setTdeclareTime(Date tdeclareTime){
		this.tdeclareTime= tdeclareTime;
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