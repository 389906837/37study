package com.cloud.cang.tec.om.service.impl;

import com.cloud.cang.tec.om.vo.ScanningOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.tec.om.dao.OrderRecordDao;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.tec.om.service.OrderRecordService;

import java.util.HashMap;
import java.util.Map;

@Service
public class OrderRecordServiceImpl extends GenericServiceImpl<OrderRecord, String> implements
        OrderRecordService {

    @Autowired
    OrderRecordDao orderRecordDao;


    @Override
    public GenericDao<OrderRecord, String> getDao() {
        return orderRecordDao;
    }


    /**
     * 扫描订单
     *
     * @param merchantId 商户ID
     * @return ScanningOrderVo
     */
    @Override
    public ScanningOrderVo scanningOrder(String merchantId) {
        return orderRecordDao.scanningOrder(merchantId);
    }

    /**
     * 查询昨日订单完成数
     *
     * @param merchantId 商户ID
     * @return int
     */
    @Override
    public Map selectOrderNum(String merchantId, Integer ipayType) {
        Map map = new HashMap();
        map.put("ipayType", ipayType);
        map.put("merchantId", merchantId);
        return orderRecordDao.selectOrderNum(map);
    }

    /**
     * 查询昨日商品销售数
     *
     * @param merchantId 商户ID
     * @return int
     */
    @Override
    public int selectYesterdaySalesNum(String merchantId) {
        return orderRecordDao.selectYesterdaySalesNum(merchantId);
    }

    /**
     * 查询昨日订单
     *
     * @param merchantId
     * @param ipayType
     * @return map  <订单编号,订单详细>
     */
    @Override
    public Map selectOrderMap(String merchantId, Integer ipayType) {
        Map map = new HashMap();
        map.put("ipayType", ipayType);
        map.put("merchantId", merchantId);
        return orderRecordDao.selectOrderMap(map);
    }

    /**
     * 查询订单
     *
     * @param merchantId 商户Id
     * @param orderCode  订单编号
     * @return OrderRecord
     */
    @Override
    public OrderRecord selectByOrderCode(String merchantId, String orderCode) {
        return orderRecordDao.selectByOrderCode(merchantId, orderCode);
    }


}