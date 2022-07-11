package com.cloud.cang.jf;

import com.cloud.cang.common.SuperDto;

/**
 * 积分账户变更参数
 * @author zhouhong
 * @version 1.0
 */
public class ChangeIntegralDto extends SuperDto {

	private static final long serialVersionUID = -2913572903105378881L;

	/**
	 * 会员ID 【必填】
	 */
	private String smemberId;

	/**
	 * 会员姓名 【必填】
	 */
	private String smemberName;

	/**
	 * 会员编号 【必填】
	 */
	private String smemberNo;
	/**
	 * 积分值(正数积分增加 负数积分减少) 【必填】
	 */
	private Integer fvalue;

	/**
	 * {@link com.cloud.cang.core.common.BizTypeDefinitionEnum.ClientType}}
	 * 来源 【必填】 10=pc 20=iphone 30=android 40=wap
	 */
	private Integer irequestSource;

	/**
	 * {@link com.cloud.cang.core.common.BizTypeDefinitionEnum.SourceBizStatus}
	 * 来源业务类型【必填】 10=平台赠送 20=新用户注册 30=开通托管账户 40=绑定银行卡 50=首次充值 60=投资 70=登陆
	 * 80=绑定邮箱 90=推荐注册 100=推荐投资 110=推荐开托管账户 120=参与活动 180=其它
	 */
	private Integer isourceType;

	/**
	 * 积分赠送规则ID 【选填】
	 */
	private String sintegralRuleId;

	/**
	 * 积分赠送规则名称 【选填】
	 */
	private String sintegralRuleName;

	/**
	 * 积分赠送规则编号 【选填】
	 */
	private String sintegralRuleNo;

	/**
	 * 是否后台操作 【选填】
	 */
	private Integer isbackOperate;

	/**
	 * 只有当类型为因邀请的好友的行为产生的奖励时才有值 【选填】
	 */
	private String sinvestmentId;

	/**
	 * 来源业务记录编号 【选填】
	 */
	private String ssourceNo;

	/**
	 * 来源业务记录ID 【选填】
	 */
	private String ssourceId;

	/**
	 * 备注【选填】
	 */
	private String sremark;
	
	/**
	 * 添加人姓名 【选填】
	 */
	private String saddUser;

	/**
	 * 来源业务记录说明 【选填】
	 */
	private String ssourceInstruction;
	/**
	 * 关联数据id【选填】
	 */
	private String slinkId;
	
	public String getSmemberId() {
		return smemberId;
	}

	public void setSmemberId(String smemberId) {
		this.smemberId = smemberId;
	}

	public String getSmemberName() {
		return smemberName;
	}

	public void setSmemberName(String smemberName) {
		this.smemberName = smemberName;
	}

	public String getSmemberNo() {
		return smemberNo;
	}

	public void setSmemberNo(String smemberNo) {
		this.smemberNo = smemberNo;
	}

	public Integer getFvalue() {
		return fvalue;
	}

	public void setFvalue(Integer fvalue) {
		this.fvalue = fvalue;
	}

	public Integer getIrequestSource() {
		return irequestSource;
	}

	public void setIrequestSource(Integer irequestSource) {
		this.irequestSource = irequestSource;
	}

	public Integer getIsourceType() {
		return isourceType;
	}

	public void setIsourceType(Integer isourceType) {
		this.isourceType = isourceType;
	}

	public String getSintegralRuleId() {
		return sintegralRuleId;
	}

	public void setSintegralRuleId(String sintegralRuleId) {
		this.sintegralRuleId = sintegralRuleId;
	}

	public String getSintegralRuleName() {
		return sintegralRuleName;
	}

	public void setSintegralRuleName(String sintegralRuleName) {
		this.sintegralRuleName = sintegralRuleName;
	}

	public String getSintegralRuleNo() {
		return sintegralRuleNo;
	}

	public void setSintegralRuleNo(String sintegralRuleNo) {
		this.sintegralRuleNo = sintegralRuleNo;
	}

	public Integer getIsbackOperate() {
		return isbackOperate;
	}

	public void setIsbackOperate(Integer isbackOperate) {
		this.isbackOperate = isbackOperate;
	}

	public String getSinvestmentId() {
		return sinvestmentId;
	}

	public void setSinvestmentId(String sinvestmentId) {
		this.sinvestmentId = sinvestmentId;
	}

	public String getSsourceNo() {
		return ssourceNo;
	}

	public void setSsourceNo(String ssourceNo) {
		this.ssourceNo = ssourceNo;
	}

	public String getSsourceId() {
		return ssourceId;
	}

	public void setSsourceId(String ssourceId) {
		this.ssourceId = ssourceId;
	}

	public String getSremark() {
		return sremark;
	}

	public void setSremark(String sremark) {
		this.sremark = sremark;
	}

	public String getSsourceInstruction() {
		return ssourceInstruction;
	}

	public void setSsourceInstruction(String ssourceInstruction) {
		this.ssourceInstruction = ssourceInstruction;
	}

	public String getSaddUser() {
		return saddUser;
	}

	public void setSaddUser(String saddUser) {
		this.saddUser = saddUser;
	}

	/**
	 * @return the slinkId
	 */
	public String getSlinkId() {
		return slinkId;
	}

	/**
	 * @param slinkId the slinkId to set
	 */
	public void setSlinkId(String slinkId) {
		this.slinkId = slinkId;
	}
	
	
	


}
