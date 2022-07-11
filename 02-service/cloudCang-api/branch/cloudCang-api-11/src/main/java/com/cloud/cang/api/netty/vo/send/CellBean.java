package com.cloud.cang.api.netty.vo.send;

/**
 * 货道实体信息
 * Created by Alex on 2018/3/28.
 */
public class CellBean {
    String cellid;          //设备某一层识别单元编号（售货设备内唯一）
    String ip;              //--所属识别模块ip地址
    String port;            //--所属识别模块服务端口
    String channel;         //--所属识别模块的通道序号
    String status;          //--代表某个通道的状态（是否打开）

    public String getCellid() {
        return cellid;
    }

    public void setCellid(String cellid) {
        this.cellid = cellid;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CellBean{" +
                "cellid='" + cellid + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", channel='" + channel + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
