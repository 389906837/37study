package com.cloud.cang.rec.op.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.op.domain.InterfaceInfoDomain;
import com.cloud.cang.rec.op.service.InterfaceInfoService;
import com.cloud.cang.rec.op.service.UserInterfaceAuthService;
import com.cloud.cang.utils.DateUtils;
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
 * 平台接口信息
 *
 * @author y11anlingfeng
 * @version 1.0
 */
@Controller
@RequestMapping("/interfaceInfo")
public class InterfaceInfoController {
    private static final Logger log = LoggerFactory.getLogger(InterfaceInfoController.class);

    @Autowired
    private InterfaceInfoService interfaceInfoService;
    @Autowired
    private UserInterfaceAuthService userInterfaceAuthService;


    /**
     * 平台接口信息管理表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "op/interfaceInfo/interfaceInfo-list";
    }


    /**
     * 查询列表
     *
     * @param paramPage
     * @param interfaceInfoDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<InterfaceInfo>> queryData(ParamPage paramPage, InterfaceInfoDomain interfaceInfoDomain, String selectInterface) {
        PageListVo<List<InterfaceInfo>> pageListVo = new PageListVo<List<InterfaceInfo>>();

        Page<InterfaceInfo> page = new Page<InterfaceInfo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        interfaceInfoDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            interfaceInfoDomain.setOrderStr("" + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        if ("true".equals(selectInterface)) {
            interfaceInfoDomain.setIstatus(20);
            interfaceInfoDomain.setIpayWay(30);
        }
        page = interfaceInfoService.selectPageByDomainWhere(page, interfaceInfoDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 删除平台接口信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            interfaceInfoService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除平台接口信息，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除平台接口信息异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 新增/编辑 平台接口
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, String sid) {
        try {
            InterfaceInfo interfaceInfo = interfaceInfoService.selectByPrimaryKey(sid);
            if (null == interfaceInfo) {
                interfaceInfo = new InterfaceInfo();
            }
            modelMap.put("interfaceInfo", interfaceInfo);
            return "op/interfaceInfo/interfaceInfo-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 新增/修改 平台接口
     *
     * @param interfaceInfo
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public ResponseVo<String> save(InterfaceInfo interfaceInfo) {
        try {
            if (StringUtils.isBlank(interfaceInfo.getId())) {
                //执行新增
                interfaceInfo = interfaceInfoService.saveInterface(interfaceInfo);
            } else {
                //执行修改
                interfaceInfoService.upInterface(interfaceInfo);
            }
        } catch (Exception e) {
            log.error("编辑平台接口异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑平台接口异常!");
        }
        //操作日志
        String logText = MessageFormat.format("编辑平台接口，平台接口编号{0}", interfaceInfo.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 选择平台接口
     *
     * @return
     */
    @RequestMapping("/selectInterface")
    public String selectInterface() {
        return "op/interfaceInfo/interfaceInfo-selectInterface";
    }

    /**
     * 编辑接口状态
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/editStatus")
    public String modifyServerStatus(String sid, ModelMap map) {
        try {
        InterfaceInfo interfaceInfo = interfaceInfoService.selectByPrimaryKey(sid);
        map.put("interfaceInfo", interfaceInfo);
        return "op/interfaceInfo/interfaceInfo-modifyStatus";
    }catch (Exception e){
            log.error("跳转页面异常：{}", e);
            return ExceptionUtil.to500();
        }
    }

    /**
     * 保存接口状态
     *
     * @param interfaceInfo
     * @return
     */
    @RequestMapping("/saveInterfaceStatus")
    @ResponseBody
    public ResponseVo saveInterfaceStatus(InterfaceInfo interfaceInfo) {
        try {
            //没有分配权限的接口才可以废弃
            if (30 == interfaceInfo.getIstatus()) {
                UserInterfaceAuth userInterfaceAuth = new UserInterfaceAuth();
                userInterfaceAuth.setIdelFlag(0);
                userInterfaceAuth.setIauthStatus(10);
                userInterfaceAuth.setSinterfaceCode(interfaceInfo.getScode());
                List list = userInterfaceAuthService.selectByEntityWhere(userInterfaceAuth);
                if (null != list && !list.isEmpty()) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("接口已有用户使用，不能废弃！");
                }
            } else if (20 == interfaceInfo.getIstatus()) {
                interfaceInfo.setTonlineTime(DateUtils.getCurrentDateTime());
            }
            interfaceInfoService.updateBySelective(interfaceInfo);
            String logText = MessageFormat.format("修改接口状态成功，接口编号{0}", interfaceInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("保存接口状态异常", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("保存接口状态异常！");
        }
    }
}
