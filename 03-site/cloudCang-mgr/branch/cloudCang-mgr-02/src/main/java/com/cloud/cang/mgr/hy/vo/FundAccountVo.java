package com.cloud.cang.mgr.hy.vo;

import com.cloud.cang.model.hy.FundAccount;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 会员资金账户表
 * @author ChangTanchang
 * @time 2018-02-07 14:31:00
 * @fileName MbrRoleController.java
 * @version 1.0
 */
public class FundAccountVo extends FundAccount {

	// 账户可用余额区间(eq:0~50)
	private BigDecimal fusableBalanceBegin;

	// 账户可用余额区间
	private BigDecimal fusableBalanceEnd;

	// 提现冻结金额(区间)
	private BigDecimal famountFrozenBegin;

	// 提现冻结金额(区间)
	private BigDecimal famountFrozenEnd;

	// 排序字段
	private String orderStr;

	public String getOrderStr() {
		return orderStr;
	}

	public void setOrderStr(String orderStr) {
		this.orderStr = orderStr;
	}

	public BigDecimal getFusableBalanceBegin() {
		return fusableBalanceBegin;
	}

	public void setFusableBalanceBegin(BigDecimal fusableBalanceBegin) {
		this.fusableBalanceBegin = fusableBalanceBegin;
	}

	public BigDecimal getFusableBalanceEnd() {
		return fusableBalanceEnd;
	}

	public void setFusableBalanceEnd(BigDecimal fusableBalanceEnd) {
		this.fusableBalanceEnd = fusableBalanceEnd;
	}

	public BigDecimal getFamountFrozenBegin() {
		return famountFrozenBegin;
	}

	public void setFamountFrozenBegin(BigDecimal famountFrozenBegin) {
		this.famountFrozenBegin = famountFrozenBegin;
	}

	public BigDecimal getFamountFrozenEnd() {
		return famountFrozenEnd;
	}

	public void setFamountFrozenEnd(BigDecimal famountFrozenEnd) {
		this.famountFrozenEnd = famountFrozenEnd;
	}
}
