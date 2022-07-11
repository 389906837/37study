package com.cloud.cang.jy;

import com.cloud.cang.act.CouponQueryResult;
import com.cloud.cang.common.SuperDto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单优惠计算服务接口
 *
 * @Description: 订单优惠-内部服务之间的model
 * @Author: zengzexiong
 * @Date: 2017年12月28日10:18:58
 * @version: 1.0
 */
public class OrderDiscountInfoDto extends SuperDto {

    /* ----------必填参数开始 ----------*/
    private String id;//用户ID

    private String scode;//用户编号

    private String smemberName;/* 用户名（手机号） */

    private String sdeviceCode;//设备编号

    private List<CommodityDiscountDto> commodityDisList;//商品集合

    /* ----------必填参数结束 ----------*/


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public List<CommodityDiscountDto> getCommodityDisList() {
        return commodityDisList;
    }

    public void setCommodityDisList(List<CommodityDiscountDto> commodityDisList) {
        this.commodityDisList = commodityDisList;
    }
}
