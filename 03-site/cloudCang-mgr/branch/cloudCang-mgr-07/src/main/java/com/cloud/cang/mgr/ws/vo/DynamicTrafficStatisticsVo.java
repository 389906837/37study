package com.cloud.cang.mgr.ws.vo;

/**
 * Created by YLF on 2020/9/1.
 */
public class DynamicTrafficStatisticsVo extends  TrafficStatisticsVo {
    //必填
    private String dynamic;//true：动态人流量统计，返回总人数、跟踪ID、区域进出人数； false：静态人数统计，返回总人数
    //非必填 当dynamic为True时，必填
    private String case_id;//任务ID

    private String case_init;//每个case的初始化信号  为true时对该case下的跟踪算法进行初始化，为false时重载该case的跟踪状态。当为false且读取不到相应case的信息时，直接重新初始化


    public String getDynamic() {
        return dynamic;
    }

    public void setDynamic(String dynamic) {
        this.dynamic = dynamic;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getCase_init() {
        return case_init;
    }

    public void setCase_init(String case_init) {
        this.case_init = case_init;
    }
}
