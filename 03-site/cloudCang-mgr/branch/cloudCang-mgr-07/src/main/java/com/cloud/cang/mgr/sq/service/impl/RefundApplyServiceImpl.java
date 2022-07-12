package com.cloud.cang.mgr.sq.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.cloud.cang.mgr.sq.domain.RefundApplyDomain;
import com.cloud.cang.mgr.sq.vo.RefundApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sq.dao.RefundApplyDao;
import com.cloud.cang.model.sq.RefundApply;
import com.cloud.cang.mgr.sq.service.RefundApplyService;

@Service
public class RefundApplyServiceImpl extends GenericServiceImpl<RefundApply, String> implements
        RefundApplyService {

    @Autowired
    RefundApplyDao refundApplyDao;


    @Override
    public GenericDao<RefundApply, String> getDao() {
        return refundApplyDao;
    }


    @Override
    public Page<RefundApplyVo> selectPageByDomainWhere(Page<RefundApplyVo> page, RefundApplyDomain refundApplyDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return refundApplyDao.selectByDomainWhere(refundApplyDomain);
    }

    /**
     * 根据订单ID等数据查看申请表
     *
     * @param refundApply
     * @return
     */
    @Override
    public RefundApply selectByOrderId(RefundApply refundApply) {
        return refundApplyDao.selectByOrderId(refundApply);
    }

    /**
     * 订单列表页脚总统计
     *
     * @param refundApplyDomain
     * @return BigDecimal
     */
    @Override
    public BigDecimal queryTotalData(RefundApplyDomain refundApplyDomain) {
        return refundApplyDao.queryTotalData(refundApplyDomain);
    }

}