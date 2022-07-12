package com.cloud.cang.mgr.tp.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.tp.domain.DeviceManageTemplateDomain;
import com.cloud.cang.mgr.tp.vo.DeviceManageTemplateVo;
import com.cloud.cang.model.tp.DeviceManageTemplate;
import com.github.pagehelper.Page;

/**
 * 设备管理信息模板表(TP_DEVICE_MANAGE_TEMPLATE)
 **/
public interface DeviceManageTemplateDao extends GenericDao<DeviceManageTemplate, String> {


    Page<DeviceManageTemplateDomain> selectByDomainWhere(DeviceManageTemplateVo deviceManageTemplateVo);

    /**
     * 设备管理信息模板
     *
     * @param deviceManageTemplate
     */
    void updateByIdSelectiveVo(DeviceManageTemplate deviceManageTemplate);



    /** codegen **/
}