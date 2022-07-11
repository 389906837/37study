package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 盘点操作参数
 * @author zhouhong
 * @version 1.0
 */
public class RealTimeOrderDto extends SuperDto {

	//======必填=====
	private String deviceId;//设备编号
	private Integer isourceClientType;//来源客户端 10=传统 20=RFID射频 30=视觉
	private String memberId;//会员ID 购物

	//======选填=====
	private List<CommodityVo> commodityVos;//盘点商品集合




	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public List<CommodityVo> getCommodityVos() {
		return commodityVos;
	}

	public void setCommodityVos(List<CommodityVo> commodityVos) {
		this.commodityVos = commodityVos;
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

	@Override
	public String toString() {
		return "RealTimeOrderDto{" +
				"deviceId='" + deviceId + '\'' +
				", commodityVos=" + commodityVos +
				", memberId='" + memberId + '\'' +
				", isourceClientType=" + isourceClientType +
				'}';
	}
}
