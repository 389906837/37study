package com.cloud.cang.api.netty.vo;

import com.cloud.cang.api.sm.vo.StockVo;

import java.util.List;

/**
 * 多商品匹配对比
 * Created by YLF on 2019/7/11.
 */
public class MultiCommodityMatch {
    private Integer visionTotal;//视觉总和
    private Integer stockTotal;//库存总和
    private String visionValue;
    private List<StockVo> stocks;

    public Integer getVisionTotal() {
        return visionTotal;
    }

    public void setVisionTotal(Integer visionTotal) {
        this.visionTotal = visionTotal;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public String getVisionValue() {
        return visionValue;
    }

    public void setVisionValue(String visionValue) {
        this.visionValue = visionValue;
    }

    public List<StockVo> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockVo> stocks) {
        this.stocks = stocks;
    }
}
