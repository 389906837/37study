package com.cloud.cang.rec.op.domain;

import com.cloud.cang.model.op.TransferLog;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * TransferLog扩展类
 * Created by yan on 2018/10/16.
 */
public class TransferLogDomain extends TransferLog {
    private String orderStr; //排序
    private String userName;
    private String appName;
    private String interfaceName;

    private String toperateDateStr;//操作时间
    private Date operateStarDate;//操作时间区间开始时间
    private Date operateEndDate;//操作时间结束时间

    public String getToperateDateStr() {
        return toperateDateStr;
    }

    public void setToperateDateStr(String toperateDateStr) {
        this.toperateDateStr = toperateDateStr;
    }

    public Date getOperateStarDate() {
        if (StringUtil.isNotBlank(this.toperateDateStr)) {
            return DateUtils.parseDate(toperateDateStr.split(" - ")[0]);
        }
        return operateStarDate;
    }

    public void setOperateStarDate(Date operateStarDate) {
        this.operateStarDate = operateStarDate;
    }

    public Date getOperateEndDate() {
        if (StringUtil.isNotBlank(this.toperateDateStr)) {
            return DateUtils.parseDate(toperateDateStr.split(" - ")[1]);
        }
        return operateEndDate;
    }

    public void setOperateEndDate(Date operateEndDate) {
        this.operateEndDate = operateEndDate;
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

    public String getUserName() {

        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }
}
