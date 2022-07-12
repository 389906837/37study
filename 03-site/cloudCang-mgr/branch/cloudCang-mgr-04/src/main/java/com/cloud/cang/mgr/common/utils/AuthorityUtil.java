package com.cloud.cang.mgr.common.utils;

import java.util.List;
import java.util.Map;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;

/**
 *  权限
 * @author zhouhong
 * @version 1.0
 */
public class AuthorityUtil {
	
	/**
	 * 根据权限码，是否显示手机号或用星号显示
	 * @param listMap
	 * @param permissionCode
	 */
	public static void permissionsMobile(List<Map<String,String>> listMap,String permissionCode){
		String mobileColumsName="SMOBILE";
		if(null!=listMap && listMap.size()>0){
			if(!SecurityUtils.getSubject().isPermitted(permissionCode)){
				for(Map<String,String> map:listMap){
					if(StringUtils.isNoneBlank(map.get(mobileColumsName))){//没有权限
							map.put(mobileColumsName, formatMobile(map.get(mobileColumsName)));
					}
				}
			}
		}
	}

	/**
	 * 根据权限码，是否显示手机号或用星号显示
	 * @param listMap
	 * @param permissionCode
	 */
	public static void permissionsMobile(List<Map<String,String>> listMap,String permissionCode,String mobileColumsName){
		if(null!=listMap && listMap.size()>0){
			if(!SecurityUtils.getSubject().isPermitted(permissionCode)){
				for(Map<String,String> map:listMap){
					if(StringUtils.isNoneBlank(map.get(mobileColumsName))){
						map.put(mobileColumsName, formatMobile(map.get(mobileColumsName)));
					}
				}
			}
		}
		if(null!=listMap && listMap.size()>0){
			for(Map<String,String> map:listMap){

			}
		}
	}
	
	
	/**
	 *  格式化手机号
	 * @param smobile
	 * @return
	 */
	private static String formatMobile(String smobile){
		if(smobile==null || smobile.length()<11)return smobile;
        String reStr = smobile.substring(smobile.length() - 4, smobile.length());
        String preStr = smobile.substring(0, smobile.length() - 8);
        StringBuilder sb = new StringBuilder();
        sb.append(preStr).append("****").append(reStr);
        return sb.toString();
	}
	
}
