package com.cloud.cang.mgr.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DeviceCommodityDomain;
import com.cloud.cang.mgr.sb.vo.DeviceCommodityVo;
import com.cloud.cang.model.sb.DeviceCommodity;
import com.github.pagehelper.Page;

/**
 * 设备商品信息表(SB_DEVICE_COMMODITY)
 **/
public interface DeviceCommodityDao extends GenericDao<DeviceCommodity, String> {

    /**
     * 分页查询
     * @param deviceCommodityVo
     * @return
     */
    Page<DeviceCommodityDomain> selectByDomainWhere(DeviceCommodityVo deviceCommodityVo);

    /**
     * 根据设备ID和商品ID从订单表与订单明细表中查询该商品的总销量
     * @param map
     * @return
     */
    Integer selectSalesNum(Map<String, String> map);

    /**
     * 根据商品ID将设备中的商品改为下架状态
     *
     * @param deviceCommodity
     */
    void updateToDropOffByCommodityId(DeviceCommodity deviceCommodity);



    /** codegen **/
}