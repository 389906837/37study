package com.cloud.cang.mgr.fr.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.fr.domain.FaceRegInfoDomain;
import com.cloud.cang.mgr.fr.service.FaceRegInfoService;
import com.cloud.cang.mgr.fr.vo.FaceRegInfoVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.fr.FaceRegInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.text.MessageFormat;
import java.util.List;

/**
 * @version 1.0
 * @description 年会人脸信息管理
 * @fileName AnnualMeetingFaceController
 * @time 2018年12月10日16:53:33
 */
@Controller
@RequestMapping("/annualMeetingFaceInfo")
public class AnnualMeetingFaceController {

    private static final Logger logger = LoggerFactory.getLogger(AnnualMeetingFaceController.class);

    @Autowired
    FaceRegInfoService faceRegInfoService;

    @Autowired
    OperatorService operatorService;


    /**
     * 年会人脸信息列表
     *
     * @return
     */
    @RequestMapping("/list")
    public String list() {
        return "fr/annualMeeting/faceInfo-list";
    }


    /**
     * 年会人脸信息列表列表数据
     *
     * @param faceRegInfoVo 初始化页面对象
     * @param paramPage     初始化分页对象
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<FaceRegInfoDomain>> queryData(FaceRegInfoVo faceRegInfoVo, ParamPage paramPage) {

        PageListVo<List<FaceRegInfoDomain>> pageListVo = new PageListVo<List<FaceRegInfoDomain>>();//返回的页面对象
        Page<FaceRegInfoDomain> page = new Page<FaceRegInfoDomain>();//新建分页对象
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        faceRegInfoVo.setIdelFlag(0);/* 是否删除0否1是 */


