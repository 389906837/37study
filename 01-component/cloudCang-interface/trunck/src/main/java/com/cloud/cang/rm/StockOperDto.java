package com.cloud.cang.rm;

import com.cloud.cang.inventory.CommodityDiffVo;

import java.util.List;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description:
 * @Author: zhouhong
 * @Date: 2018/4/15 17:32
 */
public class StockOperDto extends StockBaseDto {
    private Integer itype;//库存操作类型 10 补货 20 销售 30 盘点
    private List<CommodityDiffVo> addVoList;//上架商品集合
    private List<CommodityDiffVo> subVoList;//下架商品集合
    private String orderIds;//订单ID 多个用，隔开
    private String auditOrderId;//审核订单ID


    public Integer getItype() {
        return itype;
    }

    public void setItype(Integer itype) {
        this.itype = itype;
    }

    public List<CommodityDiffVo> getAddVoList() {
        return addVoList;
    }

    public void setAddVoList(List<CommodityDiffVo> addVoList) {
        this.addVoList = addVoList;
    }

    public List<CommodityDiffVo> getSubVoList() {
        return subVoList;
    }

    public void setSubVoList(List<CommodityDiffVo> subVoList) {
        this.subVoList = subVoList;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds;
    }

    public String getAuditOrderId() {
        return auditOrderId;
    }

    public void setAuditOrderId(String auditOrderId) {
        this.auditOrderId = auditOrderId;
    }

    @Override
    public String toString() {
        return "StockOperDto{" +
                "itype=" + itype +
                ", addVoList=" + addVoList +
                ", subVoList=" + subVoList +
                ", orderIds='" + orderIds + '\'' +
                ", auditOrderId='" + auditOrderId + '\'' +
                '}';
    }
}
