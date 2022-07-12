package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.BackstageOperDomain;
import com.cloud.cang.mgr.sb.vo.BackstageOperVo;
import com.cloud.cang.model.sb.BackstageOper;
import com.github.pagehelper.Page;

/**
 * 设备后台操作记录表(SB_BACKSTAGE_OPER)
 **/
public interface BackstageOperDao extends GenericDao<BackstageOper, String> {

    /**
     * 后台设备操作记录分页查询
     *
     * @param backstageOperVo
     * @return
     */
    Page<BackstageOperDomain> selectPageByDomainWhere(BackstageOperVo backstageOperVo);


    /** codegen **/
}