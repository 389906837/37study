package com.cloud.cang.mgr.sys.vo;

/**
 * @version 1.0
 * @description: 首页商品总览 Vo
 * @author:Yanlingfeng
 * @time:2018-03-24 14:07:05
 */
public class IndexCommodityVo {
    private Integer upShelfCommodity;//已上架
    private Integer downShelfCommodity;//已下架
    private Integer commodityStockWarn;//库存预警
    private Integer commodityExpireWarn;//过期预警

    public Integer getUpShelfCommodity() {
        return upShelfCommodity;
    }

    public void setUpShelfCommodity(Integer upShelfCommodity) {
        this.upShelfCommodity = upShelfCommodity;
    }

    public Integer getDownShelfCommodity() {
        return downShelfCommodity;
    }

    public void setDownShelfCommodity(Integer downShelfCommodity) {
        this.downShelfCommodity = downShelfCommodity;
    }

    public Integer getCommodityStockWarn() {
        return commodityStockWarn;
    }

    public void setCommodityStockWarn(Integer commodityStockWarn) {
        this.commodityStockWarn = commodityStockWarn;
    }

    public Integer getCommodityExpireWarn() {
        return commodityExpireWarn;
    }

    public void setCommodityExpireWarn(Integer commodityExpireWarn) {
        this.commodityExpireWarn = commodityExpireWarn;
    }
}
