package com.cloud.cang.rec.sys.vo;

/**
 * Created by yan on 2018/3/7.
 */
public class MenuVo {
    private String isSelect;//用户是否已选择 0:否 1:是
    private String smenuName; //数据字典快捷菜单详情名(快捷菜单名)
    private String detailId; //数据字典快捷菜单详情Id

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getSmenuName() {
        return smenuName;
    }

    public void setSmenuName(String smenuName) {
        this.smenuName = smenuName;
    }

    public String getIsSelect() {
        return isSelect;
    }

    public void setIsSelect(String isSelect) {
        this.isSelect = isSelect;
    }
}
