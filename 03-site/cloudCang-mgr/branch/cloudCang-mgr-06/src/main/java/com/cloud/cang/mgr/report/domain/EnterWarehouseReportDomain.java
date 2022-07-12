package com.cloud.cang.mgr.report.domain;

import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * 入库汇总报表 Domain
 *
 * @author yanlingfeng
 * @version 1.0
 */
public class EnterWarehouseReportDomain {
    private String merchantId;//商户ID
    private String commodityCode;//商品编号
    private String commodityName;//商品名
    private String merchantName;//商户名
    private String orderStr;//排序字段
    private Integer type;//操作类型(10:入库 20:出库)
    private Date enterWarehouseStart;//搜索开始时间
    private Date enterWarehouseEnd;//搜索结束时间
    private String enterWarehouseTime;//搜索时间

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getEnterWarehouseTime() {
        return enterWarehouseTime;
    }

    public void setEnterWarehouseTime(String enterWarehouseTime) {
        this.enterWarehouseTime = enterWarehouseTime;
    }

    public Date getEnterWarehouseStart() {
        if (StringUtil.isNotBlank(this.enterWarehouseTime)) {
            return DateUtils.parseDate(enterWarehouseTime.split(" - ")[0]);
        }
        return enterWarehouseStart;
    }

    public Date getEnterWarehouseEnd() {
        if (StringUtil.isNotBlank(this.enterWarehouseTime)) {
            return DateUtils.parseDate(enterWarehouseTime.split(" - ")[1]);
        }
        return enterWarehouseEnd;
    }

    public void setEnterWarehouseEnd(Date enterWarehouseEnd) {
        this.enterWarehouseEnd = enterWarehouseEnd;
    }

    public void setEnterWarehouseStart(Date enterWarehouseStart) {
        this.enterWarehouseStart = enterWarehouseStart;
    }


    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
