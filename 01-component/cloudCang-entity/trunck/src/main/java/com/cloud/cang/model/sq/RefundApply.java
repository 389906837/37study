package com.cloud.cang.model.sq;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 退款申请(SQ_REFUND_APPLY) **/
public class RefundApply extends GenericEntity  {

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
	
	/* 实际退款金额 */
	private BigDecimal factualRefundAmout;
	
	public BigDecimal getFactualRefundAmout(){
		return factualRefundAmout;
	}
	
	public void setFactualRefundAmout(BigDecimal factualRefundAmout){
		this.factualRefundAmout= factualRefundAmout;
	}
	/* 退款总额 */
	private BigDecimal frefundAmount;
	
	public BigDecimal getFrefundAmount(){
		return frefundAmount;
	}
	
	public void setFrefundAmount(BigDecimal frefundAmount){
		this.frefundAmount= frefundAmount;
	}
	/* 退款手续费 */
	private BigDecimal frefundFee;
	
	public BigDecimal getFrefundFee(){
		return frefundFee;
	}
	
	public void setFrefundFee(BigDecimal frefundFee){
		this.frefundFee= frefundFee;
	}
	/* 订单总额 */
	private BigDecimal ftotalAmount;
	
	public BigDecimal getFtotalAmount(){
		return ftotalAmount;
	}
	
	public void setFtotalAmount(BigDecimal ftotalAmount){
		this.ftotalAmount= ftotalAmount;
	}
	/* 申请状态 
            10=退款中
            20=退款成功
            30=退款异常 */
	private Integer istatus;
	
	public Integer getIstatus(){
		return istatus;
	}
	
	public void setIstatus(Integer istatus){
		this.istatus= istatus;
	}
	/* 版本号 */
	private Integer iversion;
	
	public Integer getIversion(){
		return iversion;
	}
	
	public void setIversion(Integer iversion){
		this.iversion= iversion;
	}
	/* 操作人(用户名) */
	private String sadduserId;
	
	public String getSadduserId(){
		return sadduserId;
	}
	
	public void setSadduserId(String sadduserId){
		this.sadduserId= sadduserId;
	}
	/* 退款审核订单编号 */
	private String sauditOrderCode;
	
	public String getSauditOrderCode(){
		return sauditOrderCode;
	}
	
	public void setSauditOrderCode(String sauditOrderCode){
		this.sauditOrderCode= sauditOrderCode;
	}
	/* 退款审核订单ID */
	private String sauditOrderId;
	
	public String getSauditOrderId(){
		return sauditOrderId;
	}
	
	public void setSauditOrderId(String sauditOrderId){
		this.sauditOrderId= sauditOrderId;
	}
	/* 币种 */
	private String scurrency;
	
	public String getScurrency(){
		return scurrency;
	}
	
	public void setScurrency(String scurrency){
		this.scurrency= scurrency;
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
	/* 退款订单编号 */
	private String sorderCode;
	
	public String getSorderCode(){
		return sorderCode;
	}
	
	public void setSorderCode(String sorderCode){
		this.sorderCode= sorderCode;
	}
	/* 退款订单ID */
	private String sorderId;
	
	public String getSorderId(){
		return sorderId;
	}
	
	public void setSorderId(String sorderId){
		this.sorderId= sorderId;
	}
	/* 交易流水号 */
	private String spaySerialNumber;
	
	public String getSpaySerialNumber(){
		return spaySerialNumber;
	}
	
	public void setSpaySerialNumber(String spaySerialNumber){
		this.spaySerialNumber= spaySerialNumber;
	}
	/* 退款编号 */
	private String srefundNo;
	
	public String getSrefundNo(){
		return srefundNo;
	}
	
	public void setSrefundNo(String srefundNo){
		this.srefundNo= srefundNo;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 第三方支付流水号 */
	private String stransactionId;
	
	public String getStransactionId(){
		return stransactionId;
	}
	
	public void setStransactionId(String stransactionId){
		this.stransactionId= stransactionId;
	}
	/* 创建时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 退款完成时间 */
	private Date tfinishDatetime;
	
	public Date getTfinishDatetime(){
		return tfinishDatetime;
	}
	
	public void setTfinishDatetime(Date tfinishDatetime){
		this.tfinishDatetime= tfinishDatetime;
	}
	/* 申请时间 */
	private Date tordertime;
	
	public Date getTordertime(){
		return tordertime;
	}
	
	public void setTordertime(Date tordertime){
		this.tordertime= tordertime;
	}

		
}