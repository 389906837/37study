package com.cloud.cang.api.hy.service.impl;

import com.cloud.cang.api.hy.dao.MemberInfoDao;
import com.cloud.cang.api.hy.service.VendstopService;
import com.cloud.cang.api.sb.dao.DeviceInfoDao;
import com.cloud.cang.api.sh.dao.MerchantInfoDao;
import com.cloud.cang.api.socketIo.vo.SessionVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.hy.MemberInfoDto;
import com.cloud.cang.hy.MemberServicesDefine;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: 37cang
 * @description:
 * @author: qzg
 * @create: 2019-08-09 17:26
 **/
@Service
public class VendstopServiceImpl implements VendstopService {
    @Autowired
    MerchantInfoDao merchantInfoDao;
    @Autowired
    DeviceInfoDao deviceInfoDao;
    @Autowired
    MemberInfoDao memberInfoDao;

    @Override
    public MemberInfo registUser(SessionVo sessionVo){
        return this.saveMember(sessionVo);
    }

    /**
     * 保存用户表信息（*在此处仅保存‘购物会员’的数据 *）
     * @param sessionVo
     * @return
     * @throws Exception
     */
    private MemberInfo saveMember(SessionVo sessionVo){
        DeviceInfo deviceInfo = deviceInfoDao.selectByPrimaryKey(sessionVo.getDeviceId());
        //判断用是否存在
        MemberInfo memberInfo = getMember(sessionVo.getPhoneNumber(),deviceInfo.getSmerchantCode());
        if(memberInfo !=null){
            return memberInfo;
        }
        MemberInfoDto memberDto = new MemberInfoDto();
        memberDto.setThirdUserId(sessionVo.getThirdUserId());
        memberDto.setNikeName(sessionVo.getUserName());
        memberDto.setSmobile(sessionVo.getPhoneNumber());
        memberDto.setSloginPassword(DigestUtils.sha1Hex("37cang"));
        memberDto.setImemberType(BizTypeDefinitionEnum.MemberType.M1_MEMBER.getCode());
        memberDto.setSuserIp(sessionVo.getSip());
        memberDto.setIsourceClientType(BizTypeDefinitionEnum.RegClientType.VENDSTOP.getCode());
        memberDto.setSsourcetype("");
        if(deviceInfo !=null){
            memberDto.setDeviceCode(deviceInfo.getScode());
            memberDto.setDeviceAddress(CoreUtils.generateDeviceAddress(deviceInfo));
            memberDto.setSmerchantId(deviceInfo.getSmerchantId());
            memberDto.setSmerchantCode(deviceInfo.getSmerchantCode());
        }
        try {
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(MemberServicesDefine.MEMBER_REGISTER);
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<MemberInfo>>() { });
            invoke.setRequestObj(memberDto);
            ResponseVo<MemberInfo> resMember = (ResponseVo<MemberInfo>) invoke.invoke();
            if(resMember!=null && resMember.isSuccess()){
                return resMember.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 用户是否存在
     * @param mobile
     * @param merchantCode
     * @return
     */
    private MemberInfo getMember(String mobile,String merchantCode){
        MemberInfo entity = new MemberInfo();
        entity.setSmobile(mobile);
        entity.setSmerchantCode(merchantCode);
        entity.setIdelFlag(0);
        entity.setImemberType(BizTypeDefinitionEnum.MemberType.M1_MEMBER.getCode());
        List<MemberInfo> memberInfos = memberInfoDao.selectByEntityWhere(entity);
        if(memberInfos!=null && memberInfos.size() >0){
            return memberInfos.get(0);
        }
        return null;
    }
}
