package com.cloud.cang.mgr.hy.web;


import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.hy.domain.IntegralAccountDomain;
import com.cloud.cang.mgr.hy.service.IntegralAccountService;
import com.cloud.cang.mgr.hy.service.IntegralSerialService;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.hy.vo.IntegralAccountVo;
import com.cloud.cang.mgr.hy.vo.IntegralSerialVo;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.model.hy.IntegralAccount;
import com.cloud.cang.model.hy.IntegralSerial;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.utils.SessionUserUtils;
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
import java.util.Map;

/**
 * @author ChangTanchang
 * @version 1.0
 * @description 会员积分账户
 * @time 2018-02-08 15:32:10
 * @fileName IntegralAccountController.java
 */
@Controller
@RequestMapping("/integralAccount")
public class IntegralAccountController {

    // 本类日志
    private static final Logger logger = LoggerFactory.getLogger(IntegralAccountController.class);

    // 注入积分serv
    @Autowired
    private IntegralAccountService integralAccountService;

    // 注入积分明细serv
    @Autowired
    private IntegralSerialService integralSerialService;

    // 会员基础信息serv
    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private PurviewService purviewService; // 权限码表

    @Autowired
    private OperatorService operatorService;

    @RequestMapping("/list")
    public String list() {
        return "hy/integralAccount-list";
    }

    /**
     * 会员积分表列表数据
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<IntegralAccountDomain>> queryDataAll(ParamPage paramPage, IntegralAccountVo integralAccountVo) {
        PageListVo<List<IntegralAccountDomain>> pageListVo = new PageListVo<List<IntegralAccountDomain>>();
        Page<IntegralAccountDomain> page = new Page<IntegralAccountDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 70);
        integralAccountVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            integralAccountVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = integralAccountService.selectAllAccount(page, integralAccountVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 会员积分明细
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/queryDataInfo")
    public String queryMemberInfo(ModelMap modelMap, String sid) {
        try {
            List<IntegralSerial> integralSerial = null;
            if (StringUtils.isNotBlank(sid)) {
                integralSerial = integralSerialService.selectMember(sid);
                modelMap.put("integralSerial", integralSerial);
            }
            return "hy/integralSerial-list";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 根据Id查询
     *
     * @param id
     * @return
     */
    @RequestMapping("/getById")
    public @ResponseBody
    ResponseVo<IntegralSerial> queryById(String id) {
        IntegralSerial integralSerial = integralSerialService.selectByPrimaryKey(id);
        return ResponseVo.getSuccessResponse(integralSerial);
    }

    /**
     * 会员明细积分值合计
     *
     * @param integralSerialVo
     * @return
     */
    @RequestMapping("/getIntegralTotal")
    public @ResponseBody
    ResponseVo<Map<String, String>> getIntegralTotal(IntegralSerialVo integralSerialVo) {
        Map<String, String> map = integralSerialService.selectIntegralTotal(integralSerialVo);
        return ResponseVo.getSuccessResponse(map);
    }

    /**
     * 跳转积分调整页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/adjustmentlist")
    public String list(ModelMap modelMap, String sid, Integer addLess) {
        try {
            IntegralAccount integralAccount = integralAccountService.selectByPrimaryKey(sid);
            if (null != integralAccount) {
                modelMap.put("integralAccount", integralAccount);
                modelMap.put("addLess", addLess);
            }
            return "hy/integralAccount-adjustmentList";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }


    /**
     * 积分调整
     *
     * @param integralAccount
     * @return
     */
    @RequestMapping("/integralAdjustment")
    public @ResponseBody
    ResponseVo<IntegralAccount> integralAdjustment(IntegralAccount integralAccount, IntegralAccountVo integralAccountVo) {
        // 创建积分账户新对象
        IntegralAccount param = new IntegralAccount();
        // 获取原对象的ID赋值给新对象作为查询条件
        param.setId(integralAccount.getId());
        // 查询当前ID的所有
        List<Map<String, String>> mapList = integralAccountService.selectIntegralAccountId(param.getId());
        // 获取页面输入的积分值
        String getTotalpointsTemp = String.valueOf(integralAccount.getItotalPoints());
        Integer getTotalpoints = Integer.valueOf(getTotalpointsTemp);
        Integer queryTotalpoints = 0;
        Integer queryIusedPoints = 0;
        Integer queryIusablePoints = 0;
        // 获取查询的总积分值
        String queryTotalpointsTemp = "";
        // 获取查询的已使用积分值
        String queryIusedPointsTemp = "";
        // 获取查询的可用积分值
        String queryIusablePointsTemp = "";
        // 循环获取数据库查询的总积分和已使用积分
        for (Map map : mapList) {
            queryTotalpointsTemp = String.valueOf(map.get("ITOTAL_POINTS"));
            queryTotalpoints = Integer.valueOf(queryTotalpointsTemp); // 总
            queryIusedPointsTemp = String.valueOf(map.get("IUSED_POINTS")); // 已使用
            queryIusedPoints = Integer.valueOf(queryIusedPointsTemp);
            queryIusablePointsTemp = String.valueOf(map.get("IUSABLE_POINTS")); // 可用
            queryIusablePoints = Integer.valueOf(queryIusablePointsTemp);
        }
        // 根据前台下拉框传入的addLess的值判断积分是增加还减少
        Integer totalPoints = 0; // 总积分
        Integer iusablePoints = 0; // 可用积分
        Integer iusedPoints = 0; // 已使用积分
        // 如果积分增加,总积分+增加的积分值,可用积分=总积分的值
        if (integralAccountVo.getAddLess().equals(0)) {
            totalPoints = getTotalpoints + queryTotalpoints;
            iusablePoints = getTotalpoints + queryIusablePoints;
            integralAccount.setItotalPoints(totalPoints);
            integralAccount.setIusablePoints(iusablePoints);
        } else {  // 如果积分减少,总积分不变,已使用积分+减少的积分值,可使用积分-减少的积分值
            integralAccount.setItotalPoints(queryTotalpoints);
            iusablePoints = queryIusablePoints - getTotalpoints;
            iusedPoints = queryIusedPoints + getTotalpoints;
            integralAccount.setIusablePoints(iusablePoints);
            integralAccount.setIusedPoints(iusedPoints);
        }
        try {
            if (StringUtil.isBlank(integralAccount.getId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.hy.tdiapro", null));
            }
            if (null == integralAccount.getItotalPoints()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("con.hy.tivcbe", null));
            }

            boolean flag = false;
            if (StringUtil.isNotBlank(integralAccount.getId())) {
                flag = true;
            }
            integralAccount = integralAccountService.saveOrUpdate(integralAccount);
            if (flag) {// 执行新增
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.hy.atpaap", null) + MessageSourceUtils.getMessageByKey("main.code", null) + "{0}", integralAccount.getItotalPoints(), integralAccount.getIusablePoints());
                LogUtil.addOPLog(LogUtil.AC_ADD, logText, null);
            } else {// 执行修改
                //操作日志
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("con.hy.rtpaap", null) + MessageSourceUtils.getMessageByKey("main.code", null) + "{0}", integralAccount.getIusablePoints(), integralAccount.getIusedPoints());
                LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);
            }
            return ResponseVo.getSuccessResponse(integralAccount);
        } catch (Exception ex) {
            logger.error("积分调整异常:{}", ex);
            return ErrorCodeEnum.ERROR_COMMON_SAVE.getResponseVo(MessageSourceUtils.getMessageByKey("con.hy.ftitiv", null));
        }
    }

}

