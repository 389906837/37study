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
 * @description ??????????????????
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
     * ????????????????????????
     *
     * @param paramPage ?????????????????????????????????
     * @return selectMerchantInfo ???????????????????????????
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
     * ????????????ID??????????????????
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
     * ??????????????????
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
            //?????????????????????
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
            log.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ????????????????????????
     *
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String list(ModelMap map, String sid) {
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(sid);
            //????????????????????? ?????????????????????????????????
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
            //?????????????????????????????????
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
            //??????????????????????????????
            MerchantClientConf merchantClientConf = merchantClientConfService.selectByPrimaryKey(sid);
            if (null == merchantClientConf) {
                merchantClientConf = new MerchantClientConf();
            }
            map.put("merchantClientConf", merchantClientConf);
            return "sh/merchantInfo/merchantInfo-edit2";
        } catch (Exception e) {
            log.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * ??????/??????????????????
     *
     * @param
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo save(@RequestParam(value = "slogoFile", required = false) MultipartFile merLogofile, @RequestParam(value = "loginLogofile", required = false) MultipartFile loginLogofile, MerchantInfo merchantInfo, String merchantUserName, MerchantDetail merchantDetail, MerchantClientConf merchantClientConf, String merchantDetailId, String KFscontactMobile) {
        try {
            if (null == merchantInfo.getDsignDate()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????????????????");
            }
            if (StringUtils.isBlank(merchantInfo.getSname())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("????????????????????????");
            }
            if (StringUtil.isBlank(merchantInfo.getScontactName()) || !StringUtil.hasCn(merchantInfo.getScontactName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("?????????????????????????????????");
            }
            if (StringUtils.isBlank((merchantInfo.getScontactMobile())) || !ValidateUtils.isMobile(merchantInfo.getScontactMobile())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("?????????????????????????????????");
            }
            if (StringUtils.isNotBlank(merchantInfo.getScontactEmail()) && !ValidateUtils.isEmail(merchantInfo.getScontactEmail())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????????????????");
            }
            if (StringUtils.isBlank(KFscontactMobile)) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("?????????????????????????????????");
            }
            if (StringUtils.isBlank(merchantClientConf.getSshortName())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("???????????????????????????");
            }
            // ????????????
            if (StringUtils.isBlank(merchantInfo.getId())) {
                return merchantInfoService.insertNewMerchant(merLogofile, loginLogofile, merchantInfo, merchantUserName, merchantDetail, merchantClientConf, KFscontactMobile);
            } else// ????????????
            {
                return merchantInfoService.upMerchant(merLogofile, loginLogofile, merchantInfo, merchantDetail, merchantClientConf, merchantDetailId, KFscontactMobile);
            }
        } catch (ServiceException sex) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(sex.getMessage());
        } catch (Exception ex) {
            log.error("???????????????????????????{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("????????????????????????");
        }
    }

    /**
     * ?????? ??????/??????
     *
     * @param checkboxId ??????Id
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
            merchantInfoService.updateBySelective(merchantInfo);//?????????
            //??????????????????????????????????????????redis??????
            //??????????????????
            if (BizTypeDefinitionEnum.IcompanyStatus.ALREADYSIGNED.getCode() == type) {
                //???????????????
                merchantInfo = merchantInfoService.selectByPrimaryKey(checkboxId);
                //?????????????????????????????????????????????????????????
                SummarizationDataPlf summarizationDataPlf1 = summarizationDataPlfService.selectByMerchantId(merchantInfo.getId());
                //??????????????????????????????????????????????????????
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
                //????????????????????????
                MerchantClientConf merchantClientConf = merchantClientConfService.selectByPrimaryKey(merchantInfo.getId());
                //key?????????CODE
                String catcheKey = RedisConst.MERCHANT_INFO_DETAILS + merchantInfo.getScode();
                String catcheKey2 = RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode();
                String catcheKey3 = RedisConst.YESTERDAY_PLATFORM_DATA_INFO + merchantInfo.getScode();
                //????????????
                try {
                    iCached.hset(RedisConst.MERCHANT_INFO, catcheKey, JSON.toJSONString(merchantInfo));
                    iCached.hset(RedisConst.MERCHANT_INFO, catcheKey2, merchantClientConf);
                    iCached.put(catcheKey3, JSON.toJSONString(summarizationDataPlf1));
                } catch (Exception e) {
                    log.error("----?????????????????????Redis?????????????????????????????????????????????" + e);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????");
                }
                log.debug("----??????Redis?????????KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey + "---" + JSON.toJSONString(merchantInfo));
                log.debug("----??????Redis?????????KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey2 + "---" + merchantClientConf);
                //??????????????????
            } else if (BizTypeDefinitionEnum.IcompanyStatus.CANCELLED.getCode() == type) {
                //key?????????CODE
                String catcheKey = RedisConst.SH_CLIENT_CONFIG + merchantInfo.getScode();
                String catcheKey2 = RedisConst.MERCHANT_INFO_DETAILS + merchantInfo.getScode();
                try {
                    iCached.hremove(RedisConst.MERCHANT_INFO, catcheKey);
                    iCached.hremove(RedisConst.MERCHANT_INFO, catcheKey2);
                } catch (Exception e) {
                    log.error("---?????????????????????Redis?????????????????????????????????????????????" + e);
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????");
                }
                log.debug("----??????Redis?????????KEY:" + RedisConst.MERCHANT_INFO + "---" + catcheKey + "---" + JSON.toJSONString(merchantInfo));
            }
            //????????????
            String logText = MessageFormat.format("??????/???????????????id{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("?????? ??????/???????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????/?????????????????????");
        }
    }

    /***
     * ?????????????????????????????????
     * @param
     */

    private void signCacheTJ(SummarizationDataPlf summarizationDataPlf, String scode) {
        try {
            String catcheKey = RedisConst.YESTERDAY_PLATFORM_DATA_INFO + scode;
            //????????????
            iCached.put(catcheKey, JSON.toJSONString(summarizationDataPlf));
        } catch (Exception e) {
            log.error("???????????????,???????????????????????????????????????{}", e);
            throw new ServiceException("????????????????????????????????????");
        }

    }

    /**
     * ??????/?????? ????????????
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
            //????????????
            String logText = MessageFormat.format("??????????????????????????????id{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("??????/?????? ?????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????????????????");
        }
    }

    /**
     * ??????/??????
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/configure")
    public @ResponseBody
    ResponseVo<String> enable(String checkboxId, Integer type, String str, String name) {
        try {
            if (StringUtils.isBlank(checkboxId) || null == merchantClientConfService.selectByPrimaryKey(checkboxId)) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
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
            //????????????
            String logText = MessageFormat.format("???????????????" + str + "???id{0}", merchantClientConf.getId());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("????????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????????????????");
        }
    }

    /**
     * ??????????????????
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
            log.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ????????????????????????
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
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("??????????????????");
            }
            merchantInfo.setTupdateTime(DateUtils.getCurrentDateTime());
            merchantInfo.setSupdateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            merchantInfoService.updateBySelective(merchantInfo);
            //????????????
            String logText = MessageFormat.format("???????????????????????????????????????{0}", merchantInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception ex) {
            log.error("?????????????????????????????????{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("????????????????????????????????????");
        }
    }


    /**
     * ????????????????????????
     *
     * @param sid ??????ID
     * @return
     */
    @RequestMapping("/menuAuthConfig")
    public String menuAuthConfig(ModelMap map, String sid) {
        try {
            //??????????????????
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
            // ???????????????Map???
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
            // ?????????????????????
            Iterator<Map.Entry<String, PurviewTreeVo>> it = treeMap.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, PurviewTreeVo> entry = it.next();
                PurviewTreeVo tree = entry.getValue();
                if (BoolUtils.toBooleanObject(tree.getBisroot())) {// ??????????????????
                    root.getNodes().add(tree);
                } else {
                    PurviewTreeVo parent = treeMap.get(tree.getSparentId());// ?????????Id??????
                    if (parent != null) {
                        parent.getNodes().add(tree);
                    }
                }
            }
            map.put("purviewList", root.getNodes());
            map.put("merchantInfo", merchantInfo);
            return "sh/merchantInfo/menuAuth-config";
        } catch (Exception e) {
            log.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();

    }

    /**
     * ????????????????????????
     *
     * @param merchantId ??????ID
     * @param purviewIds ????????????ID
     * @return
     */
    @RequestMapping("/updateMenuAuth")
    @SuppressWarnings("unchecked")
    public @ResponseBody
    ResponseVo<String> updateMenuAuth(String merchantId, String[] purviewIds) {
        try {
            MerchantInfo merchantInfo = merchantInfoService.selectByPrimaryKey(merchantId);
            if (merchantInfo == null) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("??????????????????");
            }
            if (purviewIds == null || purviewIds.length <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("????????????????????????");
            }
            merchantInfoService.updateMenuAuth(merchantInfo, purviewIds);
            // ????????????
            String logText = MessageFormat.format("????????????????????????????????????ID??????{0}", purviewIds);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("?????????????????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
        }
    }

    /**
     * ????????????
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

            //????????????
            String logText = MessageFormat.format("?????????????????????ID??????{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException se) {
            log.error("?????????????????????", se);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            log.error("?????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("???????????????????????????");
        }
    }

    /**
     * ????????????
     *
     * @param:
     */
    @RequestMapping("/selectMerchant")
    public String selectMerchant(String commodityIds, String commodityCodes, ModelMap map) {
        try {
            log.debug("??????????????????:{}", commodityIds);
            //????????????map
            Map<Integer, String> icompanyStatusMap = BizTypeDefinitionEnum.IcompanyStatus.getValue();
            map.put("icompanyStatusMap", icompanyStatusMap);
            if (StringUtil.isNotBlank(commodityIds)) {
                map.put("commodityIds", commodityIds + ",");
                map.put("commodityCodes", commodityCodes + ",");
                map.put("selectNums", commodityIds.split(",").length);//????????????
            }
            return "sh/merchantInfo/merchantInfo-select-list";
        } catch (Exception e) {
            log.error("?????????????????????{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * ??????????????????
     *
     * @param: deviceIds ??????ID??????
     */
    @RequestMapping("/getMerchantByIds")
    @ResponseBody
    ResponseVo<List<SelectMerchantVo>> getMerchantByIds(String[] commodityIds) {
        List<SelectMerchantVo> sddList = null;
        if (null != commodityIds) {
            sddList = new ArrayList<SelectMerchantVo>();
            MerchantInfo temp = null;
            SelectMerchantVo sdd = null;
            for (String commodityId : commodityIds) {//????????????ID
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
     * ????????????????????????????????????
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
     * ??????????????????(???????????????)
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
            log.error("?????????????????????{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("?????????????????????");
        }
        return ResponseVo.getSuccessResponse("?????????????????????");
    }

    /***
     * ????????????
     * @param
     */
    private void merchantInfoCached(MerchantInfo merchantInfo, MerchantConf WCMerchantConf, MerchantConf APMerchantConf, MerchantClientConf merchantClientConf, SummarizationDataPlf summarizationDataPlf) {
        try {
            //????????????????????????
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
            log.error("????????????????????????????????????{}", e);
            throw new ServiceException("????????????????????????");
        }
    }
}