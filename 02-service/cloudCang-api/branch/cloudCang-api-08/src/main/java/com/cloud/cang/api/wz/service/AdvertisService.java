package com.cloud.cang.api.wz.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.AdvertisModel;
import com.cloud.cang.model.wz.Advertis;

import java.util.List;

public interface AdvertisService extends GenericService<Advertis, String> {


    /**
     * 查询设备广告信息
     * @param scode 设备编号
     * @param sregionCode 广告运营位
     * @return
     */
    List<AdvertisModel> selectByDeviceCodeAndRegionCode(String scode, String sregionCode);
}