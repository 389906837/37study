package com.cloud.cang.wap.om.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.om.RefundAudit;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.wap.om.vo.OrderDomian;
import com.cloud.cang.wap.om.vo.RefundOrderVo;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RefundAuditService extends GenericService<RefundAudit, String> {

    /**
     * 获取退款订单数量
     * @return
     */
    Long selectRefundNum(String memberId);
    /***
     * 分页查询我的退款订单数据
     * @param page 分页参数
     * @param map 查询参数
     */
    Page<OrderDomian> selectOrderListByPage(Page<OrderDomian> page, Map<String, Object> map);

    /**
     * 获取退款审核订单信息
     * @param refundCode 退款订单编号
     * @return
     */
    RefundAudit selectByCode(String refundCode);

    /**
     * 生成退款订单
     * @param refundOrderVo 退款订单参数
     * @param request
     * @return
     */
    ResponseVo<String> generateRefundOrder(RefundOrderVo refundOrderVo, HttpServletRequest request) throws Exception;
}