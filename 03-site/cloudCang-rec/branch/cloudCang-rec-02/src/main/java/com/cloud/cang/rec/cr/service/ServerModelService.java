package com.cloud.cang.rec.cr.service;

import com.cloud.cang.model.cr.OpenServerList;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rec.cr.domain.OpenServerListDomain;
import com.cloud.cang.rec.cr.domain.ServerModelDomain;
import com.github.pagehelper.Page;

import java.io.File;

public interface ServerModelService extends GenericService<ServerModel, String> {
    /**
     * 查询列表
     *
     * @param page
     * @param serverModelDomain
     * @return
     */
    Page<ServerModel> selectPageByDomainWhere(Page<ServerModel> page, ServerModelDomain serverModelDomain);

    /**
     * 新增
     *
     * @param serverModel
     * @return
     */
    void saveServerModel(ServerModel serverModel, File file);

    /**
     * 执行修改
     *
     * @param serverModel
     */
    void upServerModel(ServerModel serverModel, File file);

    /**
     * 删除
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    /**
     * 审核
     *
     * @param serverModel
     * @return
     */
    void serverModelAudit(ServerModel serverModel);

    /**
     * 根据模型编号查询
     *
     * @param code
     * @return
     */
    ServerModel selectByCode(String code);

}