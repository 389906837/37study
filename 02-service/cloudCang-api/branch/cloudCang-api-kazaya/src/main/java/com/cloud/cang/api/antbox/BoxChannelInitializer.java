package com.cloud.cang.api.antbox;

import com.cloud.cang.api.antbox.codec.AntboxFrameDecoder;
import com.cloud.cang.api.antbox.codec.AntboxMessageDecoder;
import com.cloud.cang.api.antbox.codec.AntboxMessageEncoder;
import com.cloud.cang.api.antbox.constant.DecoderMode;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 读写器通道初始化
 */
@Sharable
class BoxChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final BoxChannelHandler handler;
    private final int readTimeoutSeconds;

    BoxChannelInitializer(BoxChannelHandler handler, Integer readTimeoutSeconds) {
        this.handler = handler;
        this.readTimeoutSeconds = readTimeoutSeconds;
    }

    @Override
    public void initChannel(SocketChannel ch) {
        //解码器
        final AntboxMessageDecoder DECODER = new AntboxMessageDecoder(DecoderMode.SERVER);
        //编码器
        final AntboxMessageEncoder ENCODER = new AntboxMessageEncoder();

        ChannelPipeline pl = ch.pipeline();

        pl.addLast(new ReadTimeoutHandler(this.readTimeoutSeconds));
        pl.addLast(new AntboxFrameDecoder());
        pl.addLast(DECODER);
        pl.addLast(ENCODER);
        // 对客户端消息进行处理
        // BoxChannelHandler extends ChannelInboundHandler
        pl.addLast(handler);
    }
}