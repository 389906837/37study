package com.cloud.cang.tec.om.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.tec.om.dao.RefundAuditDao;
import com.cloud.cang.model.om.RefundAudit;
import com.cloud.cang.tec.om.service.RefundAuditService;

@Service
public class RefundAuditServiceImpl extends GenericServiceImpl<RefundAudit, String> implements
        RefundAuditService {

    @Autowired
    RefundAuditDao refundAuditDao;


    @Override
    public GenericDao<RefundAudit, String> getDao() {
        return refundAuditDao;
    }

    /**
     * 查询昨日退款金额
     *
     * @param merchantId
     * @param itype      支付方式
     */
    @Override
    public BigDecimal selectYesTodayRefundMoney(String merchantId, Integer itype) {
        return refundAuditDao.selectYesTodayRefundMoney(merchantId, itype);
    }


}