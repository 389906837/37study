package com.cloud.cang.mgr.tp.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.tp.domain.DeviceManageTemplateDomain;
import com.cloud.cang.mgr.tp.vo.DeviceManageTemplateVo;
import com.cloud.cang.model.tp.DeviceManageTemplate;
import com.cloud.cang.model.tp.DeviceModelTemplate;
import com.github.pagehelper.Page;

import java.util.List;

public interface DeviceManageTemplateService extends GenericService<DeviceManageTemplate, String> {


    /**
     * 分页查询
     *
     * @param page
     * @param deviceManageTemplateVo
     * @return
     */
    Page<DeviceManageTemplateDomain> selectPageByDomainWhere(Page<DeviceManageTemplateDomain> page, DeviceManageTemplateVo deviceManageTemplateVo);

    /**
     * 插入数据
     *
     * @param deviceManageTemplate
     * @return
     */
    ResponseVo<DeviceModelTemplate> insertTemplate(DeviceManageTemplate deviceManageTemplate);


    /**
     * 逻辑删除
     *
     * @param checkboxId
     * @return
     */
    ResponseVo<String> deleteByLogic(String[] checkboxId);

    /**
     * 修改（可以为空值）
     *
     * @param deviceManageTemplate
     * @param deviceManageTemplateVo
     * @return
     */
    ResponseVo<DeviceManageTemplate> updateBySelectiveVo(DeviceManageTemplate deviceManageTemplate, DeviceManageTemplate deviceManageTemplateVo);

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
    List<DeviceManageTemplate> selectTemplateByMerchant(String merchantId);

}