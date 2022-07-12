package com.cloud.cang.mgr.sm.vo;

import com.cloud.cang.model.sm.StockOperRecord;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;

import java.util.Date;

/**
 * @description: 库存操作日志 VO
 * @author:ChangTanchang
 * @time:2018-01-15 15:29:05
 * @version 1.0
 */
public class StockOperLogVo extends StockOperRecord {

    // 商户名称
    private String shName;

    // 设备名称
    private String sbName;

    // 设备地址
    private String adress;

    // 商品名称
    private String spName;

    // 日期参数
    private String toperateStartDateStr;

    // 开始操作日期
    private Date taddTimeStart;

    // 结束操作日期
    private Date taddTimeEnd;

    // 排序字段
    private String orderStr;

    // 搜索条件
    private String condition;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getShName() {
        return shName;
    }

    public void setShName(String shName) {
        this.shName = shName;
    }

    public String getSbName() {
        return sbName;
    }

    public void setSbName(String sbName) {
        this.sbName = sbName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getToperateStartDateStr() {
        return toperateStartDateStr;
    }

    public void setToperateStartDateStr(String toperateStartDateStr) {
        this.toperateStartDateStr = toperateStartDateStr;
    }

    public Date getTaddTimeStart() {
        if (StringUtil.isNotBlank(this.toperateStartDateStr)) {
            return DateUtils.parseDate(toperateStartDateStr.split(" - ")[0]);
        }
        return taddTimeStart;
    }

    public void setTaddTimeStart(Date taddTimeStart) {
        this.taddTimeStart = taddTimeStart;
    }

    public Date getTaddTimeEnd() {
        if (StringUtil.isNotBlank(this.toperateStartDateStr)) {
            return DateUtils.parseDate(toperateStartDateStr.split(" - ")[1]);
        }
        return taddTimeEnd;
    }

    public void setTaddTimeEnd(Date taddTimeEnd) {
        this.taddTimeEnd = taddTimeEnd;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "StockOperLogVo{" +
                "shName='" + shName + '\'' +
                ", sbName='" + sbName + '\'' +
                ", adress='" + adress + '\'' +
                ", spName='" + spName + '\'' +
                ", toperateStartDateStr='" + toperateStartDateStr + '\'' +
                ", taddTimeStart=" + taddTimeStart +
                ", taddTimeEnd=" + taddTimeEnd +
                ", orderStr='" + orderStr + '\'' +
                ", condition='" + condition + '\'' +
                '}';
    }
}
