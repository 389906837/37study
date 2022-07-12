package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.hy.vo.IntegralSerialVo;
import com.cloud.cang.model.hy.IntegralSerial;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface IntegralSerialService extends GenericService<IntegralSerial, String> {

    /**
     * 会员明细积分值合计
     * @param integralSerialVo
     * @return
     */
    Map<String,String> selectIntegralTotal(IntegralSerialVo integralSerialVo);

    /**
     * 会员积分明细
     * @param sid
     * @return
     */
    List<IntegralSerial> selectMember(String sid);
}