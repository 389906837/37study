package com.cloud.cang.rm;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.inventory.CommodityDiffVo;

import java.util.List;

/**
 * @version 1.0
 * @Description:生成补货单服务参数
 * @Author: zhouhong
 * @Date: 2018/4/13 12:02
 */
public class ReplenishmentDto extends StockBaseDto {

    //====必填====
    private Integer isourceClientType;//补货单来源客户端10=微信 20=支付宝 30=APP
    private Integer itype;//补货类型 10=动态配货 20=普通配货

    private List<CommodityDiffVo> addVoList;//上架商品集合
    private List<CommodityDiffVo> subVoList;//下架商品集合

    private Integer irepWay;//补货方式 10=库存对比 20=开门盘货对比
    private List<CommodityDiffVo> addVoStockList;//上架修改库存商品集合
    private List<CommodityDiffVo> subVoStockList;//下架修改库存商品集合

    /* 会员用户名（手机号） */
    private String smemberName;

    //======选填======
    private String sremark;//补货备注

    public Integer getIsourceClientType() {
        return isourceClientType;
    }

    public void setIsourceClientType(Integer isourceClientType) {
        this.isourceClientType = isourceClientType;
    }

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

    public String getSremark() {
        return sremark;
    }

    public void setSremark(String sremark) {
        this.sremark = sremark;
    }

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    public Integer getIrepWay() {
        return irepWay;
    }

    public void setIrepWay(Integer irepWay) {
        this.irepWay = irepWay;
    }

    public List<CommodityDiffVo> getAddVoStockList() {
        return addVoStockList;
    }

    public void setAddVoStockList(List<CommodityDiffVo> addVoStockList) {
        this.addVoStockList = addVoStockList;
    }

    public List<CommodityDiffVo> getSubVoStockList() {
        return subVoStockList;
    }

    public void setSubVoStockList(List<CommodityDiffVo> subVoStockList) {
        this.subVoStockList = subVoStockList;
    }

    @Override
    public String toString() {
        return "ReplenishmentDto{" +
                "isourceClientType=" + isourceClientType +
                ", itype=" + itype +
                ", addVoList=" + addVoList +
                ", subVoList=" + subVoList +
                ", irepWay=" + irepWay +
                ", addVoStockList=" + addVoStockList +
                ", subVoStockList=" + subVoStockList +
                ", smemberName='" + smemberName + '\'' +
                ", sremark='" + sremark + '\'' +
                '}';
    }
}
