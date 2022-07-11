package com.cloud.cang.wap.index.vo;

import java.util.List;
import java.util.Map;

/**
 * 购物开门成功
 * Created by yan on 2018/12/27.
 */
public class OpenSuccessData {
    private Map<String,Object> open;
    private String openHint;
    private List preferentialInfos;

    public Map<String, Object> getOpen() {
        return open;
    }

    public void setOpen(Map<String, Object> open) {
        this.open = open;
    }


    public String getOpenHint() {
        return openHint;
    }

    public void setOpenHint(String openHint) {
        this.openHint = openHint;
    }

    public List getPreferentialInfos() {
        return preferentialInfos;
    }

    public void setPreferentialInfos(List preferentialInfos) {
        this.preferentialInfos = preferentialInfos;
    }
}
