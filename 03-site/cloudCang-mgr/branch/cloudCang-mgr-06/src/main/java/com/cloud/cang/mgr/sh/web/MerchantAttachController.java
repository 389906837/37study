package com.cloud.cang.mgr.sh.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.service.MerchantAttachService;
import com.cloud.cang.mgr.sh.service.MerchantClientConfService;
import com.cloud.cang.mgr.sh.service.MerchantDetailService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.model.om.RefundImgDesc;
import com.cloud.cang.model.sh.MerchantAttach;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
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

import javax.servlet.annotation.MultipartConfig;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author yanlingfeng
 * @version 1.0
 * @description 商户资质附件信息
 * @time 2018-1-15
 * @fileName MerchantAttachController.java
 */
@Controller
@MultipartConfig
@RequestMapping("/merchantAttach")
public class MerchantAttachController {

    private static final Logger log = LoggerFactory.getLogger(MerchantAttachController.class);


    @Autowired
    MerchantInfoService merchantInfoService;
    @Autowired
    private MerchantAttachService merchantAttachService;


    /**
     * 商户 添加/修改 资质附件信息（单选)
     *
     * @param merchantAttach
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    public ResponseVo editImg(MerchantAttach merchantAttach, @RequestParam(value = "attachFile", required = false) MultipartFile sattachPathFiles) {
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantAttach.getSmerchantId());
            if (null == merchantInfo) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请先保存商户基础信息！");
            }
            if (StringUtils.isBlank(merchantAttach.getId())) {
                if (null == merchantAttach.getScode()) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择资质证书类型");
                }
                if (null == merchantAttach.getIsort()) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择排序号");
                }
                if (null != sattachPathFiles && sattachPathFiles.getSize() > 0) {
                    // 1.文件上传
                    String url;
                    try {
                        url = uploadHome(sattachPathFiles);
                        merchantAttach.setSattachPath(url);
                    } catch (ServiceException e) {
                        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
                    }
                } else {
                    return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("请选择附件");
                }
                merchantAttach.setSname(GrpParaUtil.getName("SP000105", merchantAttach.getScode().toString()));
                merchantAttach.setSmerchantId(merchantAttach.getSmerchantId());
                merchantAttach.setTaddTime(DateUtils.getCurrentDateTime());
                merchantAttach.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                merchantAttach.setTupdateTime(DateUtils.getCurrentDateTime());
                merchantAttach.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                merchantAttach.setIdelFlag(0);
                merchantAttachService.insert(merchantAttach);
                //操作日志
                String logText = MessageFormat.format("商户新增资质附件信息，商户Id{0}", merchantInfo.getId());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                return ResponseVo.getSuccessResponse();
            } else {
                merchantAttach.setTupdateTime(DateUtils.getCurrentDateTime());
                merchantAttach.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                merchantAttachService.updateBySelective(merchantAttach);
                //操作日志
                String logText = MessageFormat.format("商户修改资质附件信息，商户Id{0}", merchantInfo.getId());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return ResponseVo.getSuccessResponse();
            }
        } catch (Exception e) {
            log.error("商户编辑资质附件信息（单选)异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存资质附件失败");
        }
    }

    /**
     * 查询商户资质附件信息
     *
     * @param merchantAttach
     * @return
     */
    @RequestMapping("/queryAttachDetail")
    public @ResponseBody
    PageListVo<List<MerchantAttach>> queryDetail(MerchantAttach merchantAttach) {
        PageListVo<List<MerchantAttach>> pageListVo = new PageListVo<List<MerchantAttach>>();
        MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantAttach.getSmerchantId());
        if (null == merchantInfo) {
            return pageListVo;
        }
        merchantAttach.setIdelFlag(0);
        List<MerchantAttach> pgdlist = merchantAttachService.selectByEntityWhere(merchantAttach);
        if (pgdlist != null) {
            pageListVo.setRows(pgdlist);
        }
        return pageListVo;
    }

    /**
     * 商户修改资质附件信息
     *
     * @param sid 商户资质附件信息Id
     * @return
     */
    @RequestMapping("/editDetail")
    public String editDetail(ModelMap map, String sid) {
        try {
            MerchantAttach merchantAttach = merchantAttachService.selectByPrimaryKey(sid);
            map.put("merchantAttach", merchantAttach);
            return "sh/merchantAttach/merchantAttach-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 删除资质附件信息
     *
     * @param checkboxId 资质附件信息Id
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/deleteDetail")
    public @ResponseBody
    ResponseVo delParamGroupDetail(String checkboxId) {
        MerchantAttach merchantAttach = merchantAttachService.selectByPrimaryKey(checkboxId);
        merchantAttach.setIdelFlag(1);
        merchantAttachService.updateBySelective(merchantAttach);
        //操作日志
        String logText = MessageFormat.format("商户删除资质附件信息，Id{0}", checkboxId);
        LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 查看资质附件图片
     *
     * @param sid 商户资质附件Id
     * @return
     */
    @RequestMapping("/viewImg")
    public String viewImg(ModelMap map, String sid) {
        try {
            MerchantAttach merchantAttach = merchantAttachService.selectByPrimaryKey(sid);
            if (null == merchantAttach) {
                merchantAttach = new MerchantAttach();
            }
            map.put("merchantAttach", merchantAttach);
            return "sh/merchantAttach/merchantAttach-img";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    public String uploadHome(MultipartFile file) {
        if (file == null) {
            throw new ServiceException("没有找到上传文件");
        }
        //文件大小限制
    /*    if (111 < file.getSize()) {

        }*/
        //文件原名
        String filName = file.getOriginalFilename();
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];
        //图片类型限制
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        String url = "";

        try {
            String serverPath = "merchant_attach/" + DateUtils.dateParseShortString(new Date()) + "/";
            String fileFileName = file.getOriginalFilename();
            fileFileName = fileFileName.substring(fileFileName.lastIndexOf("."));
            String tempName = DateUtils.getCurrentSTimeNumber() + fileFileName;
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                url = "/" + serverPath + tempName;
                return url;
            }
        } catch (IOException e) {
            throw new ServiceException("没有找到文件，上传失败");
        }
        return null;
    }
}