package com.cloud.cang.rec.op.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.op.TransferLog;
import com.cloud.cang.rec.op.domain.TransferLogDomain;
import com.cloud.cang.rec.op.vo.TransferLogVo;
import com.github.pagehelper.Page;

public interface TransferLogService extends GenericService<TransferLog, String> {

    /**
     * 查询列表
     *
     * @param page
     * @param transferLogDomain
     * @return
     */
    Page<TransferLogVo> queryTransferLog(Page<TransferLogVo> page, TransferLogDomain transferLogDomain);

}