package com.cloud.cang.api.antbox.protocol;


import io.netty.buffer.ByteBuf;

/**
 * 可转换为ByteBuf的对象接口
 *
 * @author yong.ma
 * @since 0.0.1
 */
public interface ByteBufable {
    ByteBuf toByteBuf();

    void writeTo(ByteBuf out);
}
