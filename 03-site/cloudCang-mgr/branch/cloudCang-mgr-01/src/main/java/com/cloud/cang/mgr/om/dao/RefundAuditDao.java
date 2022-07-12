package com.cloud.cang.mgr.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.domain.RefundAuditDomain;
import com.cloud.cang.mgr.om.vo.RefunAuditVo;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.om.RefundAudit;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 商品订单退款审核记录信息表(OM_REFUND_AUDIT)
 **/
public interface RefundAuditDao extends GenericDao<RefundAudit, String> {

    Page<RefunAuditVo> selectPageByDomainWhere(RefundAuditDomain refundAuditDomain);

    /**
     * 导出EXC
     *
     * @param queryCondition
     * @return
     */
    List<Map<String, Object>> selectDownloadExcelData(@Param("queryCondition") String queryCondition);

    /**
     * 退款申请列表页脚总合计
     *
     * @param refundAuditDomain
     * @return BigDecimal
     */
    BigDecimal queryTotalData(RefundAuditDomain refundAuditDomain);
}