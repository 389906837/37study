package com.cloud.cang.api.antbox.exception;

import com.cloud.cang.api.antbox.dto.CustomerDto;

/**
 * 售货机正与其他人进行交易中抛出此异常
 *
 * @Author chipun.cheng
 * @Date 2017年3月19日 上午11:45:25
 */
public class BoxTradingWithOtherException extends AntboxServerException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BoxTradingWithOtherException(CustomerDto tradingUser) {
        super("Device trading with user: " + tradingUser.getId());
    }

}
