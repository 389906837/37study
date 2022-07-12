package com.cloud.cang.mgr.xx.vo;

import com.cloud.cang.model.xx.MsgTemplateMain;

import java.util.Date;

/**
 * 模板主表表 Vo
 * @author ChangTanchang
 * @time:2018-01-19 10:07:00
 * @version 1.0
 */
public class MsgTemplateMainVo extends MsgTemplateMain{

    //开始 添加时间
    private Date taddtimeStart;

    //结束 添加时间
    private Date taddtimeEnd;

    //开始 修改时间
    private Date tupdatetimeStart;

    //结束 修改时间
    private Date tupdatetimeEnd;

    // 排序字段
    private String orderStr;

    // 商户名称
    private String merchantName;

    /**添加时间 开始*/
    public Date getTaddtimeStart(){
        return taddtimeStart;
    }
    /**添加时间 开始*/
    public void setTaddtimeStart(Date taddtime){
        this.taddtimeStart=taddtime;
    }
    /**添加时间结束*/
    public Date getTaddtimeEnd(){
        return taddtimeEnd;
    }
    /**添加时间结束*/
    public void setTaddtimeEnd(Date taddtime){
        this.taddtimeEnd=taddtime;
    }
    /**修改时间 开始*/
    public Date getTupdatetimeStart(){
        return tupdatetimeStart;
    }
    /**修改时间 开始*/
    public void setTupdatetimeStart(Date tupdatetime){
        this.tupdatetimeStart=tupdatetime;
    }
    /**修改时间结束*/
    public Date getTupdatetimeEnd(){
        return tupdatetimeEnd;
    }
    /**修改时间结束*/
    public void setTupdatetimeEnd(Date tupdatetime){
        this.tupdatetimeEnd=tupdatetime;
    }

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    @Override
    public String toString() {
        return "MsgTemplateMainVo{" +
                "taddtimeStart=" + taddtimeStart +
                ", taddtimeEnd=" + taddtimeEnd +
                ", tupdatetimeStart=" + tupdatetimeStart +
                ", tupdatetimeEnd=" + tupdatetimeEnd +
                ", orderStr='" + orderStr + '\'' +
                ", merchantName='" + merchantName + '\'' +
                '}';
    }
}
