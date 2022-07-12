package com.cloud.cang.mgr.sp.vo;

/**
 * Created by Alex on 2018/5/29.
 */
public class CategoryTreeVo {
    private String name;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "CategoryTreeVo{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
