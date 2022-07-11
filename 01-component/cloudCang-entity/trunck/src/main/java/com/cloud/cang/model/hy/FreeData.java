package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 支付宝免密开通数据(HY_FREE_DATA) **/
public class FreeData extends GenericEntity  {

	private static final long serialVersionUID = 1L;
	
	/*ID*/
	private String id;
	/*ID*/
	public String getId(){
		return id;
	}
	
	public void setId(String id){
		 this.id= id;
	}
	
	/* 支付宝唯一标识签约记录编号 */
	private String sagreementNo;
	
	public String getSagreementNo(){
		return sagreementNo;
	}
	
	public void setSagreementNo(String sagreementNo){
		this.sagreementNo= sagreementNo;
	}
	/* 脱敏的支付宝账号 */
	private String salipayLogonId;
	
	public String getSalipayLogonId(){
		return salipayLogonId;
	}
	
	public void setSalipayLogonId(String salipayLogonId){
		this.salipayLogonId= salipayLogonId;
	}
	/* 用户签约的支付宝账号对应的支付宝唯一用户号,以2088开头的16位纯数字组成 */
	private String salipayUserId;
	
	public String getSalipayUserId(){
		return salipayUserId;
	}
	
	public void setSalipayUserId(String salipayUserId){
		this.salipayUserId= salipayUserId;
	}
	/* 设备ID */
	private String sdeviceId;
	
	public String getSdeviceId(){
		return sdeviceId;
	}
	
	public void setSdeviceId(String sdeviceId){
		this.sdeviceId= sdeviceId;
	}
	/* 代扣协议用户的唯一签约号 */
	private String sexternalAgreementNo;
	
	public String getSexternalAgreementNo(){
		return sexternalAgreementNo;
	}
	
	public void setSexternalAgreementNo(String sexternalAgreementNo){
		this.sexternalAgreementNo= sexternalAgreementNo;
	}
	/* 商户网站的登录账号 */
	private String sexternalLogonId;
	
	public String getSexternalLogonId(){
		return sexternalLogonId;
	}
	
	public void setSexternalLogonId(String sexternalLogonId){
		this.sexternalLogonId= sexternalLogonId;
	}
	/* 是否海外购汇身份 */
	private String sforexEligible;
	
	public String getSforexEligible(){
		return sforexEligible;
	}
	
	public void setSforexEligible(String sforexEligible){
		this.sforexEligible= sforexEligible;
	}
	/* 用户登录token，目前用于集
            团的信任登录 */
	private String sloginToken;
	
	public String getSloginToken(){
		return sloginToken;
	}
	
	public void setSloginToken(String sloginToken){
		this.sloginToken= sloginToken;
	}
	/* 会员ID */
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
	/* 会员编号 */
	private String smemberNo;
	
	public String getSmemberNo(){
		return smemberNo;
	}
	
	public void setSmemberNo(String smemberNo){
		this.smemberNo= smemberNo;
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
	/* 签约成功通知类型 */
	private String snotifyType;
	
	public String getSnotifyType(){
		return snotifyType;
	}
	
	public void setSnotifyType(String snotifyType){
		this.snotifyType= snotifyType;
	}
	/* 交易流水号 */
	private String spaySerialNumber;
	
	public String getSpaySerialNumber(){
		return spaySerialNumber;
	}
	
	public void setSpaySerialNumber(String spaySerialNumber){
		this.spaySerialNumber= spaySerialNumber;
	}
	/* 协议产品码 */
	private String sproductCode;
	
	public String getSproductCode(){
		return sproductCode;
	}
	
	public void setSproductCode(String sproductCode){
		this.sproductCode= sproductCode;
	}
	/* 协议场景 */
	private String ssignScene;
	
	public String getSsignScene(){
		return ssignScene;
	}
	
	public void setSsignScene(String ssignScene){
		this.ssignScene= ssignScene;
	}
	/* 协议当前状态
            1. TEMP：暂存，协议未生效
            过；
            2. NORMAL：正常；
            3. STOP：暂停 4. UNSIGN：解约 */
	private String sstatus;
	
	public String getSstatus(){
		return sstatus;
	}
	
	public void setSstatus(String sstatus){
		this.sstatus= sstatus;
	}
	/* 芝麻信用openId */
	private String szmOpenId;
	
	public String getSzmOpenId(){
		return szmOpenId;
	}
	
	public void setSzmOpenId(String szmOpenId){
		this.szmOpenId= szmOpenId;
	}
	/* 支付宝芝麻分 */
	private String szmScore;
	
	public String getSzmScore(){
		return szmScore;
	}
	
	public void setSzmScore(String szmScore){
		this.szmScore= szmScore;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 用户代扣协议的失效时间 */
	private Date tinvalidTime;
	
	public Date getTinvalidTime(){
		return tinvalidTime;
	}
	
	public void setTinvalidTime(Date tinvalidTime){
		this.tinvalidTime= tinvalidTime;
	}
	/* 实际签约时间 */
	private Date tsignTime;
	
	public Date getTsignTime(){
		return tsignTime;
	}
	
	public void setTsignTime(Date tsignTime){
		this.tsignTime= tsignTime;
	}
	/* 解约时间 */
	private Date tunsignTime;
	
	public Date getTunsignTime(){
		return tunsignTime;
	}
	
	public void setTunsignTime(Date tunsignTime){
		this.tunsignTime= tunsignTime;
	}
	/* 修改日期 */
	private Date tupdateTime;
	
	public Date getTupdateTime(){
		return tupdateTime;
	}
	
	public void setTupdateTime(Date tupdateTime){
		this.tupdateTime= tupdateTime;
	}
	/* 用户代扣协议的实际生效时间 */
	private Date tvalidTime;
	
	public Date getTvalidTime(){
		return tvalidTime;
	}
	
	public void setTvalidTime(Date tvalidTime){
		this.tvalidTime= tvalidTime;
	}

		
}