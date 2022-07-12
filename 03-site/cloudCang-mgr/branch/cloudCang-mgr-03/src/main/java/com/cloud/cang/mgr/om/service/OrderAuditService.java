package com.cloud.cang.mgr.om.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.om.domain.OrderAuditDomain;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.vo.DownloadOrderAuditVo;
import com.cloud.cang.mgr.om.vo.OrderAuditVo;
import com.cloud.cang.mgr.om.vo.OrderRecordVo;
import com.cloud.cang.model.om.OrderAudit;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.om.OrderRecord;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

public interface OrderAuditService extends GenericService<OrderAudit, String> {

    Page<OrderAuditVo> selectPageByDomainWhere(Page<OrderAuditVo> page, OrderAuditDomain orderAuditDomain);

    /**
     * 异常订单退款
     *
     * @param checkboxId 异常订单ID
     * @return ResponseVo
     */
    ResponseVo payOrder(String checkboxId);

    /**
     * 查询异常订单列表
     *
     * @param orderAuditDomain
     * @return List<OrderAuditVo>
     */
    List<DownloadOrderAuditVo> selectByDomain(OrderAuditDomain orderAuditDomain);

    /**
     * 异常订单页脚总和
     *
     * @param orderAuditDomain
     * @return BigDecimal
     */
    BigDecimal queryTotalData(OrderAuditDomain orderAuditDomain);

    /**
     * 编辑保存审核订单
     *
     * @param orderAudit
     * @param request
     * @param commodityIds
     * @return
     * @throws UnsupportedEncodingException
     */
    ResponseVo saveOrderAudit(OrderAudit orderAudit, HttpServletRequest request, String commodityIds) throws Exception;

}