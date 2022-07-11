package com.cloud.cang.api.common.listener;

import com.cloud.cang.api.antbox.BoxServer;
import com.cloud.cang.utils.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @version 1.0
 * @ClassName: cloudCang
 * @Description: netty服务端监听器
 * @Author: zhouhong
 * @Date: 2018/3/31 14:52
 */
public class AntBoxServerListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AntBoxServerListener.class);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            // 启动netty 服务 6900 端口
            BoxServer boxServer = SpringContext.getBean(BoxServer.class);
            boxServer.start();
        } catch (Exception e) {
            logger.error("线程NETTY服务端启动异常：{}", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
