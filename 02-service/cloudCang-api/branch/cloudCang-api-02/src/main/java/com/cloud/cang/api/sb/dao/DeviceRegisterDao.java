package com.cloud.cang.api.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.DeviceRegister;

/** 设备注册信息表(SB_DEVICE_REGISTER) **/
public interface DeviceRegisterDao extends GenericDao<DeviceRegister, String> {


    /**
     * 根据32位安全密钥查询设备信息
     * @param map
     * @return
     */
    List<DeviceRegister> queryBySecurityKey(Map<String, String> map);

    /**
     * 查询所有注册设备token
     * @return 已注册设备的token
     */
    List<String> selectAll();

    /**
     * 根据注册IP查询设备注册信息
     * @param reqIp
     * @return
     */
    List<DeviceRegister> selectByReqIp(String reqIp);



    /** codegen **/
}