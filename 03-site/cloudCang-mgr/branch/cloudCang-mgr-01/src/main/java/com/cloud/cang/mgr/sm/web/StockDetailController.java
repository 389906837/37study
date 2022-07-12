package com.cloud.cang.mgr.sm.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.generic.GenericController;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.sm.service.DeviceStockService;
import com.cloud.cang.mgr.sm.service.StockDetailService;
import com.cloud.cang.mgr.sm.vo.DeviceStockVo;
import com.cloud.cang.mgr.sm.vo.StockDetailVo;
import com.cloud.cang.model.sm.DeviceStock;
import com.cloud.cang.model.sm.StockDetail;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 单机库存列表
 * @author ChangTanchang
 * @time:2018-01-24 20:00:00
 * @version 1.0
 */
@Controller
@RequestMapping("/stockdetail")
public class StockDetailController extends GenericController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(StockDetailController.class);

    // 注入serv
    @Autowired
    private StockDetailService stockDetailService;

    /**
     * 单机库存列表查询
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "sm/stockdetail-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody PageListVo<List<StockDetail>> queryDeviceStock(ParamPage paramPage, StockDetailVo stockDetailVo) {
        PageListVo<List<StockDetail>> pageListVo = new PageListVo<List<StockDetail>>();
        Page<StockDetail> page = new Page<StockDetail>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            stockDetailVo.setOrderStr(" " + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = stockDetailService.selectStockDetail(page,stockDetailVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }
}
