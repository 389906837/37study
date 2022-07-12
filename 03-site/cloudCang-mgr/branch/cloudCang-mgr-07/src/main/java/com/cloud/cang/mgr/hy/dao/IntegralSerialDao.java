package com.cloud.cang.mgr.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.hy.vo.IntegralSerialVo;
import com.cloud.cang.model.hy.IntegralSerial;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 会员积分明细(HY_INTEGRAL_SERIAL) **/
public interface IntegralSerialDao extends GenericDao<IntegralSerial, String> {

    Page<IntegralSerial> selectSerialDetailById(IntegralSerialVo integralSerialVo);

    Map<String,String> selectIntegralTotal(IntegralSerialVo integralSerialVo);

    List<IntegralSerial> selectMember(String sid);
}