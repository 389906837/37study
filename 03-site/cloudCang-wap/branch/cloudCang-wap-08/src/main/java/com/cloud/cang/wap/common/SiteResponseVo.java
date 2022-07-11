package com.cloud.cang.wap.common;


import com.cloud.cang.generic.GenericEntity;

/**
 * @Description: h5页面返回实体
 * @Author: zhouhong
 * @Date: 2016年4月22日 上午11:31:59
 * @version 1.0
 */
public class SiteResponseVo extends GenericEntity {
	
	private static final long serialVersionUID = 1L;
	
	private boolean isSuccess;//是否成功
	private int code;//代码
	private String msg;//返回信息
	private String targetUrl;//跳转路径
	private String targetWay;//跳转方式
	private int type;//页面类型 -1 错误页面 1 投资 2 充值 3 开通存管4提现 5债权认购 6预约项目预约 7预约项目投资 8代扣签约9代扣解签10公益捐助11积分商城12资讯文章13正常还款14提前还款
	
	private Object data;
	
	public SiteResponseVo() {
		super();
	}
	
	public SiteResponseVo(boolean isSuccess, int code, String msg, int type, Object data) {
		super();
		this.isSuccess = isSuccess;
		this.code = code;
		this.msg = msg;
		this.type = type;
		this.data = data;
	}
	public SiteResponseVo(boolean isSuccess, int code, String msg, int type) {
		super();
		this.isSuccess = isSuccess;
		this.code = code;
		this.msg = msg;
		this.type = type;
	}
	public SiteResponseVo(boolean isSuccess, int code, String msg,
			String targetUrl, String targetWay, int type, Object data) {
		super();
		this.isSuccess = isSuccess;
		this.code = code;
		this.msg = msg;
		this.targetUrl = targetUrl;
		this.targetWay = targetWay;
		this.type = type;
		this.data = data;
	}
	
	
	
	
	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getTargetUrl() {
		return targetUrl;
	}
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}
	public String getTargetWay() {
		return targetWay;
	}
	public void setTargetWay(String targetWay) {
		this.targetWay = targetWay;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	

		
	
}
