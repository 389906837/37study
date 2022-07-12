package com.cloud.cang.mgr.sys.vo;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @description: 首页缓存数据展示 Vo
 * @author:Yanlingfeng
 * @time:2018-03-24 14:07:05
 */
public class IndexCacheDataVo {

    private Integer tdOrderTotal;//今日订单总数
    private BigDecimal tdSaleTotal;//今日销售额
    private BigDecimal sevenDSaleTotal;//近七天销售额
    private Integer ydOrderTotal;//昨日订单数
    private BigDecimal ydSaleTotal;//昨日销售额

    public Integer getTdOrderTotal() {
        return tdOrderTotal;
    }

    public void setTdOrderTotal(Integer tdOrderTotal) {
        this.tdOrderTotal = tdOrderTotal;
    }

    public BigDecimal getTdSaleTotal() {
        return tdSaleTotal;
    }

    public void setTdSaleTotal(BigDecimal tdSaleTotal) {
        this.tdSaleTotal = tdSaleTotal;
    }

    public BigDecimal getSevenDSaleTotal() {
        return sevenDSaleTotal;
    }

    public void setSevenDSaleTotal(BigDecimal sevenDSaleTotal) {
        this.sevenDSaleTotal = sevenDSaleTotal;
    }

    public Integer getYdOrderTotal() {
        return ydOrderTotal;
    }

    public void setYdOrderTotal(Integer ydOrderTotal) {
        this.ydOrderTotal = ydOrderTotal;
    }

    public BigDecimal getYdSaleTotal() {
        return ydSaleTotal;
    }

    public void setYdSaleTotal(BigDecimal ydSaleTotal) {
        this.ydSaleTotal = ydSaleTotal;
    }
}
