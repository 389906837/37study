package com.cloud.cang.mgr.sm.service;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.sm.StandardStock;
import com.cloud.cang.generic.GenericService;

import javax.servlet.http.HttpServletRequest;

public interface StandardStockService extends GenericService<StandardStock, String> {

    /***
     * 更新标准库存数据
     * @param standardStock 标准库存数据
     * @param ilayerNum 设备层数
     *@param request  @throws ServiceException
     */
    boolean updateStandardStock(StandardStock standardStock, Integer ilayerNum, HttpServletRequest request) throws ServiceException;
}