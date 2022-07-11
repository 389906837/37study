package com.cloud.cang.core.utils;


import com.cloud.cang.core.sys.ext.CodeGeneratorProxy;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.utils.MD5;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.utils.secret.DefaultSeedAES;


/**
 * 业务组件工具类
 * 
 * @author zhouhong
 * @version 2.0
 *
 */
public class CoreUtils {

	/**
	 * 编号生成服务
	 */
	private static CodeGeneratorProxy codeGeneratorProxy = null;
	

	/**
	 * 获取编号生成服务
	 * 
	 * @return
	 */
	private static CodeGeneratorProxy getCodeGeneratorProxy() {
		if (codeGeneratorProxy == null) {
		    codeGeneratorProxy = SpringContext
					.getBean(CodeGeneratorProxy.class);
		}
		return codeGeneratorProxy;
	}

	

	/**
	 * 生成业务编号
	 * 
	 * @param codeType
	 * @return
	 */
	public static String newCode(String codeType) {

		return getCodeGeneratorProxy().selectCode(codeType);

	}
	
	/**
	 * 创建登录用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	public static String buildEncriptLoginInfo(String username,String password) {
		String content = DefaultSeedAES.encryptByKey(username) +"||"+ DefaultSeedAES.encryptByKey(password);
		String md5 = MD5.encode(content);
		return md5+"~"+content;
	}


	/**
	 * 生成设备地址
	 * @param deviceInfo 设备信息
	 * @return
	 */
	public static String generateDeviceAddress(DeviceInfo deviceInfo) {
		StringBuffer sb = new StringBuffer();
		if (StringUtil.isNotBlank(deviceInfo.getSprovinceName())) {
			sb.append(deviceInfo.getSprovinceName());
		}
		if (StringUtil.isNotBlank(deviceInfo.getScityName())) {
			sb.append(deviceInfo.getScityName());
		}
		if (StringUtil.isNotBlank(deviceInfo.getSareaName())) {
			sb.append(deviceInfo.getSareaName());
		}
		if (StringUtil.isNotBlank(deviceInfo.getSputAddress())) {
			sb.append(deviceInfo.getSputAddress());
		}
		return sb.toString();
	}
	


}
