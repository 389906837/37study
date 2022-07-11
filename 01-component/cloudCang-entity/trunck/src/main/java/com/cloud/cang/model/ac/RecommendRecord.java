package com.cloud.cang.model.ac;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 好友推荐记录表(AC_RECOMMEND_RECORD) **/
public class RecommendRecord extends GenericEntity  {

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
	/* 被推荐人首次订单实付金额 */
	private BigDecimal sorderActualMoney;
	
	public BigDecimal getSorderActualMoney(){
		return sorderActualMoney;
	}
	
	public void setSorderActualMoney(BigDecimal sorderActualMoney){
		this.sorderActualMoney= sorderActualMoney;
	}
	/* 被推荐人首次订单编号 */
	private String sorderCode;
	
	public String getSorderCode(){
		return sorderCode;
	}
	
	public void setSorderCode(String sorderCode){
		this.sorderCode= sorderCode;
	}
	/* 被推荐人首次订单ID */
	private String sorderId;
	
	public String getSorderId(){
		return sorderId;
	}
	
	public void setSorderId(String sorderId){
		this.sorderId= sorderId;
	}
	/* 被推荐人首次订单金额 */
	private BigDecimal sorderMoney;
	
	public BigDecimal getSorderMoney(){
		return sorderMoney;
	}
	
	public void setSorderMoney(BigDecimal sorderMoney){
		this.sorderMoney= sorderMoney;
	}
	/* 被推荐人编号 */
	private String spresenteeCode;
	
	public String getSpresenteeCode(){
		return spresenteeCode;
	}
	
	public void setSpresenteeCode(String spresenteeCode){
		this.spresenteeCode= spresenteeCode;
	}
	/* 被推荐人ID */
	private String spresenteeId;
	
	public String getSpresenteeId(){
		return spresenteeId;
	}
	
	public void setSpresenteeId(String spresenteeId){
		this.spresenteeId= spresenteeId;
	}
	/* 被推荐人姓名 */
	private String spresenteeName;
	
	public String getSpresenteeName(){
		return spresenteeName;
	}
	
	public void setSpresenteeName(String spresenteeName){
		this.spresenteeName= spresenteeName;
	}
	/* 推荐人编号 */
	private String sreferrerCode;
	
	public String getSreferrerCode(){
		return sreferrerCode;
	}
	
	public void setSreferrerCode(String sreferrerCode){
		this.sreferrerCode= sreferrerCode;
	}
	/* 推荐人ID */
	private String sreferrerId;
	
	public String getSreferrerId(){
		return sreferrerId;
	}
	
	public void setSreferrerId(String sreferrerId){
		this.sreferrerId= sreferrerId;
	}
	/* 推荐人姓名 */
	private String sreferrerName;
	
	public String getSreferrerName(){
		return sreferrerName;
	}
	
	public void setSreferrerName(String sreferrerName){
		this.sreferrerName= sreferrerName;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 奖励说明 */
	private String srewardInstruction;
	
	public String getSrewardInstruction(){
		return srewardInstruction;
	}
	
	public void setSrewardInstruction(String srewardInstruction){
		this.srewardInstruction= srewardInstruction;
	}
	/* 生成时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}
	/* 首次订单时间 */
	private Date torderDatetime;
	
	public Date getTorderDatetime(){
		return torderDatetime;
	}
	
	public void setTorderDatetime(Date torderDatetime){
		this.torderDatetime= torderDatetime;
	}
	/* 注册时间 */
	private Date tregDatetime;
	
	public Date getTregDatetime(){
		return tregDatetime;
	}
	
	public void setTregDatetime(Date tregDatetime){
		this.tregDatetime= tregDatetime;
	}

		
}