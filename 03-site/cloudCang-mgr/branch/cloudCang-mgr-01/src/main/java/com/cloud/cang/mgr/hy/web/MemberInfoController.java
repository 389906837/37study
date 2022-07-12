package com.cloud.cang.mgr.hy.web;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.CouponQueryResult;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExcelExportUtil;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.domain.MemberInfoDomain;
import com.cloud.cang.mgr.hy.service.FreeDataService;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.hy.vo.MemberInfoVo;
import com.cloud.cang.mgr.om.vo.OrderAuditVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.mgr.sys.util.DateUtil;
import com.cloud.cang.mgr.sys.util.DesensitizeUtil;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.pay.FreeServicesDefine;
import com.cloud.cang.pay.QuerySignDto;
import com.cloud.cang.pay.SignResult;
import com.cloud.cang.pay.WechatSignResult;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ChangTanchang
 * @version 1.0
 * @description 会员列表
 * @time 2018-02-11 16:31:00
 * @fileName MbrRoleController.java
 */
@Controller
@RequestMapping("/memberInfo")
public class MemberInfoController {

    // 本类日志
    private static final Logger logger = LoggerFactory.getLogger(MemberInfoController.class);

    // 注入serv
    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    MerchantInfoService merchantInfoService;//商户信息

    @Autowired
    private PurviewService purviewService; // 权限码表


    @RequestMapping("/list")
    public String list() {
        return "hy/memberInfo-list";
    }

    /**
     * 供优惠券批量下发的页面使用(批量添加用户页面)
     *
     * @return
     */
    @RequestMapping("/toCouponBatchList")
    public String toCouponBatchList(ModelMap modelMap, String memberIds, String memberCodes) {
        logger.debug("选择用户页面:{}", memberIds);
        try {
            if (StringUtil.isNotBlank(memberIds)) {
                modelMap.put("memberIds", memberIds + ",");
                modelMap.put("memberCodes", memberCodes + ",");
                modelMap.put("selectNums", memberIds.split(",").length);
            }
        } catch (Exception e) {
            logger.error("传入的mid为null", e);
        }
        return "hy/toCouponUserSend-list";
    }

    /**
     * 会员用户表列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<MemberInfoDomain>> queryMemberInfo(ParamPage paramPage, MemberInfoVo memberInfoVo) {
        PageListVo<List<MemberInfoDomain>> pageListVo = new PageListVo<List<MemberInfoDomain>>();
        Page<MemberInfoDomain> page = new Page<MemberInfoDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        // 是否删除0否1是
        memberInfoVo.setIdelFlag(0);
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 70);
        memberInfoVo.setQueryCondition(queryCondition);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            memberInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = memberInfoService.selectAllMemberInfo(page, memberInfoVo);
        /*List<MemberInfoDomain> list = page.getResult();
        //用户没有该权限,用户名脱敏处理
        if (!hasPurCode("MEMBERINFO_MEMBER_NAME_DESENSITIZE")) {
            for (MemberInfoDomain memberInfoDomain : list) {
                memberInfoDomain.setSmemberName(DesensitizeUtil.mobilePhone(memberInfoDomain.getSmemberName()));
            }
        }*/
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * 会员用户表列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/querySelectData")
    public @ResponseBody
    PageListVo<List<MemberInfoDomain>> querySelectData(ParamPage paramPage, MemberInfoVo memberInfoVo) {
        PageListVo<List<MemberInfoDomain>> pageListVo = new PageListVo<List<MemberInfoDomain>>();
        Page<MemberInfoDomain> page = new Page<MemberInfoDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        // 是否删除0否1是
        memberInfoVo.setIdelFlag(0);
        memberInfoVo.setIstatus(1);
        memberInfoVo.setSmerchantId(SessionUserUtils.getSessionAttributeForUserDtl().getSmerchantId());
        /*//生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 70);
        memberInfoVo.setQueryCondition(queryCondition);*/
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            memberInfoVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = memberInfoService.selectAllMemberInfo(page, memberInfoVo);
        pageListVo.setPage(page.getPageNum());
        pageListVo.setRecords(page.getTotal());
        pageListVo.setTotal(page.getPages());
        pageListVo.setRows(page.getResult());
        return pageListVo;
    }

