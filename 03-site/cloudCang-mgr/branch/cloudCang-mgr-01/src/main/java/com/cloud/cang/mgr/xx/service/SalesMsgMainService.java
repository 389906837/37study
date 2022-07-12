package com.cloud.cang.mgr.xx.service;

import com.cloud.cang.mgr.xx.domain.SalesMsgMainDomain;
import com.cloud.cang.mgr.xx.vo.SalesMsgMainVo;
import com.cloud.cang.model.xx.SalesMsgMain;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;

public interface SalesMsgMainService extends GenericService<SalesMsgMain, String> {

    /**
     * 营销短信查询接口
     * @param page
     * @param salesMsgMainVo
     * @return
     */
    Page<SalesMsgMainDomain> selectMarketing(Page<SalesMsgMainDomain> page, SalesMsgMainVo salesMsgMainVo);

    void delete(String [] checkboxId);

}