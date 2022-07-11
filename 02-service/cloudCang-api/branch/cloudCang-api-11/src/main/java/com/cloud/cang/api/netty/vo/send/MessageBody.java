package com.cloud.cang.api.netty.vo.send;

import java.util.List;

/**
 * Created by Alex on 2018/3/21.
 */
public class MessageBody {
    private int type;       //开门的方法指向 必填项
    private int volume;     //需要的参数 非必填(音量值)
    private String openTime;//定时开机时间
    private String offTime; //定时关机时间
    private String method;  //预留参数 非必填
    private String downUrl; //更新软件的下载地址（也可能是更新语音包的地址）
    private List<String> lsitcell;  //远程操作设备关闭识别通道所需的通道唯一编号 也可用于盘货（如本字段为空数组，则盘点全部模块和货仓位置）
    private List<CellBean> cellBeans;  //
    private List<UpdateBean> udateBean;    //更新特征库


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOffTime() {
        return offTime;
    }

    public void setOffTime(String offTime) {
        this.offTime = offTime;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }
}
