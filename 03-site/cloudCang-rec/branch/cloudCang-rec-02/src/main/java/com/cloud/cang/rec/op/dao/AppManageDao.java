package com.cloud.cang.rec.op.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.rec.op.domain.AppManageDomain;
import com.cloud.cang.rec.op.vo.AppManageVo;
import com.github.pagehelper.Page;

/**
 * 平台应用管理信息表(OP_APP_MANAGE)
 **/
public interface AppManageDao extends GenericDao<AppManage, String> {


    Page<AppManageVo> selectPageByDomainWhere(AppManageDomain appManageDomain);

    AppManageVo selectVoById(String id);

}