package com.cloud.cang.open.api.init;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.eye.sdk.bean.ComplexResultBean;
import com.cloud.cang.eye.sdk.bean.HostBean;
import com.cloud.cang.eye.sdk.socket.Connection;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.open.api.common.utils.IPUtils;
import com.cloud.cang.open.api.common.utils.VisualUtils;
import com.cloud.cang.open.api.cr.service.ServerListService;
import com.cloud.cang.utils.StringUtil;
import com.cloud.cang.zookeeper.confclient.listener.AbsConfigurationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;


/**
 * 初始化视觉服务器
 */
@Component
public class InitServer extends AbsConfigurationHandler {

    private final static Logger logger = LoggerFactory.getLogger(InitServer.class);
    @Autowired
    private ICached iCached;
    @Autowired
    private ServerListService serverListService;


    @PostConstruct
    public void startServer() throws Exception {
        logger.info("视觉服务开始初始化...");
        //获取服务器数据
        String sip = IPUtils.getLocalAddress();
        if (StringUtil.isBlank(sip)) {
            logger.error("获取本机IP错误，视觉服务启动失败!");
            return;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sip", sip);
        //map.put("irunStatus", "10");
        serverListService.serverInit(map);
      /*  List<ServerList> serverLists =  serverListService.selectByMap(map);
        if(null == serverLists || serverLists.size() <= 0) {
            logger.error("本服务器未绑定视觉服务器，视觉服务启动失败!");
            return;
        }
        ArrayList<HostBean> hosts = new ArrayList<HostBean>();
        HostBean hostBean = null;
        for (ServerList server : serverLists) {
            hostBean = new HostBean();
            hostBean.setSip(server.getSip());
            hostBean.setServerCode(server.getScode());
            hostBean.setPort(server.getSport());
            hosts.add(hostBean);
        }
        String param = VisualUtils.getGson().toJson(hosts);
        String resultInit = VisualUtils.getGoodsRecognition().init(param);
        int result = serverListService.updateServerInfo(resultInit);
        if (result > 0) {
            logger.info("视觉服务启动成功！");
        }
        logger.info("视觉服务初始化完成...");*/
    }


    @Override
    public boolean configurationHandler(String keyName, String value) {
        try {
            startServer();
            return true;
        } catch (Exception e) {
            logger.error("视觉服务初始化异常：{}", e);
        }
        return false;
    }
}
