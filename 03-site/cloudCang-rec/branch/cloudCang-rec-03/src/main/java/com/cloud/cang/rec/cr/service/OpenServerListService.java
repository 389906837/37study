package com.cloud.cang.rec.cr.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.cr.OpenServerList;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rec.cr.domain.OpenServerListDomain;
import com.cloud.cang.rec.cr.vo.OpenServerListVo;
import com.github.pagehelper.Page;

public interface OpenServerListService extends GenericService<OpenServerList, String> {

    /**
     * 查询列表
     *
     * @param page
     * @param openServerListDomain
     * @return
     */
    Page<OpenServerList> selectPageByDomainWhere(Page<OpenServerList> page, OpenServerListDomain openServerListDomain);

    /**
     * 新增
     *
     * @param openServerList
     * @return
     */
    OpenServerList saveOpenServer(OpenServerList openServerList);

    /**
     * 执行修改
     *
     * @param openServerList
     */
    OpenServerList upOpenServer(OpenServerList openServerList);

    /**
     * 删除开放平台API服务器
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    /**
     * 审核
     *
     * @param openServerList
     * @return
     */
    void openServerListAudit(OpenServerList openServerList);

    /**
     * 进入编辑页面查询API服务和GPU服务关联
     * @param id
     * @return
     */
    OpenServerListVo selectOpenGpuServer(String id);
}