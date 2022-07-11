package com.cloud.cang.pojo;

import com.cloud.cang.common.BaseVo;

/**
 * @version 1.0
 * @Description: 基础响应VO -- netty 接收 Android 消息
 * @Author: zhouhong
 * @Date: 2018/3/31 18:17
 */
public class BaseResponseVo extends BaseVo {


    private boolean success = false;    //  指令是否成功 code=200 success=true 其余为false
    private Integer code;               //  业务处理代码
    private Integer type;               //  消息类型 10=心跳消息 20=普通消息 30=异常消息 40=注册消息
    private String source;              //  异常源
    private String msg;                 //  业务处理消息
    private String exceptionGrade;      //  异常等级

    public String getExceptionGrade() {
        return exceptionGrade;
    }

    public void setExceptionGrade(String exceptionGrade) {
        this.exceptionGrade = exceptionGrade;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BaseResponseVo{" +
                "success=" + success +
                ", code=" + code +
                ", type=" + type +
                ", source=" + source +
                ", msg='" + msg + '\'' +
                "}" + super.toString();
    }
}
