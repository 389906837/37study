package com.cloud.cang.tec.ac.vo;

import com.cloud.cang.model.ac.ActivityConf;

/**
 * Created by yan on 2018/3/21.
 */
public class ActivityConfVo extends ActivityConf {
    private String sconfNameList; //活动名

    public String getSconfNameList() {
        return sconfNameList;
    }

    public void setSconfNameList(String sconfNameList) {
        this.sconfNameList = sconfNameList;
    }
}
