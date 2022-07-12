package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.hy.domain.MemberInfoDomain;
import com.cloud.cang.mgr.hy.vo.MemberInfoVo;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.SignResult;
import com.cloud.cang.pay.WechatSignResult;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MemberInfoService extends GenericService<MemberInfo, String> {

    /**
     * 会员用户表列表数据
     */
    Page<MemberInfoDomain> selectAllMemberInfo(Page<MemberInfoDomain> page, MemberInfoVo memberInfoVo);

    /**
     * 批量发券全部用户
     */
    List<MemberInfo> selectByWhere(MemberInfoVo memberInfoVo);

    /**
     * 根据ID数据删除会员用户表
     */
    void delete(String[] checkboxId);

    /**
     * 重置密码
     *
     * @param sid
     */
    void resetPassword(String sid);

    /**
     * 根据手机号查找用户
     *
     * @param mobile
     * @return
     */
    MemberInfo selectByMobile(String mobile, String merchantCode);

    /**
     * 根据查询条件获取导出用户信息
     *
     * @param memberInfoVo
     * @return
     */
    List<Map<String, Object>> selectMemberInfoByExport(MemberInfoVo memberInfoVo);

    /**
     * 用户支付宝免密支付补处理
     *
     * @param
     * @return ResponseVo
     */
    ResponseVo rechargeAgainUpData(SignResult signResult, HttpServletRequest request, String merchantCode) throws Exception;

    /**
     * 用户微信免密支付补处理
     *
     * @param
     * @return ResponseVo
     */
    ResponseVo WechantRechargeAgainUpData(WechatSignResult wechatSignResult, HttpServletRequest request, String merchantCode) throws Exception;

    /**
     * 获取默认商户微信配置数据
     *
     * @return 商户微信配置数据
     * @throws Exception
     */
    MerchantConf getDefaultWechatMerchantConf() throws Exception;


    /**
     * 根据条件查询手机号
     *
     * @param memberInfoVo
     * @return
     */
    String selectMemberInfoByParam(MemberInfoVo memberInfoVo);


    /**
     * 支付宝手动解约
     * @param memberInfo
     * @return
     */
    ResponseVo manualTermination(MemberInfo memberInfo,HttpServletRequest request) throws  Exception;
}