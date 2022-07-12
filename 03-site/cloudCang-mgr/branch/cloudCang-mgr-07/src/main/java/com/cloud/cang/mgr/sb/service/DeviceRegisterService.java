package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.mgr.sb.domain.AiRegisterDomain;
import com.cloud.cang.mgr.sb.domain.DeviceRegisterDomain;
import com.cloud.cang.mgr.sb.vo.AiRegisterVo;
import com.cloud.cang.mgr.sb.vo.DeviceRegisterVo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface DeviceRegisterService extends GenericService<DeviceRegister, String> {


    /**
     * 分页查询
     * @param page
     * @param deviceRegisterVo
     * @return
     */
    Page<DeviceRegisterDomain> selectPageByDomainWhere(Page<DeviceRegisterDomain> page, DeviceRegisterVo deviceRegisterVo);

    /**
     * 根据设备ID批量删除
     * @param checkboxId
     */
    void deleteByIds(String[] checkboxId);

    /**
     * 根据设备ID查询设备注册信息
     * @param deviceId
     * @return
     */
    DeviceRegister selectByDeviceId(String deviceId);

    /**
     * 根据设备注册ID查询设备注册信息，设备编号名称，商户编号名称
     * @param sid
     * @return
     */
    DeviceRegisterDomain selectViewBySid(String sid);

    /**
     * 根据页面审核状态记录结果入设备审核表与设备基础信息表
     * @param deviceRegister
     * @return
     */
    String  updateState(DeviceRegister deviceRegister);


    /**
     * 编辑页面重新生成注册码
     * @param deviceRegister
     * @return
     */
    String updateStateByEdit(DeviceRegister deviceRegister);

    /**
     * AI设备注册码分页查询
     *
     * @param page         分页对象
     * @param aiRegisterVo 查询条件
     * @return
     */
    Page<AiRegisterDomain> selectAiPageByDomainWhere(Page<AiRegisterDomain> page, AiRegisterVo aiRegisterVo);

    /**
     * 根据设备注册ID查询AI设备注册信息，AI设备编号名称，商户编号名称
     *
     * @param sid 注册表ID
     * @return
     */
    AiRegisterDomain selectAiViewBySid(String sid);


    /**
     * 通过AI设备ID
     * 逻辑删除AI设备信息
     *
     * @param aiRegister ai设备注册信息
     */
    void updateAiRegisterByAiId(DeviceRegister aiRegister);

}