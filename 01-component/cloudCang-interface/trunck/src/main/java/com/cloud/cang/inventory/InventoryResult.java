package com.cloud.cang.inventory;

import com.cloud.cang.common.SuperDto;
import com.cloud.cang.jy.CreatOrderResult;
import com.cloud.cang.rm.ReplenishmentConfirmResult;
import com.cloud.cang.rm.ReplenishmentResult;

import java.util.List;


/**
 * 盘点返回结果
 *
 * @author zhouhong
 * @version 1.0
 */
public class InventoryResult extends SuperDto {


    private Integer itype;//10 创建购物订单成功 20 创建手动支付订单成功  30 补货成功 40 审核订单成功 50游客 60重力差值较大生成购物异常订单 70 等待补货员确认补货单
    private String merchantCode;//商户编号
    private List<CreatOrderResult> orderRecords;//生成订单结果
    private Integer isFirstOrder;//是否首单 0 否 1 是
    private ReplenishmentResult replenishmentResult;//补货单结果
    private ReplenishmentConfirmResult replenishmentConfirmResult; //待补货员确认补货单
    private String auditOrderId;//盘点审核订单编号


    public ReplenishmentConfirmResult getReplenishmentConfirmResult() {
        return replenishmentConfirmResult;
    }

    public void setReplenishmentConfirmResult(ReplenishmentConfirmResult replenishmentConfirmResult) {
        this.replenishmentConfirmResult = replenishmentConfirmResult;
    }

    public Integer getItype() {
        return itype;
    }

    public void setItype(Integer itype) {
        this.itype = itype;
    }

    public ReplenishmentResult getReplenishmentResult() {
        return replenishmentResult;
    }

    public void setReplenishmentResult(ReplenishmentResult replenishmentResult) {
        this.replenishmentResult = replenishmentResult;
    }

    public List<CreatOrderResult> getOrderRecords() {
        return orderRecords;
    }

    public void setOrderRecords(List<CreatOrderResult> orderRecords) {
        this.orderRecords = orderRecords;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public Integer getIsFirstOrder() {
        return isFirstOrder;
    }

    public void setIsFirstOrder(Integer isFirstOrder) {
        this.isFirstOrder = isFirstOrder;
    }

    public String getAuditOrderId() {
        return auditOrderId;
    }

    public void setAuditOrderId(String auditOrderId) {
        this.auditOrderId = auditOrderId;
    }

    @Override
    public String toString() {
        return "InventoryResult{" +
                "itype=" + itype +
                ", merchantCode='" + merchantCode + '\'' +
                ", orderRecords=" + orderRecords +
                ", isFirstOrder=" + isFirstOrder +
                ", replenishmentResult=" + replenishmentResult +
                ", auditOrderId='" + auditOrderId + '\'' +
                '}';
    }
}
