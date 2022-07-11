package com.cloud.cang.bzc.hy.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.cloud.cang.bzc.hy.dao.IntegralAccountDao;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.jm.ChangeIntegralDto;
import com.cloud.cang.model.hy.IntegralAccount;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.bzc.hy.dao.IntegralSerialDao;
import com.cloud.cang.model.hy.IntegralSerial;
import com.cloud.cang.bzc.hy.service.IntegralSerialService;

@Service
public class IntegralSerialServiceImpl extends GenericServiceImpl<IntegralSerial, String> implements
        IntegralSerialService {
    private static Logger LOGGER = LoggerFactory.getLogger(IntegralSerialServiceImpl.class);

    @Autowired
    IntegralSerialDao integralSerialDao;

    @Autowired
    IntegralAccountDao integralAccountDao;
    @Override
    public GenericDao<IntegralSerial, String> getDao() {
        return integralSerialDao;
    }

    @Override
    public ResponseVo changeIntegralSeria(ChangeIntegralDto changeIntegralDto) {
        LOGGER.info("更新会员积分从表开始.....");
        IntegralSerial integralSerial=new IntegralSerial();
        IntegralAccount integralAccount = integralAccountDao.selectBySemerId(changeIntegralDto.getSmemberId());

        try {
            BeanUtils.copyProperties(integralSerial, changeIntegralDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.error("integralSerial对象转换失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("integralSerial对象转换失败！");
        }
        integralSerial.toString();
        integralSerial.setFbalanceValue(integralAccount.getItotalPoints());//账户积分余额
        integralSerialDao.insertSelective(integralSerial);
    return  ResponseVo.getSuccessResponse();
    }


}