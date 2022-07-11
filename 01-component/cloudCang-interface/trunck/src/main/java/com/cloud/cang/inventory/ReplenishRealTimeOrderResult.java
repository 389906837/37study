package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 补货实时订单返回结果
 * @author zengzexiong
 * @version 1.0
 */
public class ReplenishRealTimeOrderResult extends SuperDto {

    private List<ReplenishRealTimeCommodityResult> results;                     // 单设备库存商品集合
    private List<ReplenishRealTimeCommodityResult> shelfCommoditys;             // 上架商品集合
    private List<ReplenishRealTimeCommodityResult> dropOffCommoditys;           // 下架商品集合


    public List<ReplenishRealTimeCommodityResult> getResults() {
        return results;
    }

    public void setResults(List<ReplenishRealTimeCommodityResult> results) {
        this.results = results;
    }

    public List<ReplenishRealTimeCommodityResult> getShelfCommoditys() {
        return shelfCommoditys;
    }

    public void setShelfCommoditys(List<ReplenishRealTimeCommodityResult> shelfCommoditys) {
        this.shelfCommoditys = shelfCommoditys;
    }

    public List<ReplenishRealTimeCommodityResult> getDropOffCommoditys() {
        return dropOffCommoditys;
    }

    public void setDropOffCommoditys(List<ReplenishRealTimeCommodityResult> dropOffCommoditys) {
        this.dropOffCommoditys = dropOffCommoditys;
    }
}
