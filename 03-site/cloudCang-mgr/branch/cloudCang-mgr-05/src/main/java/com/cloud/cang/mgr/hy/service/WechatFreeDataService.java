package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.model.hy.WechatFreeData;
import com.cloud.cang.generic.GenericService;

public interface WechatFreeDataService extends GenericService<WechatFreeData, String> {



    /**
     * 查询免密签约数据
     * @param contractCode 系统签约唯一标识
     * @param smerchantCode 商户编号
     * @return
     */
    WechatFreeData selectByContractCode(String contractCode, String smerchantCode);
    /**
     * 保存免密数据
     * @param freeData 免密参数
     */
    WechatFreeData saveOrUpdate(WechatFreeData freeData);
}