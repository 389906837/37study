package com.cloud.cang.rec.op.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.op.domain.UserInterfaceAuthDomain;
import com.cloud.cang.rec.op.service.UserInterfaceAuthService;
import com.cloud.cang.rec.op.vo.UserInterfaceAuthVo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.List;

/**
 * 用户接口权限表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/userInterfaceAuth")
public class UserInterfaceAuthController {

    private static final Logger log = LoggerFactory.getLogger(UserInterfaceAuthController.class);

    @Autowired
    private UserInterfaceAuthService userInterfaceAuthService;

    /**
     * 用户接口权限列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "op/userInterfaceAuth/userInterfaceAuth-list";
    }


    /**
     * 查询列表
     *
     * @param paramPage
     * @param userInterfaceAuthDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<UserInterfaceAuthVo>> queryData(ParamPage paramPage, UserInterfaceAuthDomain userInterfaceAuthDomain) {
        PageListVo<List<UserInterfaceAuthVo>> pageListVo = new PageListVo<List<UserInterfaceAuthVo>>();
        Page<UserInterfaceAuthVo> page = new Page<UserInterfaceAuthVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        userInterfaceAuthDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            userInterfaceAuthDomain.setOrderStr("OUIA." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = userInterfaceAuthService.selectPageByDomainWhere(page, userInterfaceAuthDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 关闭开放平台接口权限
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/close")
    @ResponseBody
    public ResponseVo close(String checkboxId) {
        try {
            UserInterfaceAuth userInterfaceAuth = userInterfaceAuthService.close(checkboxId);
            //操作日志
            String logText = MessageFormat.format("关闭开放平台接口权限，关闭接口权限编号{0}", userInterfaceAuth.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("关闭开放平台接口权限异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("关闭开放平台接口权限失败！");
        }
    }

    /**
     * 开通接口权限
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/open")
    @ResponseBody
    public ResponseVo open(String checkboxId) {
        try {
            UserInterfaceAuth userInterfaceAuth = userInterfaceAuthService.open(checkboxId);
            //操作日志
            String logText = MessageFormat.format("开通开放平台接口权限，开通接口权限编号{0}", userInterfaceAuth.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("开通接口权限异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("开通接口权限失败！");
        }
    }
}
