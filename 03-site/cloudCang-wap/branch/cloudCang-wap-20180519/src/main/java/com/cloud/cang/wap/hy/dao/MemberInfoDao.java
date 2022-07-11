package com.cloud.cang.wap.hy.dao;

import com.cloud.cang.wap.hy.vo.MemberInfoDomain;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.MemberInfo;

import java.util.List;
import java.util.Map;


/** 会员用户表(HY_MEMBER_INFO) **/
public interface MemberInfoDao extends GenericDao<MemberInfo, String> {
	/**
	 * 根据会员ID 查找会员信息
	 * @param id
	 */
	MemberInfoDomain selectMemberInfoDomainById(String id);
	
	MemberInfo selectMemberInfoByLoginName(String loginName);
	/**
	 * 获取用户信息  
	 * @param mobile 手机号
	 */
	MemberInfo selectByMobile(String mobile);
	/**
	 * 获取用户信息  
	 * @param recommedCode 会员的广荐代码
	 */
	List<MemberInfo> selectMemberInfoByRecommedCode(String recommedCode);

	/**
	 * 判断会员是否存在 支付宝 或 微信
	 * @param map mobileNumber 手机号 bindType 绑定类型  10 微信 20 支付宝
	 */
    MemberInfo selectMemberInfoByBindType(Map<String, Object> map);
	/**
	 * 获取会员信息
	 * @param recommonCode 会员的推荐码
	 */
    MemberInfo selectMemberInfoByRecommonCode(String recommonCode);
}