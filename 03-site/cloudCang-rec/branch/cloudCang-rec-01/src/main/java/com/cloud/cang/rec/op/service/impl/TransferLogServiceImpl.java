package com.cloud.cang.rec.op.service.impl;

import java.util.List;

import com.cloud.cang.rec.op.domain.TransferLogDomain;
import com.cloud.cang.rec.op.vo.TransferLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.rec.op.dao.TransferLogDao;
import com.cloud.cang.model.op.TransferLog;
import com.cloud.cang.rec.op.service.TransferLogService;

@Service
public class TransferLogServiceImpl extends GenericServiceImpl<TransferLog, String> implements
        TransferLogService {

    @Autowired
    TransferLogDao transferLogDao;


    @Override
    public GenericDao<TransferLog, String> getDao() {
        return transferLogDao;
    }


    /**
     * 查询列表
     *
     * @param page
     * @param transferLogDomain
     * @return
     */
    @Override
    public Page<TransferLogVo> queryTransferLog(Page<TransferLogVo> page, TransferLogDomain transferLogDomain){
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return transferLogDao.queryTransferLog(transferLogDomain);
    }

}