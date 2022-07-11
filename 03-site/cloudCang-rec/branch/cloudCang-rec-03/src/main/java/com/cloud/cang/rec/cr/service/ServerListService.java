package com.cloud.cang.rec.cr.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rec.cr.domain.ServerListDomain;
import com.cloud.cang.server.GpuServerUpgrade;
import com.github.pagehelper.Page;

public interface ServerListService extends GenericService<ServerList, String> {

    /**
     * 查询列表
     *
     * @param page
     * @param serverListDomain
     * @return
     */
    Page<ServerList> selectPageByDomainWhere(Page<ServerList> page, ServerListDomain serverListDomain);

    /**
     * 新增
     *
     * @param serverList
     * @return
     */
    ServerList saveServerList(ServerList serverList);

    /**
     * 执行修改
     *
     * @param serverList
     */
    void upServerList(ServerList serverList);

    /**
     * 删除开放平台API服务器
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    /**
     * 审核
     *
     * @param serverList
     * @return
     */
    void serverListAudit(ServerList serverList);

    /**
     * 模型升级
     *
     * @param gpuServerUpgrade
     * @return
     */
    ResponseVo saveUpModel(GpuServerUpgrade gpuServerUpgrade);

    /**
     * 服务升级
     *
     * @param gpuServerUpgrade
     * @return
     */
    ResponseVo saveUpService(GpuServerUpgrade gpuServerUpgrade);
}