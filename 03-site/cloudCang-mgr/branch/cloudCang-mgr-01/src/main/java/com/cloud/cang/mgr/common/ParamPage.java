package com.cloud.cang.mgr.common;



import java.io.Serializable;


public class ParamPage implements Serializable{

    //页数
    protected int page = 0;
    // 每页大小
    protected int rowNum = 10;
    // 每页大小
    protected int rows = 10;
    // 每页大小
    protected int limit = 10;


    private String sidx;//排序字段
    private String sord;//排序类型

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "ParamPage{" +
                "page=" + page +
                ", rowNum=" + rowNum +
                ", rows=" + rows +
                ", limit=" + limit +
                ", sidx='" + sidx + '\'' +
                ", sord='" + sord + '\'' +
                '}';
    }
}
