package com.cloud.cang.open.api.cr.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.cloud.cang.eye.sdk.CAvatarEye;
import com.cloud.cang.eye.sdk.bean.ComplexResultBean;
import com.cloud.cang.eye.sdk.bean.HostBean;
import com.cloud.cang.eye.sdk.bean.ResultBean;
import com.cloud.cang.eye.sdk.socket.Connection;
import com.cloud.cang.open.api.common.utils.VisualUtils;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.open.api.cr.dao.ServerListDao;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.open.api.cr.service.ServerListService;

@Service
public class ServerListServiceImpl extends GenericServiceImpl<ServerList, String> implements
        ServerListService {

    @Autowired
    ServerListDao serverListDao;
    private static final Logger logger = LoggerFactory.getLogger(ServerListServiceImpl.class);

    @Override
    public GenericDao<ServerList, String> getDao() {
        return serverListDao;
    }

    /**
     * 获取视觉服务列表
     *
     * @param map 查询参数
     */
    @Override
    public List<ServerList> selectByMap(Map<String, Object> map) {
        return serverListDao.selectByMap(map);
    }

    /**
     * 更新视觉服务器信息
     *
     * @param status     服务器初始化状态
     * @param serverCode 服务器编号
     */
    @Override
    public void updateByServerCode(int status, String serverCode) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", status);
        map.put("serverCode", serverCode);
        serverListDao.updateByServerCode(map);
    }

    /**
     * 重启连接服务
     *
     * @param conn 连接信息
     * @return
     */
    @Override
    public int restart(Connection conn) {
        int result = 0;
        try {
            VisualUtils.getGoodsRecognition().uninit(conn);//反初始化
            this.updateByServerCode(10, conn.getServerCode());//更新服务器信息
            this.initModel(conn);//初始化连接
            result = 1;
        } catch (Exception e) {
            CAvatarEye.LOG("视觉服务重启失败，异常：" + e.getMessage(), 2);
        }
        return result;
    }

    /**
     * 初始化连接服务
     *
     * @param conn 连接信息
     * @return
     */
    @Override
    public int initModel(Connection conn) throws Exception {
        int result = 0;
        String resultBeanStr = VisualUtils.getGoodsRecognition().initModel(conn);
        if (StringUtil.isNotBlank(resultBeanStr)) {
            ResultBean resultbean = VisualUtils.getGson().fromJson(resultBeanStr, ResultBean.class);
            if (Integer.parseInt(resultbean.getCode()) == 0) {
                conn = VisualUtils.getGoodsRecognition().getConnsMap().get(conn.getServerCode());
                //更新服务器信息
                if (conn.isHasInit()) {
                    this.updateByServerCode(30, conn.getServerCode());
                } else {
                    this.updateByServerCode(10, conn.getServerCode());
                }
                result = 1;
            }
        }
        return result;
    }

    /**
     * 更新服务器数据
     *
     * @param resultInit 初始化结果
     * @return
     */
    @Override
    public int updateServerInfo(String resultInit) throws Exception {
        int result = 0;
        ComplexResultBean resultbean = VisualUtils.getGson().fromJson(resultInit, ComplexResultBean.class);
        if (Integer.parseInt(resultbean.getCode()) == 0) {
            ConcurrentMap<String, Connection> connMap = VisualUtils.getGoodsRecognition().getConnsMap();
            if (null != connMap && connMap.size() > 0) {
                Connection tempConn = null;
                for (String serverCode : connMap.keySet()) {
                    tempConn = connMap.get(serverCode);
                    if (tempConn.isHasInit()) {
                        logger.info("视觉服务初始化成功，服务器编号：{}", serverCode);
                        this.updateByServerCode(30, serverCode);//更新服务器信息
                    } else {
                        logger.error("视觉初始化失败，服务器编号：{}", serverCode);
                        this.updateByServerCode(10, serverCode);//更新服务器信息
                    }
                }
            }
            result = 1;
        }
        return result;
    }

    /**
     * gpu服务器初始化
     *
     * @param map
     */
    @Override
    public void serverInit(Map<String, Object> map) throws Exception{
        List<ServerList> serverLists =  this.selectByMap(map);
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
        int result = this.updateServerInfo(resultInit);
        if (result > 0) {
            logger.info("视觉服务启动成功！");
        }
        logger.info("视觉服务初始化完成...");
    }
}