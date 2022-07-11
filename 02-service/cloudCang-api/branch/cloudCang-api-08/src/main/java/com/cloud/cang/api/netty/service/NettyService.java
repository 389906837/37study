package com.cloud.cang.api.netty.service;

import com.cloud.cang.api.netty.handler.ServerInboundHandler;
import com.cloud.cang.api.sb.service.DeviceInfoService;
import com.cloud.cang.api.sb.service.DeviceRegisterService;
import com.cloud.cang.api.singleton.SingletonClientMap;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.corundumstudio.socketio.SocketIOClient;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.ChannelGroupFuture;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.ClientAuth;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @ClassName NettyService
 * @Description netty后台服务类
 * @Author zengzexiong
 * @Date 2018年1月23日15:31:25
 */
@Service("nettyService")
public class NettyService {

    @Autowired
    private ICached iCached;

    @Autowired
    DeviceRegisterService deviceRegisterService;    // 设备注册服务

    @Autowired
    DeviceInfoService deviceInfoService;            // 设备基础信息

    @Autowired
    NettyMsgService nettyMsgService;    // netty接收消息处理类

    @Autowired
    NettyExceptionMsgService nettyExceptionMsgService;  // netty异常消息处理服务类

    private static final Logger logger = LoggerFactory.getLogger(NettyService.class);

    /*-- Netty相关配置参数开始 --*/
    private String nettyIp = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG,"nettyIp");    //数据字典配置
    private String nettyPort = GrpParaUtil.getValue(GrpParaUtil.TCP_CONFIG,"nettyPort");    //数据字典配置
   /* private static final String nettyIp = "10.0.101.237";//服务器绑定IP地址
    private static final String nettyPort = "28859";//服务器端口号*/
//    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);//Netty提供了ChannelGroup来用于保存Channel组，用于广播
    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(1);//boss线程组
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup();//worker线程组

    /*-- Netty相关配置参数结束 --*/

    private ServerBootstrap bootstrap = null;//Netty服务端应用
    private static List<String> tokenList = new ArrayList<String>();//用于保存所有客户端组
    private String result = null;//向客户端发送消息结果
    private static ConcurrentMap<String, ChannelHandlerContext> channelMap = SingletonClientMap.newInstance().getCtxMap();//<deviceId,ctx>
    private ConcurrentMap<String, SocketIOClient> socketIOMap = SingletonClientMap.newInstance().getSocketIoMap();  //单例map
    private ChannelGroup channels = SingletonClientMap.newInstance().getChannelGroup();

    public NettyService() {

    }

    /**
     * 开启未加密服务器端
     */
    public void startServer() {
        try {
            logger.info("售货机NETTY服务端开始启动...");
            //开启Netty服务
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))//INFO级别日志
                    .childOption(ChannelOption.TCP_NODELAY, true)//禁用nagle算法
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new IdleStateHandler(25, 0, 0, TimeUnit.SECONDS)); // 心跳检测handler
                            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new ServerInboundHandler(channels, channelMap, deviceRegisterService, iCached, socketIOMap, nettyMsgService, deviceInfoService, nettyExceptionMsgService));
                        }
                    });
            bootstrap.bind(nettyIp, Integer.parseInt(nettyPort)).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("NETTY服务端启动异常：{}", e);
        } finally {
            logger.info("NETTY服务端启动结束");
            shutdown();
        }
    }

    /**
     * 开启SSL服务器端
     * @param ip 服务器IP
     * @param port 端口
     */
    public void startSSLServer(String ip, int port) {
        try {
            logger.info("NETTY SSL模式服务端开始启动...");
            //加载SSL加密证书
            File certificate = new File("E:/zzx/testCert/opencrt/server.crt");//证书
            File privateKey = new File("E:/zzx/testCert/opencrt/pkcs8_server.key");//私钥
            File rootFile = new File("E:/zzx/testCert/opencrt/ca.crt");//CA证书
            //设置Netty中使用的SSL模式
            final SslContext sslContext = SslContextBuilder.forServer(certificate, privateKey).trustManager(rootFile).clientAuth(ClientAuth.REQUIRE).build();
            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))//INFO级别日志
                    .childOption(ChannelOption.TCP_NODELAY, true)//禁用nagle算法
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            SslHandler sslHandler = sslContext.newHandler(socketChannel.alloc());
                            pipeline.addLast(sslHandler);//SSL加密
                            pipeline.addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                            pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new ServerInboundHandler(channels, channelMap, deviceRegisterService, iCached, socketIOMap, nettyMsgService, deviceInfoService, nettyExceptionMsgService));
                        }
                    });
            bootstrap.bind(ip, port).sync().channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error("NETTY SSL模式服务端启动异常：{}", e);
        } finally {
            logger.info("NETTY SSL模式服务端启动结束");
            shutdown();
        }
    }


    /**
     * 优雅地关闭服务器
     */
    public void stopServer() throws Exception {
        shutdown();
    }

    /**
     * Netty自带的优雅关闭服务端方法，关闭线程组，关闭服务器
     */
    public static void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    /**
     * 给所有的客户端推送消息
     * @param msg 消息
     */
    public String sendMsgToAll(String msg) {
        ChannelGroupFuture future = channels.writeAndFlush(msg + "\r\n");//利用netty自带的通道组给所有在线客户端发送消息
        future.addListener(new ChannelFutureListener() {//发送消息之后的监听
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    result = "failed";
                } else {
                    result = "success";
                }
            }
        });
        return result;
    }



    public ServerBootstrap getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(ServerBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public void addBlackList(String clientIp) {

    }

}
