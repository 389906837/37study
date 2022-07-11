package com.cloud.cang.rec.op.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.model.op.TransferLog;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.op.domain.TransferLogDomain;
import com.cloud.cang.rec.op.service.TransferLogService;
import com.cloud.cang.rec.op.vo.TransferLogVo;
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
 * 接口调用日志表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/transferLog")
public class TransferLogController {
    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(TransferLogController.class);

    @Autowired
    private TransferLogService transferLogService;

    /**
     * 后台操作日志
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "op/transferLog/transferLog-list";
    }

    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<TransferLogVo>> queryTransferLog(ParamPage paramPage, TransferLogDomain transferLogDomain) {
        PageListVo<List<TransferLogVo>> pageListVo = new PageListVo<List<TransferLogVo>>();

        Page<TransferLogVo> page = new Page<TransferLogVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            transferLogDomain.setOrderStr("OTL." + paramPage.getSidx()+" " + paramPage.getSord()+",");
        }
        page = transferLogService.queryTransferLog(page,transferLogDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }
}
