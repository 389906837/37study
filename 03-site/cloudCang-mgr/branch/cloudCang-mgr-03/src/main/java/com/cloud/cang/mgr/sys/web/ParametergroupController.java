package com.cloud.cang.mgr.sys.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.sys.service.ParameterGroupDetailService;
import com.cloud.cang.core.sys.service.ParametergroupService;
import com.cloud.cang.core.sys.vo.ParametergroupVo;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.sys.dao.FastMenuDao;
import com.cloud.cang.mgr.sys.service.FastMenuService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.sys.FastMenu;
import com.cloud.cang.model.sys.ParameterGroupDetail;
import com.cloud.cang.model.sys.Parametergroup;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.sun.org.apache.bcel.internal.generic.FADD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

/**
 * 数据字典参数管理相关控制层
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/paramGroup")
public class ParametergroupController {

    @Autowired
    ParametergroupService parametergroupService;

    @Autowired
    ParameterGroupDetailService parameterGroupDetailService;

    @Autowired
    private FastMenuService fastMenuService;

    private static final Logger log = LoggerFactory.getLogger(ParametergroupController.class);

    /**
     * 数据字典参数管理
     *
     * @return
     */
    @RequestMapping("/list")
    public String paramGroup() {
        return "sys/paramGroup/parametergroup-list";
    }

    /**
     * 查询数据字典参数
     *
     * @return
     */
    @RequestMapping("/query")
    public @ResponseBody
    PageListVo<List<Parametergroup>> queryParamGroup(ParamPage paramPage, ParametergroupVo parametergroup, ModelMap map) {
        PageListVo<List<Parametergroup>> pageListVo = new PageListVo<List<Parametergroup>>();

        Page<Parametergroup> page = new Page<Parametergroup>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            parametergroup.setOrderStr(paramPage.getSidx() + " " + paramPage.getSord());
        }
        page = parametergroupService.selectByVoWhere(page, parametergroup);
        map.put("groupNo", parametergroup.getSgroupNo());
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 编辑
     *
     * @param map
     * @param groupNo 编号
     * @return
     */
    @RequestMapping("/edit")
    public String edit(ModelMap map, String groupNo) {
        try {
            Parametergroup parametergroup = parametergroupService.selectByGroupNo(groupNo);
            if (parametergroup == null) {
                parametergroup = new Parametergroup();
                map.put("isAdd", 1);
            }
            map.put("parametergroup", parametergroup);
            map.put("groupNo", groupNo);
            return "sys/paramGroup/parametergroup-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 批量删除数据字典参数
     *
     * @param checkboxId
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo delParamGroup(String[] checkboxId) {
        parametergroupService.deleteParameterGroup(checkboxId);
        // 操作日志
        String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.delete.data.dic",null)+"{0}", checkboxId);
        LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 新增数据字典参数
     *
     * @param parametergroup
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<Parametergroup> save(Parametergroup parametergroup) {
        parametergroup.setSgroupNo(CoreUtils.newCode(EntityTables.SYS_PARAMETERGROUP));
        parametergroupService.insert(parametergroup);
        //操作日志
        String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.add.data.dic",null)+"，"+MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", parametergroup.getSgroupNo());
        LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        return ResponseVo.getSuccessResponse(parametergroup);
    }

    /**
     * 修改数据字典参数
     *
     * @param parametergroup
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/update")
    public @ResponseBody
    ResponseVo<Parametergroup> update(Parametergroup parametergroup) {
        parametergroupService.updateByPrimaryKey(parametergroup);
        //操作日志
        String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.modify.data.dic",null)+"，"+ MessageSourceUtils.getMessageByKey("main.code",null)+"{0}", parametergroup.getSgroupNo());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse(parametergroup);
    }


    /**
     * 查询数据字典参数明细
     *
     * @return
     */
    @RequestMapping("/queryDetail")
    public @ResponseBody
    PageListVo<List<ParameterGroupDetail>> queryDetail(ParameterGroupDetail parameterGroupDetail) {
        PageListVo<List<ParameterGroupDetail>> pageListVo = new PageListVo<List<ParameterGroupDetail>>();
        pageListVo.setRecords(50);
        if (parameterGroupDetail == null || StringUtil.isBlank(parameterGroupDetail.getSgroupid())) {
            return pageListVo;
        }
        List<ParameterGroupDetail> parameterGroupDetails = parameterGroupDetailService.selectByEntityWhere(parameterGroupDetail);
        List<ParameterGroupDetail> pgdlist = parameterGroupDetails;
        if (pgdlist != null) {
            pageListVo.setRows(pgdlist);
        }
        return pageListVo;
    }

    /**
     * 编辑数据字典参数明细
     *
     * @param map
     * @param sid 明细ID
     * @return
     */
    @RequestMapping("/editDetail")
    public String editDetail(ModelMap map, String sid, String groupId, String groupNo) {
        try {
            ParameterGroupDetail pgd = parameterGroupDetailService.selectByPrimaryKey(sid);
            if (pgd == null) {
                pgd = new ParameterGroupDetail();
                pgd.setSgroupid(groupId);
                map.put("isAdd", 1);
            }
            map.put("groupNo", groupNo);
            map.put("pgd", pgd);
            return "sys/paramGroup/parametergroupDetail-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 新增数据字典参数明细
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/saveDetail")
    public @ResponseBody
    ResponseVo saveDetail(ParameterGroupDetail parameterGroupDetail, @RequestParam(value = "loginLogofile", required = false) MultipartFile smenuIcon, String groupNo) {
        if (StringUtil.isBlank(parameterGroupDetail.getSgroupid())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.save.data.dic.error",null));
        }
        // 是否重复
        ParameterGroupDetail detail = parameterGroupDetailService.selectByExist(parameterGroupDetail);
        if (detail != null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.save.data.dic.name.exist",null));
        }
        //如果是添加 运营快捷菜单则添加其图片路径
        if ("SP000134".equals(groupNo)) {
            if (null != smenuIcon && smenuIcon.getSize() > 0) {
                // 1.文件上传
                String url;
                try {
                    url = uploadHome(smenuIcon);
                    parameterGroupDetail.setSremark(url);
                } catch (ServiceException e) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
                }
            }
        }
        parameterGroupDetailService.insert(parameterGroupDetail);
        //操作日志
        String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.add.data.dic.log",null), parameterGroupDetail.getSname());
        LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    /**
     * 修改数据字典参数明细
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/updateDetail")
    public @ResponseBody
    ResponseVo updateDetail(ParameterGroupDetail parameterGroupDetail, @RequestParam(value = "loginLogofile", required = false) MultipartFile smenuIcon, String groupNo) {
        // 是否重复
        ParameterGroupDetail detail = parameterGroupDetailService.selectByExist(parameterGroupDetail);
        if (detail != null) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("syscon.save.data.dic.name.exist",null));
        }
        //如果是修改 运营快捷菜单则修改其图片路径
        if ("SP000134".equals(groupNo)) {
            if (null != smenuIcon && smenuIcon.getSize() > 0) {
                // 1.文件上传
                String url;
                try {
                    url = uploadHome(smenuIcon);
                    parameterGroupDetail.setSremark(url);
                } catch (ServiceException e) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
                }
            }
        }
        parameterGroupDetailService.updateBySelective(parameterGroupDetail);
        //操作日志
        String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.modify.data.dic.log",null), parameterGroupDetail.getId());
        LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
        return ResponseVo.getSuccessResponse();
    }

    public String uploadHome(MultipartFile file) {
        if (file == null) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.empty",null));
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
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.error",null));
        }
        String url = "";

        try {
            String serverPath = "fast_menu/" + DateUtils.dateParseShortString(new Date()) + "/";
            filName = filName.substring(filName.lastIndexOf("."));
            String tempName = DateUtils.getCurrentSTimeNumber() + filName;
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                url = "/" + serverPath + tempName;
                return url;
            }
        } catch (IOException e) {
            throw new ServiceException(MessageSourceUtils.getMessageByKey("main.file.upload.not.found",null));
        }
        return null;
    }

    /**
     * 批量删除数据字典参数明细
     *
     * @param checkboxId
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping("/deleteDetail")
    public @ResponseBody
    ResponseVo delParamGroupDetail(String[] checkboxId, String groupNo) {
        if ("SP000134".equals(groupNo)) {
            for (String checkId : checkboxId) {
                ParameterGroupDetail parameterGroupDetail = parameterGroupDetailService.selectByPrimaryKey(checkId);
                String[] fileNameSplit = parameterGroupDetail.getSname().split("#");
                String name = fileNameSplit[0];
                fastMenuService.deleteByName(name);
            }
        }
        parameterGroupDetailService.batchDeleteByIds(checkboxId);
        // 操作日志
        String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("syscon.delete.data.dic.log",null), checkboxId);
        LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
        return ResponseVo.getSuccessResponse();
    }

}
