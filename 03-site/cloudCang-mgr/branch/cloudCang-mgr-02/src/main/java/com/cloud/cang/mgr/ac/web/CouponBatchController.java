package com.cloud.cang.mgr.ac.web;

/**
 * @version 1.0
 * @ClassName: CouponBatchController
 * @Description: 优惠券批量下发
 * @Author: ChangTanchang
 * @Date: 2018/3/20 10:33
 */

import com.cloud.cang.act.ActivityServicesDefine;
import com.cloud.cang.act.BatchCouponDto;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.PageListVo;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.core.common.RedisConst;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.mgr.ac.domain.CouponBatchDomain;
import com.cloud.cang.mgr.ac.domain.CouponBatchSelectUserDomain;
import com.cloud.cang.mgr.ac.domain.CouponCodeExDetailsDomain;
import com.cloud.cang.mgr.ac.service.CouponBatchService;
import com.cloud.cang.mgr.ac.service.CouponCodeExDetailsService;
import com.cloud.cang.mgr.ac.service.CouponUserSendService;
import com.cloud.cang.mgr.ac.vo.CouponBatchParam;
import com.cloud.cang.mgr.ac.vo.CouponBatchVo;
import com.cloud.cang.mgr.ac.vo.CouponCodeExDetailsVo;
import com.cloud.cang.mgr.common.EnumDefinition;
import com.cloud.cang.mgr.common.ParamPage;
import com.cloud.cang.mgr.common.utils.ExceptionUtil;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.service.MemberInfoService;
import com.cloud.cang.mgr.sb.service.DeviceInfoService;
import com.cloud.cang.mgr.sp.service.BrandInfoService;
import com.cloud.cang.mgr.sp.service.CategoryService;
import com.cloud.cang.mgr.sp.service.CommodityInfoService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.service.PurviewService;
import com.cloud.cang.model.ac.CouponBatch;
import com.cloud.cang.model.ac.CouponUserSend;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sp.BrandInfo;
import com.cloud.cang.model.sp.Category;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.model.sys.Operator;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/couponBatch")
public class CouponBatchController {

    private static final Logger logger = LoggerFactory.getLogger(CouponBatchController.class);

    // 优惠券批量下发
    @Autowired
    private CouponBatchService couponBatchService;

    // 优惠券批量下发(用户信息)
    @Autowired
    private CouponUserSendService couponUserSendService;

    // 券码兑换明细serv
    @Autowired
    private CouponCodeExDetailsService couponCodeExDetailsService;

    // 商品分类
    @Autowired
    private CategoryService categoryService;

    // 商品品牌
    @Autowired
    private BrandInfoService brandInfoService;

    // 商品信息
    @Autowired
    private CommodityInfoService commodityInfoService;

    // 设备信息
    @Autowired
    private DeviceInfoService deviceInfoService;

    // 会员信息
    @Autowired
    private MemberInfoService memberInfoService;

    @Autowired
    private PurviewService purviewService; // 权限码表

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private ICached iCached;

    @RequestMapping("/list")
    public String list() {
        return "ac/couponBatch-list";
    }

    @RequestMapping("/listInfo")
    public String listInfo() {
        return "ac/couponCodeExDetails-listInfo";
    }

    /**
     * 优惠券批量下发列表
     *
     * @param paramPage
     * @param couponBatchVo
     * @return
     */
    @RequestMapping("/queryData")
    public @ResponseBody
    PageListVo<List<CouponBatchDomain>> queryDataCoupon(ParamPage paramPage, CouponBatchVo couponBatchVo) {
        PageListVo<List<CouponBatchDomain>> pageListVo = new PageListVo<List<CouponBatchDomain>>();
        Page<CouponBatchDomain> page = new Page<CouponBatchDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 210);
        couponBatchVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            couponBatchVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = couponBatchService.queryDataCoupon(page, couponBatchVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }

