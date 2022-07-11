package com.cloud.cang.open.api.cr.dao;

import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.cr.ServerList;

/** GPU识别服务器列表(CR_SERVER_LIST) **/
public interface ServerListDao extends GenericDao<ServerList, String> {
    /**
     * 获取视觉服务列表
     * @param map 查询参数
     */
    List<ServerList> selectByMap(Map<String, Object> map);

    /**
     * 更新视觉服务器信息
     * @param map status 服务器初始化状态 serverCode 服务器编号
     */
    void updateByServerCode(Map<String, Object> map);
}