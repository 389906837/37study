package com.cloud.cang.core.common.aiFace;

import com.cloud.cang.generic.GenericEntity;

import java.util.Date;

/**
 * @version 1.0
 * @ClassName: ResponseAip
 * @Description: 人脸识别通用返回结果
 * @Author: zengzexiong
 * @Date: 2018年7月17日16:09:32
 */
public class ResponseAip extends GenericEntity {

    private Result result;
    private String error_msg;
    private String log_id;
    private Integer error_code;
    private Integer cached;
    private Date timestamp;


    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public Integer getError_code() {
        return error_code;
    }

    public void setError_code(Integer error_code) {
        this.error_code = error_code;
    }

    public Integer getCached() {
        return cached;
    }

    public void setCached(Integer cached) {
        this.cached = cached;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        if (null != result) {
            return "ResponseAip{" +
                    "result='" + result.toString() + '\'' +
                    ", error_msg='" + error_msg + '\'' +
                    ", log_id='" + log_id + '\'' +
                    ", error_code=" + error_code +
                    ", cached=" + cached +
                    ", timestamp=" + timestamp +
                    '}';
        } else {
            return "ResponseAip{" +
                    "error_msg='" + error_msg + '\'' +
                    ", log_id='" + log_id + '\'' +
                    ", error_code=" + error_code +
                    ", cached=" + cached +
                    ", timestamp=" + timestamp +
                    '}';
        }
    }
}
