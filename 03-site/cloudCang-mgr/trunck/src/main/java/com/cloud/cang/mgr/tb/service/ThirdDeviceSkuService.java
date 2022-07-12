package com.cloud.cang.mgr.tb.service;

import com.cloud.cang.mgr.tb.domain.ThirdDeviceSkuDomain;
import com.cloud.cang.mgr.tb.vo.ThirdDeviceSkuVo;
import com.cloud.cang.model.tb.ThirdDeviceSku;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface ThirdDeviceSkuService extends GenericService<ThirdDeviceSku, String> {


    /**
     * 第三方商户设备SKU库分页查询
     *
     * @param page
     * @param thirdDeviceSkuVo
     * @return
     */
    Page<ThirdDeviceSkuDomain> selectPageByDomainWhere(Page<ThirdDeviceSkuDomain> page, ThirdDeviceSkuVo thirdDeviceSkuVo);

    /**
     * 第三方商户设备SKU库商品详情分页查询
     *
     * @param page
     * @param thirdDeviceSkuVo
     * @return
     */
    Page<ThirdDeviceSku> selectViewPageByDomainWhere(Page<ThirdDeviceSku> page, ThirdDeviceSku thirdDeviceSkuVo);

}