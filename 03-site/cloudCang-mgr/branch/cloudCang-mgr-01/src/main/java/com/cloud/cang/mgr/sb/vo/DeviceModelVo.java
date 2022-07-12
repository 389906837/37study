package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceModel;

/**
 * @version 1.0
 * @ClassName DeviceCommodityVo
 * @Description 设备型号详细信息bean
 * @Author zengzexiong
 * @Date 2018年1月25日09:29:56
 */
public class DeviceModelVo extends DeviceModel{
//    private String id;                          /*主键*/
//    private String sdeviceId;                   /* 设备ID */
//    private String sdeviceModel;                /* 设备核心型号 */
//    private String smanufacturer;               /* 核心生产厂商 */
//    private String scoreDesc;                   /* 核心配置描述 */
//    private String sdimensions;                 /* 整体外形尺寸 */
//    private String sweight;                     /* 重量 */
//    private String sratedPower;                 /* 额定功率 */
//    private String sproductCapacity;            /* 商品容量 */
//    private String sproductTypes;               /* 商品类型 */
//    private String sdailyPower;                 /* 日耗电量 */
//    private String spayWap;                     /* 支持支付方式微信支付 支付宝 现金 银联卡  */
//    private Integer ielectricShock;             /* 防触电类型：10=0类 20=I类   30=II类  40=III类 */
//    private String slocksModel;                 /* 电子锁型号 */
//    private String slocksManufacturer;          /* 电子锁生产厂商 */
//    private String sadDimensions;               /* 广告机屏幕尺寸说明 */
//    private String sadConf;                     /* 广告机配置描叙 */
//    private Integer icompressorNum;             /* 压缩机个数 */
//    private String scompressorPosition;         /* 压缩机位置 */
//    private String scompressorDesc;             /* 压缩机描叙 */
//    private Integer ilayerNum;                  /* 设备层数 */
//    private Integer icargoRoadNum;              /* 货道数 */
//    private String sremark;                     /* 备注 */
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

    @Override
    public String toString() {
        return "DeviceModelVo{" +
                "orderStr='" + orderStr + '\'' +
                ", scode='" + scode + '\'' +
                ", sname='" + sname + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", merchantCode='" + merchantCode + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
