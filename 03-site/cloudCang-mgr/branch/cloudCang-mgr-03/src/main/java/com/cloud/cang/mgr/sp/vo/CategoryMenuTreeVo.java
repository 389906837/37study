package com.cloud.cang.mgr.sp.vo;

import java.util.ArrayList;
import java.util.List;

public class CategoryMenuTreeVo {

    private List<CategoryMenuTreeVo> nodes = new ArrayList<CategoryMenuTreeVo>();

    private String href;//链接

    private String icon;//图标

    private String text;//名称

    private String id;

    // 是否父分类
    private String iisParent;

    // 父分类ID
    private String sparentId;

    public List<CategoryMenuTreeVo> getNodes() {
        return nodes;
    }

    public void setNodes(List<CategoryMenuTreeVo> nodes) {
        this.nodes = nodes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIisParent() {
        return iisParent;
    }

    public void setIisParent(String iisParent) {
        this.iisParent = iisParent;
    }

    public String getSparentId() {
        return sparentId;
    }

    public void setSparentId(String sparentId) {
        this.sparentId = sparentId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "CategoryMenuTreeVo{" +
                "nodes=" + nodes +
                ", href='" + href + '\'' +
                ", icon='" + icon + '\'' +
                ", text='" + text + '\'' +
                ", id='" + id + '\'' +
                ", iisParent='" + iisParent + '\'' +
                ", sparentId='" + sparentId + '\'' +
                '}';
    }
}
