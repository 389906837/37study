package com.cloud.cang.api.antbox.protocol;

/**
 * CRC功能
 *
 * @author yong.ma
 * @see 0.0.1
 */
public interface CrcOperation {

    /**
     * CRC校验
     */
    public void checkCrc(int expected);
}
