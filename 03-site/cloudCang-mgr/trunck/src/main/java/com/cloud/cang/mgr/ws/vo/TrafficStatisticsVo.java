package com.cloud.cang.mgr.ws.vo;

/**
 * 人流量统计参数
 * Created by YLF on 2020/8/31.
 */
public class TrafficStatisticsVo {
    private String base64Img;//图片base64
    //非必填
    private String area;//特定框选区域坐标，支持多个多边形区域，最多支持10个区域，如输入超过10个区域，截取前10个区域进行识别。
    // 此参数为空或无此参数、或area参数设置错误时，默认识别整个图片的人数 。 当dynamic为True时，必填
    private String show;//是否输出渲染的图片，默认不返回，选true时返回渲染后的图片(base64)，其它无效值或为空则默认false

    public String getBase64Img() {
        return base64Img;
    }

    public void setBase64Img(String base64Img) {
        this.base64Img = base64Img;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }
}
