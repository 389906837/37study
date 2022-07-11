package com.cloud.cang.api.antbox;

import com.cloud.cang.core.utils.GrpParaUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;

/**
 * 蚂蚁售货机通讯服务
 *
 * @Author chipun.cheng
 * @Date 2017年3月12日 下午11:24:14
 */
@Service
public class BoxServer {
    private final Logger log = LoggerFactory.getLogger(BoxServer.class);

    @Autowired
    private BoxChannelHandler handler;
    private Channel channel;
    private EventLoopGroup bossGroup = null;
    private EventLoopGroup workerGroup = null;
    private String nettyIp = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG,"nettyIp");
    private String nettyPort = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG,"nettyPort");

    public void start() throws InterruptedException {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new BoxChannelInitializer(handler, 2000))
                    .option(ChannelOption.SO_BACKLOG, 100);

            // Bind and start to accept incoming connections.
            channel = b.bind(nettyIp,Integer.parseInt(nettyPort)).sync().channel();

            log.info("Antbox Server Started [ip: {}, port: {}, SO_BACKLOG: {}]", nettyIp,nettyPort, 100);
        } catch (Exception e) {
            log.error("Antbox Server Start fail.");
            log.error(e.getMessage(), e);
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @PreDestroy
    public void stop() {
        if (null == channel) {
            return;
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        channel.closeFuture().syncUninterruptibly();
    }

}
