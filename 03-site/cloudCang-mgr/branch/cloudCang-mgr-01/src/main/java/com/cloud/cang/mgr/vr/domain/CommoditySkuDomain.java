package com.cloud.cang.mgr.vr.domain;

import com.cloud.cang.model.vr.CommoditySku;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by Alex on 2018/3/7.
 */
public class CommoditySkuDomain extends CommoditySku {


//    private String id;/*主键*/
//    private String scode;/* 商品编号（SKU） */
//    private String scommodityName; /* 商品名称 */
//    private String scommodityImg; /* 展示图片 */
//    private String scategoryCode;/* 分类编号 */
//    private String scategoryName;/* 分类名称 */
//    private String sbigCategoryId;/* 大类ID */
//    private String sbigCategoryName;/* 大类名称 */
//    private String ssmallCategoryId; /* 小类ID */
//    private String ssmallCategoryName; /* 小类名称 */
//    private String spackageUnit; /* 最小销售包装单位 */
//    private String sspecUnit;/* 规格单位 */
//    private Integer ispecWeight; /* 规格/重量 */
//    private String sbrandId; /* 品牌ID */
//    private String sbrandName;   /* 品牌名称 */
//    private Integer ilifeType; /* 保质期类型 10=天 20=月 */
//    private Integer ishelfLife;/* 保质期 */
//    private String smanufacturer;/* 生产厂家 */
//    private Integer istatus;/* 商品状态 10=在售 20=停用 */
//    private String ionlineStatus;/* 上线状态 10 草稿 20 已上架 30 已下架 */
//    private Integer idelFlag;/* 是否删除0否1是 */
//    private String sremark;  /* 备注 */
//    private Date taddTime;/* 添加日期 */
//    private String saddUser; /* 添加人 */
//    private Date tupdateTime;/* 修改日期 */
//    private String supdateUser;/* 修改人 */
//    private Integer iverson;  /* 版本号 */

    private String commodityFullName;   //商品全名 = 品牌+商品名+口味+规格重量+规格单位+/最小单位

    public String getCommodityFullName() {
        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(this.getSbrandName())) {
            sb.append(this.getSbrandName());
        }
        if (StringUtils.isNotBlank(this.getScommodityName())) {
            sb.append(this.getScommodityName());
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
}
