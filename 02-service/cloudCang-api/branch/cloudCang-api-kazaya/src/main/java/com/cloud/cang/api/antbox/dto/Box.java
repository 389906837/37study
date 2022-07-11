package com.cloud.cang.api.antbox.dto;

import java.util.Date;

/**
 * Created by louis on 20/04/2017.
 * <p>
 * 售货机
 */

public class Box{
    private static final long serialVersionUID = 1L;
    public static final String REMIND_RECORD_BOX_ID_IS_EXIST = "机型ID已存在";

    private long id;
    private String name;
    private Long mallId;
    private String mallName;
    private Long merchantId;
    private String merchantName;
    private String serialNumber;
    private String modelNumber;
    private String qrCode;
    private String qrCodeMiniApp;
    private String qrCodeJsonData;
    private Integer provinceID;
    private Integer cityID;
    private Integer areaID;
    private String address;
    private String addressDetail;
    private Long groupId;
    private String groupName;
    private String mac;
    private Boolean activate;
    private String iotProductKey;
    private String iotDeviceSecret;
    private String iotDeviceId;
    private String iotDeviceName;
    private Integer inventoryQty;
    private Integer premiumRate;
    private Date expires;

    private String partnerBoxId;

    public String getQrCodeJsonData() {
        return qrCodeJsonData;
    }

    public void setQrCodeJsonData(String qrCodeJsonData) {
        this.qrCodeJsonData = qrCodeJsonData;
    }

    public String getQrCodeMiniApp() {
        return qrCodeMiniApp;
    }

    public void setQrCodeMiniApp(String qrCodeMiniApp) {
        this.qrCodeMiniApp = qrCodeMiniApp;
    }

    public Date getExpires() {
        return expires;
    }

    public void setExpires(Date expires) {
        this.expires = expires;
    }

    public Integer getPremiumRate() {
        return premiumRate;
    }

    public void setPremiumRate(Integer premiumRate) {
        this.premiumRate = premiumRate;
    }

    public Box() {
    }

    public Integer getInventoryQty() {
        return inventoryQty;
    }

    public void setInventoryQty(Integer inventoryQty) {
        this.inventoryQty = inventoryQty;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProvinceID() {
        return provinceID;
    }

    public void setProvinceID(Integer provinceID) {
        this.provinceID = provinceID;
    }

    public Integer getCityID() {
        return cityID;
    }

    public void setCityID(Integer cityID) {
        this.cityID = cityID;
    }

    public Integer getAreaID() {
        return areaID;
    }

    public void setAreaID(Integer areaID) {
        this.areaID = areaID;
    }

    public String getMallName() {
        return mallName;
    }

    public void setMallName(String mallName) {
        this.mallName = mallName;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getMallId() {
        return mallId;
    }

    public void setMallId(Long mallId) {
        this.mallId = mallId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }



    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }


    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }

    public String getIotProductKey() {
        return iotProductKey;
    }

    public void setIotProductKey(String iotProductKey) {
        this.iotProductKey = iotProductKey;
    }

    public String getIotDeviceSecret() {
        return iotDeviceSecret;
    }

    public void setIotDeviceSecret(String iotDeviceSecret) {
        this.iotDeviceSecret = iotDeviceSecret;
    }

    public String getIotDeviceId() {
        return iotDeviceId;
    }

    public void setIotDeviceId(String iotDeviceId) {
        this.iotDeviceId = iotDeviceId;
    }

    public String getIotDeviceName() {
        return iotDeviceName;
    }

    public void setIotDeviceName(String iotDeviceName) {
        this.iotDeviceName = iotDeviceName;
    }

    public String getPartnerBoxId() {
        return partnerBoxId;
    }

    public void setPartnerBoxId(String partnerBoxId) {
        this.partnerBoxId = partnerBoxId;
    }
}

