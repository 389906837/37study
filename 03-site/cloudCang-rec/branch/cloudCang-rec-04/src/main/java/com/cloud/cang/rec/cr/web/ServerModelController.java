package com.cloud.cang.rec.cr.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.cr.ServerModel;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.rec.common.ParamPage;
import com.cloud.cang.rec.common.utils.ExceptionUtil;
import com.cloud.cang.rec.common.utils.LogUtil;
import com.cloud.cang.rec.common.utils.PythonUtil;
import com.cloud.cang.rec.cr.domain.ServerModelDomain;
import com.cloud.cang.rec.cr.service.ServerModelService;
import com.cloud.cang.rec.sh.service.MerchantInfoService;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

/**
 * 识别服务模型管理Controller
 *
 * @author yanlingfeng
 * @version 1.0
 * @time:2018-09-20 13:29:00
 */
@Controller
@RequestMapping("/serverModel")
public class ServerModelController {
    private static final Logger log = LoggerFactory.getLogger(ServerModelController.class);

    @Autowired
    private ServerModelService serverModelService;
    @Autowired
    private MerchantInfoService merchantInfoService;

    /**
     * 识别服务模型管理列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "cr/serverModel/serverModel-list";
    }

    /**
     * 查询列表
     *
     * @param paramPage
     * @param serverModelDomain
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<ServerModel>> queryDataLog(ParamPage paramPage, ServerModelDomain serverModelDomain, String selectServerModel) {
        PageListVo<List<ServerModel>> pageListVo = new PageListVo<List<ServerModel>>();
        Page<ServerModel> page = new Page<ServerModel>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        serverModelDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            serverModelDomain.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        if (StringUtils.isNotBlank(selectServerModel) && "true".equals(selectServerModel)) {
            serverModelDomain.setIstatus(20);
        }

        page = serverModelService.selectPageByDomainWhere(page, serverModelDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 新增/编辑 识别服务模型管理
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap modelMap, String sid) {
        try {
            ServerModel serverModel = serverModelService.selectByPrimaryKey(sid);
            if (null == serverModel) {
                serverModel = new ServerModel();
            }
            if (null != serverModel.getIrangeType() && 20 == serverModel.getIrangeType()) {
                MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(serverModel.getSrangeList());
                modelMap.put("merchantName", merchantInfo.getSname());
            }
            modelMap.put("serverModel", serverModel);
            return "cr/serverModel/serverModel-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 新增/修改 识别服务模型管理
     *
     * @param serverModel
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo<String> save(ServerModel serverModel) {
        try {
            if (StringUtils.isBlank(serverModel.getSmodelAddress())) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("识别服务模型地址不能为空！");
            }
            if (serverModel.getIrangeType() == null) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("适用范围类型不能为空！");
            }
            if ((20 == serverModel.getIrangeType() || 30 == serverModel.getIrangeType()) && StringUtil.isBlank(serverModel.getSrangeList())) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("选择商户不能为空！");
            }
            File file = new File(serverModel.getSmodelAddress());
           /* if (!file.exists()) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("识别服务模型文件不存在！");
            }*/
            if (StringUtils.isBlank(serverModel.getId())) {
                //执行新增
                serverModelService.saveServerModel(serverModel, file);
            } else {
                //执行修改
                serverModelService.upServerModel(serverModel, file);
            }

        } catch (Exception e) {
            log.error("编辑识别服务模型异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("编辑识别服务模型异常!");
        }
        return ResponseVo.getSuccessResponse();
    }


    /**
     * @param bmodelZipFile bmodel zip文件
     * @param darknetZipFile darkent zip文件
     * @param jsonStr  ServerModel 对象字符串
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public ResponseVo<ServerModel> upload(@RequestParam(value = "bmodelFile", required = false) MultipartFile bmodelZipFile ,
                                          @RequestParam(value = "darknetFile", required = false) MultipartFile darknetZipFile ,
                                          @RequestParam("json")String jsonStr ) {
        try {
            if (bmodelZipFile == null ){
                return ResponseVo.getErrorResponse("没有bmodel zip文件！");
            }
            if (darknetZipFile == null ){
                return ResponseVo.getErrorResponse("没有darknet zip文件！");
            }

            if (!serverModelService.uploadModel(bmodelZipFile, darknetZipFile,  jsonStr)){
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("模型上传失败！");
            }
        } catch (Exception e) {
            log.error("模型上传失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("模型上传失败!");
        }
        return ResponseVo.getSuccessResponse();
    }






    /**
     * 删除 识别服务模型
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo delete(String[] checkboxId) {
        try {
            serverModelService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除识别服务模型，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除识别服务模型异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 识别服务模型审核
     *
     * @param
     * @return
     */
    @RequestMapping("/examine")
    public String examine(String sid, ModelMap map) {
        try {
            ServerModel serverModel = serverModelService.selectByPrimaryKey(sid);
            if (null == serverModel) {
                serverModel = new ServerModel();
            }
            map.put("serverModel", serverModel);
            return "cr/serverModel/serverModel-examine";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 识别服务模型审核
     *
     * @param serverModel
     * @return
     */
    @RequestMapping("/serverModelAudit")
    @ResponseBody
    public ResponseVo serverModelAudit(ServerModel serverModel) {
        try {
            log.info("识别服务模型审核:{}", serverModel.getScode());
            if (null == serverModel.getIstatus()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核状态不能为空！");
            }
            if (30 == serverModel.getIstatus() && null == serverModel.getSauditReason()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核驳回原因不能为空！");
            }
            serverModelService.serverModelAudit(serverModel);
        } catch (Exception e) {
            log.error("审核识别服务模型异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("审核异常！");
        }
        //操作日志
        String logText = MessageFormat.format("识别服务模型审核，审核编号{0}", serverModel.getScode());
        LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    @RequestMapping("/selectServerModel")
    public String selectServerModel() {
        return "cr/serverModel/serverModel-selectServerModel";
    }

}
