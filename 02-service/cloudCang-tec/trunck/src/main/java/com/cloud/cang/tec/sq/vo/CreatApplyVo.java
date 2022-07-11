package com.cloud.cang.tec.sq.vo;

import com.cloud.cang.model.sq.CreatApply;

/**
 * Created by YLF on 2019/10/29.
 */
public class CreatApplyVo extends CreatApply {
    private String dateCondition;//时间条件

    public String getDateCondition() {
        return dateCondition;
    }

    public void setDateCondition(String dateCondition) {
        this.dateCondition = dateCondition;
    }
}
