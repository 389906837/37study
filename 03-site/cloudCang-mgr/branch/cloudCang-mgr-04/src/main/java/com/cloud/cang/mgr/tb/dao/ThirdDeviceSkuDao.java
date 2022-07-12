package com.cloud.cang.mgr.tb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.tb.domain.ThirdDeviceSkuDomain;
import com.cloud.cang.mgr.tb.vo.ThirdDeviceSkuVo;
import com.cloud.cang.model.tb.ThirdDeviceSku;
import com.github.pagehelper.Page;

/**
 * 第三方商户设备SKU库(TB_THIRD_DEVICE_SKU)
 **/
public interface ThirdDeviceSkuDao extends GenericDao<ThirdDeviceSku, String> {


    /**
     * 第三方商户设备SKU库分页查询
     *
     * @param thirdDeviceSkuVo
     * @return
     */
    Page<ThirdDeviceSkuDomain> selectByDomainWhere(ThirdDeviceSkuVo thirdDeviceSkuVo);

    /**
     * 第三方商户设备SKU库商品详情分页查询
     *
     * @param thirdDeviceSkuVo
     * @return
     */
    Page<ThirdDeviceSku> selectViewByDomainWhere(ThirdDeviceSku thirdDeviceSkuVo);



    /** codegen **/
}