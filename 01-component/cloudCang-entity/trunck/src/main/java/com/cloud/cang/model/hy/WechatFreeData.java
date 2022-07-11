package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 微信免密开通数据(HY_WECHAT_FREE_DATA) **/
public class WechatFreeData extends GenericEntity  {

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
	
	/* 请求序列号 */
	private Integer irequestSerial;
	
	public Integer getIrequestSerial(){
		return irequestSerial;
	}
	
	public void setIrequestSerial(Integer irequestSerial){
		this.irequestSerial= irequestSerial;
	}
	/* 微信代扣方式
10:微信免密代扣
20:微信支付分代扣 */
	private Integer iwechatWithholdType;
	
	public Integer getIwechatWithholdType(){
		return iwechatWithholdType;
	}
	
	public void setIwechatWithholdType(Integer iwechatWithholdType){
		this.iwechatWithholdType= iwechatWithholdType;
	}
	/* 签约协议号 */
	private String scontractCode;
	
	public String getScontractCode(){
		return scontractCode;
	}
	
	public void setScontractCode(String scontractCode){
		this.scontractCode= scontractCode;
	}
	/* 委托代扣协议ID */
	private String scontractId;
	
	public String getScontractId(){
		return scontractId;
	}
	
	public void setScontractId(String scontractId){
		this.scontractId= scontractId;
	}
	/* 商户号 */
	private String smchId;
	
	public String getSmchId(){
		return smchId;
	}
	
	public void setSmchId(String smchId){
		this.smchId= smchId;
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
	/* 用户标识 */
	private String sopenid;
	
	public String getSopenid(){
		return sopenid;
	}
	
	public void setSopenid(String sopenid){
		this.sopenid= sopenid;
	}
	/* 用户在商户下请求唯一标识 */
	private String soutRequestNo;
	
	public String getSoutRequestNo(){
		return soutRequestNo;
	}
	
	public void setSoutRequestNo(String soutRequestNo){
		this.soutRequestNo= soutRequestNo;
	}
	/* 模板ID */
	private String splanId;
	
	public String getSplanId(){
		return splanId;
	}
	
	public void setSplanId(String splanId){
		this.splanId= splanId;
	}
	/* 协议状态
            ADD--签约 
            DELETE--解约 */
	private String sstatus;
	
	public String getSstatus(){
		return sstatus;
	}
	
	public void setSstatus(String sstatus){
		this.sstatus= sstatus;
	}
	/* 解约备注 */
	private String sunsignRemark;
	
	public String getSunsignRemark(){
		return sunsignRemark;
	}
	
	public void setSunsignRemark(String sunsignRemark){
		this.sunsignRemark= sunsignRemark;
	}
	/* 解约方式
            0-未解约 
            1-有效期过自动解约 
            2-用户主动解约 
            3-商户API解约 
            4-商户平台解约 
            5-注销 */
	private String sunsignWay;
	
	public String getSunsignWay(){
		return sunsignWay;
	}
	
	public void setSunsignWay(String sunsignWay){
		this.sunsignWay= sunsignWay;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 协议到期时间 */
	private Date tcontractExpiredTime;
	
	public Date getTcontractExpiredTime(){
		return tcontractExpiredTime;
	}
	
	public void setTcontractExpiredTime(Date tcontractExpiredTime){
		this.tcontractExpiredTime= tcontractExpiredTime;
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

		
}