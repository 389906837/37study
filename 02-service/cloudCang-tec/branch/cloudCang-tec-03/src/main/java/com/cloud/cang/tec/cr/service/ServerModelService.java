package com.cloud.cang.tec.cr.service;

import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.generic.GenericService;

public interface ServerModelService extends GenericService<ServerModel, String> {

    /**
     * 根据模型编号查询
     *
     * @param code
     * @return
     */
    ServerModel selectByCode(String code);
}