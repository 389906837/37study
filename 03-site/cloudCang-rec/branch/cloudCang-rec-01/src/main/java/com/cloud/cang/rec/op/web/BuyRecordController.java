package com.cloud.cang.rec.op.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.model.op.BuyRecord;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.cr.vo.BuyRecordVo;
import com.cloud.cang.rec.op.domain.BuyRecordDomain;
import com.cloud.cang.rec.op.domain.InterfaceAcceptDomain;
import com.cloud.cang.rec.op.service.BuyRecordService;
import com.cloud.cang.rec.op.service.InterfaceAcceptService;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.List;

/**
 * 接口购买记录表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/buyRecord")
public class BuyRecordController {

    private static final Logger log = LoggerFactory.getLogger(BuyRecordController.class);

    @Autowired
    private BuyRecordService buyRecordService;

    /**
     * 平台接口业务受理信息列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "op/buyRecord/buyRecord-list";
    }


    /**
     * 查询列表
     *
     * @param paramPage
     * @param buyRecordDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<BuyRecordVo>> queryData(ParamPage paramPage, BuyRecordDomain buyRecordDomain) {
        PageListVo<List<BuyRecordVo>> pageListVo = new PageListVo<List<BuyRecordVo>>();

        Page<BuyRecordVo> page = new Page<BuyRecordVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        buyRecordDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            buyRecordDomain.setOrderStr("OBR." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = buyRecordService.selectPageByDomainWhere(page, buyRecordDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 新增/编辑 接口购买记录
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, String sid) {
        try {
            BuyRecordDomain buyRecordDomain = buyRecordService.selectDomainById(sid);
            if (null == buyRecordDomain) {
                buyRecordDomain = new BuyRecordDomain();
            }
            modelMap.put("buyRecordDomain", buyRecordDomain);
            return "op/buyRecord/buyRecord-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 新增/修改 接口购买记录
     *
     * @param buyRecord
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo<String> save(BuyRecord buyRecord, String interfacePayType, String type) {
        try {
            if (StringUtils.isBlank(buyRecord.getSinterfaceCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("购买接口不能为空!");
            }
            if (StringUtils.isBlank(buyRecord.getSuserId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("购买用户不能为空!");
            }
            if (StringUtils.isBlank(buyRecord.getId())) {
                //执行新增
                buyRecord = buyRecordService.saveBuyRecord(buyRecord);
            } else {
                //执行修改
                buyRecordService.upBuyRecord(buyRecord);
            }
        } catch (Exception e) {
            log.error("编辑接口购买记录异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑接口购买记录异常!");
        }
        //操作日志
        String logText = MessageFormat.format("编辑接口购买记录，接口购买记录编号{0}", buyRecord.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 删除接口购买记录
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            buyRecordService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除接口购买记录，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除接口购买记录异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }
}
