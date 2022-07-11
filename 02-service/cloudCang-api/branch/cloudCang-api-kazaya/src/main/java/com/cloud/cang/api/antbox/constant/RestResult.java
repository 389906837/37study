package com.cloud.cang.api.antbox.constant;

/**
 * Created by oyhk on 14-6-28.
 *
 */
public class RestResult<T> {

	public static final String CD3[] = { "3", "温馨提示:" };
	public static final String CD2[] = { "2", "参数错误:" };
	public static final String CD1[] = { "1", "成功" };
	public static final String CD0[] = { "0", "失败" };

	public static final String CD101[] = { "101", "用户名不存在" };
	public static final String CD102[] = { "102", "用户名状态异常" };
	public static final String CD103[] = { "103", "密码错误" };
	public static final String CD104[] = { "104", "access_token 不能为空" };
	public static final String CD105[] = { "105", "access_token 无效" };
	public static final String CD106[] = { "106", "用户名不能为空" };
	public static final String CD107[] = { "107", "您没有登录权限" };
	public static final String CD108[] = { "108", "domain 不能为空" };
	public static final String CD109[] = { "109", "用户会话已过期" };
	public static final String CD110[] = { "110", "账户被禁用，请联系管理员" };
	public static final String CD111[] = { "111", "您没有权限" };
	public static final String CD112[] = { "112", "域名不存在" };

	public static final String CD200[] = { "200", "产品验证失败" };
	public static final String[] CD1301 = {"1301", "其他用户已经绑定此餐盘，请换一个餐盘吧！"};
	public static final String[] CD1302 = {"1302", "您已经绑定此餐盘，可以愉快享受美食了。"};
	public RestResult() {
	}

	public RestResult(T data) {
		this.data = data;
	}
	public RestResult(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	public RestResult(String []resultDef) {
		if (resultDef == null || resultDef.length != 2) throw new NullPointerException();
		this.code = resultDef[0];
		this.desc = resultDef[1];
	}
	
	public RestResult(String code, String desc, T data) {
		this.code = code;
		this.desc = desc;
		this.data = data;
	}

	public String code = RestResult.CD1[0];
	public String desc = RestResult.CD1[1];
	public T data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RestResult<T> custom(String code, String desc) {
		this.code = code;
		this.desc = desc;
		return this;
	}

	@Override
	public String toString() {
		return "RestResult{" + "code='" + code + '\'' + ", desc='" + desc + '\'' + ", data=" + data + '}';
	}
}
