package com.cloud.cang.rec.op.domain;

import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;


/**
 * UserInterfaceAuth 扩展类
 * Created by yan on 2018/10/16.
 */
public class UserInterfaceAuthDomain extends UserInterfaceAuth {
    private String orderStr; //排序
    private String userName;//用户名
    private String appName;//应用名称
    private String interfaceName;//接口名称
    private String saction;//接口动作
    private String lastCloseTimeStr;//最后关闭时间
    private Date lastCloseTimeStart;//最后关闭时间开始时间
    private Date lastCloseTimeEnd;//最后关闭时间结束时间
    private String openTimeStr;//开通时间
    private Date openTimeStart;//开通时间开始时间
    private Date openTimeEnd;//开通时间结束时间


    public String getOpenTimeStr() {
        return openTimeStr;
    }

    public void setOpenTimeStr(String openTimeStr) {
        this.openTimeStr = openTimeStr;
    }

    public Date getOpenTimeStart() {
        if (StringUtil.isNotBlank(this.openTimeStr)) {
            return DateUtils.parseDate(openTimeStr.split(" - ")[0]);
        }
        return openTimeStart;
    }

    public void setOpenTimeStart(Date openTimeStart) {
        this.openTimeStart = openTimeStart;
    }

    public Date getOpenTimeEnd() {
        if (StringUtil.isNotBlank(this.openTimeStr)) {
            return DateUtils.parseDate(openTimeStr.split(" - ")[1]);
        }
        return openTimeEnd;
    }

    public void setOpenTimeEnd(Date openTimeEnd) {
        this.openTimeEnd = openTimeEnd;
    }

    public String getLastCloseTimeStr() {
        return lastCloseTimeStr;
    }

    public void setLastCloseTimeStr(String lastCloseTimeStr) {
        this.lastCloseTimeStr = lastCloseTimeStr;
    }

    public Date getLastCloseTimeStart() {
        if (StringUtil.isNotBlank(this.lastCloseTimeStr)) {
            return DateUtils.parseDate(lastCloseTimeStr.split(" - ")[0]);
        }
        return lastCloseTimeStart;
    }

    public void setLastCloseTimeStart(Date lastCloseTimeStart) {
        this.lastCloseTimeStart = lastCloseTimeStart;
    }

    public Date getLastCloseTimeEnd() {
        if (StringUtil.isNotBlank(this.lastCloseTimeStr)) {
            return DateUtils.parseDate(lastCloseTimeStr.split(" - ")[1]);
        }
        return lastCloseTimeEnd;
    }

    public void setLastCloseTimeEnd(Date lastCloseTimeEnd) {
        this.lastCloseTimeEnd = lastCloseTimeEnd;
    }

    public String getSaction() {
        return saction;
    }

    public void setSaction(String saction) {
        this.saction = saction;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
