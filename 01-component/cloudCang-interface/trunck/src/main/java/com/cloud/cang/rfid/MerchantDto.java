package com.cloud.cang.rfid;

import com.cloud.cang.model.sh.MerchantInfo;

import java.io.Serializable;

public class MerchantDto implements Serializable{

	private static final long serialVersionUID = 3517472648170990945L;

	private String id;

	private String domainUrl;

	private String merchantName;

	private String operatorId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDomainUrl() {
		return domainUrl;
	}

	public void setDomainUrl(String domainUrl) {
		this.domainUrl = domainUrl;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	@Override
	public String toString() {
		return domainUrl;
	}
}