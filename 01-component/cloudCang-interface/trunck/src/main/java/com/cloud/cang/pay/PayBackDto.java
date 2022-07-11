package com.cloud.cang.pay;


import com.cloud.cang.common.SuperDto;

/**
 * 支付申请返回参数
 * @author zhouhong
 *
 */
public class PayBackDto extends SuperDto {

	private static final long serialVersionUID = 1L;

	private String orderId;//订单Id
	private JsAPIConfig jsApi;//公众号支付API
	private String mwebUrl;//H5支付跳转链接
	/* 支付类型中支付方式 10：公众号支付 20：H5支付 30：扫码支付  40：APP支付 50：刷卡支付 60：小程序支付 70:代扣*/
	private Integer ipayWay;
	/* 支付类型 10:微信 20:支付宝 30:银联 40:快捷支付 50:余额 60:其他 */
	private Integer ipayType;

	private String form;//支付宝wap付款申请返回参数


	public JsAPIConfig getJsApi() {
		return jsApi;
	}

	public void setJsApi(JsAPIConfig jsApi) {
		this.jsApi = jsApi;
	}

	public String getMwebUrl() {
		return mwebUrl;
	}

	public void setMwebUrl(String mwebUrl) {
		this.mwebUrl = mwebUrl;
	}

	public Integer getIpayWay() {
		return ipayWay;
	}

	public void setIpayWay(Integer ipayWay) {
		this.ipayWay = ipayWay;
	}

	public Integer getIpayType() {
		return ipayType;
	}

	public void setIpayType(Integer ipayType) {
		this.ipayType = ipayType;
	}


	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "PayBackDto{" +
				"orderId='" + orderId + '\'' +
				", jsApi=" + jsApi +
				", mwebUrl='" + mwebUrl + '\'' +
				", ipayWay=" + ipayWay +
				", ipayType=" + ipayType +
				", form='" + form + '\'' +
				'}';
	}
}