package com.cloud.cang.wap.hy.service;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.wap.hy.vo.MemberInfoDomain;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.wap.hy.vo.RegisterVo;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface MemberInfoService extends GenericService<MemberInfo, String> {

	/**
	 * 获取用户信息
	 * @param mobile 手机号
	 * @param merchantCode 商户编号
	 * @return
	 */
	MemberInfo selectByMobile(String mobile, String merchantCode);

	/**
	 * 获取用户信息
	 * @param recommedCode 会员的广荐代码
	 * @param merchantCode 商户编号
	 * @return
	 */
	List<MemberInfo> selectMemberInfoByRecommedCode(String recommedCode, String merchantCode);
 
	/**
	 * 根据会员ID 查找会员信息
	 * @param memberId 会员ID
	 * @return
	 */
	MemberInfoDomain selectMemberInfoDomainById(String memberId);
	/**
	 * 根据会员登录名称 查找会员信息
	 * @param loginName 会员登录名称
	 * @return
	 */
	MemberInfo selectMemberInfoByLoginName(String loginName, String merchantCode);

	/**
	 * 注册平台会员
	 * @param regVo 注册Vo
	 * @param isPwd 密码是否在自动生成
	 * @return
	 */
	MemberInfo registerMember(RegisterVo regVo, HttpServletRequest request, boolean isPwd) throws ServiceException, Exception;

	/**
	 * 处理支付宝签约成功方法
	 * @param merchantCode 商户编号
	 * @param map 返回参数 详见文档
	 * @param conf 商户配置信息
	 * @return
	 * @throws Exception
	 */
    boolean dealwithAlipaySign(String merchantCode, Map<String, String> map, MerchantConf conf) throws Exception;

	/**
	 * 处理支付宝免密解约
	 * @throws Exception
	 */
	boolean dealwithAlipayUnsign() throws Exception;

	/**
	 * 判断会员是否存在 支付宝 或 微信
	 * @param mobileNumber 手机号
	 * @param bindType 绑定类型  10 微信 20 支付宝
	 * @param merchantCode 商户编号
     * @return
	 */
    MemberInfo selectMemberInfoByBindType(String mobileNumber, Integer bindType, String merchantCode);

	/**
	 * 注册活动
	 * @param registerMember 会员信息
	 * @param recommendMember 推荐会员信息
	 */
	void recommendRegisterGive(MemberInfo registerMember, MemberInfo recommendMember);

	/**
	 * 获取会员信息
	 * @param recommonCode 会员的推荐码
	 */
	MemberInfo selectMemberInfoByRecommonCode(String recommonCode, String merchantCode);
}