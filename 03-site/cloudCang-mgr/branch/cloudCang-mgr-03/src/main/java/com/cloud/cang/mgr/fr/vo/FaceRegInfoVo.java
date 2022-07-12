package com.cloud.cang.mgr.fr.vo;

import com.cloud.cang.model.fr.FaceRegInfo;

/**
 * @version 1.0
 * @ClassName FaceRegisterInfoVo
 * @Description 年会人脸信息注册搜索对象
 * @Author zengzexiong
 * @Date 2018年12月11日09:20:32
 */
public class FaceRegInfoVo extends FaceRegInfo {
    private String orderStr;//排序字段
    private String queryCondition;          /* 查询条件 */

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    public String getQueryCondition() {
        return queryCondition;
    }

    public void setQueryCondition(String queryCondition) {
        this.queryCondition = queryCondition;
    }
}
