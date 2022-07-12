package com.cloud.cang.mgr.sh.web;

import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.domain.DomainConfDomain;
import com.cloud.cang.mgr.sh.service.DomainConfService;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sh.vo.DomainConfVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.sh.DomainConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
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
 * @author yanlingfeng
 * @version 1.0
 * @description 域名管理
 * @time 2018-1-18
 * @fileName DomainConfController.java
 */
@Controller
@RequestMapping("/domainconf")
public class DomainConfController {
    private static final Logger log = LoggerFactory.getLogger(DomainConfController.class);

    @Autowired
    MerchantInfoService merchantInfoService;
    @Autowired
    DomainConfService domainConfService;
    @Autowired
    private ICached iCached;
    @Autowired
    private OperatorService operatorService;

    @RequestMapping("/list")
    public String list() {
        return "sh/domainconf/domainconf-list";
    }

    /**
     * 用户管理列表数据
     *
     * @param paramPage 参数用于初始化分页大小
     * @return
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<DomainConfVo>> queryData(DomainConfDomain domainConfDomain, ParamPage paramPage) {
        PageListVo<List<DomainConfVo>> pageListVo = new PageListVo<List<DomainConfVo>>();
        Page<DomainConfVo> page = new Page<DomainConfVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        domainConfDomain.setIdelFlag(0);

        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 100);
        domainConfDomain.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            domainConfDomain.setOrderStr("A." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = domainConfService.selectPageByDomainWhere(page, domainConfDomain);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 根据用户ID获取商户信息
     *
     * @param id
     * @return
     */
    @RequestMapping("/getOperatorById")
    public @ResponseBody
    ResponseVo<DomainConf> getDomainConfById(String id) {
        DomainConf DomainConf = domainConfService.selectByPrimaryKey(id);
        if (DomainConf == null) {
            DomainConf = new DomainConf();
        }
        return ResponseVo.getSuccessResponse(DomainConf);
    }

