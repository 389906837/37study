package com.cloud.cang.jf;

import com.cloud.cang.common.SuperDto;

/**
 * 积分账户开户DTO
 * @author zhouhong
 *
 */
public class OpenIntegralAccountDto extends SuperDto{
	
	private static final long serialVersionUID = -4705379778937367533L;

	/**
	 * 会员ID
	 */
	private String smemberId;
	
	/**
	 * 会员名
	 */
	private String smemberName;

	/**
	 * 会员编号
	 */
	private String smemberNo;
	
	/**
	 * 修改人 【选填】
	 */
	private String supateUser;

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

	public String getSupateUser() {
		return supateUser;
	}

	public void setSupateUser(String supateUser) {
		this.supateUser = supateUser;
	}
	
	
}
