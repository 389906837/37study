package com.cloud.cang.mgr.sp.domain;

import com.cloud.cang.model.sp.BrandInfo;

/**
 * Created by Alex on 2018/2/13.
 */
public class BrandInfoDomain extends BrandInfo {
//    private String id;/*主键*/
//    private String smerchantId;/* 商户ID */
//    private String smerchantCode;/* 商户编号 */
//    private String sname;/* 品牌名称 */
//    private String slogo;/* 品牌LOGO */
//    private Integer isort;/* 排序号 */
//    private Integer idelFlag;/* 是否删除0否1是 */
//    private Date taddTime;/* 添加日期 */
//    private String saddUser;/* 添加人 */
//    private Date tupdateTime;/* 修改日期 */
//    private String supdateUser;/* 修改人 */
    private String merchantName;            /* 商户名称 */

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "BrandInfoDomain{" +
                "merchantName='" + merchantName + '\'' +
                '}';
    }
}
