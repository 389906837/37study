package com.cloud.cang.dispatcher.services.loadbalance;


import org.apache.commons.lang3.StringUtils;




public class StrategyRule {

    private String serviceName;

    private String paramName;

    private String strategyName;

    private String localIp;

    private String remoteIp;

    private String remotePort;

    private ProviderStrategy strategy;

    public void setStrategy(ProviderStrategy strategy) {
        this.strategy = strategy;
    }

    public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public String getParamName() {
        return paramName;
    }

    @Override
    public String toString() {
        return "StrategyRule [serviceName=" + serviceName + ", paramName=" + paramName + ", strategyName=" + strategyName
                + ", localIp=" + localIp + ", remoteIp=" + remoteIp + ", remotePort=" + remotePort + "]";
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public String getLocalIp() {
        return localIp;
    }

    public void setLocalIp(String localIp) {
        this.localIp = localIp;
    }

    public String getRemoteIp() {
        return remoteIp;
    }

    public void setRemoteIp(String remoteIp) {
        this.remoteIp = remoteIp;
    }

    public String getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(String remotePort) {
        this.remotePort = remotePort;
    }

    public ProviderStrategy getStrategy() {
        if (strategy == null) {
            if (StringUtils.isNotBlank(serviceName) && StringUtils.isNotBlank(strategyName)
                    && strategyName.equals("hash")) {
                strategy = new HashStrategy(paramName);
            } else if (StringUtils.isNotBlank(serviceName) && StringUtils.isNotBlank(strategyName)
			        && strategyName.equalsIgnoreCase("ip")
			       ) {
			    strategy = new IPStrategy(remoteIp, remotePort);
			} else {
			    strategy = new RoundRobinStrategy();
			}
        }
        return strategy;
    }
}
