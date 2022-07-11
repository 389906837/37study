package com.cloud.cang.server;

import java.util.Date;

/**
 * Created by yan on 2018/11/7.
 */
public class JobDto {
    private String jobName;//job名称部分或全部
    private Date time;//定时任务依据的时间
    // private Date ordertime;//定时任务执行时间

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
