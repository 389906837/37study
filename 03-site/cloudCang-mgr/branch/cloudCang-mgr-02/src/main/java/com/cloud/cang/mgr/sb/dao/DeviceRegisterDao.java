package com.cloud.cang.mgr.sb.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.AiRegisterDomain;
import com.cloud.cang.mgr.sb.domain.DeviceRegisterDomain;
import com.cloud.cang.mgr.sb.vo.AiRegisterVo;
import com.cloud.cang.mgr.sb.vo.DeviceRegisterVo;
import com.cloud.cang.model.sb.DeviceRegister;
import com.github.pagehelper.Page;

/** 设备注册信息表(SB_DEVICE_REGISTER) **/
public interface DeviceRegisterDao extends GenericDao<DeviceRegister, String> {
    /**
     * 分页查询
     * @param deviceRegisterVo
     */
    Page<DeviceRegisterDomain> selectByDomainWhere(DeviceRegisterVo deviceRegisterVo);

    /**
     * 根据设备ID查询主键ID
     * @param deviceId
     * @return
     */
    String selectIdByDeviceId(String deviceId);

    /**
     * 根据设备DI查询设备注册信息
     * @param deviceId
     * @return
     */
    DeviceRegister selectByDeviceId(String deviceId);

    /**
     * 自定义修改方法
     * @param deviceRegister
     */
    void updateByIdSelectiveVo(DeviceRegister deviceRegister);

    /**
     * 分页查询
     *
     * @param aiRegisterVo
     */
    Page<AiRegisterDomain> selectAiByDomainWhere(AiRegisterVo aiRegisterVo);

    /**
     * 逻辑删除
     *
     * @param aiRegister
     */
    void updateAiRegisterLogicDelete(DeviceRegister aiRegister);



    /** codegen **/
}