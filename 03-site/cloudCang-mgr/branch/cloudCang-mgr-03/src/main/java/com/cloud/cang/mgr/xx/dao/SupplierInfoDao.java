package com.cloud.cang.mgr.xx.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.xx.domain.SupplierInfoDomain;
import com.cloud.cang.mgr.xx.vo.SupplierInfoVo;
import com.cloud.cang.model.xx.SupplierInfo;
import com.github.pagehelper.Page;

/** 消息供应商信息表(XX_SUPPLIER_INFO) **/
public interface SupplierInfoDao extends GenericDao<SupplierInfo, String> {

    /**
     * 查询所有供应商
     * @param supplierInfoVo
     * @return
     */
    Page<SupplierInfoDomain> selectSupplierInfo(SupplierInfoVo supplierInfoVo);

    Page<SupplierInfo> selectMarketing(SupplierInfoVo supplierInfoVo);

    Page<SupplierInfo> selectSupplierInfoMarket(SupplierInfoVo supplierInfoVo);

    Page<SupplierInfo> selectSupplierInfoMsgTemp(SupplierInfoVo supplierInfoVo);

}