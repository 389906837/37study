package com.cloud.cang.facePojo;


import com.cloud.cang.faceCommon.FaceVo;

/**
 * @version 1.0
 * @Description: 基础请求VO -- netty 发送给 Android
 * @Author: zhouhong
 * @Date: 2018/3/31 18:15
 */
public class FaceRequestVo extends FaceVo {

    private boolean success = false;        //  指令是否成功 success=true 其余为false
    private String extendsParams;           //  扩展参数 JSON String
    private Integer code = 100;                   //  业务处理代码

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getExtendsParams() {
        return extendsParams;
    }

    public void setExtendsParams(String extendsParams) {
        this.extendsParams = extendsParams;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "FaceRequestVo{" +
                "success=" + success + '\'' +
                ", extendsParams='" + extendsParams + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
