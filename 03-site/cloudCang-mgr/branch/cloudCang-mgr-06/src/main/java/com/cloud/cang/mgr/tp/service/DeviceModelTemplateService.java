package com.cloud.cang.mgr.tp.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.tp.domain.DeviceModelTemplateDomain;
import com.cloud.cang.mgr.tp.vo.DeviceModelTemplateVo;
import com.cloud.cang.model.tp.DeviceModelTemplate;
import com.github.pagehelper.Page;

import java.util.List;


public interface DeviceModelTemplateService extends GenericService<DeviceModelTemplate, String> {


    /**
     * @param page
     * @param deviceModelTemplateVo
     * @return
     */
    Page<DeviceModelTemplateDomain> selectPageByDomainWhere(Page<DeviceModelTemplateDomain> page, DeviceModelTemplateVo deviceModelTemplateVo);

    /**
     * 新增设备详细信息模板
     *
     * @param deviceModelTemplate
     */
    ResponseVo<DeviceModelTemplate> insertTemplate(DeviceModelTemplate deviceModelTemplate);

    /**
     * 逻辑删除
     *
     * @param checkboxId
     * @return
     */
    ResponseVo<String> deleteByLogic(String[] checkboxId);

    /**
     * 修改模板
     *
     * @param deviceModelTemplate
     * @param deviceModelTemplateVo
     * @return
     */
    ResponseVo<DeviceModelTemplate> updateBySelectiveVo(DeviceModelTemplate deviceModelTemplate, DeviceModelTemplate deviceModelTemplateVo);

    /**
     * 改变状态
     *
     * @param checkboxId
     * @return
     */
    ResponseVo<String> changeStatus(String[] checkboxId);

    /**
     *
     * @param merchantId
     * @return
     */
    List<DeviceModelTemplate> selectTemplateByMerchant(String merchantId);


}