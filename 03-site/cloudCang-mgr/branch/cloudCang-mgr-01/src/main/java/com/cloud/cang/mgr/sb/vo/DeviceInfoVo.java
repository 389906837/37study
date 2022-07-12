package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.DeviceInfo;

import java.util.List;

/**
 * @version 1.0
 * @ClassName DeviceInfoVo
 * @Description 设备bean信息
 * @Author zengzexiong
 * @Date 2018年1月24日21:10:20
 */
public class DeviceInfoVo  extends DeviceInfo {
//    private String id;                      /*主键*/
//    private String scode;                   /* 设备编号 */
//    private String sdeviceModel;            /* 设备型号 */
//    private Integer icompressorType;        /* 压缩机类型 10=制冷    20=制热   30=制冷热 */
//    private Integer icontainerType;         /* 货柜类型 10=单开门   20=双开门 */
//    private Integer icooperationMode;       /* 合作模式 10=采购   20=租用   数据字典配置 */
//    private Integer idelFlag;               /* 是否删除0否1是 */
//    private Integer iinstallAd;             /* 是否广告机0否1是 */
//    private Integer ioperateStatus;         /* 运营状态：10=启用    20=停用 */
//    private Integer istatus;                /* 设备状态：  10=正常   20=故障   30=报废 */
//    private Integer itype;                  /* 设备类型 10=传统   20=RFID射频   30=视觉 */
//    private String smerchantId;             /* 商户ID */
//    private String smerchantName;           /* 商户名称 */
//    private String sname;                   /* 设备名称 */
//    private String saddUser;                /* 添加人 */
//    private String sareaId;                 /* 区县ID */
//    private String sareaName;               /* 投放区县 */
//    private String scityId;                 /* 城市ID */
//    private String scityName;               /* 投放城市 */
//    private String sprovinceId;             /* 省份ID */
//    private String sprovinceName;           /* 投放省份 */
//    private String sputAddress;             /* 投放地址 */
//    private Date sputTime;                  /* 投放时间 */
//    private String sreaderSerialNumber;     /* 读写器序列号 */
//    private String sremark;                 /* 备注 */
//    private String supdateUser;             /* 修改人 */
//    private Date taddTime;                  /* 添加日期 */
//    private Date tupdateTime;               /* 修改日期 */
    private List<DeviceCommodityVo> deviceCommodityVoList;       /* 设备商品集合 */

    private String groupId;//分组Id

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    private String orderStr;//排序字段

    private String sgroupName;//分组名称
    private String merchantName;//商户名称

    private String queryCondition;//查询条件
    private String deviceTypes; //设备类型查询条件（50，60）10=传统 20=RFID射频 30=视觉 40=前端视觉+重力 50=云端识别 60=云端识别+重力

    public String getDeviceTypes() {
        return deviceTypes;
    }

    public void setDeviceTypes(String deviceTypes) {
        this.deviceTypes = deviceTypes;
    }

    public String getSgroupName() {
        return sgroupName;
    }

    public void setSgroupName(String sgroupName) {
        this.sgroupName = sgroupName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public List<DeviceCommodityVo> getDeviceCommodityVoList() {
        return deviceCommodityVoList;
    }

    public void setDeviceCommodityVoList(List<DeviceCommodityVo> deviceCommodityVoList) {
        this.deviceCommodityVoList = deviceCommodityVoList;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "DeviceInfoVo{" +
                "deviceCommodityVoList=" + deviceCommodityVoList +
                ", orderStr='" + orderStr + '\'' +
                ", sgroupName='" + sgroupName + '\'' +
                ", merchantName='" + merchantName + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
