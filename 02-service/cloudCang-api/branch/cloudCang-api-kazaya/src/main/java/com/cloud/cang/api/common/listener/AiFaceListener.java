//package com.cloud.cang.api.common.listener;
//
//import com.cloud.cang.api.netty.service.AiFaceService;
//import com.cloud.cang.utils.SpringContext;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
///**
// * @version 1.0
// * @ClassName: AiFaceListener
// * @Description: 人脸识别netty服务端监听器
// * @Author: zengzexiong
// * @Date: 2018年7月19日17:22:30
// */
//public class AiFaceListener implements ServletContextListener{
//
//    private AiFaceService aiFaceService;
//    private static final Logger logger = LoggerFactory.getLogger(AiFaceService.class);
//
//    @Override
//    public void contextInitialized(ServletContextEvent servletContextEvent) {
//        aiFaceService = SpringContext.getBean(AiFaceService.class);
//        new Thread() {
//            @Override
//            public void run() {
//                try {
//                    aiFaceService.startServer();
//                } catch (Exception e) {
//                    logger.error("人脸识别线程NETTY服务端启动异常：{}", e);
//                }
//            }
//        }.start();
//    }
//
//    @Override
//    public void contextDestroyed(ServletContextEvent servletContextEvent) {
//
//    }
//}
