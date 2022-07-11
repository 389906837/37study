package com.cloud.cang.hy;

import com.cloud.cang.common.SuperDto;

/**
 * @Description: 会员信息  内部服务之间的model
 * @Author: zengzexiong
 * @Date: 2017年12月27日
 * @version 1.0
 */
public class MemberInfoDto extends SuperDto {
    private static final long serialVersionUID = 1L;

    private String smobile; //手机号码
    private String sloginPassword; //登录密码
    private Integer imemberType;//会员类型:10：购物会员 20：补货会员
    private String suserIp; //用户IP
    private Integer isourceClientType;   //10=pc20=iphone30=android40=wap50=微信
    private String ssourcetype;   //指定数据字典【来源类型】key
    private String srecommonMbrCode;   //推荐人编号（需要根据推荐人的推广代码转为推荐人的编号）
    private String headImg;//头像
    private Integer isex;//会员性别
    private String deviceCode;//设备编号
    private String deviceAddress;//设备地址
    /* 商户ID */
    private String smerchantId;
    /* 商户编号 */
    private String smerchantCode;

    private String nikeName;//昵称

    private String thirdUserId;//第三方用户id(印度项目新加字段)


    public String getSmobile() {
        return smobile;
    }

    public void setSmobile(String smobile) {
        this.smobile = smobile;
    }

    public String getSloginPassword() {
        return sloginPassword;
    }

    public void setSloginPassword(String sloginPassword) {
        this.sloginPassword = sloginPassword;
    }

    public Integer getImemberType() {
        return imemberType;
    }

    public void setImemberType(Integer imemberType) {
        this.imemberType = imemberType;
    }

    public String getSuserIp() {
        return suserIp;
    }

    public void setSuserIp(String suserIp) {
        this.suserIp = suserIp;
    }

    public Integer getIsourceClientType() {
        return isourceClientType;
    }

    public void setIsourceClientType(Integer isourceClientType) {
        this.isourceClientType = isourceClientType;
    }

    public String getSsourcetype() {
        return ssourcetype;
    }

    public void setSsourcetype(String ssourcetype) {
        this.ssourcetype = ssourcetype;
    }

    public String getSrecommonMbrCode() {
        return srecommonMbrCode;
    }

    public void setSrecommonMbrCode(String srecommonMbrCode) {
        this.srecommonMbrCode = srecommonMbrCode;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Integer getIsex() {
        return isex;
    }

    public void setIsex(Integer isex) {
        this.isex = isex;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String getSmerchantId() {
        return smerchantId;
    }

    public void setSmerchantId(String smerchantId) {
        this.smerchantId = smerchantId;
    }

    public String getSmerchantCode() {
        return smerchantCode;
    }

    public void setSmerchantCode(String smerchantCode) {
        this.smerchantCode = smerchantCode;
    }

    public String getNikeName() {
        return nikeName;
    }

    public void setNikeName(String nikeName) {
        this.nikeName = nikeName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }

    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    public String getThirdUserId() {
        return thirdUserId;
    }

    public void setThirdUserId(String thirdUserId) {
        this.thirdUserId = thirdUserId;
    }

    @Override
    public String toString() {
        return "MemberInfoDto{" +
                "smobile='" + smobile + '\'' +
                ", sloginPassword='" + sloginPassword + '\'' +
                ", imemberType=" + imemberType +
                ", suserIp='" + suserIp + '\'' +
                ", isourceClientType=" + isourceClientType +
                ", ssourcetype='" + ssourcetype + '\'' +
                ", srecommonMbrCode='" + srecommonMbrCode + '\'' +
                ", headImg='" + headImg + '\'' +
                ", isex=" + isex +
                ", deviceCode='" + deviceCode + '\'' +
                ", deviceAddress='" + deviceAddress + '\'' +
                ", smerchantId='" + smerchantId + '\'' +
                ", smerchantCode='" + smerchantCode + '\'' +
                ", nikeName='" + nikeName + '\'' +
                '}';
    }
}
