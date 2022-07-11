package com.cloud.cang.model.ac;
import com.cloud.cang.generic.GenericEntity;

import java.math.BigDecimal;
import java.util.Date;
/** 活动返积分规则记录表(AC_INTEGRAL_RULE) **/
public class IntegralRule extends GenericEntity  {

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
	
	/* 积分值,如果赠送次数为比例，该字段为比例值 */
	private BigDecimal fbaseIntegral;
	
	public BigDecimal getFbaseIntegral(){
		return fbaseIntegral;
	}
	
	public void setFbaseIntegral(BigDecimal fbaseIntegral){
		this.fbaseIntegral= fbaseIntegral;
	}
	/* 最大积分 */
	private Integer fmaxValue;
	
	public Integer getFmaxValue(){
		return fmaxValue;
	}
	
	public void setFmaxValue(Integer fmaxValue){
		this.fmaxValue= fmaxValue;
	}
	/* 最小积分 */
	private Integer fminValue;
	
	public Integer getFminValue(){
		return fminValue;
	}
	
	public void setFminValue(Integer fminValue){
		this.fminValue= fminValue;
	}
	/* 是否可修改:是否可供用户修改 */
	private Integer idelFlag;
	
	public Integer getIdelFlag(){
		return idelFlag;
	}
	
	public void setIdelFlag(Integer idelFlag){
		this.idelFlag= idelFlag;
	}
	/* 赠送方式
            10:比例
            20:固定额度 */
	private Integer igiveMethod;
	
	public Integer getIgiveMethod(){
		return igiveMethod;
	}
	
	public void setIgiveMethod(Integer igiveMethod){
		this.igiveMethod= igiveMethod;
	}
	/* 是否有效 */
	private Integer iisValid;
	
	public Integer getIisValid(){
		return iisValid;
	}
	
	public void setIisValid(Integer iisValid){
		this.iisValid= iisValid;
	}
	/* 奖励说明 */
	private String sactivityInstruction;
	
	public String getSactivityInstruction(){
		return sactivityInstruction;
	}
	
	public void setSactivityInstruction(String sactivityInstruction){
		this.sactivityInstruction= sactivityInstruction;
	}
	/* 活动编号 */
	private String sacCode;
	
	public String getSacCode(){
		return sacCode;
	}
	
	public void setSacCode(String sacCode){
		this.sacCode= sacCode;
	}
	/* 活动ID */
	private String sacId;
	
	public String getSacId(){
		return sacId;
	}
	
	public void setSacId(String sacId){
		this.sacId= sacId;
	}
	/* 添加人 */
	private String saddUser;
	
	public String getSaddUser(){
		return saddUser;
	}
	
	public void setSaddUser(String saddUser){
		this.saddUser= saddUser;
	}
	/* 规则编号 */
	private String scode;
	
	public String getScode(){
		return scode;
	}
	
	public void setScode(String scode){
		this.scode= scode;
	}
	/* 备注 */
	private String sremark;
	
	public String getSremark(){
		return sremark;
	}
	
	public void setSremark(String sremark){
		this.sremark= sremark;
	}
	/* 修改人 */
	private String supateUser;
	
	public String getSupateUser(){
		return supateUser;
	}
	
	public void setSupateUser(String supateUser){
		this.supateUser= supateUser;
	}
	/* 添加日期 */
	private Date taddTime;
	
	public Date getTaddTime(){
		return taddTime;
	}
	
	public void setTaddTime(Date taddTime){
		this.taddTime= taddTime;
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