        if (null == faceRegInfoVo) {
            faceRegInfoVo = new FaceRegInfoVo();
        }

        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 70);
        faceRegInfoVo.setQueryCondition(queryCondition);
        faceRegInfoVo.setIdelFlag(0);

        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            faceRegInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }

        //分页查询
        page = faceRegInfoService.selectPageByDomainWhere(page, faceRegInfoVo);

        //将分页查询结果转换成页面List集合
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 添加人脸信息按钮
     *
     * @param map
     * @return
     */
    @RequestMapping("/toAdd")
    public String toAdd(ModelMap map) {
        FaceRegInfo faceRegInfo = new FaceRegInfo();
        map.put("faceRegInfo", faceRegInfo);
        return "fr/annualMeeting/faceInfo-add";
    }

    /**
     * 添加用户人脸信息
     *
     * @param faceImg     人脸图片
     * @param faceRegInfo 人脸信息
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ResponseVo<FaceRegInfo> add(@RequestParam(value = "file", required = false) MultipartFile faceImg, FaceRegInfo faceRegInfo) {
        try {
            String srealName = faceRegInfo.getSrealName();
            if (StringUtils.isBlank(srealName)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.nir", null));
            }
            if (null == faceImg || faceImg.getSize() <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.pup", null));
            }
            faceRegInfo.setSoperMan(SessionUserUtils.getSessionUserName());
            //插入数据到商品品牌信息表
            faceRegInfoService.insertFaceInfo(faceImg, faceRegInfo);
            //操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.auf", null), faceRegInfo.getSrealName());
            LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            logger.info("新增用户人脸结束：{}", faceRegInfo.getSrealName());
            return ResponseVo.getSuccessResponse(faceRegInfo);
        } catch (ServiceException e) {
            logger.error("新增用户人脸信息出现ServiceException异常：{}", e);
        } catch (Exception e) {
            logger.error("新增用户人脸信息出现Exception异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.eaufi", null));
    }

    /**
     * 查看年会人脸信息详情页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toView")
    public String toView(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询年会人脸信息详情
                FaceRegInfo faceRegInfo = faceRegInfoService.selectByPrimaryKey(sid);
                if (null != faceRegInfo) {
                    map.put("faceRegInfo", faceRegInfo);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.vfifn", null), faceRegInfo.getSfaceCode());
                    LogUtil.addOPLog(LogUtil.AC_VIEW, logText4, null);
                    logger.debug("查看人脸信息页面结束，人脸编号", faceRegInfo.getSfaceCode());
                    return "fr/annualMeeting/faceInfo-view";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 修改年会人脸信息详情页面
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询年会人脸信息详情
                FaceRegInfo faceRegInfo = faceRegInfoService.selectByPrimaryKey(sid);
                if (null != faceRegInfo) {
                    map.put("faceRegInfo", faceRegInfo);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.mfibfn", null), faceRegInfo.getSfaceCode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText4, null);
                    logger.debug("修改人脸信息页面结束，人脸编号", faceRegInfo.getSfaceCode());
                    return "fr/annualMeeting/faceInfo-edit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 编辑人脸信息
     *
     * @param faceRegInfo
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public ResponseVo<FaceRegInfo> brandInfotoEdit(@RequestParam(value = "file", required = false) MultipartFile faceImg, FaceRegInfo faceRegInfo) {
        logger.debug("编辑商品品牌信息开始");
        String sid = faceRegInfo.getId();
        if (StringUtils.isNotBlank(sid)) {
            try {
                // 查询是否存在
                FaceRegInfo faceRegInfo1 = faceRegInfoService.selectByPrimaryKey(sid);
                if (null == faceRegInfo1) {
                    ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.fidne", null));
                }
                faceRegInfo.setSoperMan(SessionUserUtils.getSessionUserName());
                faceRegInfo.setSfaceCode(faceRegInfo1.getSfaceCode());
                // 修改数据
                ResponseVo<FaceRegInfo> responseVo = faceRegInfoService.updateFaceInfo(faceRegInfo, faceImg);
                faceRegInfo = responseVo.getData();
                //操作日志
                String logText1 = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.mfitc", null), faceRegInfo.getSfaceCode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
                logger.debug("修改人脸信息，人脸编号", faceRegInfo.getSfaceCode());
                return responseVo;
            } catch (ServiceException e) {
                logger.error("修改人脸信息出现ServiceException异常：{}", e);
            } catch (Exception e) {
                logger.error("修改人脸信息出现Exception异常：{}", e);
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.ficbe", null));
    }

    /**
     * 审核人脸信息
     *
     * @param sid
     * @param map
     * @return
     */
    @RequestMapping("/toAudit")
    public String toAudit(String sid, ModelMap map) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //数据库查询年会人脸信息详情
                FaceRegInfo faceRegInfo = faceRegInfoService.selectByPrimaryKey(sid);
                if (null != faceRegInfo) {
                    map.put("faceRegInfo", faceRegInfo);
                    String logText4 = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.rfibfn", null), faceRegInfo.getSfaceCode());
                    LogUtil.addOPLog(LogUtil.AC_EDIT, logText4, null);
                    logger.debug("审核人脸信息页面结束，人脸编号", faceRegInfo.getSfaceCode());
                    return "fr/annualMeeting/faceInfo-audit";
                }
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 审核人脸信息
     *
     * @param faceRegInfo
     * @return
     */
    @RequestMapping("/audit")
    @ResponseBody
    public ResponseVo<FaceRegInfo> audit(FaceRegInfo faceRegInfo) {
        logger.debug("审核人脸信息开始");
        String sid = faceRegInfo.getId();
        if (StringUtils.isNotBlank(sid)) {
            try {
                // 查询是否存在
                FaceRegInfo faceRegInfo1 = faceRegInfoService.selectByPrimaryKey(sid);
                if (null == faceRegInfo1) {
                    return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.fidne", null));
                }
                if (null == faceRegInfo.getIauditStatus()) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.psaas", null));
                }
                // 修改数据
                ResponseVo<FaceRegInfo> responseVo = faceRegInfoService.updateFaceInfoByAudit(faceRegInfo);
                faceRegInfo = responseVo.getData();
                //操作日志
                String logText1 = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.rfiicmn", null), faceRegInfo.getSfaceCode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText1, null);
                logger.debug("审核人脸信息，人脸编号", faceRegInfo.getSfaceCode());
                return responseVo;
            } catch (ServiceException e) {
                logger.error("审核人脸信息出现ServiceException异常：{}", e);
            } catch (Exception e) {
                logger.error("审核人脸信息出现Exception异常：{}", e);
            }
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.ficbe", null));
    }


    /**
     * 删除信息
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    @ResponseBody
    public ResponseVo<String> delete(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                String operater = SessionUserUtils.getSessionUserName();
                ResponseVo<String> responseVo = faceRegInfoService.deleteFaceInfo(checkboxId, operater);
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.dfifn", null), responseVo.getData().toString());
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                return responseVo;
            }
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("系统异常删除人脸信息失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.tsadtfia", null));
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.sadofif",null));
    }


    /**
     * 批量导入信息
     *
     * @param modelMap
     * @return
     */
    @RequestMapping("/batchImport")
    public String sendAdToDevice(ModelMap modelMap) {
        try {
            return "fr/annualMeeting/faceInfo-batchImport";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 批量导入年会人脸Excel识别信息
     *
     * @param file Excel文件（支持2003，2007）
     * @return
     */
    @RequestMapping("/batchImportFaceInfo")
    @ResponseBody
    public ResponseVo<String> batchImportFaceInfo(@RequestParam(value = "file", required = true) MultipartFile file) {
        logger.debug("开始批量导入年会人脸Excel识别信息");
        try {
            ResponseVo<String> responseVo = faceRegInfoService.batchImportFaceInfo(file);
            //操作日志
            if (responseVo.isSuccess()) {
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.biamfeii",null), "");
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {
                if (null != responseVo.getData()) {
                    String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.fr.biamfeii",null), "");
                    LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                }
            }
            logger.debug("批量导入年会人脸Excel识别信息完成：{}", responseVo.getMsg());
            return responseVo;
        } catch (ServiceException e) {
            logger.error("批量导入年会人脸Excel识别信息出现Service异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("批量导入年会人脸Excel识别信息出现异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("con.fr.biamfeiie",null));
    }
}
