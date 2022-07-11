package com.cloud.cang.model.sl;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** 设备操作日志(SL_DEVICE_OPER) **/
public class DeviceOper extends GenericEntity  {

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
	
	/* 客户端来源
            10=微信
            20=支付宝
            30=APP */
	private Integer iclientType;
	
	public Integer getIclientType(){
		return iclientType;
	}
	
	public void setIclientType(Integer iclientType){
		this.iclientType= iclientType;
	}
	/* 10:购物
            20:补货 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 关门图片集合 */
	private String sclosePicUrl;
	
	public String getSclosePicUrl(){
		return sclosePicUrl;
	}
	
	public void setSclosePicUrl(String sclosePicUrl){
		this.sclosePicUrl= sclosePicUrl;
	}
	/* 设备编号 */
	private String sdeviceCode;
	
	public String getSdeviceCode(){
		return sdeviceCode;
	}
	
	public void setSdeviceCode(String sdeviceCode){
		this.sdeviceCode= sdeviceCode;
	}
	/* 访问IP */
	private String sip;
	
	public String getSip(){
		return sip;
	}
	
	public void setSip(String sip){
		this.sip= sip;
	}
	/* 会员编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
	}
	/* 用户ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 会员用户名（手机号） */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 开门图片集合 */
	private String sopenPicUrl;
	
	public String getSopenPicUrl(){
		return sopenPicUrl;
	}
	
	public void setSopenPicUrl(String sopenPicUrl){
		this.sopenPicUrl= sopenPicUrl;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 操作日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 关门时间 */
	private Date tcloseTime;
	
	public Date getTcloseTime(){
		return tcloseTime;
	}
	
	public void setTcloseTime(Date tcloseTime){
		this.tcloseTime= tcloseTime;
	}
	/* 开门时间 */
	private Date topenTime;
	
	public Date getTopenTime(){
		return topenTime;
	}
	
	public void setTopenTime(Date topenTime){
		this.topenTime= topenTime;
	}

		
}