    /**
     * 优惠券批量下发详情
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/queryCouponDetails")
    public String queryCouponDetails(ModelMap modelMap, String sid) {
        try {
            // 前台页面点击查看按钮传入的优惠券批量下发表(CouponBatch)的id值
            CouponBatch couponBatch = couponBatchService.selectByPrimaryKey(sid);
            CouponUserSend couponUserSend = null;
            //获取使用数据
            if (StringUtil.isNotBlank(couponBatch.getSuseLimitBrand())) {//商品品牌
                String tempArr[] = couponBatch.getSuseLimitBrand().split(",");
                BrandInfo brandInfo = null;
                StringBuffer sb = new StringBuffer();
                for (String brandId : tempArr) {
                    brandInfo = brandInfoService.selectByPrimaryKey(brandId);
                    sb.append(brandInfo.getSname() + ",");
                }
                modelMap.put("useLimitBrand", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }
            if (StringUtil.isNotBlank(couponBatch.getSuseLimitCategory())) {//商品分类
                String tempArr[] = couponBatch.getSuseLimitCategory().split(",");
                Category category = null;
                StringBuffer sb = new StringBuffer();
                for (String categoryId : tempArr) {
                    category = categoryService.selectByPrimaryKey(categoryId);
                    sb.append(category.getSname() + ",");
                }
                modelMap.put("useLimitCategory", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }
            if (StringUtil.isNotBlank(couponBatch.getSuseLimitCommodity())) {//商品信息
                String tempArr[] = couponBatch.getSuseLimitCommodity().split(",");
                CommodityInfo commodityInfo = null;
                StringBuffer sb = new StringBuffer();
                for (String commodityCode : tempArr) {
                    commodityInfo = commodityInfoService.selectByCode(commodityCode);
                    sb.append(commodityInfo.getId() + ",");
                }
                modelMap.put("useCommodityIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }
            if (StringUtil.isNotBlank(couponBatch.getSuseLimitDevice())) {//设备信息
                String tempArr[] = couponBatch.getSuseLimitDevice().split(",");
                DeviceInfo deviceInfo = null;
                StringBuffer sb = new StringBuffer();
                for (String scode : tempArr) {
                    deviceInfo = deviceInfoService.selectByCode(scode);
                    sb.append(deviceInfo.getId() + ",");
                }
                modelMap.put("useDeviceIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }

            couponUserSend = new CouponUserSend();
            couponUserSend.setIdelFlag(0);
            couponUserSend.setSbatchId(couponBatch.getId());
            List<CouponUserSend> couponUserSendList = couponUserSendService.selectByEntityWhere(couponUserSend);

            modelMap.put("couponBatch", couponBatch);
            modelMap.put("couponUserSendList", couponUserSendList);
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return "ac/couponBatch-infoList";
    }

    /**
     * 提审
     *
     * @param couponBatchId
     * @return
     */
    @RequestMapping("/arraign")
    public @ResponseBody
    ResponseVo<String> arraign(String couponBatchId) {
        try {
            if (StringUtils.isBlank(couponBatchId)) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("没有提审的用户！");
            }
            CouponBatch couponBatch = couponBatchService.selectByPrimaryKey(couponBatchId);
            if (couponBatch.getIstatus() > EnumDefinition.COUPON_ISTATUS_DRAFT) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("该下发券已经不是<font color=red>草稿</font>状态了，提审失败！");
            }
            couponBatch.setIstatus(EnumDefinition.COUPON_ISTATUS_NOT);
            couponBatch.setSsubmitOperatorId(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            couponBatch.setTsubmitDatetime(DateUtils.getCurrentDateTime());
            couponBatchService.updateBySelective(couponBatch);
            // 操作日志
            String logText = MessageFormat.format("优惠券批量下发，提审{0}", couponBatchId);
            LogUtil.addOPLog(LogUtil.AC_OTHER, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (Exception e) {
            logger.error("优惠券批量下发提审异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("提审操作失败！");
        }
    }

    /**
     * 跳转审核页面
     *
     * @param modelMap
     * @param rid
     * @return
     */
    @RequestMapping("/reviewEdit")
    public String reviewEdit(ModelMap modelMap, String rid) {
        try {
            if (StringUtils.isNotBlank(rid)) {
                CouponBatch couponBatch = couponBatchService.selectByPrimaryKey(rid);
                modelMap.put("couponBatch", couponBatch);
                return "ac/couponBatch-reviewEdit";
            }
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 审核
     *
     * @param couponBatchId
     * @param stype
     * @param saduitMsg
     * @param msgCode
     * @return
     */
    @RequestMapping("/review")
    public @ResponseBody
    ResponseVo review(String couponBatchId, String stype, String saduitMsg, String msgCode) {
        CouponBatch couponBatch = couponBatchService.selectByPrimaryKey(couponBatchId);
        if (null == couponBatch) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("批量发券信息不存在！");
        }
        if (couponBatch.getIstatus().intValue() == 10) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("批量发券信息未提审，不能审核！");
        }
        if (StringUtils.isBlank(msgCode)) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("短信验证码不能为空！");
        }
        String code = "";
        try {
            code = (String) iCached.get(RedisConst.MGR_LOGIN_CODE + msgCode);
            if (StringUtils.isBlank(code) || !code.equals(msgCode)) {
                throw new ServiceException("");
            }
            iCached.remove(RedisConst.MGR_LOGIN_CODE + msgCode);
        } catch (Exception e1) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("短信验证码错误！");
        }
        if (couponBatch.getIstatus() >= EnumDefinition.COUPON_ISTATUS_PASS) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("该下发券已经不是<font color=red>草稿、待审核</font>状态了，提审失败！");
        }

