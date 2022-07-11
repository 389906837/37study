/**
 *
 */
package com.cang.os.mgr.wz.vo;

import java.util.Date;

import com.cang.os.bean.wz.Article;

/**
 * @author zhouhong
 */
public class ArticleVo extends Article {
    /**
     * 发布开始日期
     */
    private Date startDate;

    /**
     * 发布结束日期
     */
    private Date endDate;

    private String advertisUrl;//
    private String sketch;//
    private String sort;//不要删除 连表查询使用


    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSketch() {
        return sketch;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }

    public String getAdvertisUrl() {
        return advertisUrl;
    }

    public void setAdvertisUrl(String advertisUrl) {
        this.advertisUrl = advertisUrl;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
