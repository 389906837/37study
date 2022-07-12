package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.MonitorDataConf;

import java.util.Date;

/**
 * @version 1.0
 * @ClassName DeviceCommodityVo
 * @Description 设备监控数据配置信息bean
 * @Author zengzexiong
 * @Date 2018年1月25日10:35:25
 */
public class MonitorDataConfVo extends MonitorDataConf{
//    private String id;                          /*主键*/
//    private String sdeviceId;                   /* 设备ID */
//    private Integer iinventoryNum;              /* 每次盘货次数 */
//    private Date tinventoryTime;                /* 上次盘货时间 */
//    private Date tinventoryBeginTime;           /* 自动盘货开始时间 */
//    private Date tinventoryEndTime;             /* 自动盘货结束时间 */
//    private String sltemperature;               /* 实时温度1 */
//    private BigDecimal slcontrolTemperature;    /* 控制温度1 */
//    private Date tlcontrolBeginTime;            /* 温度控制开始时间1 */
//    private Date tlcontrolEndTime;              /* 温度控制结束时间1 */
//    private String srtemperature;               /* 实时温度2 */
//    private BigDecimal srcontrolTemperature;    /* 控制温度2 */
//    private Date trcontrolBeginTime;              /* 温度控制开始时间2 */
//    private Date trcontrolEndTime;                /* 温度控制结束时间2 */
//    private Integer iswitchStatus;                /* 定时开关机状态 0 否1 是 */
//    private Date tbootTime;                       /* 开机时间 */
//    private Date tshutDownTime;                   /* 关机时间 */
//    private Integer iactualVolume;                /* 实时音量 */
//    private Date taddTime;                      /* 添加日期 */
//    private String saddUser;                    /* 添加人 */
//    private Date tupdateTime;                   /* 修改日期 */
//    private String supdateUser;                 /* 修改人 */

    private String orderStr;//排序字段

    private String scode;                   /* 设备编号 */
    private String sname;                   /* 设备名称 */
    private String merchantName;            /* 商户名称 */
    private String merchantCode;           /* 商户编号 */
    private String queryCondition;          /* 查询条件 */

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

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

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    @Override
    public String toString() {
        return "MonitorDataConfVo{" +
                "orderStr='" + orderStr + '\'' +
                ", scode='" + scode + '\'' +
                ", sname='" + sname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                ", smerchantCode='" + merchantCode + '\'' +
                '}';
    }
}
