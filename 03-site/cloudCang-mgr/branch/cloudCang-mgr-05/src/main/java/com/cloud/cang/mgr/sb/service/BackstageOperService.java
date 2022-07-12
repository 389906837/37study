package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.mgr.sb.domain.BackstageOperDomain;
import com.cloud.cang.mgr.sb.vo.BackstageOperVo;
import com.cloud.cang.model.sb.BackstageOper;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface BackstageOperService extends GenericService<BackstageOper, String> {


    /**
     * 后台设备操作记录分页查询
     *
     * @param page
     * @param backstageOperVo
     * @return
     */
    Page<BackstageOperDomain> selectPageByDomainWhere(Page<BackstageOperDomain> page, BackstageOperVo backstageOperVo);

}