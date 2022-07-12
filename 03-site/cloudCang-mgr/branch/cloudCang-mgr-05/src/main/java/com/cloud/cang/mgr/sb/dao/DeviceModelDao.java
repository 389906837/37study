package com.cloud.cang.mgr.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.DeviceModelDomain;
import com.cloud.cang.mgr.sb.vo.DeviceModelVo;
import com.cloud.cang.model.sb.DeviceModel;
import com.github.pagehelper.Page;

/**
 * 设备型号详细信息表(SB_DEVICE_MODEL)
 **/
public interface DeviceModelDao extends GenericDao<DeviceModel, String> {

    /**
     *
     * @param deviceId
     * @return
     */
    DeviceModel selectByDeviceId(String deviceId);

    Page<DeviceModelDomain> selectByDomainWhere(DeviceModelVo deviceModelVo);

    /**
     * 自定义修改Model方法
     * @param deviceModel
     * @return
     */
    int updateByIdSelectiveVo(DeviceModel deviceModel);


    /**
     *
     * 分页查询
     * @param deviceModelVo
     */



    /** codegen **/
}