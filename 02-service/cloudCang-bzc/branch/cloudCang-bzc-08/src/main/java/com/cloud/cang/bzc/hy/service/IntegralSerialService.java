package com.cloud.cang.bzc.hy.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.jm.ChangeIntegralDto;
import com.cloud.cang.model.hy.IntegralSerial;
import com.cloud.cang.generic.GenericService;

public interface IntegralSerialService extends GenericService<IntegralSerial, String> {

    ResponseVo changeIntegralSeria (ChangeIntegralDto changeIntegralDto);
}