package com.cloud.cang.mgr.sb.vo;

import com.cloud.cang.model.sb.CameraConfig;

/**
 * @version 1.0
 * @ClassName CameraConfigVo
 * @Description 设备摄像头配置信息
 * @Author zengzexiong
 * @Date 2019年2月22日15:01:01
 */
public class CameraConfigVo  extends CameraConfig{
    private String orderStr;//排序字段
    private String queryCondition;//查询条件

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

    @Override
    public String toString() {
        return "CameraConfigVo{" +
                "orderStr='" + orderStr + '\'' +
                ", queryCondition='" + queryCondition + '\'' +
                '}';
    }
}
