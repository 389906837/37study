package com.cloud.cang.tec.ac.vo;

/**
 * 用户持有券券过期短信提醒 Vo
 * Created by yan on 2018/6/20.
 */
public class CouponUserVo {
    private String smemberId;
    private String smemberName;
    private Integer xj;//现金券
    private Integer dk;//抵扣券
    private Integer mj;//满减券
    private Integer sp;//加息券


    public String getSmemberName() {
        return smemberName;
    }

    public void setSmemberName(String smemberName) {
        this.smemberName = smemberName;
    }

    @Override
    public String toString() {
        return "CouponUserVo{" +
                "smemberId='" + smemberId + '\'' +
                ", smemberName='" + smemberName + '\'' +
                ", xj=" + xj +
                ", dk=" + dk +
                ", mj=" + mj +
                ", sp=" + sp +
                '}';
    }

    public String getSmemberId() {
        return smemberId;
    }

    public void setSmemberId(String smemberId) {
        this.smemberId = smemberId;
    }

    public Integer getXj() {
        return xj;
    }

    public void setXj(Integer xj) {
        this.xj = xj;
    }

    public Integer getDk() {
        return dk;
    }

    public void setDk(Integer dk) {
        this.dk = dk;
    }

    public Integer getMj() {
        return mj;
    }

    public void setMj(Integer mj) {
        this.mj = mj;
    }

    public Integer getSp() {
        return sp;
    }

    public void setSp(Integer sp) {
        this.sp = sp;
    }
}
