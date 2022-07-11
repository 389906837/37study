package com.cloud.cang.rec.cr.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.cr.domain.ServerListDomain;
import com.cloud.cang.rec.cr.service.ServerListService;
import com.cloud.cang.server.GpuServerUpgrade;
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
import java.util.ArrayList;
import java.util.List;

/**
 * GPU识别服务器列表Controller
 *
 * @author yanlingfeng
 * @version 1.0
 * @time:2018-09-19 14:37:00
 */
@Controller
@RequestMapping("/gpuServer")
public class ServerListController {
    private static final Logger log = LoggerFactory.getLogger(ServerListController.class);

    @Autowired
    private ServerListService serverListService;

    /**
     * GPU识别服务器列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "cr/gpuServer/gpuServer-list";
    }

    /**
     * 查询列表
     *
     * @param paramPage
     * @param serverListDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<ServerList>> queryData(ParamPage paramPage, ServerListDomain serverListDomain, String selectGpuServer) {
        PageListVo<List<ServerList>> pageListVo = new PageListVo<List<ServerList>>();

        Page<ServerList> page = new Page<ServerList>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        serverListDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            serverListDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        if ("true".equals(selectGpuServer)) {
            serverListDomain.setIauditStatus(20);
            serverListDomain.setStatusCondition("(ISTATUS =20 OR ISTATUS= 30)");
        }
        page = serverListService.selectPageByDomainWhere(page, serverListDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 新增/编辑 GPU识别服务器
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, String sid) {
        try {
            ServerList serverList = serverListService.selectByPrimaryKey(sid);
            if (null == serverList) {
                serverList = new ServerList();
            }
            modelMap.put("serverList", serverList);
            return "cr/gpuServer/gpuServer-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 新增/修改 GPU识别服务器
     *
     * @param serverList
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo<String> save(ServerList serverList) {
        try {
            if (StringUtils.isBlank(serverList.getId())) {
                //执行新增
                serverList = serverListService.saveServerList(serverList);
            } else {
                //执行修改
                serverListService.upServerList(serverList);
            }
        } catch (Exception e) {
            log.error("编辑GPU识别服务器异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑GPU识别服务器异常!");
        }
        //操作日志
        String logText = MessageFormat.format("编辑GPU识别服务器，服务器编号{0}", serverList.getScode());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 删除GPU识别服务器
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            serverListService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除GPU识别服务器，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("GPU识别服务器异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * GPU识别服务器审核
     *
     * @param
     * @return
     */
    @RequestMapping("/examine")
    public String examine(String sid, ModelMap map) {
        try {
            ServerList serverList = serverListService.selectByPrimaryKey(sid);
            if (null == serverList) {
                serverList = new ServerList();
            }
            map.put("serverList", serverList);
            return "cr/gpuServer/gpuServer-examine";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * GPU识别服务器审核
     *
     * @param serverList
     * @return
     */
    @RequestMapping("/serverListAudit")
    @ResponseBody
    public ResponseVo openServerAudit(ServerList serverList) {
        try {
            log.info("GPU识别服务器审核:{}", serverList.getScode());
            if (null == serverList.getIauditStatus()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核状态不能为空！");
            }
            if (30 == serverList.getIauditStatus() && StringUtils.isBlank(serverList.getSauditReason())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核驳回原因不能为空！");
            }
            serverListService.serverListAudit(serverList);
        } catch (Exception e) {
            log.error("审核标注管理文件异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("审核异常！");
        }
        //操作日志
        String logText = MessageFormat.format("GPU识别服务器审核，服务器编号{0}", serverList.getScode());
        LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * @Author: yanlingfeng
     * @Description: 选择GPU服务器
     * @param: deviceIds 已选择设备ID
     */
    @RequestMapping("/selectDevice")
    public String selectDevice(String deviceIds, String deviceCodes, ModelMap map) {
        log.debug("选择GPU服务器:{}", deviceIds);
        if (StringUtil.isNotBlank(deviceIds)) {
            map.put("deviceIds", deviceIds + ",");
            map.put("deviceCodes", deviceCodes + ",");
            map.put("selectNums", deviceIds.split(",").length);//选择个数
        }
        return "cr/gpuServer/gpuServer-select-list";
    }

    /**
     * @Author: yanlingfeng
     * @Description: 获取设备信息
     * @param: deviceIds 设备ID集合
     * @Date: 2018/2/10 11:00
     */
    @RequestMapping("/getDeviceAndGroupByIds")
    @ResponseBody
    ResponseVo<List<ServerList>> getDeviceAndGroupByIds(String[] deviceIds) {
        List<ServerList> sddList = null;
        if (null != deviceIds) {
            sddList = new ArrayList<ServerList>();
            ServerList temp = null;
          /*  SelectDeviceDomain sdd = null;*/
            for (String deviceId : deviceIds) {//循环设备ID
                temp = serverListService.selectByPrimaryKey(deviceId);
           /*     if (null != temp) {
                    sdd = new SelectDeviceDomain();
                    sdd.setDeviceId(temp.getId());
                    sdd.setDeviceCode(temp.getScode());
                    sdd.setDeviceName(temp.getSname());
                    sdd.setAddress(getAddress(temp));
                    sdd.setGroupName(temp.getSgroupName());
                    sddList.add(sdd);
                }*/
                sddList.add(temp);
            }
        }
        return ResponseVo.getSuccessResponse(sddList);
    }

    @RequestMapping("/upModel")
    public String upModel() {
        return "cr/gpuServer/gpuServer-upModel";
    }

    /**
     * 模型升级
     *
     * @param gpuServerUpgrade
     * @return
     */
    @RequestMapping("/saveUpModel")
    @ResponseBody
    public ResponseVo saveUpModel(GpuServerUpgrade gpuServerUpgrade) {
        if (StringUtils.isBlank(gpuServerUpgrade.getSmodelCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级模型不能为空");
        }
        if (StringUtils.isBlank(gpuServerUpgrade.getTimeType())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级开始时间不能为空");
        }
        if ("20".equals(gpuServerUpgrade.getTimeType()) && StringUtils.isBlank(gpuServerUpgrade.getDproduceDate())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级定时时间不能为空");
        }
        if (StringUtils.isBlank(gpuServerUpgrade.getIrangeDevice())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级gpu服务器类型不能为空");
        }
        if ("20".equals(gpuServerUpgrade.getIrangeDevice()) && StringUtils.isBlank(gpuServerUpgrade.getDeviceCodes())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级gpu服务器不能为空");
        }
        return serverListService.saveUpModel(gpuServerUpgrade);
    }


    @RequestMapping("/upService")
    public String upService() {
        return "cr/gpuServer/gpuServer-upService";
    }

    /**
     * 服务升级
     *
     * @param gpuServerUpgrade 选择部分时，需要设备ID，全部时不需要
     * @return
     */
    @RequestMapping("/saveUpService")
    @ResponseBody
    public ResponseVo saveUpService(GpuServerUpgrade gpuServerUpgrade) {
        if (StringUtils.isBlank(gpuServerUpgrade.getUrl())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级地址不能为空");
        }
        if (StringUtils.isBlank(gpuServerUpgrade.getVersion())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级版本不能为空");
        }
        if (StringUtils.isBlank(gpuServerUpgrade.getTimeType())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级开始时间不能为空");
        }
        if ("1".equals(gpuServerUpgrade.getTimeType()) && StringUtils.isBlank(gpuServerUpgrade.getDproduceDate())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级定时时间不能为空");
        }
        if (StringUtils.isBlank(gpuServerUpgrade.getIrangeDevice())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级gpu服务器类型不能为空");
        }
        if ("1".equals(gpuServerUpgrade.getIrangeDevice()) && StringUtils.isBlank(gpuServerUpgrade.getDeviceIds())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("升级gpu服务器不能为空");
        }
        return serverListService.saveUpService(gpuServerUpgrade);
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
        ServerList serverList = serverListService.selectByPrimaryKey(sid);
        map.put("serverList", serverList);
        return "cr/gpuServer/gpuServer-modifyServerStatus";
    }

    /**
     * 保存编辑服务器状态
     *
     * @param serverList
     * @return
     */
    @RequestMapping("/saveServerStatus")
    @ResponseBody
    public ResponseVo saveServerStatus(ServerList serverList) {
        try {
            if (30 == serverList.getIstatus() || 40 == serverList.getIstatus()) {
                serverList.setIrunStatus(10);
            }
            serverListService.updateBySelective(serverList);
            String logText = MessageFormat.format("修改服务器状态成功，服务器编号{0}", serverList.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("保存服务器状态异常", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("保存服务器状态异常！");
        }
    }
}
