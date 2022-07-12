package com.cloud.cang.mgr.sb.domain;

import com.cloud.cang.model.sb.DeviceInfo;
import org.apache.commons.lang3.StringUtils;

/**
 * 拼接地址=省份+城市+区县+详细地址
 * Created by Alex on 2018/2/8.
 */
public class DeviceInfoDomain extends DeviceInfo {

    private String address;//地址
    private Integer istandardStockStatus;//标准库存状态 10=启用 20=禁用
    private String sgroupName;//分组名称
    private String merchantName;//商户名称
    private String orderStr;//排序字段

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getSgroupName() {
        return sgroupName;
    }

    public void setSgroupName(String sgroupName) {
        this.sgroupName = sgroupName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAddress() {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(this.getSprovinceName())) {
            sb.append(this.getSprovinceName());
        }
        if (StringUtils.isNotBlank(this.getScityName())) {
            sb.append(this.getScityName());
        }
        if (StringUtils.isNotBlank(this.getSareaName())) {
            sb.append(this.getSareaName());
        }
        if (StringUtils.isNotBlank(this.getSputAddress())) {
            sb.append(this.getSputAddress());
        }
        return sb.toString();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIstandardStockStatus() {
        return istandardStockStatus;
    }

    public void setIstandardStockStatus(Integer istandardStockStatus) {
        this.istandardStockStatus = istandardStockStatus;
    }

    @Override
    public String toString() {
        return "DeviceInfoDomain{" +
                "address='" + address + '\'' +
                ", istandardStockStatus=" + istandardStockStatus +
                ", sgroupName='" + sgroupName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
