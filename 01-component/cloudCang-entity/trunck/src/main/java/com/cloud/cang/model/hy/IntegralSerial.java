package com.cloud.cang.model.hy;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import com.cloud.cang.generic.GenericEntity;
/** 会员积分明细(HY_INTEGRAL_SERIAL) **/
public class IntegralSerial extends GenericEntity  {

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
	
	/* 账户积分余额 */
	private Integer fbalanceValue;
	
	public Integer getFbalanceValue(){
		return fbalanceValue;
	}
	
	public void setFbalanceValue(Integer fbalanceValue){
		this.fbalanceValue= fbalanceValue;
	}
	/* 积分值 */
	private Integer fvalue;
	
	public Integer getFvalue(){
		return fvalue;
	}
	
	public void setFvalue(Integer fvalue){
		this.fvalue= fvalue;
	}
	/* 来源
            10=微信
            20=支付宝
            30=APP
             */
	private Integer irequestSource;
	
	public Integer getIrequestSource(){
		return irequestSource;
	}
	
	public void setIrequestSource(Integer irequestSource){
		this.irequestSource= irequestSource;
	}
	/* 是否后台操作 */
	private Integer isbackOperate;
	
	public Integer getIsbackOperate(){
		return isbackOperate;
	}
	
	public void setIsbackOperate(Integer isbackOperate){
		this.isbackOperate= isbackOperate;
	}
	/* 来源类型 
            10=平台赠送
            20=用户注册
            30=开托管账户
            40=首次绑银行卡
            50=首次充值
            60=参与下单
            61=首次订单
            70=邀请好友注册
            80=邀请好友首次订单
            90=开托管账户推荐
            100=活动所得
            110=签到
            120=券码兑换
             */
	private Integer isourceType;
	
	public Integer getIsourceType(){
		return isourceType;
	}
	
	public void setIsourceType(Integer isourceType){
		this.isourceType= isourceType;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 积分赠送活动规则ID */
	private String sintegralRuleId;
	
	public String getSintegralRuleId(){
		return sintegralRuleId;
	}
	
	public void setSintegralRuleId(String sintegralRuleId){
		this.sintegralRuleId= sintegralRuleId;
	}
	/* 积分赠送活动规则名称 */
	private String sintegralRuleName;
	
	public String getSintegralRuleName(){
		return sintegralRuleName;
	}
	
	public void setSintegralRuleName(String sintegralRuleName){
		this.sintegralRuleName= sintegralRuleName;
	}
	/* 积分赠送活动规则编号 */
	private String sintegralRuleNo;
	
	public String getSintegralRuleNo(){
		return sintegralRuleNo;
	}
	
	public void setSintegralRuleNo(String sintegralRuleNo){
		this.sintegralRuleNo= sintegralRuleNo;
	}
	/* 会员ID */
	private String smemberId;
	
	public String getSmemberId(){
		return smemberId;
	}
	
	public void setSmemberId(String smemberId){
		this.smemberId= smemberId;
	}
	/* 会员姓名 */
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
	/* 只有当类型为因邀请的好友的行为产生的奖励时才有值 */
	private String spresenteeId;
	
	public String getSpresenteeId(){
		return spresenteeId;
	}
	
	public void setSpresenteeId(String spresenteeId){
		this.spresenteeId= spresenteeId;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 来源业务记录ID */
	private String ssourceId;
	
	public String getSsourceId(){
		return ssourceId;
	}
	
	public void setSsourceId(String ssourceId){
		this.ssourceId= ssourceId;
	}
	/* 来源业务记录说明 */
	private String ssourceInstruction;
	
	public String getSsourceInstruction(){
		return ssourceInstruction;
	}
	
	public void setSsourceInstruction(String ssourceInstruction){
		this.ssourceInstruction= ssourceInstruction;
	}
	/* 来源业务记录编号 */
	private String ssourceNo;
	
	public String getSsourceNo(){
		return ssourceNo;
	}
	
	public void setSsourceNo(String ssourceNo){
		this.ssourceNo= ssourceNo;
	}
	/* 产生时间 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
	}

		
}