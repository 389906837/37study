package com.cloud.cang.open.sdk.model.response;

import com.cloud.cang.open.sdk.mapping.BaseField;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: 视觉识别结果model
 * @Author: zhouhong
 * @Date: 2018/9/4 15:31
 */
public class GoodDetail implements Serializable {
    @BaseField("vrCode")
    private String vrCode;
    @BaseField("number")
    private String number;

    //2020/3/12 qzg 带坐标
    @BaseField("prob")
    private String prob;
    @BaseField("posx")
    private String posx;
    @BaseField("posy")
    private String posy;
    @BaseField("posw")
    private String posw;
    @BaseField("posh")
    private String posh;

    public String getVrCode() {
        return vrCode;
    }

    public void setVrCode(String vrCode) {
        this.vrCode = vrCode;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getProb() {
        return prob;
    }

    public void setProb(String prob) {
        this.prob = prob;
    }

    public String getPosx() {
        return posx;
    }

    public void setPosx(String posx) {
        this.posx = posx;
    }

    public String getPosy() {
        return posy;
    }

    public void setPosy(String posy) {
        this.posy = posy;
    }

    public String getPosw() {
        return posw;
    }

    public void setPosw(String posw) {
        this.posw = posw;
    }

    public String getPosh() {
        return posh;
    }

    public void setPosh(String posh) {
        this.posh = posh;
    }

    @Override
    public String toString() {
        return "GoodDetail{" +
                "vrCode='" + vrCode + '\'' +
                ", number='" + number + '\'' +
                ", prob='" + prob + '\'' +
                ", posx='" + posx + '\'' +
                ", posy='" + posy + '\'' +
                ", posw='" + posw + '\'' +
                ", posh='" + posh + '\'' +
                '}';
    }
}
