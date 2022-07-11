package com.cloud.cang.core.rmq.interfaces;

public enum HandlerEnum {
    ACK(10," 正常消费,MQ移除队列"),
    ACK_BIZ_FAIL(11," 正常消费,MQ移除队列,业务处理失败"),
    NACK(20,"重回队列"),
    REJECT(30,"重回队列3次,若还未正常消费,则消息放入死信队列");

    private int code;
    private String desc;

    HandlerEnum(int code,String desc){

    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
