package com.cloud.cang.api.antbox.constant;

/**
 * 售货机状态
 *
 * @Author chipun.cheng
 * @Date 2017年3月25日 下午10:42:47
 */
public enum BoxStatus {
    /**
     * 初始状态
     */
    INIT,

    /**
     * 空闲状态
     */
    IDLE,

    /**
     * 登录状态
     */
    LOGIN,

    /**
     * 开门状态
     */
    OPENED,

    /**
     * 盘点中
     */
    BUSY
}
