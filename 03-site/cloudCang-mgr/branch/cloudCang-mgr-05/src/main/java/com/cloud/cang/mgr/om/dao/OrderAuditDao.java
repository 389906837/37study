package com.cloud.cang.mgr.om.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.om.domain.OrderAuditDomain;
import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.vo.DownloadOrderAuditVo;
import com.cloud.cang.mgr.om.vo.OrderAuditVo;
import com.cloud.cang.mgr.om.vo.OrderRecordVo;
import com.cloud.cang.model.om.OrderAudit;
import com.github.pagehelper.Page;

/**
 * 商品订单审核记录信息表(OM_ORDER_AUDIT)
 **/
public interface OrderAuditDao extends GenericDao<OrderAudit, String> {


    Page<OrderAuditVo> selectPageByDomainWhere(OrderAuditDomain orderAuditDomain);

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

}