package com.cloud.cang.bzc.hy.service.impl;

import com.cloud.cang.act.CouponQueryDto;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.jm.ChangeIntegralDto;
import com.cloud.cang.model.ac.CouponUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.bzc.hy.dao.IntegralAccountDao;
import com.cloud.cang.model.hy.IntegralAccount;
import com.cloud.cang.bzc.hy.service.IntegralAccountService;

@Service
public class IntegralAccountServiceImpl extends GenericServiceImpl<IntegralAccount, String> implements
        IntegralAccountService {

    private static Logger LOGGER = LoggerFactory.getLogger(IntegralAccountServiceImpl.class);
    @Autowired
    IntegralAccountDao integralAccountDao;


    @Override
    public GenericDao<IntegralAccount, String> getDao() {
        return integralAccountDao;
    }


    @Override
    public ResponseVo changeIntegralAccoun(ChangeIntegralDto changeIntegralDto) {

        LOGGER.info("更新会员积分主表开始.....");
        //根据首次活动判断用户是否有积分主表记录
        IntegralAccount integralAccount = integralAccountDao.selectBySemerId(changeIntegralDto.getSmemberId());
        if (null == integralAccount) {
            LOGGER.info("该用户没有积分记录表！" );
            return ErrorCodeEnum.ERROR_PAC_CHANGE_INTEGRAL.getResponseVo("该用户没有积分记录表!");
        }
        return  updateIntegraAccount(changeIntegralDto,integralAccount);
    }

    private  ResponseVo updateIntegraAccount(ChangeIntegralDto changeIntegralDto,IntegralAccount integralAccount){
        Integer  itotalPoints =changeIntegralDto.getFvalue()+integralAccount.getItotalPoints();//总积分
        Integer  iusablePoints =changeIntegralDto.getFvalue()+integralAccount.getIusablePoints();//可以使用积分

        integralAccount.setItotalPoints(itotalPoints);
        integralAccount.setIusablePoints(iusablePoints);
        integralAccount.setSmemberId(changeIntegralDto.getSmemberId());

        int i =integralAccountDao.updateBySmemberId(integralAccount);
       // integralAccount.iusablePoints
        if(1!=i){
            return  ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("变更积分主表异常！");
        }
        return  ResponseVo.getSuccessResponse();
    }
}

