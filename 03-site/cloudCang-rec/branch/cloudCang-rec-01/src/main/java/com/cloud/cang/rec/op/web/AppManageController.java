package com.cloud.cang.rec.op.web;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.op.domain.AppManageDomain;
import com.cloud.cang.rec.op.service.AppManageService;
import com.cloud.cang.rec.op.vo.AppManageVo;
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

import javax.swing.plaf.PanelUI;
import java.text.MessageFormat;
import java.util.List;

/**
 * 平台应用管理信息表
 *
 * @author yanlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/appManage")
public class AppManageController {
    private static final Logger log = LoggerFactory.getLogger(AppManageController.class);

    @Autowired
    private AppManageService appManageService;
    @Autowired
    ICached iCached;

    /**
     * 平台应用管理信息列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "op/appManage/appManage-list";
    }


    /**
     * 查询列表
     *
     * @param paramPage
     * @param appManageDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<AppManage>> queryData(ParamPage paramPage, AppManageDomain appManageDomain) {
        PageListVo<List<AppManage>> pageListVo = new PageListVo<List<AppManage>>();
        Page<AppManage> page = new Page<AppManage>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        appManageDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            appManageDomain.setOrderStr("" + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = appManageService.selectPageByDomainWhere(page, appManageDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 平台应用审核
     *
     * @param
     * @return
     */
    @RequestMapping("/examine")
    public String examine(String sid, ModelMap map) {
        try {
            AppManage appManage = appManageService.selectByPrimaryKey(sid);
            if (null == appManage) {
                appManage = new AppManage();
            }
            map.put("appManage", appManage);
            return "op/appManage/appManage-examine";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 平台应用审核
     *
     * @param appManage
     * @return
     */
    @RequestMapping("/appManageAudit")
    @ResponseBody
    public ResponseVo appManageAudit(AppManage appManage) {
        try {
            log.info("平台应用审核:{}", appManage.getScode());
            if (null == appManage.getIauditStatus()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核状态不能为空！");
            }
            if (30 == appManage.getIauditStatus() && StringUtils.isBlank(appManage.getSauditReason())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核驳回原因不能为空！");
            }
            appManageService.appManageAudit(appManage);
        } catch (Exception e) {
            log.error("平台应用审核异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("平台应用审核异常！");
        }
        //操作日志
        String logText = MessageFormat.format("平台应用审核，审核编号{0}", appManage.getScode());
        LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 新增/编辑 平台应用
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, String sid) {
        try {
            AppManageVo appManageVo = appManageService.selectVoById(sid);
            if (null == appManageVo) {
                appManageVo = new AppManageVo();
            }
            modelMap.put("appManage", appManageVo);
            return "op/appManage/appManage-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 新增/修改 平台应用
     *
     * @param appManage
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo<String> save(AppManage appManage) {
        try {
            if (StringUtils.isBlank(appManage.getId())) {
                //执行新增
                appManage = appManageService.saveAppManage(appManage);
            } else {
                //执行修改
                appManageService.upAppManage(appManage);
            }
        } catch (Exception e) {
            log.error("编辑平台应用异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑平台应用异常!");
        }
        //操作日志
        String logText = MessageFormat.format("编辑平台应用，平台应用编号{0}", appManage.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 删除平台应用管理信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            appManageService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除删除平台应用管理信息，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除删除平台应用管理信息异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 查看用户详情页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/view")
    public String userInfoView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询该设备信息
                AppManage appManage = appManageService.selectByPrimaryKey(sid);
                if (null != appManage) {
                    map.put("appManage", appManage);
                    return "op/appManage/appManage-view";
                }
            }
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 更新应用缓存(已审核应用)
     *
     * @param
     * @return
     */
    @RequestMapping("/cache")
    @ResponseBody
    public ResponseVo cache() {
        try {
            AppManage temp = new AppManage();
            temp.setIauditStatus(20);
            temp.setIdelFlag(0);
            List<AppManage> appManages = appManageService.selectByEntityWhere(temp);
            for (AppManage appManage : appManages) {
                String catcheKey = RedisConst.OP_APP_MANAGE + appManage.getSappId();
                iCached.put(catcheKey, JSON.toJSONString(appManage));
            }
        } catch (Exception e) {
            log.error("更新应用缓存异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更新缓存失败");
        }
        return ResponseVo.getSuccessResponse("更新缓存成功！");
    }
}
