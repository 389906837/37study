package com.cloud.cang.sb;

import com.cloud.cang.common.SuperDto;

import java.util.List;

/**
 * 长连接后台服务，设备信息
 *
 * @author zengzexiong
 * @version 1.0
 * @date 2018年1月24日14:49:35
 */
public class DeviceDto extends SuperDto {



    /* ----------参数结束 ----------*/


    /*
     * 设备ID,如果是多台用逗号“，”拼接
     */
    private String id;  //必填

    /*
     * 后台管理系统向客户端发送的指令操作参数，
     * 按照指令选填，音量定时开关机（必填），
     * 其他选填
     */
    private String operateParam;

    private String function;    //必填----后台管理系统向客户端发送的指令
    private String userId;  //必填----操作员ID（后台管理系统操作员ID，支付宝/微信用户，定时任务）


    private String scode;   //未使用----设备编号,如果是多台用逗号“，”拼接
    private Integer istatus;    // 未使用----状态：10=正常 20=故障 30=报废
    private List<DeviceCommodityDto> commodityDtoList;  //（未使用）----设备商品信息

    /* ----------参数结束 ----------*/


    /*
     *
     */

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOperateParam() {
        return operateParam;
    }

    public void setOperateParam(String operateParam) {
        this.operateParam = operateParam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScode() {
        return scode;
    }

    public void setScode(String scode) {
        this.scode = scode;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public Integer getIstatus() {
        return istatus;
    }

    public void setIstatus(Integer istatus) {
        this.istatus = istatus;
    }

    public List<DeviceCommodityDto> getCommodityDtoList() {
        return commodityDtoList;
    }

    public void setCommodityDtoList(List<DeviceCommodityDto> commodityDtoList) {
        this.commodityDtoList = commodityDtoList;
    }

    @Override
    public String toString() {
        return "DeviceDto{" +
                "id='" + id + '\'' +
                ", scode='" + scode + '\'' +
                ", function='" + function + '\'' +
                ", operateParam='" + operateParam + '\'' +
                ", istatus=" + istatus +
                ", commodityDtoList=" + commodityDtoList +
                '}';
    }
}
