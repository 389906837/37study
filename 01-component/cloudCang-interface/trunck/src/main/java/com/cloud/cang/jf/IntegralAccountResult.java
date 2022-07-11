package com.cloud.cang.jf;

import com.cloud.cang.common.SuperDto;

import java.util.Date;

public class IntegralAccountResult  extends SuperDto {



	private static final long serialVersionUID = 1L;
	
	//-----private-----
	private String id;   //主键
	private String smemberId;   //会员ID
	private String saccountCode;   //账户编号
	private String smemberNo;   //会员编号
	private String smemberName;   //会员名
	private String supateUser;   //修改人
	private Integer iusedPoints;   //已使用积分
	private Integer ifrozenPoints;   //冻结积分
	private Integer itotalPoints;   //总积分
	private Integer iversion;   //版本号
	private Date tupdateTime;   //修改日期
	private Integer iusablePoints;   //可用积分
	private Date taddTime;   //添加日期
	private String sbusId;
	private String sbusCode;
	
	//-----get set-----
	 /**主键*/
	 public String getId(){
		 return id;
	 }
	 /**主键*/
	 public void setId(String id){
		 this.id=id;
	 }
	 /**会员ID*/
	 public String getSmemberId(){
		 return smemberId;
	 }
	 /**会员ID*/
	 public void setSmemberId(String smemberId){
		 this.smemberId=smemberId;
	 }
	 /**账户编号*/
	 public String getSaccountCode(){
		 return saccountCode;
	 }
	 /**账户编号*/
	 public void setSaccountCode(String saccountCode){
		 this.saccountCode=saccountCode;
	 }
	 /**会员编号*/
	 public String getSmemberNo(){
		 return smemberNo;
	 }
	 /**会员编号*/
	 public void setSmemberNo(String smemberNo){
		 this.smemberNo=smemberNo;
	 }
	 /**会员名*/
	 public String getSmemberName(){
		 return smemberName;
	 }
	 /**会员名*/
	 public void setSmemberName(String smemberName){
		 this.smemberName=smemberName;
	 }
	 /**修改人*/
	 public String getSupateUser(){
		 return supateUser;
	 }
	 /**修改人*/
	 public void setSupateUser(String supateUser){
		 this.supateUser=supateUser;
	 }
	 /**已使用积分*/
	 public Integer getIusedPoints(){
		 return iusedPoints;
	 }
	 /**已使用积分*/
	 public void setIusedPoints(Integer iusedPoints){
		 this.iusedPoints=iusedPoints;
	 }
	 /**冻结积分*/
	 public Integer getIfrozenPoints(){
		 return ifrozenPoints;
	 }
	 /**冻结积分*/
	 public void setIfrozenPoints(Integer ifrozenPoints){
		 this.ifrozenPoints=ifrozenPoints;
	 }
	 /**总积分*/
	 public Integer getItotalPoints(){
		 return itotalPoints;
	 }
	 /**总积分*/
	 public void setItotalPoints(Integer itotalPoints){
		 this.itotalPoints=itotalPoints;
	 }
	 /**版本号*/
	 public Integer getIversion(){
		 return iversion;
	 }
	 /**版本号*/
	 public void setIversion(Integer iversion){
		 this.iversion=iversion;
	 }
	 /**修改日期*/
	 public Date getTupdateTime(){
		 return tupdateTime;
	 }
	 /**修改日期*/
	 public void setTupdateTime(Date tupdateTime){
		 this.tupdateTime=tupdateTime;
	 }
	 /**可用积分*/
	 public Integer getIusablePoints(){
		 return iusablePoints;
	 }
	 /**可用积分*/
	 public void setIusablePoints(Integer iusablePoints){
		 this.iusablePoints=iusablePoints;
	 }
	 /**添加日期*/
	 public Date getTaddTime(){
		 return taddTime;
	 }
	 /**添加日期*/
	 public void setTaddTime(Date taddTime){
		 this.taddTime=taddTime;
	 }
	public String getSbusId() {
		return sbusId;
	}
	public void setSbusId(String sbusId) {
		this.sbusId = sbusId;
	}
	public String getSbusCode() {
		return sbusCode;
	}
	public void setSbusCode(String sbusCode) {
		this.sbusCode = sbusCode;
	}
	

		

	
}
