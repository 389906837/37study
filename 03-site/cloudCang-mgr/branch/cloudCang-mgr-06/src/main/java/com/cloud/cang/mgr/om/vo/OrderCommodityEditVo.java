package com.cloud.cang.mgr.om.vo;

import java.math.BigDecimal;

/**
 * 编辑商品Vo
 * Created by yan on 2018/6/28.
 */
public class OrderCommodityEditVo {
    private String sname;//商品名
    private BigDecimal fsalePrice;//销售价
    private BigDecimal fcostAmount;//成本价
    private BigDecimal ftaxPoint;//税点

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public BigDecimal getFsalePrice() {
        return fsalePrice;
    }

    public void setFsalePrice(BigDecimal fsalePrice) {
        this.fsalePrice = fsalePrice;
    }

    public BigDecimal getFcostAmount() {
        return fcostAmount;
    }

    public void setFcostAmount(BigDecimal fcostAmount) {
        this.fcostAmount = fcostAmount;
    }

    public BigDecimal getFtaxPoint() {
        return ftaxPoint;
    }

    public void setFtaxPoint(BigDecimal ftaxPoint) {
        this.ftaxPoint = ftaxPoint;
    }
}
