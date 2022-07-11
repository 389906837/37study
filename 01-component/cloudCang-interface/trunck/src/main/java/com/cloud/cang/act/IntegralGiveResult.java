package com.cloud.cang.act;

import java.io.Serializable;

/**
 * 积分发放结果
 * @author zhouhong
 * @version 1.0
 */
public class IntegralGiveResult implements Serializable{
	
	private static final long serialVersionUID = -8467421339876889560L;

	private Integer integralValue;//积分值
	
	private String integralRuleName;//积分活动名称

	public Integer getIntegralValue() {
		return integralValue;
	}

	public void setIntegralValue(Integer integralValue) {
		this.integralValue = integralValue;
	}

	public String getIntegralRuleName() {
		return integralRuleName;
	}

	public void setIntegralRuleName(String integralRuleName) {
		this.integralRuleName = integralRuleName;
	}

	@Override
	public String toString() {
		return "IntegralGiveResult [integralRuleName=" + integralRuleName + ", integralValue=" + integralValue + "]";
	}
	
}
