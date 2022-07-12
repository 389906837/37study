package com.cloud.cang.mgr.tp.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.tp.dao.DeviceManageTemplateDao;
import com.cloud.cang.mgr.tp.domain.DeviceManageTemplateDomain;
import com.cloud.cang.mgr.tp.service.DeviceManageTemplateService;
import com.cloud.cang.mgr.tp.vo.DeviceManageTemplateVo;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.tp.DeviceManageTemplate;
import com.cloud.cang.model.tp.DeviceModelTemplate;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceManageTemplateServiceImpl extends GenericServiceImpl<DeviceManageTemplate, String> implements
        DeviceManageTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceManageTemplateServiceImpl.class);


    @Autowired
    DeviceManageTemplateDao deviceManageTemplateDao;


    @Override
    public GenericDao<DeviceManageTemplate, String> getDao() {
        return deviceManageTemplateDao;
    }


    /**
     * 设备管理信息模板
     *
     * @param page
     * @param deviceManageTemplateVo
     * @return
     */
    @Override
    public Page<DeviceManageTemplateDomain> selectPageByDomainWhere(Page<DeviceManageTemplateDomain> page, DeviceManageTemplateVo deviceManageTemplateVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<DeviceManageTemplateDomain>) deviceManageTemplateDao.selectByDomainWhere(deviceManageTemplateVo);
    }

    /**
     * 设备管理信息模板
     *
     * @param deviceManageTemplate
     * @return
     */
    @Override
    public ResponseVo<DeviceModelTemplate> insertTemplate(DeviceManageTemplate deviceManageTemplate) {
//        DeviceManageTemplate deviceManageTemplateVo = new DeviceManageTemplate();
//        deviceManageTemplateVo.setSname(deviceManageTemplate.getSname());
//        deviceManageTemplateVo.setSmerchantId(deviceManageTemplate.getSmerchantId());
//        deviceManageTemplateVo.setSmerchantCode(deviceManageTemplate.getSmerchantCode());
//        deviceManageTemplateVo.setIdelFlag(0);
//        List<DeviceManageTemplate> deviceModelTemplateList = deviceManageTemplateDao.selectByEntityWhere(deviceManageTemplateVo);
//        if (CollectionUtils.isNotEmpty(deviceModelTemplateList)) {
//            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该模板名称已经存在");
//        }
        String code = CoreUtils.newCode(EntityTables.TP_DEVICE_MANAGE_TEMPLATE);        // 编号
        if (StringUtils.isBlank(code)) {
            logger.error("设备管理信息模板生成出错");
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("添加失败");
        }
        deviceManageTemplate.setScode(code);
        deviceManageTemplate.setIstatus(20);
        deviceManageTemplateDao.insert(deviceManageTemplate);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 设备管理信息模板
     *
     * @param checkboxId
     * @return
     */
    @Override
    public ResponseVo<String> deleteByLogic(String[] checkboxId) {
        for (String id : checkboxId) {
            DeviceManageTemplate deviceManageTemplate = new DeviceManageTemplate();
            deviceManageTemplate.setId(id);
            deviceManageTemplate.setIdelFlag(1);
            deviceManageTemplateDao.updateByIdSelective(deviceManageTemplate);
        }
        return ResponseVo.getSuccessResponse(StringUtils.join(checkboxId));
    }

    /**
     * 设备管理信息模板
     *
     * @param deviceManageTemplate
     * @param deviceManageTemplateVo
     * @return
     */
    @Override
    public ResponseVo<DeviceManageTemplate> updateBySelectiveVo(DeviceManageTemplate deviceManageTemplate, DeviceManageTemplate deviceManageTemplateVo) {
//        List<DeviceManageTemplate> deviceManageTemplateList = deviceManageTemplateDao.selectByEntityWhere(deviceManageTemplateVo);
//        if (CollectionUtils.isNotEmpty(deviceManageTemplateList)) {
//            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该模板名称已经存在");
//        }
        deviceManageTemplateDao.updateByIdSelectiveVo(deviceManageTemplate);
        return ResponseVo.getSuccessResponse(deviceManageTemplateDao.selectByPrimaryKey(deviceManageTemplate.getId()));
    }


    /**
     *
     * @param checkboxId
     * @return
     */
    @Override
    public ResponseVo<String> changeStatus(String[] checkboxId) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String id : checkboxId) {
            if (StringUtils.isNotBlank(id)) {
                DeviceManageTemplate deviceManageTemplate = deviceManageTemplateDao.selectByPrimaryKey(id);
                if (null == deviceManageTemplate) continue;
                DeviceManageTemplate deviceManageTemplateVo = new DeviceManageTemplate();
                if (deviceManageTemplate.getIstatus() == 10) {
                    deviceManageTemplateVo.setIstatus(20);
                } else if (deviceManageTemplate.getIstatus() == 20) {
                    deviceManageTemplateVo.setIstatus(10);
                }
                deviceManageTemplateVo.setId(id);
                deviceManageTemplateDao.updateByIdSelective(deviceManageTemplateVo);
                stringBuffer.append(deviceManageTemplate.getScode()).append(",");
            }
        }
        String codes = stringBuffer.substring(0, stringBuffer.lastIndexOf(","));
        return ResponseVo.getSuccessResponse(codes);
    }

    /**
     *
     * @param merchantId
     * @return
     */
    @Override
    public List<DeviceManageTemplate> selectTemplateByMerchant(String merchantId) {
        DeviceManageTemplate deviceManageTemplateVo = new DeviceManageTemplate();
        deviceManageTemplateVo.setIdelFlag(0);
        deviceManageTemplateVo.setIstatus(10);
        deviceManageTemplateVo.setSmerchantId(merchantId);
        return deviceManageTemplateDao.selectByEntityWhere(deviceManageTemplateVo);
    }
}