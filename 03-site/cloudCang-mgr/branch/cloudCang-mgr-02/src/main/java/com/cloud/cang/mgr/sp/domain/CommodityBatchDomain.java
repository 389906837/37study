package com.cloud.cang.mgr.sp.domain;

import com.cloud.cang.model.sp.CommodityBatch;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Alex on 2018/2/22.
 */
public class CommodityBatchDomain extends CommodityBatch {

//    private String id;/*主键*/
//    private String smerchantId;/* 商户ID */
//    private String smerchantCode;/* 商户编号 */
//    private String scommodityId;/* 商品ID */
//    private String scommodityCode;/* 商品编号 */
//    private String sbatchNo;/* 批次号 */
//    private Date dproduceDate;/* 生产日期 */
//    private Date dexpiredDate;/* 过期日期 */
//    private Integer istatus;/* 状态 10=启用 20=禁用 */
//    private Integer istockStatus;/* 库存状态 10=待上架 20=部分上架 30=全部上架 */
//    private Integer isaleStatus;/* 销售状态 10=销售中 20=售罄 */
//    private Integer idelFlag;/* 是否删除 0否1是 */
//    private String sremark;/* 备注 */
//    private Date taddTime; /* 添加日期 */
//    private String saddUser;/* 添加人 */
//    private Date tupdateTime;/* 修改日期 */
//    private String supdateUser;/* 修改人 */



    private String merchantName;            /* 商户名称 */
    private String commodityName;            /* 商品名称 */

    private String commodityFullName;   // 商品拼接名
    private String sbrandName;  /* 品牌名称 */
    private String staste;  /* 口味 */
    private Integer ispecWeight;    /* 规格/重量 */
    private String sspecUnit;   /* 规格单位 */
    private String spackageUnit;    /* 最小销售包装单位 */

    public String getCommodityFullName() {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(this.getSbrandName())) {
            sb.append(this.getSbrandName());
        }
        if (StringUtils.isNotBlank(this.getCommodityName())) {
            sb.append(this.getCommodityName());
        }
        if (StringUtils.isNotBlank(this.getStaste())) {
            sb.append(this.getStaste());
        }
        if (null != this.getIspecWeight()) {
            sb.append(this.getIspecWeight());
        }
        if (null != this.getSspecUnit()) {
            sb.append(this.getSspecUnit());
        }
        if (StringUtils.isNotBlank(this.getSpackageUnit())) {
            sb.append("/"+this.getSpackageUnit());
        }
        return sb.toString();
    }

    public void setCommodityFullName(String commodityFullName) {
        this.commodityFullName = commodityFullName;
    }

    public String getSbrandName() {
        return sbrandName;
    }

    public void setSbrandName(String sbrandName) {
        this.sbrandName = sbrandName;
    }

    public String getStaste() {
        return staste;
    }

    public void setStaste(String staste) {
        this.staste = staste;
    }

    public Integer getIspecWeight() {
        return ispecWeight;
    }

    public void setIspecWeight(Integer ispecWeight) {
        this.ispecWeight = ispecWeight;
    }

    public String getSspecUnit() {
        return sspecUnit;
    }

    public void setSspecUnit(String sspecUnit) {
        this.sspecUnit = sspecUnit;
    }

    public String getSpackageUnit() {
        return spackageUnit;
    }

    public void setSpackageUnit(String spackageUnit) {
        this.spackageUnit = spackageUnit;
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

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "CommodityBatchDomain{" +
                "merchantName='" + merchantName + '\'' +
                "commodityName='" + commodityName + '\'' +
                '}';
    }
}
