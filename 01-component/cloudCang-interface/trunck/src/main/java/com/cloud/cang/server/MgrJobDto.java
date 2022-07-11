package com.cloud.cang.server;

import java.util.Date;

/**
 * @Description 后台升级视觉识别模型，视觉服务，升级APK定时任务
 * @ClassName MgrJobDto
 * @Author Alex
 * @Date 2018年12月7日13:21:15
 * @Version 1.0
 */
public class MgrJobDto {

    private String jobName;             // job名称部分或全部
    private Date time;                  // 定时任务依据的时间
    private Integer paramType;          // 任务类型    10=视觉服务升级 20=视觉库升级 30=客户端APK升级

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getParamType() {
        return paramType;
    }

    public void setParamType(Integer paramType) {
        this.paramType = paramType;
    }
}
