package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.sb.domain.DeviceCommodityDomain;
import com.cloud.cang.mgr.sb.vo.DeviceCommodityVo;
import com.cloud.cang.model.sb.DeviceCommodity;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.Map;

public interface DeviceCommodityService extends GenericService<DeviceCommodity, String> {


    /**
     * 分页查询
     * @param page
     * @param deviceCommodityVo
     * @return
     */
    Page<DeviceCommodityDomain> selectPageByDomainWhere(Page<DeviceCommodityDomain> page, DeviceCommodityVo deviceCommodityVo);

    /**
     * 根据设备商品表ID查询，设备商品表信息，设备信息，商户信息
     * @param sid
     * @return
     */
    DeviceCommodityDomain selectDomainByID(String sid);

    /**
     * 修改设备商品状态为上架
     * @param checkboxId
     * @param commondityShelf
     * @return
     */
    ResponseVo<String> updateStatusByIds(String[] checkboxId, String commondityShelf);

    /**
     * 根据设备ID和商品ID从订单表与订单明细表中查询该商品的总销量
     * @param map
     * @return
     */
    Integer selectSalesNum(Map<String, String> map);

    /**
     * 下架商品
     * @param checkboxId
     * @param merchantId
     * @param user
     */
    void dropOffCommodity(String[] checkboxId, String merchantId, String user);

}