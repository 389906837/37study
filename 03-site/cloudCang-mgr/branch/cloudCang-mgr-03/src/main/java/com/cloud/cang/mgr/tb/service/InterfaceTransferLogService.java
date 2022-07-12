package com.cloud.cang.mgr.tb.service;

import com.cloud.cang.mgr.tb.domain.InterfaceTransferLogDomain;
import com.cloud.cang.mgr.tb.vo.InterfaceTransferLogVo;
import com.cloud.cang.model.tb.InterfaceTransferLog;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface InterfaceTransferLogService extends GenericService<InterfaceTransferLog, String> {


    /**
     * 第三方接口调用查询
     *
     * @param page
     * @param interfaceTransferLogVo
     * @return
     */
    Page<InterfaceTransferLogDomain> selectPageByDomainWhere(Page<InterfaceTransferLogDomain> page, InterfaceTransferLogVo interfaceTransferLogVo);

}