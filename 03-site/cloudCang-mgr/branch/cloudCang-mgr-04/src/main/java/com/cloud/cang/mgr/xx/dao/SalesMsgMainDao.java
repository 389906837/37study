package com.cloud.cang.mgr.xx.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.xx.domain.SalesMsgMainDomain;
import com.cloud.cang.mgr.xx.vo.SalesMsgMainVo;
import com.cloud.cang.model.xx.SalesMsgMain;
import com.github.pagehelper.Page;

import java.util.List;

/** 营销短信主表(XX_SALES_MSG_MAIN) **/
public interface SalesMsgMainDao extends GenericDao<SalesMsgMain, String> {

    Page<SalesMsgMainDomain> selectMarketing(SalesMsgMainVo salesMsgMainVo);

}