package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceRegister;

/**
 * @version 1.0
 * @ClassName DeviceRegisterVo
 * @Description 设备注册信息bean
 * @Author zengzexiong
 * @Date 2018年1月25日10:35:33
 */
public class DeviceRegisterVo extends DeviceRegister{
//    private String id;                      /*主键*/
//    private String sdeviceId;               /* 设备ID */
//    private String sdeviceCode;             /* 设备编号 */
//    private String sregIp;                  /* 注册IP */
//    private Date taddTime;                  /* 添加日期 */
//    private Date tregTime;                  /* 注册时间 */
//    private String ssecurityKey;            /* 安全秘钥 */
//    private String sauditUser;              /* 审核人 */
//    private Date tauditTime;                /* 审核时间 */
//    private String sremark;                 /* 审核备注 */
//    private String istatus;                 /* 状态： 10 待审核 20  审核通过  30 审核拒绝 */
//    private Integer idelFlag;               /* 是否删除0否1是 */
//    private Date tupdateTime;               /* 修改日期 */
//    private String sregPort;                /* 注册端口 */
//    private String supdateUser;             /* 修改人 */

    private String orderStr;//排序字段
    private String sname;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */
    private String queryCondition;          /* 查询条件 */

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
