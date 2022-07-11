package com.cloud.cang.rec.cr.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.model.cr.RecognitionServer;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.cr.domain.RecognitionServerDomain;
import com.cloud.cang.rec.cr.service.RecognitionServerService;
import com.cloud.cang.rec.cr.service.ServerModelService;
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

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 开放平台视觉服务器管理Controller
 *
 * @author yanlingfeng
 * @version 1.0
 * @time:2019-11-13 16:17:00
 */
@Controller
@RequestMapping("/recognitionServer")
public class RecognitionServerController {
    private static final Logger log = LoggerFactory.getLogger(RecognitionServerController.class);
    @Autowired
    private RecognitionServerService recognitionServerService;
    @Autowired
    private ServerModelService serverModelService;

    /**
     * 云识别开放平台列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "cr/recognitionServer/recognitionServer-list";
    }

    /**
     * @param paramPage
     * @param recognitionServerDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<RecognitionServer>> queryData(ParamPage paramPage, RecognitionServerDomain recognitionServerDomain) {
        PageListVo<List<RecognitionServer>> pageListVo = new PageListVo<List<RecognitionServer>>();

        Page<RecognitionServer> page = new Page<RecognitionServer>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        recognitionServerDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            recognitionServerDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = recognitionServerService.selectPageByDomainWhere(page, recognitionServerDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 根商户可以所有商户设备
     *
     * @Author: zengzexiong
     * @param: deviceInfoVo
     * @param: paramPage
     * @param: deviceType 30=所有视觉柜子，40=带大屏的视觉柜子，50=云端识别设备（包含重力，不带大屏）
     * @Date: 2019年1月19日14:22:39
     */
    @RequestMapping("/queryRootDeviceData")
    @ResponseBody
    public PageListVo<List<RecognitionServer>> queryRootDeviceData(ParamPage paramPage, RecognitionServerDomain recognitionServerDomain, String deviceType) {
        PageListVo<List<RecognitionServer>> pageListVo;//返回的页面对象
        pageListVo = new PageListVo<List<RecognitionServer>>();
        Page<RecognitionServer> page = new Page<RecognitionServer>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == recognitionServerDomain) {
            recognitionServerDomain = new RecognitionServerDomain();
        }
        //分页查询
        page = recognitionServerService.selectPageByDomainWhere(page, recognitionServerDomain);
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
            RecognitionServer recognitionServer = recognitionServerService.selectByPrimaryKey(sid);
            if (null == recognitionServer) {
                recognitionServer = new RecognitionServer();
            }
            modelMap.put("recognitionServer", recognitionServer);
            return "cr/recognitionServer/recognitionServer-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 新增/修改 开识别服务器
     *
     * @param recognitionServer
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo<String> save(RecognitionServer recognitionServer) {
        try {
            if (StringUtils.isBlank(recognitionServer.getId())) {
                //执行新增
                recognitionServerService.saveRecognitionServer(recognitionServer);
            } else {
                //执行修改
                recognitionServerService.upRecognitionServer(recognitionServer);
            }
        } catch (Exception e) {
            log.error("编辑识别服务器异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑识别服务器异常!");
        }
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 开放平台识别服务器审核
     *
     * @param
     * @return
     */
    @RequestMapping("/examine")
    public String examine(String sid, ModelMap map) {
        try {
            RecognitionServer recognitionServer = recognitionServerService.selectByPrimaryKey(sid);
            if (null == recognitionServer) {
                recognitionServer = new RecognitionServer();
            }
            map.put("recognitionServer", recognitionServer);
            return "cr/recognitionServer/recognitionServer-examine";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 开放平台识别服务器审核
     *
     * @param recognitionServer
     * @return
     */
    @RequestMapping("/recognitionServerAudit")
    @ResponseBody
    public ResponseVo openServerAudit(RecognitionServer recognitionServer) {
        try {
            log.info("开放平台识别服务器审核:{}", recognitionServer.getScode());
            if (null == recognitionServer.getIauditStatus()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核状态不能为空！");
            }
            if (30 == recognitionServer.getIauditStatus() && StringUtils.isBlank(recognitionServer.getSauditReason())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核驳回原因不能为空！");
            }
            recognitionServerService.recognitionServerAudit(recognitionServer);
        } catch (Exception e) {
            log.error("识别服务器审核异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("审核异常！");
        }
        //操作日志
        String logText = MessageFormat.format("识别服务器审核，审核编号{0}", recognitionServer.getScode());
        LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 删除开放平台识别服务器
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            recognitionServerService.deleteRecognitionServer(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除识别服务器，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除识别服务器异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 开放平台识别服务器更新模型
     *
     * @param
     * @return
     */
    @RequestMapping("/upModel")
    public String upModel(String sid, ModelMap map) {
        try {
            RecognitionServer recognitionServer = recognitionServerService.selectByPrimaryKey(sid);
          /*  if (null == recognitionServer) {
                recognitionServer = new RecognitionServer();
            }*/
            ServerModel serverModel = serverModelService.selectByPrimaryKey(recognitionServer.getSmodelId());
              /*  if (null == serverModel) {
                serverModel = new ServerModel();
            }*/

            map.put("recognitionServer", recognitionServer);
            map.put("serverModel", serverModel);
            return "cr/recognitionServer/recognitionServer-upModel";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 更新模型
     *
     * @param recognitionServer
     * @param
     * @return
     */
    @RequestMapping("/saveUpModel")
    @ResponseBody
    public ResponseVo saveUpModel(RecognitionServer recognitionServer, /*Integer iisRestartImmediately, */BigDecimal fvisThresh) {
        try {
        /*    if (null == iisRestartImmediately) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更选择是否立即重启服务器！");
            }*/
            if (null == fvisThresh) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更输入模型识别置信度！");
            }
            return recognitionServerService.saveUpModel(recognitionServer,/* iisRestartImmediately,*/fvisThresh);
        } catch (Exception e) {
            log.error("开放平台识别服务器更新模型异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常更新模型失败！");
        }
    }

    /**
     * 重启识别服务器
     *
     * @return
     */
    @RequestMapping("/serveReboot")
    public String serveReboot() {
        try {
            return "cr/recognitionServer/recognitionServer-serveReboot";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 重启识别服务器
     *
     * @return
     */
    @RequestMapping("/reboot")
    @ResponseBody
    public ResponseVo reboot(Integer irangeServer, String recognitionServerIds, String recognitionServerCodes) {
        try {
            if (null == irangeServer) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择操作识别服务器范围!");
            }
            if (20 == irangeServer && StringUtil.isBlank(recognitionServerIds)) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择识别服务器!");
            }
            return recognitionServerService.reboot(irangeServer, recognitionServerIds, recognitionServerCodes);
        } catch (Exception e) {
            log.error("重启识别服务器异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("重启识别服务器异常!");
    }

    @RequestMapping("/selectRecognitionServer")
    public String selectRecognitionServer(String recognitionServerIds, String recognitionServerCodes, ModelMap map) {
        log.debug("选择设备页面:{}", recognitionServerIds);
        if (StringUtil.isNotBlank(recognitionServerIds)) {
            map.put("recognitionServerIds", recognitionServerIds + ",");
            map.put("recognitionServerCodes", recognitionServerCodes + ",");
            map.put("selectNums", recognitionServerIds.split(",").length);//选择个数
        }

        return "cr/recognitionServer/recognitionServer-selectRecognitionServer";
    }

    /**
     * @Author: zhouhong
     * @Description: 获取设备信息
     * @param: deviceIds 设备ID集合
     * @Date: 2018/2/10 11:00
     */
    @RequestMapping("/getDeviceByIds")
    @ResponseBody
    ResponseVo<List<RecognitionServer>> getDeviceByIds(String[] recognitionServerIds) {
        List<RecognitionServer> sddList = null;
        if (null != recognitionServerIds) {
            sddList = new ArrayList<RecognitionServer>();
            RecognitionServer temp = null;
            RecognitionServer sdd = null;
            for (String recognitionServerId : recognitionServerIds) {//循环设备ID
                temp = recognitionServerService.selectByPrimaryKey(recognitionServerId);
                if (null != temp) {
                    sdd = new RecognitionServer();
                    sdd.setId(temp.getId());
                    sdd.setScode(temp.getScode());
                    sdd.setSname(temp.getSname());
                    sdd.setSmodelCode(temp.getSmodelCode());
                    sddList.add(sdd);
                }
            }
        }
        return ResponseVo.getSuccessResponse(sddList);
    }
}