    @RequestMapping("/getById")
    public @ResponseBody
    ResponseVo<MemberInfo> queryDataByCondition(String id) {
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(id);
        return ResponseVo.getSuccessResponse(memberInfo);
    }

    /**
     * 会员基本信息
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/queryAll")
    public String queryMemberInfo(ModelMap modelMap, String sid) {
        try {
            MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(sid);
            if (memberInfo == null) {
                memberInfo = new MemberInfo();
            }
            modelMap.put("memberInfo", memberInfo);
            return "hy/memberInfo-infoList";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 跳转编辑页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/edit")
    public String addList(ModelMap modelMap, String sid) {
        try {
            MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(sid);
            if (memberInfo == null) {
                memberInfo = new MemberInfo();
            }
            modelMap.put("memberInfo", memberInfo);
            return "hy/memberInfo-edit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存会员用户表
     *
     * @param memberInfo
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<MemberInfo> save(MemberInfo memberInfo) {
        // 获取商户ID
        String merchantId = memberInfo.getSmerchantId();
        try {
            // 如果id为空就就添加
            if (StringUtils.isBlank(memberInfo.getId()) && StringUtils.isNotBlank(merchantId)) {
                // 根据商户ID查询商户
                MerchantInfo merchantInfo = merchantInfoService.selectOne(merchantId);
                // 商户编号
                String merchantCode = merchantInfo.getScode();
                memberInfo.setScode(CoreUtils.newCode(EntityTables.HY_MEMBER_INFO));
                if (StringUtils.isNotEmpty(merchantCode)) {
                    memberInfo.setSmerchantCode(merchantCode);//商户编号
                    memberInfo.setIdelFlag(0);
                    memberInfoService.insert(memberInfo);
                    // 操作日志
                    String logText = MessageFormat.format("新增会员用户表名称{0},编号{1}", memberInfo.getSrealName(), memberInfo.getScode());
                    LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
                }
            } else {
                // 修改
                MemberInfo oldMemberInfo = memberInfoService.selectByPrimaryKey(memberInfo.getId());
                memberInfo.setScode(oldMemberInfo.getScode());
                memberInfoService.updateBySelective(memberInfo);
                // 操作日志
                String logText = MessageFormat.format("修改会员用户表名称{0},编号{1}", memberInfo.getSrealName(), memberInfo.getScode());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }

            return ResponseVo.getSuccessResponse(memberInfo);
        } catch (ServiceException e) {
            logger.error("保存会员用户表异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("保存会员用户表异常：{}", e);
        }
        return new ResponseVo<>(false, 111, "商户ID为空,不能添加");
    }

    /**
     * 根据ID数据删除会员用户表
     *
     * @param checkboxId
     * @return
     * @throws Exception
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> delete(String[] checkboxId) {
        try {
            if (ArrayUtils.isNotEmpty(checkboxId)) {
                memberInfoService.delete(checkboxId);//逻辑删除
                //操作日志
                String logText = MessageFormat.format("删除设备与设备注册信息", checkboxId);
                LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
                return ResponseVo.getSuccessResponse();
            }
        } catch (Exception e) {
            logger.error("系统异常删除设备与设备注册失败！", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除设备与设备注册异常！");
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("系统异常删除设备与设备注册失败！");
    }

    /**
     * 重置密码
     *
     * @param sid
     * @return
     */
    @RequestMapping("/resetPassword")
    public @ResponseBody
    ResponseVo<String> resetPass(String sid) {
        memberInfoService.resetPassword(sid);
        try {
            //操作日志
            String logText = MessageFormat.format("重置用户密码，修改用户id{0}", sid);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception ex) {
            logger.error("重置密码异常：{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo("重置用户密码错误");
        }

    }

    /**
     * 推荐奖励是否启用/禁用(0否1是)
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/enableRecoaward")
    public @ResponseBody
    ResponseVo<String> enableRecoaward(String checkboxId, Integer type) {

        try {
            MemberInfo memberInfo = new MemberInfo();
            memberInfo.setId(checkboxId);
            memberInfo.setIisEnableRecoaward(type);
            memberInfo.setUpdateTime(DateUtils.getCurrentDateTime());
            memberInfo.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            // 操作日志
            String logText = MessageFormat.format("启禁用会员奖励，编号{0}", memberInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            memberInfoService.updateBySelective(memberInfo);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("推荐奖励是否启用/禁用异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("启禁用推荐会员奖励失败");
        }
    }

    /**
     * 冻结/解冻
     *
     * @param checkboxId
     * @param type
     * @return
     */
    @RequestMapping("/freezeAccount")
    public @ResponseBody
    ResponseVo<String> freezeAccount(String checkboxId, Integer type) {

        try {
            MemberInfo memberInfo = new MemberInfo();
            if (type.equals(1) || type.equals(4)) {
                memberInfo.setId(checkboxId);
                memberInfo.setIstatus(type);
                memberInfo.setUpdateTime(DateUtils.getCurrentDateTime());
                memberInfo.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            }
            // 操作日志
            String logText = MessageFormat.format("冻结/解冻，编号{0}", memberInfo.getScode());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            memberInfoService.updateBySelective(memberInfo);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("冻结/解冻异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("冻结/解冻失败");
        }
    }

    /**
     * 加入黑名单
     *
     * @param checkboxId
     * @param type
     * @return
     */
    @RequestMapping("/blackList")
    public @ResponseBody
    ResponseVo<String> blackList(String checkboxId, Integer type) {
        try {
            MemberInfo memberInfo = new MemberInfo();
            if (type.equals(1)) {
                memberInfo.setId(checkboxId);
                memberInfo.setIstatus(3);
                memberInfo.setUpdateTime(DateUtils.getCurrentDateTime());
                memberInfo.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            } else {
                memberInfo.setId(checkboxId);
                memberInfo.setIstatus(1);
                memberInfo.setUpdateTime(DateUtils.getCurrentDateTime());
                memberInfo.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            }
            memberInfoService.updateBySelective(memberInfo);
            // 操作日志
            String logText = MessageFormat.format("加入黑名单，状态{0}", memberInfo.getIstatus());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("加入黑名单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("加入黑名单失败");
        }
    }

    /**
     * 后台导出Excel
     *
     * @param request
     * @param response
     * @param memberInfoVo
     */
    @RequestMapping("/downloadExcel")
    public void downloadExcel(HttpServletRequest request, HttpServletResponse response, MemberInfoVo memberInfoVo) {
        ExcelExportUtil e = new ExcelExportUtil();
        List<Map<String, Object>> list = memberInfoService.selectMemberInfoByExport(memberInfoVo);
        e.createRow(0);
        String[] names = new String[]{"序号", "会员编号", "会员用户名", "商户名称", "性别", "会员手机号", "注册时间", "会员状态", "会员类型",
                "会员等级", "注册设备编号", "注册平台", "是否已首单", "是否实名认证", "是否首次绑卡", "最近购物时间"};
        for (int j = 0; j < names.length; j++) {//表头
            e.setCell(j, names[j]);
        }
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> obj = list.get(i);
            String sex = String.valueOf(obj.get("ISEX"));
            String status = String.valueOf(obj.get("ISTATUS"));
            String type = String.valueOf(obj.get("IREG_CLIENT_TYPE"));
            String level = String.valueOf(obj.get("IMEMBER_LEVEL"));
            String ctype = String.valueOf(obj.get("IREG_CLIENT_TYPE"));
            String order = String.valueOf(obj.get("IIS_ORDER"));
            String verified = String.valueOf(obj.get("IIS_VERIFIED"));
            String card = String.valueOf(obj.get("IIS_FIRST_TIE_CARD"));
            String code = String.valueOf(obj.get("SREG_DEVICE_CODE"));
            String stime = String.valueOf(obj.get("TLAST_SHOP_TIME"));
            e.createRow(i + 1);
            e.setCell(0, String.valueOf(i + 1));
            e.setCell(1, String.valueOf(obj.get("SCODE")));
            e.setCell(2, String.valueOf(obj.get("SMEMBER_NAME")));
            e.setCell(3, String.valueOf(obj.get("MERCHANTNAME")));
            if (sex.equals("0")) {
                e.setCell(4, String.valueOf("女"));
            } else {
                e.setCell(4, String.valueOf("男"));
            }
            e.setCell(5, String.valueOf(obj.get("SMOBILE")));
            e.setCell(6, String.valueOf(obj.get("TREGISTER_TIME")));
            if (status.equals("1")) {
                e.setCell(7, String.valueOf("正常"));
            }
            if (status.equals("2")) {
                e.setCell(7, String.valueOf("注销停用"));
            }
            if (status.equals("3")) {
                e.setCell(7, String.valueOf("系统黑名单"));
            }
            if (status.equals("4")) {
                e.setCell(7, String.valueOf("冻结"));
            }
            if (type.equals("10")) {
                e.setCell(8, String.valueOf("购物会员"));
            }
            if (type.equals("20")) {
                e.setCell(8, String.valueOf("补货会员"));
            }
            if (level.equals("10")) {
                e.setCell(9, String.valueOf("大众会员"));
            }
            if (level.equals("20")) {
                e.setCell(9, String.valueOf("黄金会员"));
            }
            if (level.equals("30")) {
                e.setCell(9, String.valueOf("铂金会员"));
            }
            if (level.equals("40")) {
                e.setCell(9, String.valueOf("钻石会员"));
            }
            if (code.equals("null")) {
                e.setCell(10, String.valueOf(" "));
            } else {
                e.setCell(10, String.valueOf(obj.get("SREG_DEVICE_CODE")));
            }
            if (ctype.equals("10")) {
                e.setCell(11, String.valueOf("微信支付"));
            }
            if (ctype.equals("20")) {
                e.setCell(11, String.valueOf("支付宝"));
            }
            if (ctype.equals("30")) {
                e.setCell(11, String.valueOf("微信公众号"));
            }
            if (ctype.equals("40")) {
                e.setCell(11, String.valueOf("支付宝生活号"));
            }
            if (ctype.equals("50")) {
                e.setCell(11, String.valueOf("APP android"));
            }
            if (ctype.equals("60")) {
                e.setCell(11, String.valueOf("APP ios "));
            }
            if (order.equals("0")) {
                e.setCell(12, String.valueOf("否"));
            }
            if (order.equals("1")) {
                e.setCell(12, String.valueOf("是"));
            }
            if (verified.equals("0")) {
                e.setCell(13, String.valueOf("否"));
            }
            if (verified.equals("1")) {
                e.setCell(13, String.valueOf("是"));
            }
            if (card.equals("0")) {
                e.setCell(14, String.valueOf("否"));
            }
            if (card.equals("1")) {
                e.setCell(14, String.valueOf("是"));
            }
            if (stime.equals("null")) {
                e.setCell(15, String.valueOf(" "));
            } else {
                e.setCell(15, String.valueOf(obj.get("TLAST_SHOP_TIME")));
            }
        }
        e.downloadExcel(request, response, "会员列表-" + DateUtils.getCurrentDTimeNumber() + ".xls");
    }

    /**
     * 支付宝免密补处理
     *
     * @param checkboxId 会员id
     * @param type       补处理类型 0:支付宝 1:微信
     * @return
     */
    @RequestMapping("/rechargeAgain")
    public @ResponseBody
    ResponseVo<String> rechargeAgain(String checkboxId, Integer type, HttpServletRequest request) {
        logger.info("补处理开始，参数{}" + checkboxId + ":" + type);
        if (StringUtils.isBlank(checkboxId) || null == type) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补处理参数错误！");
        }
        try {
            MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(checkboxId);

            //0 :支付宝免密支付补处理
            //1 :微信免密支付补处理
            if (0 == type) {
                QuerySignDto querySignDto = new QuerySignDto();
                querySignDto.setSmemberId(checkboxId);
                querySignDto.setSmerchantCode(memberInfo.getSmerchantCode());
                // 创建Rest服务客户端
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_QUERY_SIGN);
                invoke.setRequestObj(querySignDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<SignResult>>() {
                });
                ResponseVo<SignResult> responseVo = (ResponseVo<SignResult>) invoke.invoke();
                if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {
                    //协议当前状态1. TEMP：暂存，协议未生效过；2. NORMAL：正常；3. STOP：暂停
                   /* if ("NORMAL".equals(responseVo.getData().getStatus())) {*/
                    logger.info("开始支付宝免密支付补处理！");
                    try {
                        ResponseVo responseVo1 = memberInfoService.rechargeAgainUpData(responseVo.getData(), request);
                        if (responseVo1.isSuccess()) {
                            logger.info("支付宝免密支付补处理成功！");
                            return ResponseVo.getSuccessResponse("支付宝免密支付补处理成功！");

                        } else {
                            logger.info("支付宝补处理失败，参数{}" + JSON.toJSONString(responseVo1));
                            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付宝免密支付补处理失败！");
                        }
                    } catch (Exception e) {
                        logger.error("支付宝补处理异常", e);
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("支付宝免密支付补处理异常！");
                    }
                } else if (null != responseVo && null == responseVo.getData() && 1 == memberInfo.getIaipayOpen()) {
                    MemberInfo updata = new MemberInfo();
                    updata.setId(memberInfo.getId());
                    updata.setIaipayOpen(0);
                    updata.setUpdateTime(DateUtils.getCurrentDateTime());
                    updata.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    memberInfoService.updateBySelective(updata);
                    return ResponseVo.getSuccessResponse("支付宝补处理成功！");
                }
            } else if (1 == type) {
                QuerySignDto querySignDto = new QuerySignDto();
                querySignDto.setSmemberId(checkboxId);
                querySignDto.setSmerchantCode(memberInfo.getSmerchantCode());
                // 创建Rest服务客户端
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.WECHAT_QUERY_SIGN);
                invoke.setRequestObj(querySignDto);
                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<WechatSignResult>>() {
                });
                ResponseVo<WechatSignResult> responseVo = (ResponseVo<WechatSignResult>) invoke.invoke();
                if (null != responseVo && responseVo.isSuccess() && null != responseVo.getData()) {
                    //协议状态 0-签约中 1-解约
                  /*  if ("0".equals(responseVo.getData().getContract_state())) {*/
                    logger.info("开始微信免密支付补处理！");
                    try {
                        ResponseVo responseVo1 = memberInfoService.WechantRechargeAgainUpData(responseVo.getData(), request);
                        if (responseVo1.isSuccess()) {
                            logger.info("微信免密支付补处理成功！");
                            return ResponseVo.getSuccessResponse("微信免密支付补处理成功！");

                        } else {
                            logger.info("微信免密支付补处理失败，参数{}" + JSON.toJSONString(responseVo1));
                            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信免密支付补处理失败！");
                        }
                    } catch (Exception e) {
                        logger.error("微信免密支付补处理异常，参数{}" + e);
                        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("微信免密支付补处理异常！");
                    }
                 /*   }*/
                } else if (null != responseVo && null == responseVo.getData() && 1 == memberInfo.getIwechatOpen()) {
                    MemberInfo updata = new MemberInfo();
                    updata.setId(memberInfo.getId());
                    updata.setIwechatOpen(0);
                    updata.setUpdateTime(DateUtils.getCurrentDateTime());
                    updata.setUpateUser(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
                    memberInfoService.updateBySelective(updata);
                    return ResponseVo.getSuccessResponse("微信免密补处理成功！");
                }
            } else {
                logger.info("补处理类型错误!", type);
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补处理类型错误！");
            }
        } catch (Exception e) {
            logger.error("支付宝补处理异常，参数{}" + e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("补处理异常！");
        }
        return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("免密支付补处理失败！");
    }
}
