package com.cloud.cang.bzc.hy.service.impl;

import com.cloud.cang.bzc.hy.dao.MemberInfoDao;
import com.cloud.cang.bzc.hy.service.IntegralAccountService;
import com.cloud.cang.bzc.hy.service.MemberInfoService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.core.utils.InviteCodeUtil;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.hy.MemberConstants;
import com.cloud.cang.hy.MemberInfoDto;
import com.cloud.cang.model.hy.IntegralAccount;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * 会员注册-业务实现类
 *
 * @Author: zengzexiong
 * @Date: 2017年12月27日
 * @version 1.0
 */
@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, String> implements MemberInfoService {

    private static Logger LOGGER = LoggerFactory.getLogger(MemberInfoServiceImpl.class);


    @Autowired
    MemberInfoDao memberInfoDao;

    @Autowired
    private IntegralAccountService integralAccountService;

    @Override
    public GenericDao<MemberInfo, String> getDao() {
        return memberInfoDao;
    }

    /**
     * 会员注册
     *
     * @param memDto 会员注册信息
     * @return 注册成功返回success，否则返回false
     */
    @Override
    @Transactional
    public ResponseVo<MemberInfo> accountRegister(MemberInfoDto memDto) {
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(
                "开始创建会员账户，手机号：{}，来源客户端：{}，会员IP：{}",
                new Object[]{
                    memDto.getSmobile(),
                    memDto.getIsourceClientType(),
                    memDto.getSuserIp()
                });
        }

        //会员账户注册
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setScode(CoreUtils.newCode(MemberConstants.GenerateCode.MEMBER_CODE));//会员编号
        //商户信息
        memberInfo.setSmerchantId(memDto.getSmerchantId());
        memberInfo.setSmerchantCode(memDto.getSmerchantCode());

        memberInfo.setSmemberName(memDto.getSmobile());//会员名(唯一编号，手机号)
        memberInfo.setSmobile(memDto.getSmobile());//手机号
        memberInfo.setSnickName(memDto.getNikeName());//昵称
        if(StringUtil.isNotBlank(memDto.getHeadImg())) {//头像地址
            memberInfo.setSheadImage(memDto.getHeadImg());
        }
        memberInfo.setIsex(memDto.getIsex());//会员性别
        memberInfo.setSloginPwd(memDto.getSloginPassword());//登录密码
        memberInfo.setImemberType(memDto.getImemberType());//会员类型:10：购物会员 20：补货会员
        memberInfo.setImemberLevel(BizTypeDefinitionEnum.MemberLevel.PUBLIC.getCode());//会员等级
        memberInfo.setTregisterTime(new Date());//注册时间
        memberInfo.setIstatus(BizTypeDefinitionEnum.MemberStatus.NORMAL.getCode());//会员状态 0正常1停用2:冻结

        memberInfo.setIisOrder(0);//是否已首单 0否1是
        if(StringUtil.isNotBlank(memDto.getDeviceCode())) {
            memberInfo.setSregDeviceCode(memDto.getDeviceCode());//注册设备编号
            memberInfo.setSdeviceAddress(memDto.getDeviceAddress());//注册设备地址
        }
        memberInfo.setIregClientType(memDto.getIsourceClientType());//客户端类型：10=pc20=iphone30=android40=wap50=微信

        //来源类型名称取数据字典，会员来源类型
        if (StringUtil.isNotBlank(memDto.getSsourcetype())) {
            ParameterGroupDetail pgd = GrpParaUtil.getDetailForName(GrpParaUtil.MEMBER_SOURCE, memDto.getSsourcetype());
            if (pgd != null) {
                memberInfo.setSsourceCode(pgd.getSvalue());//参数值,来源渠道代码
                memberInfo.setSsourceType(pgd.getSname());//参数名称，来源渠道名称
                memberInfo.setSsourceTypeName(pgd.getSremark());//说明，来源渠道类型
            }
        }
        memberInfo.setIisEnableRecoaward(1);//推荐奖励是否启用
        //推荐人编号
        if (StringUtils.isNotBlank(memDto.getSrecommonMbrCode())){//推荐人编号（需要根据推荐人的推广代码转为推荐人的编号)
            String recommendUsers= memberInfoDao.selectMemberInfoByRecommedCode(memDto.getSrecommonMbrCode(),memDto.getSmerchantCode());
            if (recommendUsers != null){
                memberInfo.setSrecommonMbrCode(recommendUsers);
            }
        }
        memberInfo.setSrecommonCode(InviteCodeUtil.buildInviteCode());//自动生成邀请码
        memberInfo.setIdelFlag(0);//是否删除0否1是
        memberInfo.setSmemberRegip(memDto.getSuserIp());//注册IP
        memberInfo.setIaipayOpen(0);//支付宝免密是否开通
        memberInfo.setIwechatOpen(0);//微信支付免密是否开通
        memberInfo.setIwechatPointOpen(0);//微信支付分免密是否开通
        memberInfo.setIisReplenishment(0);//是否补货员
        memberInfo.setIisVerified(0);//是否实名认证
        memberInfo.setIisFirstTieCard(0);//是否首次绑卡
        memberInfo.setIisBackstageAdd(0);//是否后台新增
        memberInfo.setAddTime(new Date());//添加日期
        memberInfo.setUpdateTime(new Date());//修改日期

        //保存用户并添加角色
        insertAndSetRole(memberInfo);

        //会员账户创建成功，记录日志
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(
                "会员账户创建成功......，会员ID：{},会员编号:{},会员手机号:{}",
                new Object[] {
                    memberInfo.getId(),
                    memberInfo.getScode(),
                    memDto.getSmobile() });
        }

        //开通积分账户
        openIntegralAccount(memberInfo);

        return ResponseVo.getSuccessResponse(memberInfo);
    }

    /**
     * 注册时开通会员积分账户
     * @param memberInfo 会员信息
     * @return 账户信息
     */
    private IntegralAccount openIntegralAccount(MemberInfo memberInfo) {
        IntegralAccount integralAccount = new IntegralAccount();

        //记录日志
        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(
                "开始创建会员积分账户......，会员编号：{},会员姓名:{},会员Id:{}",
                new Object[] {
                    memberInfo.getScode(),
                    memberInfo.getSmemberName(),
                    memberInfo.getId() });
        }

        //开始设置会员积分账户对象属性
        integralAccount.setSaccountCode(CoreUtils.newCode("hy_integral_account"));//账户编号
        integralAccount.setIfrozenPoints(0);//冻结积分
        integralAccount.setItotalPoints(0);//总积分
        integralAccount.setIusablePoints(0);//可用积分
        integralAccount.setIusedPoints(0);//已使用积分
        integralAccount.setIversion(1);//版本号
        integralAccount.setSmemberId(memberInfo.getId());//会员ID
        integralAccount.setSmemberCode(memberInfo.getScode());//会员编号
        integralAccount.setSmemberName(memberInfo.getSmemberName());//会员用户名（手机号）
        integralAccount.setTaddTime(DateUtils.getCurrentDateTime());//添加日期
        integralAccount.setTupdateTime(DateUtils.getCurrentDateTime());//修改日期

        //保存会员积分账户
        integralAccountService.insert(integralAccount);

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info(
                "完成创建会员积分账户......，会员编号：{},会员姓名:{},会员Id:{},积分账户ID:{},积分账户编号:{}",
                new Object[] {
                    memberInfo.getScode(),
                    memberInfo.getSmemberName(),
                    memberInfo.getId(),
                    integralAccount.getId(),
                    integralAccount.getSaccountCode() });
        }

        return integralAccount;
    }

    /**
     * 保存用户并添加角色
     * @param memberInfo 注册的会员信息
     * @return 返回插入结果
     */
    private int insertAndSetRole(MemberInfo memberInfo) {
        int row=getDao().insert(memberInfo);

        //购物会员:10 ,补货会员:20
        if(memberInfo.getImemberType()==10) {
            this.memberInfoDao.addRoleForUser(memberInfo.getId());
        }
        return row;
    }


}
