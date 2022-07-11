package com.cloud.cang.open.api.cr.thread;

import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.eye.sdk.bean.ComplexResultBean;
import com.cloud.cang.eye.sdk.bean.HostBean;
import com.cloud.cang.eye.sdk.socket.Connection;
import com.cloud.cang.lock.JedisLock;

import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.model.xx.MsgTask;
import com.cloud.cang.open.api.common.utils.EnviornConfig;
import com.cloud.cang.open.api.common.utils.IPUtils;
import com.cloud.cang.open.api.common.utils.VisualUtils;
import com.cloud.cang.open.api.cr.service.ServerListService;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

/**
 * 定时查询GPU服务器初始化状态任务
 * @author zhouhong
 * @version 1.0
 */
@Component
public class GpuStatusThreadTask {
    
    private static final Logger logger = LoggerFactory.getLogger(GpuStatusThreadTask.class);
    
    @Autowired
	private EnviornConfig enviornConfig;
    @Autowired
	private JedisCluster jedisCluster;
	@Autowired
	private ServerListService serverListService;
    private boolean isRun  = true;
    JedisLock gpuQueryLock = null;


    @PostConstruct
    public void start() {
		if (enviornConfig.isProduct()) {
			logger.info("定时查询GPU服务器初始化状态任务启动......");
			gpuQueryLock = new JedisLock(jedisCluster, "lock_gpu_init_status_query");
			gpuInitStatusSchedule();
		}
    }
    
    /**
     * 定时查询GPU服务器初始化状态 任务调度
     */
	private void gpuInitStatusSchedule() {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				logger.info("定时查询GPU服务器初始化状态任务启动......");
				do {
					try {
						//5分钟一次
						TimeUnit.MINUTES.sleep(5);
						if (gpuQueryLock.acquire()) {

							String sip = IPUtils.getLocalAddress();
							if (StringUtil.isBlank(sip)) {
								logger.error("获取本机IP错误，定时查询GPU服务器初始化状态任务执行失败!");
								continue;
							}
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("sip", sip);
							List<ServerList> serverLists = serverListService.selectByMap(map);
							if (null == serverLists || serverLists.size() <= 0) {
								logger.error("本服务器未绑定视觉服务器，不需要查询初始化状态！");
								continue;
							}
							//获取当前连接集合
							ConcurrentMap<String, Connection> connMap = VisualUtils.getGoodsRecognition().getConnsMap();
							Connection conn = null;
							int result = 0;
							List<ServerList> serverListTemp = new ArrayList<ServerList>();
							for (ServerList server : serverLists) {
								conn = connMap.get(server.getScode());
								if (null != conn) {
									result = VisualUtils.getGoodsRecognition().getInitStatus(conn);
									logger.info("定时查询GPU服务器初始化状态，GPU服务器编号：{}，返回初始化状态：{}");
									if (result == 0) {
										serverListService.initModel(conn);//重新初始化服务器
									} else if (!conn.isHasInit()) {
										conn.setHasInit(true);
										VisualUtils.getGoodsRecognition().getConnsMap().put(server.getScode(), conn);
										serverListService.updateByServerCode(30, conn.getServerCode());//更新服务器信息
									}
								} else {
									serverListTemp.add(server);
								}
							}
							if (serverListTemp.size() > 0) {
								ArrayList<HostBean> hosts = new ArrayList<HostBean>();
								HostBean hostBean = null;
								for (ServerList server : serverListTemp) {
									hostBean = new HostBean();
									hostBean.setSip(server.getSip());
									hostBean.setServerCode(server.getScode());
									hostBean.setPort(server.getSport());
									hosts.add(hostBean);
								}
								String param = VisualUtils.getGson().toJson(hosts);
								String resultInit = VisualUtils.getGoodsRecognition().init(param);
								if (StringUtil.isNotBlank(resultInit)) {
									logger.info("定时查询GPU服务器初始化状态，重新初始化没有连接服务，初始化结果:{}", resultInit);
									serverListService.updateServerInfo(resultInit);
								}
							}
						}
					} catch (Exception e) {
						logger.error("定时查询GPU服务器初始化状态异常：{}", e);
					} finally {
						gpuQueryLock.release();
					}
				} while (isRun);
			}
		});

	}

    @PreDestroy
    public void destroy() {
		isRun = false;
    }

}
