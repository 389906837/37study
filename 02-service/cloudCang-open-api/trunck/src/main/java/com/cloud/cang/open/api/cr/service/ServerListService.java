package com.cloud.cang.open.api.cr.service;

import com.cloud.cang.eye.sdk.socket.Connection;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.generic.GenericService;

import java.util.List;
import java.util.Map;

public interface ServerListService extends GenericService<ServerList, String> {

    /**
     * 获取视觉服务列表
     *
     * @param map 查询参数
     */
    List<ServerList> selectByMap(Map<String, Object> map);

    /**
     * 更新视觉服务器信息
     *
     * @param status     服务器初始化状态
     * @param serverCode 服务器编号
     */
    void updateByServerCode(int status, String serverCode);

    /**
     * 重启连接服务
     *
     * @param conn 连接信息
     * @return
     */
    int restart(Connection conn);

    /**
     * 初始化连接服务
     *
     * @param conn 连接信息
     * @return
     */
    int initModel(Connection conn) throws Exception;

    /**
     * 更新服务器数据
     *
     * @param resultInit 初始化结果
     * @return
     */
    int updateServerInfo(String resultInit) throws Exception;

    /**
     * gpu服务器初始化
     *
     * @param map
     */
    void serverInit(Map<String, Object> map) throws Exception;
}