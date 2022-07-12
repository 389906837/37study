package com.cloud.cang.mgr.tp.service.impl;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.tp.dao.DeviceModelTemplateDao;
import com.cloud.cang.mgr.tp.domain.DeviceModelTemplateDomain;
import com.cloud.cang.mgr.tp.service.DeviceModelTemplateService;
import com.cloud.cang.mgr.tp.vo.DeviceModelTemplateVo;
import com.cloud.cang.model.EntityTables;
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
public class DeviceModelTemplateServiceImpl extends GenericServiceImpl<DeviceModelTemplate, String> implements
        DeviceModelTemplateService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceModelTemplateServiceImpl.class);

    @Autowired
    private DeviceModelTemplateDao deviceModelTemplateDao;


    @Override
    public GenericDao<DeviceModelTemplate, String> getDao() {
        return deviceModelTemplateDao;
    }


    @Override
    public Page<DeviceModelTemplateDomain> selectPageByDomainWhere(Page<DeviceModelTemplateDomain> page, DeviceModelTemplateVo deviceModelTemplateVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<DeviceModelTemplateDomain>) deviceModelTemplateDao.selectByDomainWhere(deviceModelTemplateVo);
    }

    /**
     * 新增设备详细信息模板
     *
     * @param deviceModelTemplate
     * @return
     */
    @Override
    public ResponseVo<DeviceModelTemplate> insertTemplate(DeviceModelTemplate deviceModelTemplate) {
//        DeviceModelTemplate deviceModelTemplateVo = new DeviceModelTemplate();
//        deviceModelTemplateVo.setSname(deviceModelTemplate.getSname());
//        deviceModelTemplateVo.setSmerchantId(deviceModelTemplate.getSmerchantId());
//        deviceModelTemplateVo.setSmerchantCode(deviceModelTemplate.getSmerchantCode());
//        deviceModelTemplateVo.setIdelFlag(0);
//        List<DeviceModelTemplate> deviceModelTemplateList = deviceModelTemplateDao.selectByEntityWhere(deviceModelTemplateVo);
//        if (CollectionUtils.isNotEmpty(deviceModelTemplateList)) {
//            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该模板名称已经存在");
//        }

        String code = CoreUtils.newCode(EntityTables.TP_DEVICE_MODEL_TEMPLATE);        // 编号
        if (StringUtils.isBlank(code)) {
            logger.error("设备详细信息模板生成出错");
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("添加失败");

        }
        deviceModelTemplate.setScode(code);
        deviceModelTemplate.setIstatus(20);
        deviceModelTemplateDao.insert(deviceModelTemplate);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 逻辑删除
     *
     * @param checkboxId
     * @return
     */
    @Override
    public ResponseVo<String> deleteByLogic(String[] checkboxId) {
        for (String id : checkboxId) {
            DeviceModelTemplate deviceModelTemplate = new DeviceModelTemplate();
            deviceModelTemplate.setId(id);
            deviceModelTemplate.setIdelFlag(1);
            deviceModelTemplateDao.updateByIdSelective(deviceModelTemplate);
        }
        return ResponseVo.getSuccessResponse(StringUtils.join(checkboxId));
    }

    /**
     * 修改模板
     *
     * @param deviceModelTemplate
     * @param deviceModelTemplateVo
     * @return
     */
    @Override
    public ResponseVo<DeviceModelTemplate> updateBySelectiveVo(DeviceModelTemplate deviceModelTemplate, DeviceModelTemplate deviceModelTemplateVo) {
//        List<DeviceModelTemplate> deviceModelTemplateList = deviceModelTemplateDao.selectByEntityWhere(deviceModelTemplateVo);
//        if (CollectionUtils.isNotEmpty(deviceModelTemplateList)) {
//            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("该模板名称已经存在");
//        }
        deviceModelTemplateDao.updateByIdSelectiveVo(deviceModelTemplate);
        return ResponseVo.getSuccessResponse(deviceModelTemplateDao.selectByPrimaryKey(deviceModelTemplate.getId()));
    }

    /**
     * 改变状态
     *
     * @param checkboxId
     * @return
     */
    @Override
    public ResponseVo<String> changeStatus(String[] checkboxId) {
        StringBuffer stringBuffer = new StringBuffer();
        for (String id : checkboxId) {
            if (StringUtils.isNotBlank(id)) {
                DeviceModelTemplate deviceModelTemplate = deviceModelTemplateDao.selectByPrimaryKey(id);
                if (null == deviceModelTemplate) continue;
                DeviceModelTemplate deviceModelTemplateVo = new DeviceModelTemplate();
                if (deviceModelTemplate.getIstatus() == 10) {
                    deviceModelTemplateVo.setIstatus(20);
                } else if (deviceModelTemplate.getIstatus() == 20) {
                    deviceModelTemplateVo.setIstatus(10);
                }
                deviceModelTemplateVo.setId(id);
                deviceModelTemplateDao.updateByIdSelective(deviceModelTemplateVo);
                stringBuffer.append(deviceModelTemplate.getScode()).append(",");
            }
        }
        String codes = stringBuffer.substring(0, stringBuffer.lastIndexOf(","));
        return ResponseVo.getSuccessResponse(codes);
    }

    @Override
    public List<DeviceModelTemplate> selectTemplateByMerchant(String merchantId) {
        DeviceModelTemplate deviceModelTemplateVo = new DeviceModelTemplate();
        deviceModelTemplateVo.setIdelFlag(0);
        deviceModelTemplateVo.setIstatus(10);
        deviceModelTemplateVo.setSmerchantId(merchantId);
        return deviceModelTemplateDao.selectByEntityWhere(deviceModelTemplateVo);
    }

}