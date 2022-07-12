package com.cloud.cang.mgr.wz.vo;

import com.cloud.cang.model.wz.KeyWords;

import java.util.Date;

/**
 * @description 网站关键字VO
 * @author ChangTanchang
 * @time 2018-03-29 09:17:15
 * @fileName KeyWordsController.java
 * @version 1.0
 */
public class KeyWordsVo extends KeyWords{
    //开始 添加日期
    private Date taddTimeStart;

    //结束 添加日期
    private Date taddTimeEnd;

    // 排序
    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public Date getTaddTimeStart() {
        return taddTimeStart;
    }

    public void setTaddTimeStart(Date taddTimeStart) {
        this.taddTimeStart = taddTimeStart;
    }

    public Date getTaddTimeEnd() {
        return taddTimeEnd;
    }

    public void setTaddTimeEnd(Date taddTimeEnd) {
        this.taddTimeEnd = taddTimeEnd;
    }

    @Override
    public String toString() {
        return "KeyWordsVo{" +
                ", taddTimeStart=" + taddTimeStart +
                ", taddTimeEnd=" + taddTimeEnd +
                ", orderStr='" + orderStr + '\'' +
                '}';
    }
}
