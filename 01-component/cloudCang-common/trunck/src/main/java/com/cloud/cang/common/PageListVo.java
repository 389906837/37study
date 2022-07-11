/*
 * Copyright (C) 2006-2012 37cang All rights reserved
 * Date: 2015年5月28日
 * Description:PageListVo.java
 */
package com.cloud.cang.common;

import java.io.Serializable;

public class PageListVo<T> implements Serializable {


	private static final long serialVersionUID = 5436311696304320083L;

    /*private String code;//返回数据状态 默认0
    private String msg;//
    private long count;//总条数
    private T data;//数据集合*/


    /**
     * 分页总条数
     */
    private long total = 0;
    /**
     * 页数
     */
    private int page = 1;
    /**
     * 一页条数
     */
    private long records = 10;

    /**
     * 返回的实体类
     */
    private T rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageListVo{" +
                "total=" + total +
                ", page=" + page +
                ", records=" + records +
                ", rows=" + rows +
                '}';
    }
}
