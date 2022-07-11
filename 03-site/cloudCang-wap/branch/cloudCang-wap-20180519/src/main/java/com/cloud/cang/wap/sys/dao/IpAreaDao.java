package com.cloud.cang.wap.sys.dao;


import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.IpArea;

/** IP地址管理(SYS_IP_AREA) **/
public interface IpAreaDao extends GenericDao<IpArea, String> {


    /**
     * 根据Ip查找
     * @param ip
     * @return
     */
    IpArea selectIpAreaByIp(String ip);
}