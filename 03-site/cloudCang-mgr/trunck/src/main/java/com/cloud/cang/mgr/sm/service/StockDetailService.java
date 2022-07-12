package com.cloud.cang.mgr.sm.service;

import com.cloud.cang.mgr.sm.vo.StockDetailVo;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface StockDetailService extends GenericService<StockDetail, String> {

    /**
     * 总库存查询接口
     * @param page
     * @param stockDetailVo
     * @return
     */
    Page<StockDetail> selectStockDetail(Page<StockDetail> page, StockDetailVo stockDetailVo);

    /**
     * 根据ID查询
     * @param sid
     * @return
     */
    List<StockDetail> selectInfoId(String sid);
}