package com.cloud.cang.wap.sys.service;

import com.cloud.cang.model.sys.IpArea;
import com.cloud.cang.generic.GenericService;

public interface IpAreaService extends GenericService<IpArea, String> {

    /**
     * 根据Ip查找
     * @param ip
     * @return
     */
    IpArea selectIpAreaByIp(String ip);
}