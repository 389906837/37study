package com.cloud.cang.dispatcher.services;

import java.io.Serializable;

import com.cloud.cang.dispatcher.services.loadbalance.StrategyRule;

public class ServiceConfig implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8358890389485627183L;
	
	
	
	private String configName;
	
	/**
	 * 服务配置负载决策
	 */
	private StrategyRule strategyRule;

	public StrategyRule getStrategyRule() {
		return strategyRule;
	}

	public void setStrategyRule(StrategyRule strategyRule) {
		this.strategyRule = strategyRule;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}
	
	
	
	


	
	
	
	
	

}
