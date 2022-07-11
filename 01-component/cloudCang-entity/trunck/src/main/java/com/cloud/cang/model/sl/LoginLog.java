package com.cloud.cang.model.sl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 会员登录日志记录(SL_LOGIN_LOG) **/
public class LoginLog extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*主键ID*/
	private String id;
	/*主键ID*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 国家 */
	private String country;
	
	public String getCountry(){
		return country;
	}
	
	public void setCountry(String country){
		this.country= country;
	}
	/* 10：微信
            20：支付宝
            30：APP
            40：WAP站
            50：PC站
            60：人脸识别设备 */
	private Integer ideviceType;
	
	public Integer getIdeviceType(){
		return ideviceType;
	}
	
	public void setIdeviceType(Integer ideviceType){
		this.ideviceType= ideviceType;
	}
	/* 登录类型 10：授权登录 20：账号登录 30：SSO登录 40:人脸识别登录 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
	}
	/* 登录AI设备编号（当前只支持人脸识别时填写） */
	private String saiCode;
	
	public String getSaiCode(){
		return saiCode;
	}
	
	public void setSaiCode(String saiCode){
		this.saiCode= saiCode;
	}
	/* 城市 */
	private String scity;
	
	public String getScity(){
		return scity;
	}
	
	public void setScity(String scity){
		this.scity= scity;
	}
	/* 登录IP地址 */
	private String sip;
	
	public String getSip(){
		return sip;
	}
	
	public void setSip(String sip){
		this.sip= sip;
	}
	/* 会员Id */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 会员名 */
	private String smemberName;
	
	public String getSmemberName(){
		return smemberName;
	}
	
	public void setSmemberName(String smemberName){
		this.smemberName= smemberName;
	}
	/* 省份 */
	private String sprovince;
	
	public String getSprovince(){
		return sprovince;
	}
	
	public void setSprovince(String sprovince){
		this.sprovince= sprovince;
	}
	/* 用户编号 */
	private String suserCode;
	
	public String getSuserCode(){
		return suserCode;
	}
	
	public void setSuserCode(String suserCode){
		this.suserCode= suserCode;
	}
	/* 用户姓名 */
	private String suserRealname;
	
	public String getSuserRealname(){
		return suserRealname;
	}
	
	public void setSuserRealname(String suserRealname){
		this.suserRealname= suserRealname;
	}
	/* 登录时间 */
	private Date tloginTime;
	
	public Date getTloginTime(){
		return tloginTime;
	}
	
	public void setTloginTime(Date tloginTime){
		this.tloginTime= tloginTime;
	}

		
}