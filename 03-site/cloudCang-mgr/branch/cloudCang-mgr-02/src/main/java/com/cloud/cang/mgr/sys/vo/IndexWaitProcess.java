package com.cloud.cang.mgr.sys.vo;

/**
 * @version 1.0
 * @description: 首页展示待处理事务 Vo
 * @author:Yanlingfeng
 * @time:2018-03-24 14:07:05
 */
public class IndexWaitProcess {

    private Integer pPaymentOrderNum;//待付款订单数
    private Integer pRefundNum;//待审核退款数
    private Integer pReplenishmentOrderNum;//待补货订单数
    private Integer deviceFaultNum;//设备故障数
    private Integer commodityErrorNum;//商品异常数
    private Integer stocktakingError;//盘货异常数

    public Integer getStocktakingError() {
        return stocktakingError;
    }

    public void setStocktakingError(Integer stocktakingError) {
        this.stocktakingError = stocktakingError;
    }

    public Integer getpPaymentOrderNum() {
        return pPaymentOrderNum;
    }

    public void setpPaymentOrderNum(Integer pPaymentOrderNum) {
        this.pPaymentOrderNum = pPaymentOrderNum;
    }

    public Integer getpRefundNum() {
        return pRefundNum;
    }

    public void setpRefundNum(Integer pRefundNum) {
        this.pRefundNum = pRefundNum;
    }

    public Integer getpReplenishmentOrderNum() {
        return pReplenishmentOrderNum;
    }

    public void setpReplenishmentOrderNum(Integer pReplenishmentOrderNum) {
        this.pReplenishmentOrderNum = pReplenishmentOrderNum;
    }

    public Integer getDeviceFaultNum() {
        return deviceFaultNum;
    }

    public void setDeviceFaultNum(Integer deviceFaultNum) {
        this.deviceFaultNum = deviceFaultNum;
    }

    public Integer getCommodityErrorNum() {
        return commodityErrorNum;
    }

    public void setCommodityErrorNum(Integer commodityErrorNum) {
        this.commodityErrorNum = commodityErrorNum;
    }
}
