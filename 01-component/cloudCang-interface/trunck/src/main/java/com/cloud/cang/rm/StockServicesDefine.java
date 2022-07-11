package com.cloud.cang.rm;


import com.cloud.cang.common.ResponseVo;

/**
 * 库存服务
 * @author zhouhong
 * @version 1.0
 */
public class StockServicesDefine {

    /**
     * 生成补货单服务
     * @Param {@link ReplenishmentDto }
     * @return {@link com.cloud.cang.common.ResponseVo<ReplenishmentResult>} ReplenishmentResult
     */
    public static final String GENERATE_REPLENISHMENT_SERVICE = "com.cloud.cang.rmp.ws.DeviceStockChangeService.generateReplenishment";

    /**
     * 库存操作服务
     * @Param {@link StockOperDto}
     * @return {@link ResponseVo <String>}
     */
    public static final String STOCK_OPERATE_SERVICE = "com.cloud.cang.rmp.ws.DeviceStockChangeService.stockOper";

}
