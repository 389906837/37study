package com.cloud.cang.rmp.sm.vo;

import com.cloud.cang.model.sm.StandardDetail;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Description: 标准库存Vo
 * @Author: zhouhong
 * @Date: 2018/4/21 11:45
 */
public class StandardStockVo extends StandardDetail {

    //设备商品库存
    private Integer istock;
    /* 销售价 */
    private BigDecimal fsalePrice;
    /* 商品名称 */
    private String sname;

    public Integer getIstock() {
        return istock;
    }

    public void setIstock(Integer istock) {
        this.istock = istock;
    }

    public BigDecimal getFsalePrice() {
        return fsalePrice;
    }

    public void setFsalePrice(BigDecimal fsalePrice) {
        this.fsalePrice = fsalePrice;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Override
    public String toString() {
        return "StandardStockVo{" +
                "istock=" + istock +
                ", fsalePrice=" + fsalePrice +
                ", sname='" + sname + '\'' +
                '}';
    }
}
