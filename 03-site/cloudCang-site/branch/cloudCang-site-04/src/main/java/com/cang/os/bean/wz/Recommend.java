package com.cang.os.bean.wz;

import com.cang.os.bean.BaseBean;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * 推荐表(Recommend)
 **/
public class Recommend extends BaseBean {
    private static final long serialVersionUID = 1L;

    //-----private-----
    private String id;   //主键ID
    private ObjectId advertisId;   //新闻Id
    private Integer sort;//排序
    private String sketch;//简述
    private Date taddTime;   //添加日期
    private String saddUser;   //添加人
    private Date tupdateTime;   //修改日期
    private String supateUser;   //修改人
    private Integer iisExhibition;//是否展示
    private String advertisUrl;//
    private String stitleImage;
    //-----get set-----


    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStitleImage() {
        return stitleImage;
    }

    public void setStitleImage(String stitleImage) {
        this.stitleImage = stitleImage;
    }

    public Integer getIisExhibition() {
        return iisExhibition;
    }

    public void setIisExhibition(Integer iisExhibition) {
        this.iisExhibition = iisExhibition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAdvertisUrl() {
        return advertisUrl;
    }

    public void setAdvertisUrl(String advertisUrl) {
        this.advertisUrl = advertisUrl;
    }

    public ObjectId getAdvertisId() {
        return advertisId;
    }

    public void setAdvertisId(ObjectId advertisId) {
        this.advertisId = advertisId;
    }


    public String getSketch() {
        return sketch;
    }

    public Date getTaddTime() {
        return taddTime;
    }

    public void setTaddTime(Date taddTime) {
        this.taddTime = taddTime;
    }

    public String getSaddUser() {
        return saddUser;
    }

    public void setSaddUser(String saddUser) {
        this.saddUser = saddUser;
    }

    public Date getTupdateTime() {
        return tupdateTime;
    }

    public void setTupdateTime(Date tupdateTime) {
        this.tupdateTime = tupdateTime;
    }

    public String getSupateUser() {
        return supateUser;
    }

    public void setSupateUser(String supateUser) {
        this.supateUser = supateUser;
    }

    public void setSketch(String sketch) {
        this.sketch = sketch;
    }

}
