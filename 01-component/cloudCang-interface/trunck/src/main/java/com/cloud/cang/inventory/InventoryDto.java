package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * 盘点操作参数
 * @author zhouhong
 * @version 1.0
 */
public class InventoryDto extends SuperDto {

	private static final long serialVersionUID = -2913572903105378881L;
	//======必填=====
	private Integer inventoryType;//盘点类型 10 关门盘点 20 定时盘点 30 主动盘点
	private Integer closeType;//关门类型 10 购物 20 补货员 关门盘点必填
	private String deviceId;//设备编号
	private BigDecimal totalWeight;     //盘货总重量
	private BigDecimal stockTotalWeight;//库存总重量
	//======选填=====
	private List<CommodityVo> commodityVos;//盘点商品集合
	private String memberId;//会员ID 购物
	private Integer isourceClientType;//来源客户端
	private String sip;//操作IP

	//=====RFID====
	private Set<String> lossLables;
	private Set<String> newLables;
	private Set<String> currentLables;
	private String sext;



	public Set<String> getLossLables() {
		return lossLables;
	}

	public void setLossLables(Set<String> lossLables) {
		this.lossLables = lossLables;
	}

	public Set<String> getNewLables() {
		return newLables;
	}

	public void setNewLables(Set<String> newLables) {
		this.newLables = newLables;
	}

	public Set<String> getCurrentLables() {
		return currentLables;
	}

	public void setCurrentLables(Set<String> currentLables) {
		this.currentLables = currentLables;
	}

	public BigDecimal getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(BigDecimal totalWeight) {
		this.totalWeight = totalWeight;
	}

	public BigDecimal getStockTotalWeight() {
		return stockTotalWeight;
	}

	public void setStockTotalWeight(BigDecimal stockTotalWeight) {
		this.stockTotalWeight = stockTotalWeight;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Integer getInventoryType() {
		return inventoryType;
	}

	public void setInventoryType(Integer inventoryType) {
		this.inventoryType = inventoryType;
	}

	public Integer getCloseType() {
		return closeType;
	}

	public void setCloseType(Integer closeType) {
		this.closeType = closeType;
	}

	public List<CommodityVo> getCommodityVos() {
		return commodityVos;
	}

	public void setCommodityVos(List<CommodityVo> commodityVos) {
		this.commodityVos = commodityVos;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getIsourceClientType() {
		return isourceClientType;
	}

	public void setIsourceClientType(Integer isourceClientType) {
		this.isourceClientType = isourceClientType;
	}

	public String getSip() {
		return sip;
	}

	public void setSip(String sip) {
		this.sip = sip;
	}

	public String getSext() {
		return sext;
	}

	public void setSext(String sext) {
		this.sext = sext;
	}

	@Override
	public String toString() {
		return "InventoryDto{" +
				"inventoryType=" + inventoryType +
				", closeType=" + closeType +
				", deviceId='" + deviceId + '\'' +
				", commodityVos=" + commodityVos +
				", memberId='" + memberId + '\'' +
				", isourceClientType=" + isourceClientType +
				", sip='" + sip + '\'' +
				'}';
	}
}
