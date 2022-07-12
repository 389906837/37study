package com.cloud.cang.mgr.cr.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.cr.domain.ServerModelDomain;
import com.cloud.cang.model.cr.ServerModel;
import com.github.pagehelper.Page;

public interface ServerModelService extends GenericService<ServerModel, String> {

    /**
     * 查询列表
     *
     * @param page
     * @param serverModelDomain
     * @return
     */
    Page<ServerModel> selectPageByDomainWhere(Page<ServerModel> page, ServerModelDomain serverModelDomain);
}