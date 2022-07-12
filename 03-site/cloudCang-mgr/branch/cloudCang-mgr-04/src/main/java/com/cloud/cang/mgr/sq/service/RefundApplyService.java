package com.cloud.cang.mgr.sq.service;

import com.cloud.cang.mgr.om.domain.OrderRecordDomain;
import com.cloud.cang.mgr.om.vo.OrderRecordMoneyStaVo;
import com.cloud.cang.mgr.sq.domain.RefundApplyDomain;
import com.cloud.cang.mgr.sq.vo.RefundApplyVo;
import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.math.BigDecimal;
import java.util.Map;

public interface RefundApplyService extends GenericService<RefundApply, String> {

    Page<RefundApplyVo> selectPageByDomainWhere(Page<RefundApplyVo> page, RefundApplyDomain refundApplyDomain);

    RefundApply selectByOrderId(RefundApply refundApply);

    /**
     * 订单列表页脚总统计
     *
     * @param refundApplyDomain
     * @return BigDecimal
     */
    BigDecimal queryTotalData(RefundApplyDomain refundApplyDomain);
}