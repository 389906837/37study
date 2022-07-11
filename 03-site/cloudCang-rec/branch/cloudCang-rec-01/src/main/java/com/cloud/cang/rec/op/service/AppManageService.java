package com.cloud.cang.rec.op.service;

import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rec.op.domain.AppManageDomain;
import com.cloud.cang.rec.op.domain.UserInfoDomain;
import com.cloud.cang.rec.op.vo.AppManageVo;
import com.cloud.cang.rec.op.vo.UserInfoVo;
import com.github.pagehelper.Page;

public interface AppManageService extends GenericService<AppManage, String> {


    /**
     * 查询列表
     *
     * @param page
     * @param appManageDomain
     * @return
     */
    Page<AppManage> selectPageByDomainWhere(Page<AppManage> page, AppManageDomain appManageDomain);

    /**
     * 审核
     *
     * @param appManage
     * @return
     */
    void appManageAudit(AppManage appManage) throws Exception;

    /**
     * 保存新增平台应用
     *
     * @param appManage
     * @return AppManage
     */
    AppManage saveAppManage(AppManage appManage) throws Exception;

    /**
     * 保存编辑平台应用
     *
     * @param appManage
     * @return AppManage
     */
    void upAppManage(AppManage appManage);

    /**
     * 根据Id查看domain
     *
     * @param id
     * @return
     */
    AppManageVo selectVoById(String id);

    /**
     * 删除平台应用管理信息
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);

}