package com.cloud.cang.mgr.sh.web;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.sh.domain.MerchantInfoDomain;
import com.cloud.cang.mgr.sh.domain.PurviewDomain;
import com.cloud.cang.mgr.sh.service.*;
import com.cloud.cang.mgr.sh.vo.MerchantInfoVo;
import com.cloud.cang.mgr.sh.vo.PurviewTreeVo;
import com.cloud.cang.mgr.sh.vo.SelectMerchantVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.tj.service.SummarizationDataPlfService;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantDetail;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.tj.SummarizationDataPlf;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.BoolUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.*;


/**
 * @author yanlingfeng
 * @version 1.0
 * @description 商户基本信息
 * @time 2018-1-15
 * @fileName MerchantInfoController.java
 */
@Controller
@MultipartConfig
@RequestMapping("/merchantInfo")
public class MerchantInfoController {

    private static final Logger log = LoggerFactory.getLogger(MerchantInfoController.class);


    @Autowired
    MerchantInfoService merchantInfoService;
    @Autowired
    MerchantDetailService merchantDetailService;
    @Autowired
    private PurviewService purviewService;
    @Autowired
    private OperatorService operatorService;
    @Autowired
    private MerchantClientConfService merchantClientConfService;
    @Autowired
    private ICached iCached;
    @Autowired
    private MerchantConfService merchantConfService;
    @Autowired
    private SummarizationDataPlfService summarizationDataPlfService;

    @RequestMapping("/list")
    public String list(String method, ModelMap map) {
        if (StringUtil.isNotBlank(method)) {
            map.put("method", method);
        }
        return "sh/merchantInfo/merchantInfo-list";
    }

