package com.cloud.cang.wap.ac.service;

import com.cloud.cang.model.ac.DiscountRecord;
import com.cloud.cang.generic.GenericService;

import java.util.Map;

public interface DiscountRecordService extends GenericService<DiscountRecord, String> {


    /**
     * 获取订单优惠信息
     * @param params 查询参数
     */
    DiscountRecord selectDiscountRecordByMap(Map<String, Object> params);
}