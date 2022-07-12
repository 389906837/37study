package com.cloud.cang.mgr.rm.web;

import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.common.utils.MessageSourceUtils;
import com.cloud.cang.mgr.rm.domain.ReplenishMentPlanDetailDomain;
import com.cloud.cang.mgr.rm.domain.ReplenishMentPlanDomain;
import com.cloud.cang.mgr.rm.service.ReplenishmentPlanDetailService;
import com.cloud.cang.mgr.rm.service.ReplenishmentPlanService;
import com.cloud.cang.mgr.rm.vo.ReplenishMentPlanVo;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sb.vo.ReplenishmentVo;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.model.rm.ReplenishmentPlan;
import com.cloud.cang.model.rm.ReplenishmentPlanDetail;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.rm.DynamicReplenishmentDto;
import com.cloud.cang.rm.DynamicReplenishmentResult;
import com.cloud.cang.rm.ReplenishmentPlanServicesDefine;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品计划补货记录查询
 *
 * @author ChangTanchang
 * @version 1.0
 * @time:2018-01-24 16:07:00
 */
@Controller
@RequestMapping("/replenishmentplant")
public class ReplenishMentPlanController {

    // 本类日志
    private static final Logger log = LoggerFactory.getLogger(ReplenishMentPlanController.class);

    // 注入serv
    @Autowired
    private ReplenishmentPlanService replenishmentPlanService;

    @Autowired
    private OperatorService operatorService; // 操作员信息表

    @Autowired
    MerchantInfoService merchantInfoService; // 商户信息表

    @Autowired
    DeviceInfoService deviceInfoService; // 设备基础信息表

    @Autowired
    ReplenishmentPlanDetailService replenishmentPlanDetailService; // 计划商品补货明细表

    @RequestMapping("/list")
    public String list() {
        return "rm/replenishmentplan-list";
    }

