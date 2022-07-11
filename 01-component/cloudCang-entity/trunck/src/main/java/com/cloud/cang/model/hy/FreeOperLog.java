package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 微信支付宝免密操作记录(HY_FREE_OPER_LOG) **/
public class FreeOperLog extends GenericEntity  {

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
	
	/* 操作动作 10 签约 20 解约 */
	private Integer iaction;
	
	public Integer getIaction(){
		return iaction;
	}
	
	public void setIaction(Integer iaction){
		this.iaction= iaction;
	}
	/* 操作类型 10 微信 20 支付宝 */
	private Integer itype;
	
	public Integer getItype(){
		return itype;
	}
	
	public void setItype(Integer itype){
		this.itype= itype;
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
	/* 操作IP */
	private String soperIp;
	
	public String getSoperIp(){
		return soperIp;
	}
	
	public void setSoperIp(String soperIp){
		this.soperIp= soperIp;
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
	/* 操作时间 */
	private Date toperTime;
	
	public Date getToperTime(){
		return toperTime;
	}
	
	public void setToperTime(Date toperTime){
		this.toperTime= toperTime;
	}

		
}