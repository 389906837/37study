package com.cloud.cang.mgr.sys.vo;

/**
 * @version 1.0
 * @description: 首页用户展示 Vo
 * @author:Yanlingfeng
 * @time:2018-03-24 14:07:05
 */
public class IndexMemberVo {
    private Integer tdAdd;//今日新增
    private Integer ydAdd;//昨日新增
    private Integer mAdd;//本月新增
    private Integer memberTotal;//会员总数

    public Integer getTdAdd() {
        return tdAdd;
    }

    public void setTdAdd(Integer tdAdd) {
        this.tdAdd = tdAdd;
    }

    public Integer getYdAdd() {
        return ydAdd;
    }

    public void setYdAdd(Integer ydAdd) {
        this.ydAdd = ydAdd;
    }

    public Integer getmAdd() {
        return mAdd;
    }

    public void setmAdd(Integer mAdd) {
        this.mAdd = mAdd;
    }

    public Integer getMemberTotal() {
        return memberTotal;
    }

    public void setMemberTotal(Integer memberTotal) {
        this.memberTotal = memberTotal;
    }
}
