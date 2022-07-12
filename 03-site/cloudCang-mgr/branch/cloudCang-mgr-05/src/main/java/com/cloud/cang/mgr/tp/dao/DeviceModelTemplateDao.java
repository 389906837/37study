package com.cloud.cang.mgr.tp.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.tp.domain.DeviceModelTemplateDomain;
import com.cloud.cang.mgr.tp.vo.DeviceModelTemplateVo;
import com.cloud.cang.model.tp.DeviceModelTemplate;
import com.github.pagehelper.Page;

/**
 * 设备型号详细信息模板表(TP_DEVICE_MODEL_TEMPLATE)
 **/
public interface DeviceModelTemplateDao extends GenericDao<DeviceModelTemplate, String> {

    /**
     * 修改模板（空值）
     *
     * @param deviceModelTemplate
     */
    void updateByIdSelectiveVo(DeviceModelTemplate deviceModelTemplate);

    /**
     * 分页查询
     *
     * @param deviceModelTemplateVo
     * @return
     */
    Page<DeviceModelTemplateDomain> selectByDomainWhere(DeviceModelTemplateVo deviceModelTemplateVo);


    /** codegen **/
}