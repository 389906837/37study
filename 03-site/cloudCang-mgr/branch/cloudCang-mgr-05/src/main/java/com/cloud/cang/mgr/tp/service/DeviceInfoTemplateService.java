package com.cloud.cang.mgr.tp.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.tp.domain.DeviceInfoTemplateDomain;
import com.cloud.cang.mgr.tp.vo.DeviceInfoTemplateVo;
import com.cloud.cang.model.tp.DeviceInfoTemplate;
import com.github.pagehelper.Page;

import java.util.List;


public interface DeviceInfoTemplateService extends GenericService<DeviceInfoTemplate, String> {


    /**
     *
     * @param page
     * @param deviceInfoTemplateVo
     * @return
     */
    Page<DeviceInfoTemplateDomain> selectPageByDomainWhere(Page<DeviceInfoTemplateDomain> page, DeviceInfoTemplateVo deviceInfoTemplateVo);

    /**
     *
     * @param deviceInfoTemplate
     * @return
     */
    ResponseVo<DeviceInfoTemplate> insertTemplate(DeviceInfoTemplate deviceInfoTemplate);

    /**
     *
     * @param checkboxId
     * @return
     */
    ResponseVo<String> deleteByLogic(String[] checkboxId);


    /**
     *
     * @param deviceInfoTemplate
     * @param deviceInfoTemplateVo
     * @return
     */
    ResponseVo<DeviceInfoTemplate> updateBySelectiveVo(DeviceInfoTemplate deviceInfoTemplate, DeviceInfoTemplate deviceInfoTemplateVo);

    /**
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
    List<DeviceInfoTemplate> selectTemplateByMerchant(String merchantId);

}