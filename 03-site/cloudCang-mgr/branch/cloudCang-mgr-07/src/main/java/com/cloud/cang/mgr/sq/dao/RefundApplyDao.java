package com.cloud.cang.mgr.sq.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sq.domain.RefundApplyDomain;
import com.cloud.cang.mgr.sq.vo.RefundApplyVo;
import com.cloud.cang.model.sq.RefundApply;
import com.github.pagehelper.Page;

/**
 * 退款申请(SQ_REFUND_APPLY)
 **/
public interface RefundApplyDao extends GenericDao<RefundApply, String> {


    /**
     * 查询退款记录
     *
     * @param refundApplyDomain
     * @return
     */
    Page<RefundApplyVo> selectByDomainWhere(RefundApplyDomain refundApplyDomain);

    RefundApply selectByOrderId(RefundApply refundApply);

    /**
     * 订单列表页脚总统计
     *
     * @param refundApplyDomain
     * @return BigDecimal
     */
    BigDecimal queryTotalData(RefundApplyDomain refundApplyDomain);
}