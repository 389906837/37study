package com.cloud.cang.dispatcher.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cloud.cang.dispatcher.utils.DispatcherUtils;
import org.apache.catalina.Container;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 监听程序，用于为服务发现使用，取得tomcat的端口和上下文配置信息
 * @version 1.0
 */
public class ServiceDispatcherListener implements ServletContextListener {
	
	public static Logger logger = LoggerFactory
			.getLogger(ServiceDispatcherListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext ctx = sce.getServletContext();
		logger.info("---------------启动服务信息监听----------------------");
		try {
			Object o = FieldUtils.readField(ctx, "context", true);
			StandardContext sCtx = (StandardContext) FieldUtils.readField(o,
					"context", true);
			logger.info("Tomcat Path :{}",sCtx.getPath());
			DispatcherUtils.setAppContext(sCtx.getPath());
			Container container = sCtx;

			Container c = container.getParent();
			while (c != null && !(c instanceof StandardEngine)) {
				c = c.getParent();
			}
			if (c != null) {
				StandardEngine engine = (StandardEngine) c;
				for (Connector connector : engine.getService().findConnectors()) {
					if (connector.getProtocol().toLowerCase().indexOf("http") != -1) {
						logger.info("Tomcat Port :{}",connector.getPort());
						DispatcherUtils.setPort(connector.getPort());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("---------------服务信息监听结束----------------------");

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}

}