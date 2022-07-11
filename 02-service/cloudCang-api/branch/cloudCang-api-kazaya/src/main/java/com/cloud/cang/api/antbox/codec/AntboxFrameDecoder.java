package com.cloud.cang.api.antbox.codec;

import com.cloud.cang.api.antbox.protocol.AntboxObject;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AntboxFrameDecoder
 *
 * @author yong.ma
 * @since 0.0.1
 */
public class AntboxFrameDecoder extends LengthFieldBasedFrameDecoder {
    private static final Logger LOG = LoggerFactory.getLogger(AntboxFrameDecoder.class);

    public AntboxFrameDecoder() {
        super(AntboxObject.MAX_FRAME_LENGTH, AntboxObject.LENGTH_FIELD_OFFSET,
                AntboxObject.LENGTH_FIELD_LENGTH, AntboxObject.LENGTH_ADJUSTMENT, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext handlerCtx, ByteBuf in) throws Exception {
        if (in.readableBytes() == 0) {
            if (LOG.isWarnEnabled()) {
                // 当lvs listen 时，不做处理
                String ipAddress = AntboxUtil.getRemoteIp(handlerCtx);
                if (AntboxUtil.isLvsListen(ipAddress)) {
                    LOG.warn("lvs listen , from ip : {}", ipAddress);
                } else {
                    LOG.warn("decode(" + handlerCtx + ", " + in + "): Empty buffer");
                }
            }
            return null;
        }
        if (AntboxObject.HEADER_STX != in.getUnsignedByte(0)) {
            final short b = in.readUnsignedByte();
            if (LOG.isWarnEnabled()) {
                String h = Integer.toHexString(b);
                LOG.warn("Unexpected header: 0x" + h + " (not 0x"
                        + Integer.toHexString(AntboxObject.HEADER_STX) + ", discard). BUFFER: 0x"
                        + h + ByteBufUtil.hexDump(in));
                in.clear();
            }
            return null;
        }
        return super.decode(handlerCtx, in);
    }

}
