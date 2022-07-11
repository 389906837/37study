package com.cloud.cang.api.antbox.codec;

import com.cloud.cang.api.antbox.protocol.AntboxObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 自动售货机编码器(AntboxObject to ByteBuf)
 *
 * @author yong.ma
 * @since 0.0.1
 */
public class AntboxMessageEncoder extends MessageToByteEncoder<AntboxObject> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AntboxObject msg, ByteBuf out)
            throws Exception {
        msg.writeTo(out);
    }
}
