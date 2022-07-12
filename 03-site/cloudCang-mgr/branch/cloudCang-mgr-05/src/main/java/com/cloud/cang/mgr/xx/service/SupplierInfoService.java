package com.cloud.cang.mgr.xx.service;

import com.cloud.cang.mgr.xx.domain.SupplierInfoDomain;
import com.cloud.cang.mgr.xx.vo.SupplierInfoVo;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface SupplierInfoService extends GenericService<SupplierInfo, String> {

    /**
     * 短信供应商接口
     * @param page
     * @param supplierInfoVo
     * @return
     */
    Page<SupplierInfoDomain> selectSupplierInfo(Page<SupplierInfoDomain> page, SupplierInfoVo supplierInfoVo);

    /**
     * 删除
     * @param checkboxId
     */
    void delete(String [] checkboxId);

    /**
     * 营销短信
     * @param page
     * @param supplierInfoVo
     * @return
     */
    Page<SupplierInfo> selectMarketing(Page<SupplierInfo> page,SupplierInfoVo supplierInfoVo);

    /**
     * 短信供应商只展示营销和禁用状态的数据(营销短信)
     * @param page
     * @param supplierInfoVo
     * @return
     */
    Page<SupplierInfo> selectSupplierInfoMarket(Page<SupplierInfo> page, SupplierInfoVo supplierInfoVo);

    /**
     * 短信供应商只展示启用的状态数据(消息协议模板)
     * @param page
     * @param supplierInfoVo
     * @return
     */
    Page<SupplierInfo> selectSupplierInfoMsgTemp(Page<SupplierInfo> page, SupplierInfoVo supplierInfoVo);

}