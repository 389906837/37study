package com.cloud.cang.api.antbox.protocol;


import io.netty.buffer.ByteBuf;

/**
 * <tt>AntboxObject</tt>解析器
 *
 * @author yong.ma
 * @see {@link AntboxObject}
 * @since 0.0.1
 */
public interface AntboxObjectParser {
    AntboxObject parse(ByteBuf buf);
}
