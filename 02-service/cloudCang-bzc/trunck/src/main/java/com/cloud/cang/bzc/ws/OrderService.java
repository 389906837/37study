package com.cloud.cang.bzc.ws;


import com.alibaba.fastjson.JSON;
import com.cloud.cang.act.*;
import com.cloud.cang.bzc.ac.service.UseRangeService;
import com.cloud.cang.bzc.hy.service.MemberInfoService;
import com.cloud.cang.bzc.hy.service.TrashAuthoriseService;
import com.cloud.cang.bzc.om.service.OrderRecordService;
import com.cloud.cang.bzc.sb.service.DeviceInfoService;
import com.cloud.cang.bzc.sp.service.CommodityInfoService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.BizTypeDefinitionEnum;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.jy.*;
import com.cloud.cang.model.ac.UseRange;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sp.CommodityInfo;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author yanlingfeng
 * @ClassName: OrderService
 * @Description: 订单服务
 */
@RestController
@RequestMapping("/orderService")
@RegisterRestResource
public class OrderService {
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private CommodityInfoService commodityInfoService;
    @Autowired
    private UseRangeService useRangeService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private TrashAuthoriseService trashAuthoriseService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    /**
     * 创建订单
     *
     * @param orderCommDto
     * @return
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createOrder", method = RequestMethod.POST)
    public ResponseVo<GeneratingOrderResults> createOrder(@RequestBody OrderCommDto orderCommDto) {
        logger.debug("创建订单服务开始...");
        try {
            /*orderCommDto = newOrderCommDto();*/
            // 校验基础参数
            ResponseVo<String> validateResult = validateCreateParam(orderCommDto);
            if (!validateResult.isSuccess()) {
                logger.info("创建订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }
            // 生成订单
            Map map = orderRecordService.generateOrder(orderCommDto);
            ResponseVo<List<CreatOrderResult>> orderVo = (ResponseVo<List<CreatOrderResult>>) map.get("responseVo");
            if (null == orderVo || !orderVo.isSuccess()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(orderVo.getMsg());
            }
            GeneratingOrderResults generatingOrderResults = new GeneratingOrderResults();
            generatingOrderResults.setCreatOrderResultList(orderVo.getData());

            //调用活动赠送服务
            List<CommodityDiscountDto> commodityDiscountDtoList = (List<CommodityDiscountDto>) map.get("commodityDiscountDtoList");
            Integer isFirstOrder = callingActivity(orderCommDto.getOrderDto(), orderVo, commodityDiscountDtoList);

            //异步调用更新优惠券服务
            if (StringUtils.isNotBlank(orderCommDto.getOrderDto().getSusedCouponId())) {
                orderRecordService.upCoupon(orderCommDto.getOrderDto(), orderVo);
            }

            //异步调用更新活动优惠记录服务
            updateDiscountRecord(orderCommDto.getOrderDto(), orderVo);

            MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderCommDto.getOrderDto().getSmemberId());
            Integer isourceClientType = orderCommDto.getOrderDto().getIsourceClientType();
            if (null != isFirstOrder) {
                generatingOrderResults.setIsFirstOrder(isFirstOrder);
            } else {
                //是否已经首单
                if (0 == memberInfo.getIisOrder()) {
                    generatingOrderResults.setIsFirstOrder(1);
                } else {
                    generatingOrderResults.setIsFirstOrder(0);
                }
            }
            //异步修改用户购物信息
            if (0 == memberInfo.getIisOrder()) {
                upMemberOrder(true, memberInfo.getId(), isourceClientType);
            } else {
                upMemberOrder(false, memberInfo.getId(), isourceClientType);
            }
            //调用积分变更服务
        /*    DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(orderCommDto.getOrderDto().getSdeviceId());
            if (null != deviceInfo.getIisBindTrash() && deviceInfo.getIisBindTrash() == 1) {
                Map<String, Object> map1 = new HashMap();
                map1.put("smemberId", memberInfo.getId());
                map1.put("strashCode", deviceInfo.getStrashCode());
                map1.put("strashBrand", deviceInfo.getStrashBrand());
                map1.put("istatus", 10);
                TrashAuthorise trashAuthorise = trashAuthoriseService.selectByUserIdAndTrash(map);
                if(trashAuthorise!=null){
                    //调用第三方变更积分接口
                    String url = GrpParaUtil.getValue("SP000181", "changePoint");
                    String key = "37cang";
                    String pwd = "cang@37";
                    Map<String, Object> temp = new HashMap();
                    temp.put("mobile", memberInfo.getSmobile());
                    temp.put("appKey", key);
                    temp.put("point", token);
                    String token = DigestUtils.md5Hex(key + pwd + memberInfo.getSmobile());
                    temp.put("token", token);
                }
            }*/
            return ResponseVo.getSuccessResponse(generatingOrderResults);
        } catch (ServiceException e) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(e.getMessage());
        } catch (Exception e) {
            logger.error("创建订单服务异常：", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("创建订单异常！");
        }
    }

    /**
     * 修改用户购物信息
     *
     * @param isFirstOrder 是否是首单
     * @param memberId     会员id
     */
    private void upMemberOrder(final Boolean isFirstOrder, final String memberId, final Integer isourceClientType) {

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("########下订单修改用户购物信息开始########");
                    MemberInfo memberInfo = new MemberInfo();
                    memberInfo.setId(memberId);
                    memberInfo.setTlastShopTime(DateUtils.getCurrentDateTime());//最近购物时间
                    memberInfo.setIlastShopPlatform(isourceClientType);//最近购物平台
                    if (isFirstOrder) {
                        memberInfo.setIisOrder(1);//是否已首单
                        memberInfo.setIfirstOrderPlatform(isourceClientType);//首单平台
                        memberInfo.setTfirstOrderTime(DateUtils.getCurrentDateTime());//首单时间
                    }
                    memberInfoService.updateBySelective(memberInfo);
                    logger.info("下订单修改用户购物信息成功！");
                } catch (Exception e) {
                    logger.error("下订单修改用户购物信息异常！：{}", e);
                    throw new ServiceException("下订单修改用户购物信息异常！");
                }
            }
        });
    }

    /**
     * 调用活动赠送服务
     *
     * @param orderDto 商品订单表
     * @param orderVo  用户生成的订单
     * @return int 是否首单活动
     */
    private Integer callingActivity(OrderDto orderDto, ResponseVo<List<CreatOrderResult>> orderVo, List<CommodityDiscountDto> commodityDiscountDtoList) {
        try {
            MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderDto.getSmemberId());
            ResponseVo res = new ResponseVo();
            if (0 == memberInfo.getIisOrder()) {
                //如果用户没有参加首单，查看商户该设备有没有对应首单活动
                res = prox(orderDto, orderVo, BizTypeDefinitionEnum.SourceBizStatus.FIRST_ORDER.getCode(), commodityDiscountDtoList);
                if (!res.isSuccess()) {
                    logger.info("用户首单活动不存在或已失效");
                    //查看用户有没有下单活动,并调用
                    res = prox(orderDto, orderVo, BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode(), commodityDiscountDtoList);
                    if (!res.isSuccess()) {
                        logger.info("用户下单活动不存在或已失效");
                    } else {
                        logger.info("用户下单活动调用完成");
                        return 0;
                    }
                } else {
                    logger.info("用户首单活动调用完成");
                    return 1;
                }
            } else {
                //用户已参加首单,调用用户下单场景活动
                prox(orderDto, orderVo, BizTypeDefinitionEnum.SourceBizStatus.ORDER.getCode(), commodityDiscountDtoList);
                return 0;
            }
        } catch (Exception e) {
            logger.error("下单调用场景活动服务异常：{}", e);
        }
        return null;
    }

    /**
     * 调用更新活动优惠服务
     * 更新活动优惠记录
     * 优惠总额大于0
     * 如果是积分+其他=优惠总额 不需要处理
     *
     * @param orderDto 商品订单表
     * @param orderVo  用户生成的订单
     */
    private void updateDiscountRecord(final OrderDto orderDto, final ResponseVo<List<CreatOrderResult>> orderVo) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //积分+其他优惠总额
                    BigDecimal pointAndOtherDiscount = orderDto.getFpointDiscountAmount().add(orderDto.getFotherDiscountAmount());
                    logger.info("积分+其他优惠总额为：{}", pointAndOtherDiscount);
                    if (orderDto.getFdiscountAmount().compareTo(BigDecimal.ZERO) > 0 && orderDto.getFdiscountAmount().compareTo(pointAndOtherDiscount) > 0) {
                        int orderCount = orderVo.getData().size();//生成的订单数量
                        String sourceCode = "";
                        String sourceId = "";
                        Integer iisDismantling = 0;//是否拆单
                        if (orderCount > 1) {
                            sourceCode = orderVo.getData().get(0).getOrderRecord().getSdismantlingCode();//拆单编号
                            iisDismantling = 1;
                        } else {
                            sourceCode = orderVo.getData().get(0).getOrderRecord().getSorderCode();
                            sourceId = orderVo.getData().get(0).getOrderRecord().getId();
                        }
                        //组装数据
                        OrderInvocationActivityDto orderInvocationActivityDto = new OrderInvocationActivityDto();
                        orderInvocationActivityDto.setSmemberId(orderDto.getSmemberId());//用户ID
                        orderInvocationActivityDto.setSmemberCode(orderDto.getSmemberCode());//用户Code
                        orderInvocationActivityDto.setSmemberName(orderDto.getSmemberName());//用户名
                        orderInvocationActivityDto.setSmerchantId(orderDto.getSmerchantId());//商户ID
                        orderInvocationActivityDto.setSmerchantCode(orderDto.getSmerchantCode());//商户编号
                        orderInvocationActivityDto.setSacId(orderDto.getSacId());  // 活动ID 多个，隔开
                        orderInvocationActivityDto.setIsourceType(BizTypeDefinitionEnum.SourceBizStatus.SALESPROMOTION.getCode());//来源单据类型(促销活动)
                        orderInvocationActivityDto.setSsourceCode(sourceCode);//来源单据Code
                        orderInvocationActivityDto.setSsourceId(sourceId);//来源单据Id
                        orderInvocationActivityDto.setSsourceDeviceId(orderDto.getSdeviceId());//来源设备Id
                        orderInvocationActivityDto.setSsourceDeviceCode(orderDto.getSdeviceCode());//来源设备Code
                        orderInvocationActivityDto.setSsourceDeviceAddress(orderDto.getSdeviceAddress());//来源设备地址
                        orderInvocationActivityDto.setScouponId(orderDto.getSusedCouponId());//券使用ID
                        orderInvocationActivityDto.setScouponCode(orderDto.getSusedCouponCode());//券使用编号
                        orderInvocationActivityDto.setIisDismantling(iisDismantling);//是否拆单
                        orderInvocationActivityDto.setUpType(30);
                        logger.info("################下订单更新活动优惠服务数据组装完成！参数为：{}################" + orderInvocationActivityDto);
                        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.UP_DISCOUNT_RECORD);
                        invoke.setRequestObj(orderInvocationActivityDto); // post 参数
                        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo>() {
                        });
                        ResponseVo responseVo = (ResponseVo) invoke.invoke();
                        if (null != responseVo && responseVo.isSuccess()) {
                            logger.info("调用更新活动优惠记录服务成功");
                        } else {
                            logger.error("调用更新活动优惠记录服务失败");
                            throw new ServiceException("调用更新活动优惠记录服务失败");
                        }
                    }
                } catch (Exception e) {
                    logger.error("调用更新活动优惠记录服务失败:{}", e);
                    throw new ServiceException("调用更新活动优惠记录服务失败");
                }
            }
        });
    }

    /**
     * 调用活动赠送服务
     *
     * @param orderDto 商品订单表
     * @param orderVo  生成订单
     * @param code     用户下单场景活动类型
     */
    private ResponseVo prox(OrderDto orderDto, ResponseVo<List<CreatOrderResult>> orderVo, Integer code, List<CommodityDiscountDto> commodityDiscountDtoList) throws Exception {
        UseRange useRange = useRangeService.selectBySmerchantId(orderDto.getSmerchantId(), code);
        if (null != useRange) {
            int type = useRange.getIrangeType();
            if (10 == type || (20 == type && (Arrays.asList(useRange.getSdeviceId().split(",")).contains(orderDto.getSdeviceId())))) {
                int isDis = orderVo.getData().size();//订单是否拆单(订单数量大于1为拆单订单)
                GiveActivityDto giveActivityDto = new GiveActivityDto();
                logger.info("开始组装下定单活动赠送服务参数...............");
                if (isDis > 1) {
                    String sdismantlingCode = orderVo.getData().get(0).getOrderRecord().getSdismantlingCode();//拆单编号
                    giveActivityDto.setSsourceCode(sdismantlingCode);//如果订单为拆单订单,则来源单据Code为拆单编号
                    giveActivityDto.setSsourceInstruction("下单赠送订单拆单，拆单编号：" + sdismantlingCode);//来源说明
                } else {
                    String orderId = orderVo.getData().get(0).getOrderRecord().getId();//订单Id
                    String orderCode = orderVo.getData().get(0).getOrderRecord().getSorderCode();//订单Code
                    giveActivityDto.setSsourceId(orderId);//来源单据Id为订单ID
                    giveActivityDto.setSsourceCode(orderCode);//来源单据Code为订单Code
                    giveActivityDto.setSsourceInstruction("下单赠送,订单编号：" + orderCode);//来源说明
                }
                giveActivityDto.setMemberId(orderDto.getSmemberId());//用户Id
                giveActivityDto.setMemberCode(orderDto.getSmemberCode());//用户Code
                giveActivityDto.setMemberRealName(orderDto.getSmemberName());//用户名
                giveActivityDto.setSsourceType(code);//来源单据类型 下单
                giveActivityDto.setActiveConfCode(useRange.getSacCode());//来源活动编号
                giveActivityDto.setSdeviceId(orderDto.getSdeviceId());//来源设备Id
                giveActivityDto.setSdeviceCode(orderDto.getSdeviceCode());//来源设备Code
                giveActivityDto.setSdeviceAddress(orderDto.getSdeviceAddress());//来源设备地址
                giveActivityDto.setSmerchantId(orderDto.getSmerchantId());//商户ID
                giveActivityDto.setSmerchantCode(orderDto.getSmerchantCode());//商户Code

                OrderParam orderParam = new OrderParam();//订单参数
                orderParam.setCommodityDiscountDtoList(commodityDiscountDtoList);
                orderParam.setFactualPayAmount(orderDto.getFactualPayAmount());//订单实付金额

                giveActivityDto.setOrderParam(orderParam);
                logger.info("################组装下定单活动赠送服务参数完成！参数为：{}################" + giveActivityDto);
              /*  try {*/
                RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.ACTIVITY_GIVE_SERVICE);
                invoke.setRequestObj(giveActivityDto); // post 参数
                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<GiveActivityResult>>() {
                });
                ResponseVo<GiveActivityResult> responseVo = (ResponseVo<GiveActivityResult>) invoke.invoke();
                if (null != responseVo && responseVo.isSuccess()) {
                    logger.info("调用活动赠送接口服务成功");
                } else {
                    logger.error("调用活动赠送接口服务失败");
                        /*throw new ServiceException("调用活动赠送接口服务失败");*/
                }
           /*     } catch (Exception e) {
                    logger.error("调用活动赠送接口服务异常", e);
                    throw new ServiceException("调用活动赠送接口服务异常");
                }*/
                return ResponseVo.getSuccessResponse();
            } else {
                return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo();
            }
        } else {
            logger.info("活动应用范围明细表不存在！");
            return ErrorCodeEnum.ERROR_COMMON_CHECK.getResponseVo();
        }
    }

    /**
     * 验证订单参数是否有效
     *
     * @param orderCommDto
     * @return
     */
    @SuppressWarnings("unchecked")
    private ResponseVo<String> validateCreateParam(OrderCommDto orderCommDto) {
        logger.info("创建订单校验参数开始.....参数：{}", orderCommDto);
        if (null == orderCommDto.getOrderDto()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单信息不能为空！");
        }
        if (StringUtils.isBlank(orderCommDto.getOrderDto().getSmemberId()) || StringUtils.isBlank(orderCommDto.getOrderDto().getSmemberCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("会员信息不能为空！");
        }
        List<OrderCommodityDto> commodityDtos = orderCommDto.getComms();
        if (commodityDtos == null || commodityDtos.size() <= 0) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单商品信息不能为空！");
        }
        String sdeviceId = orderCommDto.getOrderDto().getSdeviceId();
        if (StringUtils.isBlank(sdeviceId)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单设备不能为空！");
        }
        if (StringUtils.isBlank(orderCommDto.getOrderDto().getSmerchantId()) || StringUtils.isBlank(orderCommDto.getOrderDto().getSmerchantCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单商户信息不能为空！");

        }
       /* for (int i = 0; i < commodityDtos.size(); i++) {
            String commodityId = commodityDtos.get(i).getScommodityId();
            //根据商品Id查询商品明细
            CommodityInfo commodityDtl = commodityInfoService.selectByPrimaryKey(commodityId);
            if (null == commodityDtl) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品不存在！");
            }
        }*/
        logger.info("创建订单校验参数成功.....");
        return ResponseVo.getSuccessResponse();
    }

    //数据
    OrderCommDto newOrderCommDto() {
        OrderCommDto orderCommDto = new OrderCommDto();
        OrderDto orderDto = new OrderDto();
        orderDto.setSmemberCode("1");
        orderDto.setSmemberName("yyf");
        orderDto.setSmemberId("2d5e51622e6311e8b099000c2937a246");
        orderDto.setIsourceClientType(20);
        orderDto.setSdeviceId("1");
        orderDto.setSdeviceCode("1");
        orderDto.setSmerchantId("f6befd4fff6e11e7be44000c2937a246");
        orderDto.setSmerchantCode("OP201801220037");

        orderCommDto.setOrderDto(orderDto);
        List<OrderCommodityDto> comms = new ArrayList<>();
        CommodityInfo commodityInfo = commodityInfoService.selectByPrimaryKey("79c9493c529c11e8b099000c2937a246");
        for (int x = 0; x < 2; x++) {
            OrderCommodityDto orderCommodity = new OrderCommodityDto();
            orderCommodity.setScommodityId(commodityInfo.getId());
            orderCommodity.setFcommodityPrice(new BigDecimal("0.05"));//单价
            orderCommodity.setForderCount(5); /* 订单数量 */
            orderCommodity.setScommodityCode(commodityInfo.getScode()); /* 商品编号 */
            orderCommodity.setScommodityName(commodityInfo.getSname());  /* 商品名称 */
            orderCommodity.setSsmallCategoryId(commodityInfo.getSsmallCategoryId()); /* 商品小类ID */
            orderCommodity.setSbrandId(commodityInfo.getSbrandId()); /* 商品品牌ID */
            orderCommodity.setFcostAmount(commodityInfo.getFcostPrice());/* 单商品成本价 */
            comms.add(orderCommodity);
           /* z = Integer.valueOf(z) - 1 + "";*/
        }
        orderCommDto.setComms(comms);
        return orderCommDto;
    }

    /**
     * 生成审核订单
     *
     * @param exceptionOrderDto 异常订单Dto
     * @return ResponseVo
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/createExceptionOrder", method = RequestMethod.POST)
    public ResponseVo createExceptionOrder(@RequestBody ExceptionOrderDto exceptionOrderDto) {
        try {
            logger.info("#################下单异常,开始生成审核订单#################：{}", exceptionOrderDto);
            // 校验基础参数
            ResponseVo<String> validateResult = validateCreateExceptionOrderParam(exceptionOrderDto);
            if (!validateResult.isSuccess()) {
                logger.info("创建审核订单参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }
            logger.info("审核订单基础参数校验成功！");
            // 生成审核订单
            ResponseVo responseVo = orderRecordService.generateExceptionOrder(exceptionOrderDto);
            if (!responseVo.isSuccess()) {
                logger.info("生成审核订单失败：{}", JSON.toJSONString(responseVo));
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("生成审核订单失败");
            }
            logger.info("审核订单生成成功，审核订单ID：{}", responseVo.getData());
            return responseVo;
        } catch (ServiceException se) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(se.getMessage());
        } catch (Exception e) {
            logger.error("生成审核订单异常：{}", e);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("生成审核订单失败!");
        }
    }

    /**
     * 验证订单参数是否有效
     *
     * @param exceptionOrderDto
     * @return
     */
    @SuppressWarnings("unchecked")
    private ResponseVo<String> validateCreateExceptionOrderParam(ExceptionOrderDto exceptionOrderDto) {
        logger.info("创建订单校验参数开始.....参数：{}", exceptionOrderDto);
        if (StringUtils.isBlank(exceptionOrderDto.getSmerchantId()) || StringUtils.isBlank(exceptionOrderDto.getSmerchantCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商户信息不能为空");
        }
        if (StringUtil.isBlank(exceptionOrderDto.getSdeviceId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单设备ID不能为空！");
        }
        if (StringUtil.isBlank(exceptionOrderDto.getSdeviceCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单设备编号不能为空！");
        }
        if (StringUtil.isBlank(exceptionOrderDto.getSdeviceAddress())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单设备地址不能为空！");
        }
        if (StringUtil.isBlank(exceptionOrderDto.getSreaderSerialNumber())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单设备读写器序列号不能为空！");
        }
        if (StringUtil.isBlank(exceptionOrderDto.getSmemberId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户Id不能为空！");
        }
        if (StringUtil.isBlank(exceptionOrderDto.getSmemberCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户编号不能为空！");
        }
        if (StringUtil.isBlank(exceptionOrderDto.getSmemberName())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户名不能为空！");
        }
        if (null == exceptionOrderDto.getItype()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("异常审核订单审核类型不能为空！");
        }
        if (null == exceptionOrderDto.getIsourceClientType()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("来源客户端不能为空！");
        }
        if (null == exceptionOrderDto.getFtotalAmount()) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单总金额不能为空！");
        }
        /*List<ExceptionOrderCommodityDto> exceptionOrderCommodityDtoDtoList = exceptionOrderDto.getExceptionOrderCommodityDtoList();
        if (exceptionOrderCommodityDtoDtoList == null || exceptionOrderCommodityDtoDtoList.size() <= 0) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单商品明细不能为空");
        }*/
        logger.info("创建订单校验参数成功.....");
        return ResponseVo.getSuccessResponse();
    }
}
