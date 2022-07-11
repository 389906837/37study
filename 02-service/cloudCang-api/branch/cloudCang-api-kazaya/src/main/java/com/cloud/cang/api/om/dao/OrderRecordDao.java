package com.cloud.cang.api.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.api.om.vo.OrderDomian;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.OrderRecord;
import com.github.pagehelper.Page;

/**
 * 商品订单记录信息表(OM_ORDER_RECORD)
 **/
public interface OrderRecordDao extends GenericDao<OrderRecord, String> {

    /**
     * 查询用户的异常订单
     *
     * @param memberId 会员ID
     * @return
     */
    List<OrderRecord> selectExceptionOrder(String memberId);


    /***
     * 分页查询我的订单数据
     * @param map 查询参数
     */
    Page<OrderDomian> selectOrderListByPage(Map<String, Object> map);
}