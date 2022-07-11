package com.cloud.cang.api.sb.vo;

import java.util.Date;

/**
 * @version 1.0
 * @ClassName DeviceRegisterVo
 * @Description 设备注册信息bean，设备首次注册
 * @Author zengzexiong
 * @Date 2018年1月25日11:01:51
 */
public class DeviceRegisterVo {
    private String id;                      /*主键*/
    private String sdeviceId;               /* 设备ID */
    private String sdeviceCode;             /* 设备编号 */
    private String sregIp;                  /* 注册IP */
    private Date taddTime;                  /* 添加日期 */
    private Date tregTime;                  /* 注册时间 */
    private String ssecurityKey;            /* 安全秘钥 */
    private String istatus;                 /* 状态： 10 待审核 20  审核通过  30 审核拒绝 */
    private Integer idelFlag;               /* 是否删除0否1是 */
    private String sregPort;                /* 注册端口 */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSdeviceId() {
        return sdeviceId;
    }

    public void setSdeviceId(String sdeviceId) {
        this.sdeviceId = sdeviceId;
    }

    public String getSdeviceCode() {
        return sdeviceCode;
    }

    public void setSdeviceCode(String sdeviceCode) {
        this.sdeviceCode = sdeviceCode;
    }

    public String getSregIp() {
        return sregIp;
    }

    public void setSregIp(String sregIp) {
        this.sregIp = sregIp;
    }

    public Date getTaddTime() {
        return taddTime;
    }

    public void setTaddTime(Date taddTime) {
        this.taddTime = taddTime;
    }

    public Date getTregTime() {
        return tregTime;
    }

    public void setTregTime(Date tregTime) {
        this.tregTime = tregTime;
    }

    public String getSsecurityKey() {
        return ssecurityKey;
    }

    public void setSsecurityKey(String ssecurityKey) {
        this.ssecurityKey = ssecurityKey;
    }

    public String getIstatus() {
        return istatus;
    }

    public void setIstatus(String istatus) {
        this.istatus = istatus;
    }

    public Integer getIdelFlag() {
        return idelFlag;
    }

    public void setIdelFlag(Integer idelFlag) {
        this.idelFlag = idelFlag;
    }

    public String getSregPort() {
        return sregPort;
    }

    public void setSregPort(String sregPort) {
        this.sregPort = sregPort;
    }
}
