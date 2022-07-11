package com.cloud.cang.wap.sb.vo;


import java.io.Serializable;

/**
 * 设备信息
 * @author zhouhong
 * @version 1.0
 */
public class DeviceVo implements Serializable {

	private String merchantCode;//商户编号
	private String deviceId;//设备Id
	private String deviceCode;//设备编号
	private String openSource;//开门来源  wechat 微信 支付宝 alipay APP app
	private String openDoorKey;//开门秘钥 有时效性
	private Integer isScan;//是否扫码 0 否 1 是
	private String aiId;	//人脸识别设备Id
	private String aiCode;	//人脸识别设备编号
	private String errorCode;	//错误代码

	public String getAiId() {
		return aiId;
	}

	public void setAiId(String aiId) {
		this.aiId = aiId;
	}

	public String getAiCode() {
		return aiCode;
	}

	public void setAiCode(String aiCode) {
		this.aiCode = aiCode;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getOpenSource() {
		return openSource;
	}

	public void setOpenSource(String openSource) {
		this.openSource = openSource;
	}

	public Integer getIsScan() {
		return isScan;
	}

	public void setIsScan(Integer isScan) {
		this.isScan = isScan;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getOpenDoorKey() {
		return openDoorKey;
	}

	public void setOpenDoorKey(String openDoorKey) {
		this.openDoorKey = openDoorKey;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public String toString() {
		return "DeviceVo{" +
				"merchantCode='" + merchantCode + '\'' +
				", deviceId='" + deviceId + '\'' +
				", deviceCode='" + deviceCode + '\'' +
				", openSource='" + openSource + '\'' +
				", openDoorKey='" + openDoorKey + '\'' +
				", isScan=" + isScan +
				", aiId='" + aiId + '\'' +
				", aiCode='" + aiCode + '\'' +
				", errorCode='" + errorCode + '\'' +
				'}';
	}
}