    /**
     * 商户管理列表数据
     *
     * @param paramPage 参数用于初始化分页大小
     * @return selectMerchantInfo 选择已签约商户使用
     */
    @RequestMapping("/queryData")
    @ResponseBody
    public PageListVo<List<MerchantInfoVo>> queryData(MerchantInfoDomain merchantInfoDomain, ParamPage paramPage, String selectMerchantInfo) {

        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 10);
        if ("true".equals(selectMerchantInfo)) {
            merchantInfoDomain.setIstatus(20);
        }
        merchantInfoDomain.setCondition(sql);
        PageListVo<List<MerchantInfoVo>> pageListVo = new PageListVo<List<MerchantInfoVo>>();
        Page<MerchantInfoVo> page = new Page<MerchantInfoVo>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        merchantInfoDomain.setIdelFlag(0);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            merchantInfoDomain.setOrderStr("shi." + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = merchantInfoService.selectPageByDomainWhere(page, merchantInfoDomain);
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
    ResponseVo<MerchantInfo> getMerchantInfoById(String id) {
        MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(id);
        if (merchantInfo == null) {
            merchantInfo = new MerchantInfo();
        }
        return ResponseVo.getSuccessResponse(merchantInfo);
    }

    /**
     * 查看商户详情
     *
     * @param sid
     * @return
     */
    @RequestMapping("/queryAll")
    public String quertyAll(ModelMap map, String sid) {
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(sid);
            MerchantDetail merchantDetail = null;
            if (20 == merchantInfo.getItype()) {
                merchantDetail = merchantDetailService.selectByPrimaryKey(sid);
            }
            if (null == merchantInfo) {
                merchantInfo = new MerchantInfo();
            }
            if (null == merchantDetail) {
                merchantDetail = new MerchantDetail();
            }
            //商户客户端配置
            MerchantConf merchantConf = new MerchantConf();
            merchantConf.setSmerchantId(sid);
            merchantConf.setItype(10);
            merchantConf.setIdelFlag(0);
            MerchantConf wCmerchantConf = merchantConfService.selectByIdType(merchantConf);
            if (null == wCmerchantConf) {
                wCmerchantConf = new MerchantConf();
            }
            merchantConf.setSmerchantId(sid);
            merchantConf.setItype(20);
            merchantConf.setIdelFlag(0);
            MerchantConf aPmerchantConf = merchantConfService.selectByIdType(merchantConf);
            if (null == aPmerchantConf) {
                aPmerchantConf = new MerchantConf();
            }
            map.put("wCmerchantConf", wCmerchantConf);
            map.put("aPmerchantConf", aPmerchantConf);


            MerchantClientConf merchantClientConf = merchantClientConfService.selectByPrimaryKey(sid);
            map.put("merchantClientConf", merchantClientConf);
            map.put("merchantInfo", merchantInfo);
            map.put("merchantDetail", merchantDetail);
            return "sh/merchantInfo/merchantInfo-query";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 添加编辑商户页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap map, String sid) {
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(sid);
            //如果用户是企业 查询商户基础详细信息表
            if (null != merchantInfo && 20 == merchantInfo.getItype()) {
                MerchantDetail merchantDetail = merchantDetailService.selectByPrimaryKey(sid);
                if (null == merchantDetail) {
                    merchantDetail = new MerchantDetail();
                }
                map.put("merchantDetail", merchantDetail);
            }
            if (merchantInfo == null) {
                merchantInfo = new MerchantInfo();
            }
            map.put("merchantInfo", merchantInfo);
            //查询商户资质附件信息表
            MerchantConf wCmerchantConf = new MerchantConf();
            MerchantConf merchantConf = new MerchantConf();
            if (StringUtils.isNotBlank(sid)) {
                merchantConf.setSmerchantId(sid);
                merchantConf.setItype(10);
                merchantConf.setIdelFlag(0);
                wCmerchantConf = merchantConfService.selectByIdType(merchantConf);
                if (null == wCmerchantConf) {
                    wCmerchantConf = new MerchantConf();
                }
            }
            MerchantConf aPmerchantConf = new MerchantConf();
            if (StringUtils.isNotBlank(sid)) {
                merchantConf.setSmerchantId(sid);
                merchantConf.setItype(20);
                merchantConf.setIdelFlag(0);
                aPmerchantConf = merchantConfService.selectByIdType(merchantConf);
                if (null == aPmerchantConf) {
                    aPmerchantConf = new MerchantConf();
                }
            }
            map.put("wCmerchantConf", wCmerchantConf);
            map.put("aPmerchantConf", aPmerchantConf);
            //查询商户客户端配置表
            MerchantClientConf merchantClientConf = merchantClientConfService.selectByPrimaryKey(sid);
            if (null == merchantClientConf) {
                merchantClientConf = new MerchantClientConf();
            }
            map.put("merchantClientConf", merchantClientConf);
            return "sh/merchantInfo/merchantInfo-edit2";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 添加/修改商户信息
     *
     * @param
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo save(@RequestParam(value = "slogoFile", required = false) MultipartFile merLogofile, @RequestParam(value = "loginLogofile", required = false) MultipartFile loginLogofile, MerchantInfo merchantInfo, String merchantUserName, MerchantDetail merchantDetail, MerchantClientConf merchantClientConf, String merchantDetailId, String KFscontactMobile) {
        try {
            if (null == merchantInfo.getDsignDate()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("请选择商户签约日！");
            }
            if (StringUtils.isBlank(merchantInfo.getSname())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户名不能为空！");
            }
            if (StringUtil.isBlank(merchantInfo.getScontactName()) || !StringUtil.hasCn(merchantInfo.getScontactName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("联系人姓名必须为中文！");
            }
            if (StringUtils.isBlank((merchantInfo.getScontactMobile())) || !ValidateUtils.isMobile(merchantInfo.getScontactMobile())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("联系人手机号格式错误！");
            }
            if (StringUtils.isNotBlank(merchantInfo.getScontactEmail()) && !ValidateUtils.isEmail(merchantInfo.getScontactEmail())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("联系人邮箱格式错误！");
            }
            if (StringUtils.isBlank(KFscontactMobile)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("客服联系电话不能为空！");
            }
            if (StringUtils.isBlank(merchantClientConf.getSshortName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户简称不能为空！");
            }
            // 执行新增
            if (StringUtils.isBlank(merchantInfo.getId())) {
                return merchantInfoService.insertNewMerchant(merLogofile, loginLogofile, merchantInfo, merchantUserName, merchantDetail, merchantClientConf, KFscontactMobile);
            } else// 执行修改
            {
                return merchantInfoService.upMerchant(merLogofile, loginLogofile, merchantInfo, merchantDetail, merchantClientConf, merchantDetailId, KFscontactMobile);
            }
        } catch (ServiceException sex) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(sex.getMessage());
        } catch (Exception ex) {
            log.error("编辑商户信息异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存商户信息失败");
        }
    }

    /**
     * 商户 签约/解约
     *
     * @param checkboxId 商户Id
     * @return
     */
    @RequestMapping("/contract")
    @ResponseBody
    public ResponseVo<String> contract(String checkboxId, Integer type) {
        try {
            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setId(checkboxId);
            merchantInfo.setIstatus(type);
            merchantInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            merchantInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            merchantInfo.setDsignDate(DateUtils.getCurrentDateTime());
            merchantInfoService.updateBySelective(merchantInfo);//签约日
            //修改商户状态为已签约则更新到redis缓存
            //签约更新缓存
            if (BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode() == type) {
                //商户信息表
                merchantInfo = merchantInfoService.selectByPrimaryKey(checkboxId);
                //签约则将商户对应平台数据汇总表加入缓存
                SummarizationDataPlf summarizationDataPlf1 = summarizationDataPlfService.selectByMerchantId(merchantInfo.getId());
                //当天首次签约则添加商户平台数据汇总表
                if (null == summarizationDataPlf1) {
                    summarizationDataPlf1 = new SummarizationDataPlf();
                    summarizationDataPlf1.setSmerchantId(merchantInfo.getId());
                    summarizationDataPlf1.setSmerchantCode(merchantInfo.getScode());
                    summarizationDataPlf1.setForderAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setForderRefundAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setIrefundNum(0);
                    summarizationDataPlf1.setIrefundAuditSuccessNum(0);
                    summarizationDataPlf1.setIabnormalNum(0);
                    summarizationDataPlf1.setIabnormalDealwithNum(0);
                    summarizationDataPlf1.setIabnormalIgnoreNum(0);
                    summarizationDataPlf1.setIabnormalChargebackNum(0);
                    summarizationDataPlf1.setIorderNum(0);
                    summarizationDataPlf1.setIorderManNum(0);
                    summarizationDataPlf1.setIregisteNum(0);
                    summarizationDataPlf1.setIdeviceNum(0);
                    summarizationDataPlf1.setIvisitorsNum(0);
                    summarizationDataPlf1.setIreplenishmentNum(0);
                    summarizationDataPlf1.setIrefundAuditFailNum(0);
                    summarizationDataPlf1.setIrefundAuditSuccessNum(0);
                    summarizationDataPlf1.setFapplyRefundAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setForderSuccessAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setForderFailAmount(BigDecimal.ZERO);

                    summarizationDataPlf1.setFdiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setFfirstDiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setFcouponDeductionAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setFpointDiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setFpromotionDiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setFactualPayAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setFotherDiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf1.setImemberNum(0);
                    summarizationDataPlf1.setInotReplenishmentNum(0);
                    summarizationDataPlf1.setIrefundAuditNum(0);

                    summarizationDataPlf1.setTaddTime(DateUtils.getCurrentDateTime());
                    summarizationDataPlf1.setDsummerEndTime(DateUtils.addDays(DateUtils.getDayEnd(new Date()), -1));
                    summarizationDataPlfService.insert(summarizationDataPlf1);
                /*    signCacheTJ(summarizationDataPlf1, merchantInfo.getScode());*/
                }
                //商户客户端配置表
                MerchantClientConf merchantClientConf = merchantClientConfService.selectByPrimaryKey(merchantInfo.getId());
                //key为商户CODE
                String catcheKey = RedisConst.MERCHANT_INFO_DETAILS + merchantInfo.getScode();
                String catcheKey2 = RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode();
                String catcheKey3 = RedisConst.YESTERDAY_PLATFORM_DATA_INFO + merchantInfo.getScode();
                //放入缓存
                try {
                    iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(merchantInfo));
                    iCached.hset(RedisConst.MERCHANT_INFO, catcheKey2, merchantClientConf);
                    iCached.put(catcheKey3, JSON.toJSONString(summarizationDataPlf1));
                } catch (Exception e) {
                    log.error("----签约商户，更新Redis结束，缓存失败！！，错误信息：" + e);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更新商户缓存失败");
                }
                log.debug("----更新Redis缓存主KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey + "---" + JSON.toJSONString(merchantInfo));
                log.debug("----更新Redis缓存主KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey2 + "---" + merchantClientConf);
                //解约更新缓存
            } else if (BizTypeDefinitionEnum.IcompanyStatus.CANCELLED.getCode() == type) {
                //key为商户CODE
                String catcheKey = RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode();
                String catcheKey2 = RedisConst.MERCHANT_INFO_DETAILS + merchantInfo.getScode();
                try {
                    iCached.hremove(RedisConst.MERCHANT_INFO, catcheKey);
                    iCached.hremove(RedisConst.MERCHANT_INFO, catcheKey2);
                } catch (Exception e) {
                    log.error("---解约商户，更新Redis结束，缓存失败！！，错误信息：" + e);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更新商户缓存失败");
                }
                log.debug("----更新Redis缓存主KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey + "---" + JSON.toJSONString(merchantInfo));
            }
            //操作日志
            String logText = MessageFormat.format("签约/解约商户，id{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("商户 签约/解约异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("签约/解约商户失败！");
        }
    }

    /***
     * 签约后将统计表加入缓存
     * @param
     */

    private void signCacheTJ(SummarizationDataPlf summarizationDataPlf, String scode) {
        try {
            String catcheKey = RedisConst.YESTERDAY_PLATFORM_DATA_INFO + scode;
            //放入缓存
            iCached.put(catcheKey, JSON.toJSONString(summarizationDataPlf));
        } catch (Exception e) {
            log.error("商户签约后,添加平台数据汇总缓存失败：{}", e);
            throw new ServiceException("添加平台数据汇总缓存失败");
        }

    }

    /**
     * 启用/禁用 二级分销
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/enable")
    public @ResponseBody
    ResponseVo<String> enable(String checkboxId, Integer type) {
        try {
            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setId(checkboxId);
            merchantInfo.setIdistributionSwitch(type);
            merchantInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            merchantInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            merchantInfoService.updateBySelective(merchantInfo);
            //操作日志
            String logText = MessageFormat.format("启禁用商户二级分销，id{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("启用/禁用 二级分销异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("二级分销启禁用失败！");
        }
    }

    /**
     * 启用/禁用
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/configure")
    public @ResponseBody
    ResponseVo<String> enable(String checkboxId, Integer type, String str, String name) {
        try {
            if (StringUtils.isBlank(checkboxId) || null == merchantClientConfService.selectByPrimaryKey(checkboxId)) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("商户无客户端配置！");
            }
            MerchantClientConf merchantClientConf = new MerchantClientConf();
            merchantClientConf.setId(checkboxId);
            merchantClientConf.setTupdateTime(DateUtils.getCurrentDateTime());
            merchantClientConf.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            if ("iisConfWechatGzh".equals(name)) {
                merchantClientConf.setIisConfWechatGzh(type);
            } else if ("iisConfAlipayShh".equals(name)) {
                merchantClientConf.setIisConfAlipayShh(type);
            } else if ("iisConfWechat".equals(name)) {
                merchantClientConf.setIisConfWechat(type);
            } else if ("iisConfAlipay".equals(name)) {
                merchantClientConf.setIisConfAlipay(type);
            }
            merchantClientConfService.updateBySelective(merchantClientConf);
            //操作日志
            String logText = MessageFormat.format("商户启禁用" + str + "，id{0}", merchantClientConf.getId());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("商户启禁用异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("二级分销启禁用失败！");
        }
    }

    /**
     * 二级分销页面
     *
     * @param sid
     * @return
     */
    @RequestMapping("/toDisEdit")
    public String toDisEdit(ModelMap map, String sid) {
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(sid);
            if (null == merchantInfo) {
                merchantInfo = new MerchantInfo();
            }
            map.put("merchantInfo", merchantInfo);
            return "sh/merchantInfo/merchantInfo-dis";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存用户二级分销
     *
     * @param merchantInfo
     * @return
     */
    @RequestMapping("/saveDis")
    @ResponseBody
    public ResponseVo saveDis(MerchantInfo merchantInfo) {
        try {
            MerchantInfo merchantInfo1Old = merchantInfoService.selectByPrimaryKey(merchantInfo.getId());
            if (null == merchantInfo1Old) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户不存在！");
            }
            merchantInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            merchantInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            merchantInfoService.updateBySelective(merchantInfo);
            //操作日志
            String logText = MessageFormat.format("保存商户二级分销设置，编号{0}", merchantInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception ex) {
            log.error("保存用户二级分销异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("保存用户二级分销设置失败");
        }
    }


    /**
     * 商户菜单权限配置
     *
     * @param sid 商户ID
     * @return
     */
    @RequestMapping("/menuAuthConfig")
    public String menuAuthConfig(ModelMap map, String sid) {
        try {
            //获取商户权限
            Map<String, Object> paramMap = new HashMap<String, Object>();
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(sid);
            if (!merchantInfo.getSparentId().equals("0")) {
                paramMap.put("iisMerchantUsed", 1);
            }

            paramMap.put("merchantId", merchantInfo.getId());
            paramMap.put("orderCondition", " b.sparent_id asc, a.dadd_date asc");
            List<PurviewDomain> purviewList = purviewService.selectPurviewByMerchant(paramMap);
            Map<String, PurviewTreeVo> treeMap = new LinkedHashMap<String, PurviewTreeVo>();
            Map<String, PurviewTreeVo> treeMap1 = new LinkedHashMap<String, PurviewTreeVo>();
            PurviewTreeVo root = new PurviewTreeVo();
            // 把菜单加到Map里
            for (PurviewDomain purview : purviewList) {
                PurviewTreeVo tree = new PurviewTreeVo();
                tree.setId(purview.getId());
                tree.setText(purview.getSpurName());
                tree.setIsSelect(purview.getIsSelect());
                tree.setMenuName(purview.getMenuName());
                if (purview.getMenuParentId().equals("0")) {
                    tree.setBisroot("1");
                    treeMap1.put(purview.getMenuId(), tree);
                    tree.setSparentId("0");
                } else {
                    PurviewTreeVo temp = treeMap1.get(purview.getMenuParentId());
                    tree.setSparentId(temp.getId());
                    tree.setBisroot("0");
                }

                treeMap.put(purview.getId(), tree);
            }
            // 递归添加子节点
            Iterator<Map.Entry<String, PurviewTreeVo>> it = treeMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, PurviewTreeVo> entry = it.next();
                PurviewTreeVo tree = entry.getValue();
                if (BoolUtils.toBooleanObject(tree.getBisroot())) {// 如果是根节点
                    root.getNodes().add(tree);
                } else {
                    PurviewTreeVo parent = treeMap.get(tree.getSparentId());// 根据父Id查询
                    if (parent != null) {
                        parent.getNodes().add(tree);
                    }
                }
            }
            map.put("purviewList", root.getNodes());
            map.put("merchantInfo", merchantInfo);
            return "sh/merchantInfo/menuAuth-config";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();

    }

    /**
     * 商户菜单权限分配
     *
     * @param merchantId 商户ID
     * @param purviewIds 商户权限ID
     * @return
     */
    @RequestMapping("/updateMenuAuth")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> updateMenuAuth(String merchantId, String[] purviewIds) {
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantId);
            if (merchantInfo == null) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("商户不存在！");
            }
            if (purviewIds == null || purviewIds.length <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择菜单权限！");
            }
            merchantInfoService.updateMenuAuth(merchantInfo, purviewIds);
            // 操作日志
            String logText = MessageFormat.format("商户菜单权限分配，权限码ID集合{0}", purviewIds);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("商户菜单权限分配异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常操作失败！");
        }
    }

    /**
     * 删除商户
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            merchantInfoService.delete(checkboxId);

            //操作日志
            String logText = MessageFormat.format("删除商户，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException se) {
            log.error("删除商户失败！", se);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            log.error("删除商户异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除失败！");
        }
    }

    /**
     * 选择商户
     *
     * @param:
     */
    @RequestMapping("/selectMerchant")
    public String selectMerchant(String commodityIds, String commodityCodes, ModelMap map) {
        try {
            log.debug("选择商户页面:{}", commodityIds);
            //签约状态map
            Map<Integer, String> icompanyStatusMap = BizTypeDefinitionEnum.IcompanyStatus.getValue();
            map.put("icompanyStatusMap", icompanyStatusMap);
            if (StringUtil.isNotBlank(commodityIds)) {
                map.put("commodityIds", commodityIds + ",");
                map.put("commodityCodes", commodityCodes + ",");
                map.put("selectNums", commodityIds.split(",").length);//选择个数
            }
            return "sh/merchantInfo/merchantInfo-select-list";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 获取商户信息
     *
     * @param: deviceIds 商户ID集合
     */
    @RequestMapping("/getMerchantByIds")
    @ResponseBody
    ResponseVo<List<SelectMerchantVo>> getMerchantByIds(String[] commodityIds) {
        List<SelectMerchantVo> sddList = null;
        if (null != commodityIds) {
            sddList = new ArrayList<SelectMerchantVo>();
            MerchantInfo temp = null;
            SelectMerchantVo sdd = null;
            for (String commodityId : commodityIds) {//循环设备ID
                temp = merchantInfoService.selectByPrimaryKey(commodityId);
                if (null != temp) {
                    sdd = new SelectMerchantVo();
                    sdd.setMerchantId(temp.getId());
                    sdd.setMerchantCode(temp.getScode());
                    sdd.setMerchantName(temp.getSname());
                    sddList.add(sdd);
                }
            }
        }
        return ResponseVo.getSuccessResponse(sddList);
    }

    /**
     * 设备选择指定商户（单选）
     *
     * @param
     * @return
     */
    @RequestMapping("/radioSelectList")
    public String radioSelectList(String sid, ModelMap map) {
        if (null != sid) {
            map.put("sid", sid);
        }
        return "sh/merchantInfo/merchantInfo-radioSelect-list";
    }

    /**
     * 更新商户缓存(已签约商户)
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/cache")
    public ResponseVo cache() {
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 10);
        MerchantInfoDomain merchantInfoDomain = new MerchantInfoDomain();
        merchantInfoDomain.setCondition(sql);
        merchantInfoDomain.setIdelFlag(0);
        merchantInfoDomain.setIstatus(BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode());
        List<MerchantInfo> merchantInfoList = merchantInfoService.selectByDomainWhere(merchantInfoDomain);
        try {
            for (MerchantInfo merchantInfo : merchantInfoList) {
                MerchantConf merchantConf = new MerchantConf();
                merchantConf.setSmerchantId(merchantInfo.getId());
                merchantConf.setIdelFlag(0);
                merchantConf.setItype(10);
                MerchantConf WCmerchantConf = merchantConfService.selectByIdType(merchantConf);
                merchantConf.setSmerchantId(merchantInfo.getId());
                merchantConf.setIdelFlag(0);
                merchantConf.setItype(20);
                MerchantConf APmerchantConf = merchantConfService.selectByIdType(merchantConf);
                MerchantClientConf merchantClientConf = new MerchantClientConf();
                merchantClientConf.setId(merchantInfo.getId());
                merchantClientConf.setIdelFlag(0);
                MerchantClientConf merchantClientConf1 = merchantClientConfService.selectByWhere(merchantClientConf);
                SummarizationDataPlf summarizationDataPlf = summarizationDataPlfService.selectByMerchantId(merchantInfo.getId());
                if (null == summarizationDataPlf) {
                    summarizationDataPlf = new SummarizationDataPlf();
                    summarizationDataPlf.setSmerchantId(merchantInfo.getId());
                    summarizationDataPlf.setSmerchantCode(merchantInfo.getScode());
                    summarizationDataPlf.setForderAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setForderRefundAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setIrefundNum(0);
                    summarizationDataPlf.setIrefundAuditSuccessNum(0);
                    summarizationDataPlf.setIabnormalNum(0);
                    summarizationDataPlf.setIabnormalDealwithNum(0);
                    summarizationDataPlf.setIabnormalIgnoreNum(0);
                    summarizationDataPlf.setIabnormalChargebackNum(0);
                    summarizationDataPlf.setIorderNum(0);
                    summarizationDataPlf.setIorderManNum(0);
                    summarizationDataPlf.setIregisteNum(0);
                    summarizationDataPlf.setIdeviceNum(0);
                    summarizationDataPlf.setIvisitorsNum(0);
                    summarizationDataPlf.setIreplenishmentNum(0);
                    summarizationDataPlf.setIrefundAuditFailNum(0);
                    summarizationDataPlf.setIrefundAuditSuccessNum(0);
                    summarizationDataPlf.setFapplyRefundAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setForderSuccessAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setForderFailAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setTaddTime(DateUtils.getCurrentDateTime());

                    summarizationDataPlf.setFdiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setFfirstDiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setFcouponDeductionAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setFpointDiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setFpromotionDiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setFactualPayAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setFotherDiscountAmount(BigDecimal.ZERO);
                    summarizationDataPlf.setImemberNum(0);
                    summarizationDataPlf.setInotReplenishmentNum(0);
                    summarizationDataPlf.setIrefundAuditNum(0);
                    summarizationDataPlf.setDsummerEndTime(DateUtils.addDays(DateUtils.getDayEnd(new Date()), -1));
                    summarizationDataPlfService.insert(summarizationDataPlf);
                }
                merchantInfoCached(merchantInfo, WCmerchantConf, APmerchantConf, merchantClientConf1, summarizationDataPlf);
            }
        } catch (ServiceException se) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            log.error("更新缓存失败：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("更新缓存失败！");
        }
        return ResponseVo.getSuccessResponse("更新缓存成功！");
    }

    /***
     * 更新缓存
     * @param
     */
    private void merchantInfoCached(MerchantInfo merchantInfo, MerchantConf WCMerchantConf, MerchantConf APMerchantConf, MerchantClientConf merchantClientConf, SummarizationDataPlf summarizationDataPlf) {
        try {
            //已签约才存入缓存
            String catcheKey = RedisConst.MERCHANT_INFO_DETAILS + merchantInfo.getScode();
            iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(merchantInfo));
            catcheKey = RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode();
            iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, merchantClientConf);
            if (null != WCMerchantConf) {
                catcheKey = RedisConst.SH_WECHAT_CONFIG + merchantInfo.getScode();
                iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(WCMerchantConf));
            }
            if (null != APMerchantConf) {
                catcheKey = RedisConst.SH_ALIPAY_CONFIG + merchantInfo.getScode();
                iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(APMerchantConf));
            }
            catcheKey = RedisConst.YESTERDAY_PLATFORM_DATA_INFO + merchantInfo.getScode();
            iCached.put(catcheKey, JSON.toJSONString(summarizationDataPlf));
        } catch (Exception e) {
            log.error("添加或更新域名缓存失败：{}", e);
            throw new ServiceException("更新商户缓存失败");
        }
    }
}