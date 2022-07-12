package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceManage;

/**
 * @version 1.0
 * @ClassName DeviceCommodityVo
 * @Description 设备管理bean信息
 * @Author zengzexiong
 * @Date 2018年1月25日09:29:56
 */
public class DeviceManageVo extends DeviceManage{
    //    private String id;                          /*主键*/
//    private String saddUser;                    /* 添加人 */
//    private String sareaCode;                   /* 所属区域编号 数据字典配置 */
//    private String sareaPrincipal;              /* 区域负责人 */
//    private String sareaPrincipalContact;       /* 区域负责人联系方式 */
//    private String sdeviceId;                   /* 设备ID */
//    private String sdevicePrincipal;            /* 设备负责人 */
//    private String sdevicePrincipalContact;     /* 设备负责人联系方式 */
//    private String smaintain;                   /* 设备维护人姓名 */
//    private String smaintainContact;            /* 维护人联系方式 */
//    private String sremark;                     /* 备注 */
//    private String sreplenishment;              /* 补货员姓名 */
//    private String sreplenishmentContact;       /* 补货员联系方式 */
//    private String supdateUser;                 /* 修改人 */
//    private Date taddTime;                      /* 添加日期 */
//    private Date tupdateTime;                   /* 修改日期 */
    private String orderStr;//排序字段

    private String scode;                   /* 设备编号 */
    private String sname;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */
    private String queryCondition;          /* 查询条件 */

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

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
