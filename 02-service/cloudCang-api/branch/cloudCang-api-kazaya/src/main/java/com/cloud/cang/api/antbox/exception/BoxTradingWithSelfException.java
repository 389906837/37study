package com.cloud.cang.api.antbox.exception;

/**
 * 已经在交易中异常，当同一个人在自己开门后再次开门，抛出此异常
 *
 * @Author chipun.cheng
 * @Date 2017年3月19日 上午11:14:43
 */
public class BoxTradingWithSelfException extends AntboxServerException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BoxTradingWithSelfException() {
        super("Device trading with self.");
    }

}
