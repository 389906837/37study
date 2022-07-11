package com.cloud.cang.inventory;


import com.cloud.cang.common.ResponseVo;

/**
 * 设备盘点服务
 *
 * @author zhouhong
 * @version 1.0
 */
public class InventoryServicesDefine {

    /**
     * 设备盘点处理服务
     *
     * @Param {@link InventoryDto }
     * @return {@link ResponseVo<InventoryResult>} InventoryResult
     */
    public static final String INVENTORY_DEALWITH_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.dealwithInventory";

    /**
     * 计算实时订单服务
     *
     * @Param {@link RealTimeOrderDto }
     * @return {@link ResponseVo<RealTimeOrderResult>} RealTimeOrderResult
     */
    public static final String CREATE_REAL_TIME_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.createRealTimeOrder";

    /**
     * 计算商品差实时订单服务
     *
     * @Param {@link RealTimeCommodityDiffOrderDto }
     * @return {@link ResponseVo<RealTimeOrderResult>} RealTimeOrderResult
     */
    public static final String CREATE_REAL_TIME_COMMOFITY_DIFF_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.createCommodityDiffRealTimeOrder";

    /**
     * 计算补货实时订单服务
     *
     * @Param {@link ReplenishRealTimeOrderDto }
     * @return {@link ResponseVo<ReplenishRealTimeOrderResult>} ReplenishRealTimeOrderResult
     */
    public static final String REPLENISHMENT_REAL_TIME_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.createReplenishmentRealtimeOrder";

    /**
     * 计算补货商品差实时订单服务
     *
     * @Param {@link RealTimeCommodityDiffOrderDto }
     * @return {@link ResponseVo<ReplenishRealTimeOrderResult>} ReplenishRealTimeOrderResult
     */
    public static final String REPLENISHMENT_REAL_TIME_COMMOFITY_DIFF_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.createCommodityDiffReplenishmentRealtimeOrder";

    /**
     * 设备关门盘点（根据商品数量差）
     * 生成订单服务
     *
     * @Param {@link InventoryDiffDto }
     * @return {@link ResponseVo< InventoryDiffResult >} InventoryDiffResult
     */
    public static final String INVENTORY_COMMODITY_DIFF_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.dealwithCommodityDiff";

    /**
     * 设备开门前盘点库存数量，对比上次关门库存数量
     * 如果有商品数量差，生成警告日志
     *
     * @Param {@link BeforeOpenDoorInventoryDto }
     * @return {@link ResponseVo<String>}
     */
    public static final String BEFORE_OPEN_DOOR_INVENTORY_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.beforeOpenDoorInventoryService";

    /**
     * 视觉重力柜关门（根据商品数量差和重力差）
     * 生成订单服务
     *
     * @Param {@link InventoryDto }
     * @return {@link ResponseVo<InventoryResult>} InventoryResult
     */
    public static final String LOCAL_GRAVITY_CLOSE_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.localGravityCloseService";

    /**
     * 视觉重力柜关门（根据商品数量差和重力差）
     * 生成订单服务
     *
     * @Param {@link LayeredInventoryDto }
     * @return {@link ResponseVo<InventoryResult>} InventoryResult
     */
    public static final String LOCAL_GRAVITY_LAYERED_CLOSE_SERVICE = "com.cloud.cang.bzc.ws.InventoryService.localGravityLayeredCloseService";


}
