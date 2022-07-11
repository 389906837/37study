package com.cloud.cang.api.socketIo.vo;

import java.io.Serializable;

/**
 * @version 1.0
 * @Description: socketIO 交互VO
 * @Author: zhouhong
 * @Date: 2018/3/30 13:46
 */
public class SocketResponseVo<T> implements Serializable {
    /**
     * 是否成功标志。
     * 需要特别说明的是，如果返回处理中，则此处为false
     */
    private boolean isSuccess;
    /**
     * 错误编码
     */
    private int errorCode;

    /**
     * 错误返回说明
     */
    private String msg;
    /**
     * 操作类型
     * 10 购物开门 20 补货员开门 30 购物关门 40 补货员关门 50 购物异常 60 补货异常
     */
    private Integer types;
    /**
     * 返回的实体类
     */
    private T data;


    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTypes() {
        return types;
    }

    public void setTypes(Integer types) {
        this.types = types;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SocketResponseVo() {
        super();
        this.isSuccess = true;
        this.errorCode=0;
        this.data=null;
    }

    public SocketResponseVo(T obj) {
        super();
        this.isSuccess = true;
        this.errorCode=0;
        this.data=obj;
    }

    public SocketResponseVo(boolean isSuccess, int errorCode, String msg) {
        this.isSuccess = isSuccess;
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public SocketResponseVo(boolean isSuccess, Integer types) {
        this.isSuccess = isSuccess;
        this.types = types;
    }

    public static <T> SocketResponseVo<T> getSuccessResponse(T obj){
        return new SocketResponseVo<T>(obj);
    }
    public static <T> SocketResponseVo<T> getSuccessResponse(){
        return new SocketResponseVo<T>();
    }
    @Override
    public String toString() {
        return "SocketResponseVo{" +
                "isSuccess=" + isSuccess +
                ", errorCode=" + errorCode +
                ", msg='" + msg + '\'' +
                ", types=" + types +
                ", data=" + data +
                '}';
    }
}
