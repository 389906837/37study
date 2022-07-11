package com.cloud.cang.rec.op.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.model.op.BuyRecord;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.model.op.InterfaceAccount;
import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.op.domain.InterfaceAccountDomain;
import com.cloud.cang.rec.op.domain.UserInterfaceAuthDomain;
import com.cloud.cang.rec.op.service.InterfaceAccountService;
import com.cloud.cang.rec.op.service.UserInterfaceAuthService;
import com.cloud.cang.rec.op.vo.InterfaceAccountVo;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.file.WatchEvent;
import java.text.MessageFormat;
import java.util.List;

/**
 * 接口账户记录表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/interfaceAccount")
public class InterfaceAccountController {

    private static final Logger log = LoggerFactory.getLogger(InterfaceAccountController.class);

    @Autowired
    private InterfaceAccountService interfaceAccountService;

    /**
     * 开放平台用户列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "op/interfaceAccount/interfaceAccount-list";
    }


    /**
     * 查询列表
     *
     * @param paramPage
     * @param interfaceAccountDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<InterfaceAccountVo>> queryData(ParamPage paramPage, InterfaceAccountDomain interfaceAccountDomain) {
        PageListVo<List<InterfaceAccountVo>> pageListVo = new PageListVo<List<InterfaceAccountVo>>();
        Page<InterfaceAccountVo> page = new Page<InterfaceAccountVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        interfaceAccountDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            interfaceAccountDomain.setOrderStr("OIA." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = interfaceAccountService.selectPageByDomainWhere(page, interfaceAccountDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 接口充值页面
     *
     * @return
     */
    @RequestMapping("/recharge")
    public String recharge(String sid, ModelMap map) {
        try {
            InterfaceAccount interfaceAccount = interfaceAccountService.selectByPrimaryKey(sid);
            map.put("interfaceAccount", interfaceAccount);
            return "op/interfaceAccount/interfaceAccount-recharge";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
            return ExceptionUtil.to500();
        }
    }

    /**
     * 接口充值
     *
     * @param interfaceAccount
     * @param buyRecord
     * @return
     */
    @RequestMapping("/saveRecharge")
    @ResponseBody
    public ResponseVo saveRecharge(Integer type, InterfaceAccount interfaceAccount, BuyRecord buyRecord, Integer orderIstatus) {
        try {
            if (null == orderIstatus) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("充值订单状态不能为空！");
            }
            buyRecord.setIstatus(orderIstatus);
            return interfaceAccountService.saveRecharge(type, interfaceAccount, buyRecord);
        } catch (Exception e) {
            log.error("接口充值异常，充值接口ID：{}", interfaceAccount.getSinterfaceId());
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("接口充值失败！");
        }
    }

    /**
     * 关闭接口账户
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/close")
    @ResponseBody
    public ResponseVo close(String checkboxId) {
        try {
            InterfaceAccount temp = interfaceAccountService.selectByPrimaryKey(checkboxId);
            if (null == temp) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("接口账户不存在！");
            }
            InterfaceAccount interfaceAccount = new InterfaceAccount();
            interfaceAccount.setId(checkboxId);
            interfaceAccount.setIstatus(20);
            interfaceAccountService.updateBySelective(interfaceAccount);
            //操作日志
            String logText = MessageFormat.format("接口账户充值，接口账户编号{0}", temp.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("关闭接口账户异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("关闭接口账户失败！");
        }
    }
}
