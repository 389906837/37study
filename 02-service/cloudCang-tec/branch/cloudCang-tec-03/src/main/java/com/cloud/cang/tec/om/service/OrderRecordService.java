package com.cloud.cang.tec.om.service;

import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.tec.om.vo.ScanningOrderVo;

import java.util.Map;

public interface OrderRecordService extends GenericService<OrderRecord, String> {
    /**
     * 扫描订单
     *
     * @param merchantId 商户ID
     * @return ScanningOrderVo
     */
    ScanningOrderVo scanningOrder(String merchantId);

    /**
     * 查询昨日订单完成数
     *
     * @param merchantId 商户ID
     * @return Map
     */
    Map selectOrderNum(String merchantId, Integer ipayType);

    /**
     * 查询昨日销售数
     *
     * @param merchantId 商户ID
     * @return int
     */
    int selectYesterdaySalesNum(String merchantId);

    /**
     * 查询昨日订单
     *
     * @param merchantId
     * @param ipayType
     * @return map  <订单编号,订单详细>
     */
    Map selectOrderMap(String merchantId, Integer ipayType);

    /**
     * 查询订单
     *
     * @param merchantId 商户Id
     * @param orderCode  订单编号
     * @return OrderRecord
     */
    OrderRecord selectByOrderCode(String merchantId, String orderCode);


}