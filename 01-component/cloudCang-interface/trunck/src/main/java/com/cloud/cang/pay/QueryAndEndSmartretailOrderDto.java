package com.cloud.cang.pay;

import java.util.List;

/**
 * Created by YLF on 2019/10/28.
 */
public class QueryAndEndSmartretailOrderDto {
    private String deviceId;
    private String smerchantCode;//商户编号
    private String smemberId;//会员id
    private Integer finish_type;//标识用户订单使用情况：1 未使用服务，取消订单；2 完成服务使用，结束订单
    private Integer total_amount;//大于等于0的数字，单位为分 需满足：总金额=付费项目金额之和-商户优惠项目金额之和<=订单风险金额 未使用服务，取消订单时，该字段必须填0.
    private boolean profit_sharing;//是否指定服务商分账，true-需要分账，false-不需要分账
    private String real_service_end_time;//服务结束时间
    private String orderId;//订单ID
    private List<FeeDto> fees;//	后付费，最多包含100条付费项目。
    private String cancel_reason;//取消原因
    private String creatApplyId;//申请ID


    public String getCancel_reason() {
        return cancel_reason;
    }

    public void setCancel_reason(String cancel_reason) {
        this.cancel_reason = cancel_reason;
    }

    public List<FeeDto> getFees() {
        return fees;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setFees(List<FeeDto> fees) {
        this.fees = fees;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public Integer getFinish_type() {
        return finish_type;
    }

    public void setFinish_type(Integer finish_type) {
        this.finish_type = finish_type;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public boolean isProfit_sharing() {
        return profit_sharing;
    }

    public void setProfit_sharing(boolean profit_sharing) {
        this.profit_sharing = profit_sharing;
    }

    public String getReal_service_end_time() {
        return real_service_end_time;
    }

    public void setReal_service_end_time(String real_service_end_time) {
        this.real_service_end_time = real_service_end_time;
    }

    @Override
    public String toString() {
        return "QueryAndEndSmartretailOrderDto{" +
                "deviceId='" + deviceId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", smemberId='" + smemberId + '\'' +
                ", finish_type=" + finish_type +
                ", total_amount=" + total_amount +
                ", profit_sharing=" + profit_sharing +
                ", real_service_end_time='" + real_service_end_time + '\'' +
                ", orderId='" + orderId + '\'' +
                ", fees=" + fees +
                ", cancel_reason='" + cancel_reason + '\'' +
                ", creatApplyId='" + creatApplyId + '\'' +
                '}';
    }

    public String getCreatApplyId() {
        return creatApplyId;
    }

    public void setCreatApplyId(String creatApplyId) {
        this.creatApplyId = creatApplyId;
    }
}
