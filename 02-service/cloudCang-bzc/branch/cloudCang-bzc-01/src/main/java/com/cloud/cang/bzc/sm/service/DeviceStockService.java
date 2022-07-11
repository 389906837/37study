package com.cloud.cang.bzc.sm.service;

import com.cloud.cang.bzc.sm.vo.StockVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.inventory.*;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sm.DeviceStock;

import java.util.List;
import java.util.Map;

public interface DeviceStockService extends GenericService<DeviceStock, String> {
    /**
     *
     *查询库存
     */
    int selectByMap(String sdeviceId, String commodityId);

    /**
     *
     * 修改库存
     */
   int updateIstock(Map <String,Object> map);

    /**
     * 处理底层盘点服务
     * @param inventoryDto 盘点参数
     * @return  Integer 10 创建购物订单成功 20 创建手动支付订单成功  30 补货成功 40 审核订单成功 50 处理成功
     */
    ResponseVo<InventoryResult> dealwithInventory(InventoryDto inventoryDto) throws Exception;

    /**
     * 查询设备商品编号库存明细
     * @param deviceId 设备ID
     */
    List<StockVo> selectStockByDeviceId(String deviceId);

    /***
     * 查询设备库存
     * @param deviceId 设备ID
     * @param commodityId 商品ID
     */
    DeviceStock selectDeviceStockByCommodityId(String deviceId, String commodityId);

    /**
     * 计算设备库存商品差
     * @param commodityVos 盘点商品集合
     * @param deviceInfo 设备数据
     * @param memberInfo 会员数据
     */
    Map<String,Object> calcCommodityDiffByType(List<CommodityVo> commodityVos, DeviceInfo deviceInfo, MemberInfo memberInfo);

    /**
     * 关门时根据商品差计算订单商品信息
     *
     * @param inventoryCommodityDiffVoList
     * @param deviceInfo
     * @param memberInfo
     * @return
     */
    Map<String, Object> calcIncrementsAndDecrements(List<InventoryCommodityDiffVo> inventoryCommodityDiffVoList, DeviceInfo deviceInfo, MemberInfo memberInfo);

    /**
     * 根据商品数量差生成订单
     *
     * @param inventoryDiffDto
     * @return
     */
    ResponseVo<InventoryDiffResult> dealwithCommodityDiff(InventoryDiffDto inventoryDiffDto);

    /**
     * 开门前盘货对比上次关门的库存
     *
     * @param beforeOpenDoorInventoryDto
     * @return
     */
    ResponseVo<String> dealwithBeforeOpenDoorInventory(BeforeOpenDoorInventoryDto beforeOpenDoorInventoryDto);

}