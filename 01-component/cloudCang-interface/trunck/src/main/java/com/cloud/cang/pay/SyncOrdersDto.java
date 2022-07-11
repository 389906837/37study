package com.cloud.cang.pay;

import com.alibaba.fastjson.JSON;

import javax.xml.soap.Detail;
import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 微信支付分同步订单
 * @Author: yanlingfeng
 * @Date: 2019/8/1 14:57
 */
public class SyncOrdersDto implements Serializable {
    /*必填*/
    private String type;//场景类型 Order_Paid-用户付款成功
    private String smerchantCode;//商户编号
    private String orderCode;//订单编号
    private String orderId;//订单Id
    private Object detail;//

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

  /*  public static void main(String[] args) {
        SyncOrdersDto syncOrdersDto = new SyncOrdersDto();
        SyncOrdersDto.detail detail = new SyncOrdersDto.detail();
        detail.setPaid_time("EE");
        syncOrdersDto.setDetail(detail);
        syncOrdersDto.setOrderCode("AA");
        syncOrdersDto.setOrderId("BB");
        syncOrdersDto.setSmerchantCode("CC");
        syncOrdersDto.setType("DD");
        System.out.println(JSON.toJSONString(syncOrdersDto));
    }*/


    /*内容信息详情
场景类型=“Order_Paid”时，必填，详见detail表格说明*/
    public static class detail {
        private String paid_time;//用户通过其他方式付款成功的实际时间

        @Override
        public String toString() {
            return "detail{" +
                    "paid_time='" + paid_time + '\'' +
                    '}';
        }

        public String getPaid_time() {
            return paid_time;
        }

        public void setPaid_time(String paid_time) {

            this.paid_time = paid_time;
        }
    }

    @Override
    public String toString() {
        return "SyncOrdersDto{" +
                "type='" + type + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", detail=" + detail +
                '}';
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
