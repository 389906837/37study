package com.cloud.cang.model.ac;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;
/** 规则数据限制记录表
当券或积分有设计是  一次或每天几次 规则时，在该表中记录对应的规则流水，以便于统计(AC_FIRST_REWARD_INTEGRAL) **/
public class FirstRewardIntegral extends GenericEntity  {

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
	
	/* 是否使用过 */
	private Integer iisUse;
	
	public Integer getIisUse(){
		return iisUse;
	}
	
	public void setIisUse(Integer iisUse){
		this.iisUse= iisUse;
	}
	/* 类型
            1=积分
            2=劵 */
	private Integer iruleType;
	
	public Integer getIruleType(){
		return iruleType;
	}
	
	public void setIruleType(Integer iruleType){
		this.iruleType= iruleType;
	}
	/* 积分账户id */
	private String sintegralAccountId;
	
	public String getSintegralAccountId(){
		return sintegralAccountId;
	}
	
	public void setSintegralAccountId(String sintegralAccountId){
		this.sintegralAccountId= sintegralAccountId;
	}
	/* 规则编号 */
	private String sintegralRuleCode;
	
	public String getSintegralRuleCode(){
		return sintegralRuleCode;
	}
	
	public void setSintegralRuleCode(String sintegralRuleCode){
		this.sintegralRuleCode= sintegralRuleCode;
	}
	/* 会员编号 */
	private String smemberCode;
	
	public String getSmemberCode(){
		return smemberCode;
	}
	
	public void setSmemberCode(String smemberCode){
		this.smemberCode= smemberCode;
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
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 规则ID(积分或送券规则) */
	private String sruleId;
	
	public String getSruleId(){
		return sruleId;
	}
	
	public void setSruleId(String sruleId){
		this.sruleId= sruleId;
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