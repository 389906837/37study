package com.cloud.cang.rec.cr.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.cr.ServerModel;
import com.github.pagehelper.Page;

/**
 * 识别服务模型管理(CR_SERVER_MODEL)
 **/
public interface ServerModelDao extends GenericDao<ServerModel, String> {

    /**
     * 查询列表
     *
     * @param serverModel
     * @return
     */
    Page<ServerModel> selectPageByDomainWhere(ServerModel serverModel);

    /**
     * 根据模型编号查询
     *
     * @param code
     * @return
     */
    ServerModel selectByCode(String code);
}