    /**
     * 商品补货记录查询
     *
     * @param
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<ReplenishMentPlanDomain>> queryReplenishMent(ParamPage paramPage, ReplenishMentPlanVo replenishMentPlanVo) {
        PageListVo<List<ReplenishMentPlanDomain>> pageListVo = new PageListVo<List<ReplenishMentPlanDomain>>();
        Page<ReplenishMentPlanDomain> page = new Page<ReplenishMentPlanDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        if (null == replenishMentPlanVo) {
            replenishMentPlanVo = new ReplenishMentPlanVo();
        }
        //生成查询条件
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionUserId());
        String queryCondition = operatorService.generatePurviewSql(operator, 80);
        replenishMentPlanVo.setQueryCondition(queryCondition);
        //页面对象排序方式 -> 按照ID升序排列
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            replenishMentPlanVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = replenishmentPlanService.selectReplenishMentPlan(page, replenishMentPlanVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 商品补货记录信息表明细
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/queryReplenishMentPlanInfo")
    public String queryReplenishMentInfo(ModelMap modelMap, String sid) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                ReplenishmentPlan replenishmentPlan = replenishmentPlanService.selectByPrimaryKey(sid);
                MerchantInfo merchantInfo = null; // 商户信息表
                DeviceInfo deviceInfo = null; // 设备信息表
                List<ReplenishMentPlanDetailDomain> list = null; // 计划补货商品明细表

                String getSid = replenishmentPlan.getId();
                if (StringUtils.isNotBlank(getSid)) {
                    merchantInfo = merchantInfoService.selectOne(replenishmentPlan.getSmerchantId());
                    deviceInfo = deviceInfoService.selectByPrimaryKey(replenishmentPlan.getSdeviceId());
                    list = replenishmentPlanDetailService.selectInfoId(getSid);
                }
                modelMap.put("replenishmentPlan", replenishmentPlan);
                modelMap.put("merchantInfo", merchantInfo);
                modelMap.put("deviceInfo", deviceInfo);
                modelMap.put("list", list);
            }
            return "rm/replenishmentplan-infoList";
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to404();
    }

    /**
     * 动态生成补货单
     *
     * @param sid      设备ID
     * @param modelMap
     * @return
     */
    @RequestMapping("/generateDynamicReplenishment")
    public String toGenerateDynamicReplenishment(String sid, ModelMap modelMap) {
        try {
            if (StringUtils.isNotBlank(sid)) {
                //查询设备信息
                ReplenishMentPlanDomain replenishMentPlanDomain = replenishmentPlanService.selectByrmpId(sid);//计划商品补货记录信息表
                if (null != replenishMentPlanDomain) {
                    DynamicReplenishmentDto replenishmentDto = new DynamicReplenishmentDto();
                    replenishmentDto.setSdeviceId(replenishMentPlanDomain.getSdeviceId());
                    //调用服务
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ReplenishmentPlanServicesDefine.DYNAMIC_REPLENISHMENT_SERVICE);// 服务名称
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<DynamicReplenishmentResult>>() {
                    });
                    invoke.setRequestObj(replenishmentDto); // post 参数
                    ResponseVo<DynamicReplenishmentResult> resVO = (ResponseVo<DynamicReplenishmentResult>) invoke.invoke();
                    if (resVO.isSuccess()) {
                        DynamicReplenishmentResult result = resVO.getData();
                        modelMap.put("replenishment", result);
                        if (result.isUpdate()) {
                            return "rm/dynamic_replenishment";//可以修改
                        } else {
                            return "rm/dynamic_replenishment_view";//不能修改
                        }
                    }
                    return ExceptionUtil.toBussinessError(resVO.getMsg(), -1, modelMap);
                }
            }
            return ExceptionUtil.to404();
        } catch (Exception e) {
            log.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 动态生成补货单
     *
     * @param replenishmentVo 补货单参数
     * @param request
     * @return
     */
    @RequestMapping("/replenishment/generate")
    public @ResponseBody
    ResponseVo<ReplenishmentPlan> generateReplenishment(ReplenishmentVo replenishmentVo, HttpServletRequest request) {
        try {
            if (null == replenishmentVo || null == replenishmentVo.getCommodityIds() || replenishmentVo.getCommodityIds().length <= 0) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("common.data.error"));
            }
            if (StringUtil.isBlank(replenishmentVo.getDeviceId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(MessageSourceUtils.getMessageByKey("common.data.error"));
            }
            ResponseVo<ReplenishmentPlan> responseVo = replenishmentPlanService.generateReplenishment(replenishmentVo, request);
            if (responseVo.isSuccess()) {
                String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.generate.replenishment"), responseVo.getData().getSdeviceCode(), DateUtils.dateToString(responseVo.getData().getTgenerateTime()));
                LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
                return responseVo;
            } else {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(responseVo.getMsg());
            }
        } catch (ServiceException e) {
            log.error("动态生成补货单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            log.error("操作动态生成补货单异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("rm.generate.replenishment.error"));
    }

    /**
     * 手动修改补货状态
     *
     * @param checkboxId
     * @param type
     * @return
     */
    @RequestMapping("/replenishMentStatus")
    public @ResponseBody
    ResponseVo<String> replenishMentStatus(String checkboxId, Integer type) {
        try {
            ReplenishmentPlan replenishmentPlan = new ReplenishmentPlan();
            // 10:待配货{可修改为:配送中,已完成.取消配货}
            if (type.equals(20)) {
                replenishmentPlan.setId(checkboxId);
                replenishmentPlan.setIstatus(type);
            }
            if (type.equals(30)) {
                replenishmentPlan.setId(checkboxId);
                replenishmentPlan.setIstatus(30);
            }
            if (type.equals(40)) {
                replenishmentPlan.setId(checkboxId);
                replenishmentPlan.setIstatus(40);
            }
            replenishmentPlanService.updateBySelective(replenishmentPlan);
            // 操作日志
            String logText = MessageFormat.format(MessageSourceUtils.getMessageByKey("log.manual.modify.replenishment.plan"), replenishmentPlan.getIstatus());
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            log.error("手动修改补货状态异常:{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(MessageSourceUtils.getMessageByKey("rm.manual.replenishment.error"));
        }
    }
}
