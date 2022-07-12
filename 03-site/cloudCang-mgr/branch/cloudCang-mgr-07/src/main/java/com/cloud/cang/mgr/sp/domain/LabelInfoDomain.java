package com.cloud.cang.mgr.sp.domain;

import com.cloud.cang.model.sp.LabelInfo;

/**
 * Created by Alex on 2018/2/13.
 */
public class LabelInfoDomain extends LabelInfo {

//    private String id;/*主键*/
//    private String smerchantId;/* 商户ID */SMERCHANT_ID
//    private String smerchantCode;/* 商户编号 */SMERCHANT_CODE
//    private String sname;/* 标签名称 */SNAME
//    private Integer iisParent;/* 是否父标签0否1是 */IIS_PARENT
//    private String sparentId;/* 父标签ID */SPARENT_ID
//    private Integer isort;/* 排序号 */ISORT
//    private Integer idelFlag;/* 是否删除0否1是 */IDEL_FLAG
//    private Date taddTime;/* 添加日期 */TADD_TIME
//    private String saddUser;/* 添加人 */SADD_USER
//    private Date tupdateTime;/* 修改日期 */TUPDATE_TIME
//    private String supdateUser;/* 修改人 */SUPDATE_USER

    private String merchantName;            /* 商户名称 */

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}
