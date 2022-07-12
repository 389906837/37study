package com.cloud.cang.mgr.om.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.domain.RefundAuditDomain;
import com.cloud.cang.mgr.om.vo.RefunAuditVo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.om.RefundAudit;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface RefundAuditService extends GenericService<RefundAudit, String> {

    Page<RefunAuditVo> selectPageByDomainWhere(Page<RefunAuditVo> page, RefundAuditDomain refundAuditDomain);

    /**
     * 审核订单退款
     *
     * @param refundAudit
     * @return
     */
    ResponseVo refund(RefundAudit refundAudit, HttpServletRequest request);

    /**
     * 审核订单
     *
     * @param refundAuditDomain
     * @return
     */
    ResponseVo audit(RefundAuditDomain refundAuditDomain, HttpServletRequest request) throws Exception;

    /**
     * 导出EXC
     *
     * @param queryCondition
     * @return
     */
    List<Map<String, Object>> selectDownloadExcelData(String queryCondition);

    /**
     * 退款申请列表页脚总合计
     *
     * @param refundAuditDomain
     * @return BigDecimal
     */
    BigDecimal queryTotalData(RefundAuditDomain refundAuditDomain);


}