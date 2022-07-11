package com.cloud.cang.rec.cr.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.model.cr.OpenGpuServer;
import com.cloud.cang.model.cr.OpenServerList;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.cr.domain.OpenServerListDomain;
import com.cloud.cang.rec.cr.service.OpenGpuServerService;
import com.cloud.cang.rec.cr.service.OpenServerListService;
import com.cloud.cang.rec.cr.vo.OpenServerListVo;
import com.cloud.cang.recongition.VisionOperServicesDefine;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.MessageFormat;
import java.util.List;


/**
 * 开放平台API服务器管理Controller
 *
 * @author yanlingfeng
 * @version 1.0
 * @time:2018-09-18 16:17:00
 */
@Controller
@RequestMapping("/openServer")
public class OpenServerController {
    private static final Logger log = LoggerFactory.getLogger(OpenServerController.class);

    @Autowired
    private OpenServerListService openServerListService;
    @Autowired
    private OpenGpuServerService openGpuServerService;

    /**
     * 云识别开放平台列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "cr/openServer/openServer-list";
    }

    /**
     * @param paramPage
     * @param openServerListDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<OpenServerList>> queryData(ParamPage paramPage, OpenServerListDomain openServerListDomain) {
        PageListVo<List<OpenServerList>> pageListVo = new PageListVo<List<OpenServerList>>();

        Page<OpenServerList> page = new Page<OpenServerList>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        openServerListDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            openServerListDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = openServerListService.selectPageByDomainWhere(page, openServerListDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 新增/编辑 开放平台服务器
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, String sid) {
        try {
            OpenServerListVo openServerList = openServerListService.selectOpenGpuServer(sid);
            if (null == openServerList) {
                openServerList = new OpenServerListVo();
            }
            modelMap.put("openServerList", openServerList);
            return "cr/openServer/openServer-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 新增/修改 开放平台服务器
     *
     * @param openServerList
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo<String> save(OpenServerList openServerList) {
        try {
            if (StringUtils.isBlank(openServerList.getId())) {
                //执行新增
                openServerList = openServerListService.saveOpenServer(openServerList);
            } else {
                //执行修改
                openServerList = openServerListService.upOpenServer(openServerList);
            }
        } catch (Exception e) {
            log.error("编辑开放平台服务器异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑开放平台API服务器异常!");
        }
        //操作日志
        String logText = MessageFormat.format("编辑开放平台服务器，开放平台服务器编号{0}", openServerList.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 删除开放平台API服务器
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            openServerListService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除开放平台API服务器，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除开放平台API服务器异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 开放平台服务器审核
     *
     * @param
     * @return
     */
    @RequestMapping("/examine")
    public String examine(String sid, ModelMap map) {
        try {
            OpenServerList openServerList = openServerListService.selectByPrimaryKey(sid);
            if (null == openServerList) {
                openServerList = new OpenServerList();
            }
            map.put("openServerList", openServerList);
            return "cr/openServer/openServer-examine";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 开放平台服务器审核
     *
     * @param openServerList
     * @return
     */
    @RequestMapping("/openServerAudit")
    @ResponseBody
    public ResponseVo openServerAudit(OpenServerList openServerList) {
        try {
            log.info("开放平台服务器审核:{}", openServerList.getScode());
            if (null == openServerList.getIauditStatus()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核状态不能为空！");
            }
            if (30 == openServerList.getIauditStatus() && StringUtils.isBlank(openServerList.getSauditReason())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核驳回原因不能为空！");
            }
            openServerListService.openServerListAudit(openServerList);
        } catch (Exception e) {
            log.error("审核标注管理文件异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("审核异常！");
        }
        //操作日志
        String logText = MessageFormat.format("开放平台服务器审核，审核编号{0}", openServerList.getScode());
        LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 选择GPU服务器
     *
     * @param sid
     * @return
     */
    @RequestMapping("/choiceGpuServer")
    public String choiceGpuServer(String sid, ModelMap map) {
        try {
            OpenServerListVo openServerList = openServerListService.selectOpenGpuServer(sid);
            if (null == openServerList) {
                openServerList = new OpenServerListVo();
            }
            if (StringUtil.isNotBlank(openServerList.getA())) {
                map.put("deviceIds", openServerList.getA() + ",");
                map.put("selectNums", openServerList.getA().split(",").length);//选择个数
            }
            map.put("openServerList", openServerList);
            return "cr/openServer/openServer-choiceGpuServer";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 选择保存GPU服务器
     *
     * @param deviceIds
     * @param id
     * @return
     */
    @RequestMapping("/saveChoiceGpuServer")
    @ResponseBody
    public ResponseVo saveChoiceGpuServer(String deviceIds, String id) {
        openGpuServerService.deleteByOpenServerId(id);
        if (StringUtils.isNotBlank(deviceIds)) {
            String[] arr = deviceIds.split(",");
            if (null != arr && arr.length > 0) {
                OpenGpuServer openGpuServer = null;
                for (int a = 0; a < arr.length; a++) {
                    openGpuServer = new OpenGpuServer();
                    openGpuServer.setSgpuId(arr[a]);
                    openGpuServer.setSopenServerId(id);
                    openGpuServerService.insert(openGpuServer);
                }
            }
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 修改服务器状态
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/modifyServerStatus")
    public String modifyServerStatus(String sid, ModelMap map) {
        OpenServerList openServerList = openServerListService.selectByPrimaryKey(sid);
        map.put("openServerList", openServerList);
        return "cr/openServer/openServer-modifyServerStatus";
    }

    /**
     * '
     * 保存编辑服务器状态
     *
     * @param openServerList
     * @return
     */
    @RequestMapping("/saveServerStatus")
    @ResponseBody
    public ResponseVo saveServerStatus(OpenServerList openServerList) {
        try {
            openServerListService.updateBySelective(openServerList);
            String logText = MessageFormat.format("修改服务器状态成功，服务器编号{0}", openServerList.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("保存服务器状态异常", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("保存服务器状态异常！");
        }
    }

    /**
     * 初始化GPU服务器
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/initGpuServer")
    @ResponseBody
    public ResponseVo initGpuServer(String checkboxId) {
        try {
            OpenServerList openServerList = openServerListService.selectByPrimaryKey(checkboxId);
            log.info("################初始化GPU服务器！参数为：{}################" + openServerList.getSip());
            RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(VisionOperServicesDefine.GPU_SERVER_INIT);
            invoke.setRequestObj(openServerList.getSip()); // post 参数
            invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
            });
            ResponseVo<String> responseVo = (ResponseVo<String>) invoke.invoke();
            if (null != responseVo && responseVo.isSuccess()) {
                log.info("初始化GPU服务器成功！");
            } else {
                log.error("初始化GPU服务器失败！");
            }
            String logText = MessageFormat.format("初始化GPU服务器，服务器编号{0}", openServerList.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();

        } catch (Exception e) {
            log.error("初始化GPU服务器异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("初始化GPU服务器异常！");
        }
    }
}
