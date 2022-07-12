package com.cloud.cang.mgr.sl.domain;

import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.sl.DeviceOper;
import org.apache.shiro.SecurityUtils;

/**
 * 设备操作日志返回设备地址拼接字段
 * @author ChangTanchang
 * @time:2018-04-25 09:11:00
 * @version 1.0
 */
public class OperatelogDomain extends DeviceOper{

    // 设备地址(sql语句里做拼接:省份+城市+区县+详细地址)
    private String adress;

    // 设备名称
    private String sbName;

    // 排序字段
    private String orderStr;

    // 会员脱敏数据
    private String smemberNameDesensitize;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getSmemberNameDesensitize() {
        if (!SecurityUtils.getSubject().isPermitted("MEMBERINFO_MEMBER_NAME_DESENSITIZE_OL")) {
            return DesensitizeUtil.mobilePhone(this.getSmemberName());
        }
        return this.getSmemberName();
    }

    public void setSmemberNameDesensitize(String smemberNameDesensitize) {
        this.smemberNameDesensitize = smemberNameDesensitize;
    }

    @Override
    public String toString() {
        return "OperatelogDomain{" +
                "adress='" + adress + '\'' +
                ", sbName='" + sbName + '\'' +
                ", orderStr='" + orderStr + '\'' +
                ", smemberNameDesensitize='" + smemberNameDesensitize + '\'' +
                '}';
    }
}
