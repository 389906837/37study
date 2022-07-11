package com.cloud.cang.api.antbox.codec;


import com.cloud.cang.api.antbox.constant.DecoderMode;
import com.cloud.cang.api.antbox.protocol.AntboxDataPkg;
import com.cloud.cang.api.antbox.protocol.AntboxObject;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 自动售货机解码器(ByteBuf to AntboxResponse)
 *
 * @author yong.ma
 * @since 0.0.1
 */
public class AntboxMessageDecoder extends ByteToMessageDecoder {

    private final DecoderMode mode;

    public AntboxMessageDecoder(DecoderMode mode) {
        this.mode = mode;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        //System.out.println("===================================接收客户端指令："+ ByteBufUtil.hexDump(in));
        if (in.readableBytes() == 0) {
            return;
        }

        final int expectedCrc = AntboxUtil.calcCrc(in, AntboxDataPkg.LENGTH_FIELD_OFFSET, AntboxDataPkg.CRC_BYTES_NUM);
        AntboxObject obj = AntboxObject.valueOf(in, mode);
        obj.checkHead();
        obj.checkCrc(expectedCrc);


        out.add(obj);
    }
}
