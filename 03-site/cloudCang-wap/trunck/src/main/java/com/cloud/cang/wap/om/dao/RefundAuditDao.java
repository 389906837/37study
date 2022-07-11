package com.cloud.cang.wap.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.om.RefundAudit;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.github.pagehelper.Page;

/** 商品订单退款审核记录信息表(OM_REFUND_AUDIT) **/
public interface RefundAuditDao extends GenericDao<RefundAudit, String> {
    /**
     * 获取退款订单数量
     * @return
     */
    Long selectRefundNum(String memberId);
    /***
     * 分页查询我的退款订单数据
     * @param map 查询参数
     */
    Page<OrderDomian> selectOrderListByPage(Map<String, Object> map);
    /**
     * 获取退款审核订单信息
     * @param refundCode 退款订单编号
     * @return
     */
    RefundAudit selectByCode(String refundCode);
}