    @RequestMapping("/edit")
    public String list(ModelMap map, String sid) {
        try {
            DomainConf domainConf = domainConfService.selectByPrimaryKey(sid);
            if (domainConf == null) {
                domainConf = new DomainConf();
            }
            map.put("domainConf", domainConf);
            MerchantInfo merchantInfo = new MerchantInfo();
        /*merchantInfo.setId(domainConf.getSmerchantId());*/
            merchantInfo.setIdelFlag(0);
            List<MerchantInfo> merchantInfoList = merchantInfoService.selectByEntityWhere(merchantInfo);
            map.put("merchantInfoList", merchantInfoList);
            return "sh/domainconf/domainconf-edit";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 修改增加域名
     *
     * @param domainConf
     * @return
     */
    @RequestMapping("/save")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<DomainConf> save(DomainConf domainConf) {
        try {
            if (StringUtils.isBlank(domainConf.getSmerchantId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户不能为空！");
            }
            if (StringUtils.isBlank(domainConf.getSdomainUrl())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("域名不能为空！");
            }
            // 执行新增
            if (StringUtils.isBlank(domainConf.getId())) {

                if (domainConfService.isUrlExist(domainConf.getSdomainUrl())) {
                    return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("域名已经存在");
                }
                domainConf.setTaddTime(DateUtils.getCurrentDateTime());
                domainConf.setSaddUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                domainConf.setTupdateTime(DateUtils.getCurrentDateTime());
                domainConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                domainConf.setIdelFlag(0);
                domainConf.setIstatus(10);

                domainConfService.insert(domainConf);
                //操作日志
                String logText = MessageFormat.format("新增域名，id{0}", domainConf.getId());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                return ResponseVo.getSuccessResponse(domainConf);
            } else {  // 执行修改
                DomainConf domainConf1Old = domainConfService.selectByPrimaryKey(domainConf.getId());
                if (!domainConf1Old.getSdomainUrl().equals(domainConf.getSdomainUrl()) && domainConfService.isUrlExist(domainConf.getSdomainUrl())) {
                    return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("域名已经存在");
                }
                domainConf.setTupdateTime(DateUtils.getCurrentDateTime());
                domainConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                domainConfService.updateBySelective(domainConf);
                //操作日志
                String logText = MessageFormat.format("编辑域名，id{0}", domainConf.getId());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
                return ResponseVo.getSuccessResponse(domainConf);
            }
        } catch (Exception ex) {
            log.error("修改增加域名异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存域名失败");
        }

    }

    /***
     * 域名更新缓存(已通过审核域名)
     * @param
     */
    @ResponseBody
    @RequestMapping("/cache")
    public ResponseVo cache() {
        DomainConf domainConf = new DomainConf();
        domainConf.setIdelFlag(0);
        domainConf.setIstatus(20);
        List<DomainConf> domainConfList = domainConfService.selectByEntityWhere(domainConf);
        try {
            for (DomainConf domainConf1 : domainConfList) {
                addOrUpdateCached(domainConf1);
            }
        } catch (ServiceException se) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(se.getMessage());
        } catch (Exception e) {
            log.error("更新域名缓存失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("更新域名缓存失败");
        }
        return ResponseVo.getSuccessResponse("更新缓存成功！");
    }

    /***
     * 添加或更新缓存
     * @param domainConf
     */
    private void addOrUpdateCached(final DomainConf domainConf) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                if (StringUtils.isNotBlank(domainConf.getSdomainUrl())) {
                    try {
                        if (domainConf.getIstatus().intValue() == BizTypeDefinitionEnum.DomainStatus.AUDIT_SUCCESS.getCode()) {
                            iCached.remove(domainConf.getSdomainUrl());//remove 缓存
                            //审核通过才存入缓存
                            iCached.put(domainConf.getSdomainUrl(), domainConf);
                        }
                    } catch (Exception e) {
                        log.error("添加或更新域名缓存失败：{}", e);
                        throw new ServiceException("添加或更新域名缓存失败");
                    }
                }
            }
        });
    }


    /**
     * 删除域名
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            domainConfService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除域名，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("删除域名异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 审核域名
     *
     * @param
     * @return
     */
    @RequestMapping("/examine")
    public String Examine(String sid, ModelMap map) {
        try {
            DomainConf domainConf = domainConfService.selectByPrimaryKey(sid);
            if (domainConf == null) {
                domainConf = new DomainConf();
            }
            map.put("domainConf", domainConf);
            return "sh/domainconf/domainConf-examine";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 审核域名: 通过/驳回
     *
     * @param up
     * @return
     */
    @RequestMapping("/saveExamine")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<DomainConf> saveExamine(DomainConf domainConf, String up) {
        try {
            if (null != domainConf && 30 == domainConf.getIstatus() && StringUtils.isBlank(domainConf.getSauditOpinion())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核驳回原因不能为空！");
            }
            DomainConf domainConf1 = domainConfService.selectByPrimaryKey(domainConf.getId());
            domainConf1.setSauditOpinion(domainConf.getSauditOpinion());
            domainConf1.setIstatus(domainConf.getIstatus());
            domainConf1.setTupdateTime(DateUtils.getCurrentDateTime());
            domainConf1.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            domainConf1.setTauditTime(DateUtils.getCurrentDateTime());
            domainConf1.setSauditOperId(SessionUserUtils.getSessionAttributeForUserDtl().getId());
            domainConf1.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            if (StringUtils.isBlank(up)) {      //如果up为空，表示商户当前没有通过审核的域名直接修改
                domainConfService.updateBySelective(domainConf1);
                addOrUpdateCached(domainConf1);
            } else {
                DomainConf domainConfOld = new DomainConf();
                domainConfOld.setTupdateTime(DateUtils.getCurrentDateTime());
                domainConfOld.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                domainConfOld.setTauditTime(DateUtils.getCurrentDateTime());
                domainConfOld.setSmerchantId(domainConf.getSmerchantId());
                domainConfOld.setSauditOperId(SessionUserUtils.getSessionAttributeForUserDtl().getId());
                domainConfOld.setSauditOperName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                domainConfOld.setIstatus(10);
                //查询通过审核的域名
                String oldUrl = domainConfService.selectThroughAuditByMerchantId(domainConf.getSmerchantId());
                iCached.remove(oldUrl);//remove 缓存
                domainConfService.updateBySmerchantId(domainConfOld);
                domainConfService.updateBySelective(domainConf1);
                addOrUpdateCached(domainConf1);
            }

            //操作日志
            String logText = MessageFormat.format("修改域名状态，域名id{0}", domainConf.getId());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse(domainConf);
        } catch (ServiceException ex) {
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(ex.getMessage());
        } catch (Exception ex) {
            log.error("审核域名: 通过/驳回异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("修改域名状态失败");
        }

    }

    /**
     * 查看该用户有没有已通过审核的域名
     *
     * @return
     */
    @RequestMapping("/selectDomain")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<DomainConf> selectDomain(DomainConf domainConf) {
        try {
            //查询用户是否有已通过审核的域名
            if (BizTypeDefinitionEnum.DomainStatus.AUDIT_SUCCESS.getCode() == domainConf.getIstatus()) {
                if (domainConfService.isIstatusExist(domainConf.getSmerchantId())) {
                    return ResponseVo.getSuccessResponse(domainConf);
                }
            }
            return ResponseVo.getSuccessResponse();
        } catch (Exception ex) {
            log.error("查看该用户有没有已通过审核的域名异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("修改域名状态失败!");
        }

    }


    /**
     * 启用/停用
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/enable")
    public @ResponseBody
    ResponseVo<String> enable(String checkboxId, Integer type) {
        try {
            if (StringUtils.isBlank(checkboxId)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择启禁用域名！");
            }
            if (null != type && type == 40) {
                //停用域名,删除已通过审核的域名
                DomainConf domainConf = domainConfService.selectByPrimaryKey(checkboxId);
                if (20 == domainConf.getIstatus()) {
                    iCached.remove(domainConf.getSdomainUrl());//remove 缓存
                }
            }
            DomainConf domainConf = new DomainConfDomain();
            domainConf.setId(checkboxId);
            domainConf.setIstatus(type);
            domainConf.setTupdateTime(DateUtils.getCurrentDateTime());
            domainConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            domainConfService.updateBySelective(domainConf);
            //操作日志
            String logText = MessageFormat.format("启用/停用域名，域名id{0}", domainConf.getId());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("商户域名启停用异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(" 商户域名启停用失败！");
        }
    }

}

