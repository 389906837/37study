package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.jy.CreatOrderResult;
import com.cloud.cang.rm.ReplenishmentResult;

import java.util.List;


/**
 * 关门时差值计算返回订单结果
 * @author zengzexiong
 * @version 1.0
 */
public class InventoryDiffResult extends SuperDto {

    private Integer itype;//10 创建购物订单成功 20 创建手动支付订单成功  30 补货成功 40 审核订单成功 50游客 60 购物异常订单 70 补货异常订单
    private String merchantCode;//商户编号
    private List<CreatOrderResult> orderRecords;//生成订单结果
    private Integer isFirstOrder;//是否首单 0 否 1 是
    private ReplenishmentResult replenishmentResult;//补货单结果

    private String auditOrderId;//盘点审核订单编号

    public Integer getItype() {
        return itype;
    }

    public void setItype(Integer itype) {
        this.itype = itype;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public List<CreatOrderResult> getOrderRecords() {
        return orderRecords;
    }

    public void setOrderRecords(List<CreatOrderResult> orderRecords) {
        this.orderRecords = orderRecords;
    }

    public Integer getIsFirstOrder() {
        return isFirstOrder;
    }

    public void setIsFirstOrder(Integer isFirstOrder) {
        this.isFirstOrder = isFirstOrder;
    }

    public ReplenishmentResult getReplenishmentResult() {
        return replenishmentResult;
    }

    public void setReplenishmentResult(ReplenishmentResult replenishmentResult) {
        this.replenishmentResult = replenishmentResult;
    }

    public String getAuditOrderId() {
        return auditOrderId;
    }

    public void setAuditOrderId(String auditOrderId) {
        this.auditOrderId = auditOrderId;
    }
}
