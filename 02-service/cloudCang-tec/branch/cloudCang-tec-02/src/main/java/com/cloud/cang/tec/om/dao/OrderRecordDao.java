package com.cloud.cang.tec.om.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.tec.om.vo.ScanningOrderVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品订单记录信息表(OM_ORDER_RECORD)
 **/
public interface OrderRecordDao extends GenericDao<OrderRecord, String> {


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
     * @param map {merchantId:商户ID ipayType:支付类型}
     * @return Map
     */
    Map selectOrderNum(Map map);

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
     * @param map
     * @return map  <订单编号,订单详细>
     */
    @MapKey("sorderCode")
    Map<String, OrderRecord> selectOrderMap(Map map);

    /**
     * 查询订单
     *
     * @param merchantId 商户Id
     * @param orderCode  订单编号
     * @return OrderRecord
     */
    OrderRecord selectByOrderCode(@Param("merchantId") String merchantId, @Param("orderCode") String orderCode);
}