        if (stype.equals("11")) {
            Date date = DateUtils.getCurrentDateTime();
            if (date.after(couponBatch.getDcouponExpiryDate())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("审核时间大于券失效时间,审核失败");
            }
            couponBatch.setIstatus(EnumDefinition.COUPON_ISTATUS_PASS);
            couponBatch.setSauditOperatorName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            couponBatch.setTauditDatetime(date);
            couponBatch.setSauditOpinion(saduitMsg);
            couponBatchService.updateBySelective(couponBatch);

            //调用接口 生成数据到发券表
            try {
                logger.debug("开始调用批量发券接口，批次号:{} ------------->", couponBatch.getSbatchCode());

                ResponseVo<String> responseVo = null;

                BatchCouponDto batchCouponDto = new BatchCouponDto();
                batchCouponDto.setBatchId(couponBatch.getId());
                //创建Rest服务客户端
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.BATCH_COUPON_SERVICE);
                invoke.setRequestObj(batchCouponDto);
                //返回对象中含有泛型，则需要设置返回类型，否则无需设置
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                });
                responseVo = (ResponseVo<String>) invoke.invoke();

                if (responseVo.isSuccess()) {
                    logger.debug("成功批量发券，批次号:{}  ------------->", couponBatch.getSbatchCode());
                } else {
                    throw new ServiceException("调用批量发券接口错误：" + responseVo.getMsg());
                }
            } catch (Exception e) {
                logger.debug("调用批量发券失败，批次号:{}  ------------->{}", couponBatch.getSbatchCode(), e);
                throw new ServiceException("调用批量发券失败！" + e.getMessage());
            }
            //操作日志
            String logText = MessageFormat.format("审核通过下发优惠券，名称{0},编号{1},总计发放{2}张", couponBatch.getScouponTypeName(), couponBatch.getSbatchCode(), couponBatch.getIbatchNumber());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);

            return ResponseVo.getSuccessResponse("审核通过成功！优惠券已下发，总计发放:<font color=red>" + couponBatch.getIbatchNumber() + "</font>张");
        } else {
            couponBatch.setIstatus(21);//10:DRAFT:草稿11:NOT:待审核20:PASS:审核通过21:REFUSED:审核拒绝
            couponBatch.setSauditOperatorName(SessionUserUtils.getSessionAttributeForUserDtl().getRealName());
            couponBatch.setTauditDatetime(DateUtils.getCurrentDateTime());
            couponBatch.setSauditOpinion(saduitMsg);
            couponBatchService.updateBySelective(couponBatch);

            //操作日志
            String logText = MessageFormat.format("审核拒绝下发优惠券，名称{0},编号{1}", couponBatch.getScouponTypeName(), couponBatch.getSbatchCode(), couponBatch.getIbatchNumber());
            LogUtil.addOPLog(LogUtil.AC_EDIT, logText, null);

            return ResponseVo.getSuccessResponse("审核拒绝成功！");
        }
    }

    /**
     * 跳转添加/编辑页面
     *
     * @param modelMap
     * @param sid
     * @return
     */
    @RequestMapping("/toEdit")
    public String toEdit(ModelMap modelMap, String sid) {
        try {
            CouponBatch couponBatch = couponBatchService.selectByPrimaryKey(sid);
            if (couponBatch == null) { // 新增
                couponBatch = new CouponBatch();
            } else { //编辑获取用户信息
                //获取使用数据
                if (StringUtil.isNotBlank(couponBatch.getSuseLimitBrand())) {//商品品牌
                    String tempArr[] = couponBatch.getSuseLimitBrand().split(",");
                    BrandInfo brandInfo = null;
                    StringBuffer sb = new StringBuffer();
                    for (String brandId : tempArr) {
                        brandInfo = brandInfoService.selectByPrimaryKey(brandId);
                        sb.append(brandInfo.getSname() + ",");
                    }
                    modelMap.put("useLimitBrand", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                }
                if (StringUtil.isNotBlank(couponBatch.getSuseLimitCategory())) {//商品分类
                    String tempArr[] = couponBatch.getSuseLimitCategory().split(",");
                    Category category = null;
                    StringBuffer sb = new StringBuffer();
                    for (String categoryId : tempArr) {
                        category = categoryService.selectByPrimaryKey(categoryId);
                        sb.append(category.getSname() + ",");
                    }
                    modelMap.put("useLimitCategory", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                }
                if (StringUtil.isNotBlank(couponBatch.getSuseLimitCommodity())) {//商品信息
                    String tempArr[] = couponBatch.getSuseLimitCommodity().split(",");
                    CommodityInfo commodityInfo = null;
                    StringBuffer sb = new StringBuffer();
                    for (String commodityCode : tempArr) {
                        commodityInfo = commodityInfoService.selectByCode(commodityCode);
                        sb.append(commodityInfo.getId() + ",");
                    }
                    modelMap.put("useCommodityIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                }
                if (StringUtil.isNotBlank(couponBatch.getSuseLimitDevice())) {//设备信息
                    String tempArr[] = couponBatch.getSuseLimitDevice().split(",");
                    DeviceInfo deviceInfo = null;
                    StringBuffer sb = new StringBuffer();
                    for (String scode : tempArr) {
                        deviceInfo = deviceInfoService.selectByCode(scode);
                        sb.append(deviceInfo.getId() + ",");
                    }
                    modelMap.put("useDeviceIds", sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 1) : "");
                }

                CouponUserSend userSend = new CouponUserSend();
                userSend.setSbatchId(couponBatch.getId());
                List<CouponUserSend> sendList = couponUserSendService.selectByEntityWhere(userSend);
                StringBuffer sb = new StringBuffer();
                for (CouponUserSend couponUserSend : sendList) {
                    sb.append(couponUserSend.getSmemberId() + ",");
                }
                modelMap.put("smemberId", StringUtil.isNotBlank(sb.toString()) ? sb.toString().substring(0, sb.toString().length() - 1) : "");
            }
            modelMap.put("couponBatch", couponBatch);
            return "ac/couponBatch-toEdit";
        } catch (Exception e) {
            logger.error("跳转页面异常：{}", e);
        }
        return ExceptionUtil.to500();
    }

    /**
     * 保存优惠券批量下发表
     *
     * @param couponBatch
     * @return
     */
    @RequestMapping("/save")
    public @ResponseBody
    ResponseVo<CouponBatch> save(HttpServletRequest request, CouponBatch couponBatch, CouponBatchParam param) {
        ResponseVo responseVo = ResponseVo.getSuccessResponse();
        try {
            if (param.getMember() == null || (param.getMember() != 0 && StringUtil.isBlank(param.getSmemberId()))) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("请选择下发用户！");
            }
            if (couponBatch.getIcouponType().intValue() == BizTypeDefinitionEnum.CouponType.COMMODITY.getCode()) {
                if (StringUtil.isBlank(param.getScommodityId()) || StringUtil.isBlank(param.getScommodityCode())) {
                    return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("商品券请选择商品信息！");
                }
                couponBatch.setSuseLimitCommodity(param.getScommodityCode());
            }
            if (param.getMember().intValue() == 0 && (null == param.getCouponNum() || param.getCouponNum().intValue() <= 0)) {
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("券下发数量不能为空或小于0！");
            }
            couponBatch.setIdelFlag(0);//是否删除
            couponBatch.setItype(0);
            couponBatch.setScouponTypeName(GrpParaUtil.getName(EnumDefinition.SCOUPON_TYPE_PARAM_CODE, couponBatch.getIcouponType().toString()));//劵类型名称
            return couponBatchService.saveOrUpdate(request, couponBatch, param);
        } catch (ServiceException e) {
            logger.error("批量下发券保存异常:{}", e);
            responseVo.setMsg(e.getMessage());
        } catch (Exception e) {
            logger.error("批量下发券保存异常:{}", e);
            responseVo.setMsg("批量下发券保存异常");
        }
        responseVo.setSuccess(false);
        return responseVo;
    }

    /**
     * 批量添加会员用户信息
     *
     * @param memberId
     * @return
     */
    @RequestMapping("/batch")
    public @ResponseBody
    ResponseVo<List<CouponUserSend>> batchCouponBatchUserSend(String[] memberId, String batchId) {
        ResponseVo<List<CouponUserSend>> responseVo = ResponseVo.getSuccessResponse();
        List<CouponUserSend> list = null;
        if (memberId != null) {
            list = new ArrayList<CouponUserSend>();
            MemberInfo temp = null;
            CouponUserSend batchAdd = null;
            CouponUserSend params = null;
            List<CouponUserSend> userSends = null;
            // 循环会员ID
            for (String id : memberId) {
                userSends = null;
                if (StringUtil.isNotBlank(batchId)) {
                    params = new CouponUserSend();
                    params.setSbatchId(batchId);
                    params.setSmemberId(id);
                    userSends = couponUserSendService.selectByEntityWhere(params);
                }
                if (null != userSends && userSends.size() > 0) {
                    list.add(userSends.get(0));
                } else {
                    temp = memberInfoService.selectByPrimaryKey(id);
                    if (temp != null) {
                        batchAdd = new CouponUserSend();
                        batchAdd.setSmemberId(temp.getId());
                        batchAdd.setSmemberCode(temp.getScode());
                        batchAdd.setSmemberName(temp.getSmemberName());
                        batchAdd.setInumber(1);
                        list.add(batchAdd);
                    }
                }
            }
            responseVo.setData(list);
        }
        return responseVo;
    }


    /**
     * 根据ID删除优惠券批量下发
     *
     * @param checkboxId
     * @return
     */
    @RequestMapping("/delete")
    public @ResponseBody
    ResponseVo<String> detele(String[] checkboxId) {
        try {
            couponBatchService.delete(checkboxId);
            //操作日志
            String logText = MessageFormat.format("删除优惠券批量下发用户，删除ID集合{0}", checkboxId);
            LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
            return ResponseVo.getSuccessResponse();
        } catch (ServiceException e) {
            logger.error("根据ID删除优惠券批量下发异常：{}",e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("删除优惠券批量下发异常：{}",e);
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("申请状态为待审核或审核通过时请勿删！");
        }
    }

    /**
     * 券码兑换明细列表
     *
     * @param paramPage
     * @param couponCodeExDetailsVo
     * @return
     */
    @RequestMapping("/queryDataInfo")
    public @ResponseBody
    PageListVo<List<CouponCodeExDetailsDomain>> queryDataCouponCodeExDetails(ParamPage paramPage, CouponCodeExDetailsVo couponCodeExDetailsVo) {
        PageListVo<List<CouponCodeExDetailsDomain>> pageListVo = new PageListVo<List<CouponCodeExDetailsDomain>>();
        Page<CouponCodeExDetailsDomain> page = new Page<CouponCodeExDetailsDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String tempSql = operatorService.generatePurviewSql(operator, 20);
        String sql = tempSql.replace("A", "B");
        couponCodeExDetailsVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            couponCodeExDetailsVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = couponCodeExDetailsService.queryDataCouponCouponCodeExDetails(page, couponCodeExDetailsVo);
       /* List<CouponCodeExDetailsDomain> list = page.getResult();
        //用户没有该权限,用户名脱敏处理
        if (!hasPurCode("MEMBERINFO_MEMBER_NAME_DESENSITIZE")) {
            for (CouponCodeExDetailsDomain couponCodeExDetails : list) {
                couponCodeExDetails.setSmemberName(DesensitizeUtil.mobilePhone(couponCodeExDetails.getSmemberName()));
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
     * 券码生成管理返回列表
     *
     * @return
     */
    @RequestMapping("/codeList")
    public String codeList() {
        return "ac/couponBatch-codeList";
    }

    /**
     * 查询券码列表
     *
     * @param paramPage
     * @param couponBatchVo
     * @return
     */
    @RequestMapping("/queryCodeData")
    public @ResponseBody
    PageListVo<List<CouponBatchDomain>> queryCodeData(ParamPage paramPage, CouponBatchVo couponBatchVo) {
        PageListVo<List<CouponBatchDomain>> pageListVo = new PageListVo<List<CouponBatchDomain>>();
        Page<CouponBatchDomain> page = new Page<CouponBatchDomain>();
        page.setPageNum(paramPage.getPage());
        page.setPageSize(paramPage.getRows());
        Operator operator = operatorService.selectByPrimaryKey(SessionUserUtils.getSessionAttributeForUserDtl().getId());
        String sql = operatorService.generatePurviewSql(operator, 20);
        couponBatchVo.setCondition(sql);
        if (StringUtil.isNotBlank(paramPage.getSidx()) && StringUtil.isNotBlank(paramPage.getSord())) {
            couponBatchVo.setOrderStr(" " + paramPage.getSidx() + " " + paramPage.getSord() + ",");
        }
        page = couponBatchService.queryDataCoupon(page, couponBatchVo);
        if (page != null) {
            pageListVo.setPage(page.getPageNum());
            pageListVo.setRecords(page.getTotal());
            pageListVo.setTotal(page.getPages());
            pageListVo.setRows(page.getResult());
        }
        return pageListVo;
    }


    /**
     * Excel批量上传文件
     *
     * @param excelFile
     * @return
     */
    @RequestMapping("/batchUploadExcel")
    @ResponseBody
    public ResponseVo<List<CouponBatchSelectUserDomain>> batchUploadExcel(@RequestParam(value = "file", required = false) MultipartFile excelFile) {
        ResponseVo<List<CouponBatchSelectUserDomain>> responseVo = new ResponseVo<>();
        try {
            responseVo = couponBatchService.readUserInfo(excelFile);
            return responseVo;
        } catch (ServiceException e) {
            logger.error("批量下发优惠券出现ServiceException异常：{}", e);
            return ErrorCodeEnum.ERROR_APP_PARAM_EXCEPTION.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("批量下发优惠券出现ServiceException异常：{}", e);
        }
        return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("上传失败");
    }

}


