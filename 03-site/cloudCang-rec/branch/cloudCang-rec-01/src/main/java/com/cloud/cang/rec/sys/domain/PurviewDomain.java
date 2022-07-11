package com.cloud.cang.rec.sys.domain;


import com.cloud.cang.model.sys.Purview;

/**
 * @version 1.0
 * @Description: 权限domain
 * @Author: yanlingfeng
 * @Date: 2018/2/10 16:14
 */
public class PurviewDomain extends Purview {
    private String menuParentId; //父菜单ID
    private String menuId; //菜单ID
    private String menuName; //菜单名称
    private Integer isSelect;//是否选择
    private Integer isRoot;//是否根菜单
    private String menuParentName; //父菜单名称

    public Integer getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(Integer isSelect) {
        this.isSelect = isSelect;
    }

    public String getMenuParentId() {
        return menuParentId;
    }

    public void setMenuParentId(String menuParentId) {
        this.menuParentId = menuParentId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Integer getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Integer isRoot) {
        this.isRoot = isRoot;
    }

    public String getMenuParentName() {
        return menuParentName;
    }

    public void setMenuParentName(String menuParentName) {
        this.menuParentName = menuParentName;
    }

    @Override
    public String toString() {
        return "PurviewDomain{" +
                "menuParentId='" + menuParentId + '\'' +
                ", menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", isSelect=" + isSelect +
                ", isRoot=" + isRoot +
                ", menuParentName='" + menuParentName + '\'' +
                '}';
    }
}
