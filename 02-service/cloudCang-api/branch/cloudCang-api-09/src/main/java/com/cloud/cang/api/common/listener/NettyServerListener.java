package com.cloud.cang.api.common.listener;

import com.cloud.cang.api.netty.service.NettyService;
import com.cloud.cang.utils.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.FutureTask;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: netty服务端监听器
 * @Author: zhouhong
 * @Date: 2018/3/31 14:52
 */
public class NettyServerListener implements ServletContextListener {

    private NettyService nettyService;
    private static final Logger logger = LoggerFactory.getLogger(NettyServerListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        nettyService = SpringContext.getBean(NettyService.class);
        new Thread(){
            @Override
            public  void run(){
                try {
                    nettyService.startServer();
                } catch (Exception e) {
                    logger.error("线程NETTY服务端启动异常：{}", e);
                }
            }
        }.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
