package com.cloud.cang.mgr.tp.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.tp.domain.DeviceInfoTemplateDomain;
import com.cloud.cang.mgr.tp.vo.DeviceInfoTemplateVo;
import com.cloud.cang.model.tp.DeviceInfoTemplate;
import com.github.pagehelper.Page;

/**
 * 设备基础信息模板表(TP_DEVICE_INFO_TEMPLATE)
 **/
public interface DeviceInfoTemplateDao extends GenericDao<DeviceInfoTemplate, String> {

    /**
     * 修改（可以为空值）
     *
     * @param deviceInfoTemplate
     */
    void updateByIdSelectiveVo(DeviceInfoTemplate deviceInfoTemplate);

    /**
     * 分页查询
     *
     * @param deviceInfoTemplateVo
     */
    Page<DeviceInfoTemplateDomain> selectByDomainWhere(DeviceInfoTemplateVo deviceInfoTemplateVo);


    /** codegen **/
}