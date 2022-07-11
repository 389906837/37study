package com.cloud.cang.bzc.om.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.act.*;
import com.cloud.cang.bzc.ac.service.*;
import com.cloud.cang.bzc.common.PriceUtil;
import com.cloud.cang.bzc.hy.service.FreeDataService;
import com.cloud.cang.bzc.hy.service.MemberInfoService;
import com.cloud.cang.bzc.om.dao.OrderCommodityDao;
import com.cloud.cang.bzc.om.dao.OrderRecordDao;
import com.cloud.cang.bzc.om.service.OrderAuditCommodityService;
import com.cloud.cang.bzc.om.service.OrderAuditService;
import com.cloud.cang.bzc.om.service.OrderRecordService;
import com.cloud.cang.bzc.om.vo.ActivityDiscountDetailVo;
import com.cloud.cang.bzc.om.vo.ActivityUseRangeVo;
import com.cloud.cang.bzc.om.vo.ActivityVo;
import com.cloud.cang.bzc.sb.service.DeviceInfoService;
import com.cloud.cang.bzc.sh.service.MerchantClientConfService;
import com.cloud.cang.bzc.sh.service.MerchantConfService;
import com.cloud.cang.bzc.sh.service.MerchantInfoService;
import com.cloud.cang.bzc.sm.service.DeviceStockService;
import com.cloud.cang.bzc.sm.vo.StockVo;
import com.cloud.cang.cache.redis.ICached;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.common.*;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.dispatcher.support.RestServiceInvokeBuilder;
import com.cloud.cang.dispatcher.support.RestServiceInvoker;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.hy.MemberConstants;
import com.cloud.cang.inventory.*;
import com.cloud.cang.jy.*;
import com.cloud.cang.model.EntityTables;
import com.cloud.cang.model.ac.*;
import com.cloud.cang.model.hy.FreeData;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.om.OrderAudit;
import com.cloud.cang.model.om.OrderAuditCommodity;
import com.cloud.cang.model.om.OrderCommodity;
import com.cloud.cang.model.om.OrderRecord;
import com.cloud.cang.model.sb.DeviceInfo;
import com.cloud.cang.model.sh.MerchantClientConf;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.pay.FreePaymentDto;
import com.cloud.cang.pay.FreePaymentResult;
import com.cloud.cang.pay.FreeServicesDefine;
import com.cloud.cang.pay.UnsignDto;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Service
public class OrderRecordServiceImpl extends GenericServiceImpl<OrderRecord, String> implements
        OrderRecordService {

    private static final Logger logger = LoggerFactory.getLogger(OrderRecordServiceImpl.class);
    @Autowired
    OrderRecordDao orderRecordDao;
    @Autowired
    private OrderCommodityDao orderCommodityDao;
    @Autowired
    private MerchantClientConfService merchantClientConfService;
    @Autowired
    private ICached iCached;
    @Autowired
    private MemberInfoService memberInfoService;
    @Autowired
    private ActivityConfService activityConfService;
    @Autowired
    private DiscountRecordService discountRecordService;
    @Autowired
    private OrderAuditService orderAuditService;
    @Autowired
    private MerchantConfService merchantConfService;
    @Autowired
    private OrderAuditCommodityService orderAuditCommodityService;
    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private DeviceStockService deviceStockService;
    @Autowired
    private UseRangeService useRangeService;
    @Autowired
    private FreeDataService freeDataService;


    @Override
    public GenericDao<OrderRecord, String> getDao() {
        return orderRecordDao;
    }

    @Override
    public ResponseVo<OrderDiscountInfoResult> calculateOrderDiscount(OrderDiscountInfoDto orderDisDto) throws Exception {
        if (logger.isInfoEnabled()) {
            logger.info(
                    "开始计算订单中的优惠...",
                    new Object[]{
                            orderDisDto.getScode(),
                            orderDisDto.getSdeviceCode(),
                            orderDisDto.getCommodityDisList().toString()
                    });
        }

		/* ----------0.计算优惠后的订单信息 ----------*/
        OrderDiscountInfoResult orderDiscount = new OrderDiscountInfoResult();
        /*--初始化计算优惠时的常用变量--*/
        StringBuffer commodityCategoryBuffer = new StringBuffer();        //商品小类
        StringBuffer commodityBrandBuffer = new StringBuffer();        //商品品牌
        StringBuffer commodityCodeBuffer = new StringBuffer();        //商品编号
        String commodityCategoryStr = null;   //商品小类拼接字符串
        String commodityBrandStr = null; //商品品牌拼接字符串
        String commodityCodeStr = null;     //商品编号拼接字符串
        String deviceScode = orderDisDto.getSdeviceCode();//设备编号
        BigDecimal commodityTotalPrice = new BigDecimal(0);//商品总价
        BigDecimal orderCostAmountPrice = new BigDecimal(0);//订单成本价
        BigDecimal commodityNum = new BigDecimal(0);//商品总数量
        BigDecimal userFirstOrder = new BigDecimal(0);//首单优惠金额
        BigDecimal promotionMoneyTemp = null;//用于临时促销优惠金额
        BigDecimal promotionMoney = new BigDecimal(0);//当前活动促销优惠金额 最优
        BigDecimal totalPromotionMoney = new BigDecimal(0);//当前活动促销金额
        BigDecimal couponMoney = new BigDecimal(0);//优惠券最大金额
        BigDecimal actualPayAmount = new BigDecimal(0);//订单实际支付金额
        BigDecimal fdiscountAmount = new BigDecimal(0);//订单优惠总额
        ActivityVo firstAct = null;//首单活动信息
        ActivityVo promotionAct = null;//促销活动信息
        CouponQueryResult mostCoupon = new CouponQueryResult();//最大金额的优惠券
        List<CommodityDiscountDto> firstDisCommodity = new ArrayList<>();//首单活动参与有效商品集合
        List<CommodityDiscountDto> promotionDisCommodity = new ArrayList<>();//促销活动参与有效商品集合
        List<CommodityDiscountDto> couponDisCommodity = new ArrayList<>();//可以使用优惠券有效商品集合
        List<CommodityDiscountDto> commodityDiscountList = new ArrayList<>();//商品使用首单，促销，优惠券后的集合,最后返给订单
        boolean isQuery = false;//判断是否已查询活动优惠信息数据
      /*  ActivityVo activityBean = new ActivityVo();//临时存储促销活动的信息
        OrderDiscountInfoDto tempPromotionOrder = null;//临时存储促销活动的订单草稿
        OrderDiscountInfoDto tempSomePromotionOrder = null;//临时存储部分商品促销活动的订单草稿
        List<OrderDiscountInfoDto> tempOrderDisList = null;////记录促销活动的优惠信息,每种活动的优惠计算结果及活动信息放入该集合中
        Map<String, BigDecimal> resultMap = new HashMap<>(); // 比较商品金额与各种优惠金额之后的结果*/


		/* ----------1.根据用户ID获取用户首单信息 ----------*/
        String userId = orderDisDto.getId();
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(userId);
        Integer isFirstOrder = memberInfo.getIisOrder();//是否已首单，0否，1是
        if (logger.isDebugEnabled()) {
            logger.debug("获取用户首单信息，首单是否可用：{}", isFirstOrder);
        }

		 /* ----------2.获取所有促销活动信息----------*/
        List<ActivityVo> activityDtoList = activityConfService.selectAllInfo(memberInfo.getSmerchantCode());  //所有活动的list集合
        if (logger.isDebugEnabled()) {
            logger.debug("获取促销活动信息:{}", new Object[]{activityDtoList});
        }

		/*  ----------3.计算商品总价,----------*/
        List<CommodityDiscountDto> commodityDisList = orderDisDto.getCommodityDisList();//获取订单中的商品项信息(controller层已经对集合进行判空)
        final Map<String, BigDecimal> commodityMap = new HashMap<String, BigDecimal>();
        for (CommodityDiscountDto commodity : commodityDisList) {
            BigDecimal productPrice = PriceUtil.multiply(commodity.getFcommodityPrice(), new BigDecimal(commodity.getForderCount()));//某类商品总价 = 商品售价 * 商品数量
            commodityTotalPrice = PriceUtil.add(commodityTotalPrice, productPrice);//累加该订单中的商品总价
            commodityNum = PriceUtil.add(commodityNum, new BigDecimal(commodity.getForderCount()));//累加订单中的商品的数量，（后面部分促销活动需要根据商品数量打折或满减）
            commodity.setFcommodityAmount(productPrice);//该类商品的总金额
            /* 拼接商品编号，小类ID，品牌字符串，用来查询优惠券 */
            commodityCodeBuffer.append(commodity.getScommodityCode() + ",");//商品编号
            commodityCategoryBuffer.append(commodity.getSsmallCategoryId() + ",");//商品小类ID
            commodityBrandBuffer.append(commodity.getSbrandId() + ",");//商品品牌
            commodityMap.put(commodity.getScommodityCode(), commodity.getFcommodityPrice());
        }
        commodityCategoryStr = commodityCategoryBuffer.toString();
        commodityCategoryStr = commodityCategoryStr.substring(0, commodityCategoryStr.length() - 1);
        commodityBrandStr = commodityBrandBuffer.toString();
        commodityBrandStr = commodityBrandStr.substring(0, commodityBrandStr.length() - 1);
        commodityCodeStr = commodityCodeBuffer.toString();
        commodityCodeStr = commodityCodeStr.substring(0, commodityCodeStr.length() - 1);

        /*  ----------4.查询用户或设备参加促销活动的次数----------*/
        //该数据结构为{活动编号，该活动参加次数}
        Map<String, Integer> userTotalMap = null;//用户总参与次数
        Map<String, Integer> userDayMap = null;//用户每天参与次数
        Map<String, Integer> deviceTotalMap = null;//设备总参与次数
        Map<String, Integer> deviceMap = null;//设备每天参与次数

		/*  ----------5.数据库查询到的活动(活动状态为：已启用，活动时间在正常范围内)----------*/
        if (CollectionUtils.isNotEmpty(activityDtoList)) {
            ActivityUseRangeVo useRangeVoTemp = null;
            /*--遍历优惠类型（大类：场景，促销）--*/
            for (ActivityVo ac : activityDtoList) {
                //判断活动应用范围
                useRangeVoTemp = useRangeService.selectRangeBySacCode(ac.getSconfCode());//查询活动应用范围
                if (null == useRangeVoTemp) {
                    continue;
                }
                ac.setAcUseRange(useRangeVoTemp);
                //判断活动设备的应用范围
                Map<String, Object> tempMap = activityRange(ac, commodityDisList, orderDisDto.getSdeviceCode());
                if (!Boolean.valueOf((boolean) tempMap.get("flag"))) {
                    //活动设备限制不通过
                    continue;
                }

                //判断活动次数限制
                if (!isQuery) {
                    //查询活动参与优惠记录信息
                    userTotalMap = discountRecordService.queryUserAllCount(userId);//用户总参与次数
                    userDayMap = discountRecordService.queryUserDayCount(userId);//用户每天参与次数
                    deviceTotalMap = discountRecordService.queryDeviceAllCount(orderDisDto.getSdeviceCode());//设备总参与次数
                    deviceMap = discountRecordService.queryDeviceDayCount(orderDisDto.getSdeviceCode());//设备每天参与次数
                    isQuery = true;
                }
                if (!countLimit(ac, orderDisDto.getSdeviceCode(), userTotalMap, userDayMap, deviceTotalMap, deviceMap)) {
                    //活动次数限制不通过
                    continue;
                }
                //计算优惠金额
                if (BizTypeDefinitionEnum.ActivityDiscountWay.USER_FIRSTORDER.getCode() == ac.getIdiscountWay()) {//优惠大类：10 首单
                    if (0 == isFirstOrder.intValue()) {//首单是否可用
                        if (CollectionUtils.isNotEmpty(ac.getDiscountDetailList())) {
                            ActivityDiscountDetailVo acDetail = ac.getDiscountDetailList().get(0);
                            if (acDetail.getFtargetMoney().doubleValue() == 0 || commodityTotalPrice.doubleValue() >= acDetail.getFtargetMoney().doubleValue()) {
                                firstDisCommodity = (List<CommodityDiscountDto>) tempMap.get("someDisCommodity");
                                userFirstOrder = acDetail.getFdiscountMoney();//首单优惠金额
                                firstAct = ac;//首单活动信息
                            }
                        }
                    }
                } else if (BizTypeDefinitionEnum.ActivityType.PROMOTIONS_ACTIVITY.getCode() == ac.getItype()) {  //场景活动 20:促销活动
                    //promotionDisCommodity = (List<CommodityDiscountDto>) tempMap.get("someDisCommodity");//默认参与促销活动的商品 等于 活动的
                    promotionMoneyTemp = calculatePromotionInfo(ac, commodityTotalPrice, commodityNum, (List<CommodityDiscountDto>) tempMap.get("someDisCommodity"));//计算促销优惠
                    if (promotionMoneyTemp != null && promotionMoneyTemp.doubleValue() > 0) {//促销优惠金额满足
                        //计算最优金额
                        if (promotionMoney == null) {
                            promotionMoney = promotionMoneyTemp;
                            promotionAct = ac;
                            promotionDisCommodity = (List<CommodityDiscountDto>) tempMap.get("someDisCommodity");
                        } else if (promotionMoneyTemp.compareTo(promotionMoney) > 0) {
                            promotionMoney = promotionMoneyTemp;
                            promotionAct = ac;
                            promotionDisCommodity = (List<CommodityDiscountDto>) tempMap.get("someDisCommodity");
                        }
                    }
                }
            }
        }

		/*  ----------6.计算活动促销金额 判断首单和促销是否叠加,选择最优方案----------*/
        if (firstAct != null) { //首单活动可用
            totalPromotionMoney = userFirstOrder;
            if (firstAct.getIisSuperposition().intValue() == 1) {//优惠叠加可用
                if (promotionAct != null) {//促销活动可用 总优惠相加
                    totalPromotionMoney = totalPromotionMoney.add(promotionMoney);
                    orderDiscount.setSacId(firstAct.getId() + "," + promotionAct.getId());                          // 活动ID
                    orderDiscount.setSacCode(firstAct.getSconfCode() + "," + promotionAct.getSconfCode());          // 活动编号
                }
            } else {
                orderDiscount.setSacId(firstAct.getId());
                orderDiscount.setSacCode(firstAct.getSconfCode());
                if (promotionAct != null) {//优惠叠加不可用，促销活动可用
                    if (promotionMoney.compareTo(totalPromotionMoney) > 0) {//比较首单优惠金额 和促销金额
                        totalPromotionMoney = promotionMoney;
                        userFirstOrder = BigDecimal.ZERO;                           // 首单金额变为0
                        orderDiscount.setSacId(promotionAct.getId());               // 活动ID
                        orderDiscount.setSacCode(promotionAct.getSconfCode());      // 活动编号
                    } else {
                        promotionMoney = BigDecimal.ZERO;//促销金额变为0
                    }
                }
            }
        } else if (promotionAct != null) {//促销活动可用
            totalPromotionMoney = promotionMoney;
            orderDiscount.setSacId(promotionAct.getId());                           // 活动ID
            orderDiscount.setSacCode(promotionAct.getSconfCode());                  // 活动编号
        }

     /* resultMap = compareOrderMoenyToPromotionMoney(userFirstOrder, promotionMoney, couponMoney, commodityTotalPrice);
        userFirstOrder = resultMap.get("userFirstOrder");
        promotionMoney = resultMap.get("promotionMoney");
        couponMoney = resultMap.get("couponMoney");
        if (PriceUtil.equalZero(couponMoney)) { // 如果优惠券金额为0，不使用优惠券
            mostCoupon = new CouponQueryResult();
        }*/


		/* ----------7.0 计算商品优惠信息 ----------*/
       /* Map<String, Object> discountMap = calculateCommodityDiscount(commodityDisList, userFirstOrder, firstDisCommodity, promotionMoney, promotionDisCommodity, couponMoney, couponDisCommodity);//订单商品集合*/
        Map<String, Object> discountMap = calculateCommodityDiscount(commodityDisList, userFirstOrder, firstDisCommodity, promotionMoney, promotionDisCommodity);//订单商品集合
        commodityDiscountList = (List<CommodityDiscountDto>) discountMap.get("commodityDisList");
        //commodityDiscountList = calculateCommodityDiscount(commodityDisList, userFirstOrder, firstDisCommodity, promotionMoney, promotionDisCommodity, couponMoney, couponDisCommodity);//订单商品集合
         /* ----------7.1 计算订单商品总优惠信息 ----------*/
        userFirstOrder = (BigDecimal) discountMap.get("totalFirstOrder");
        promotionMoney = (BigDecimal) discountMap.get("totalPromotion");
        actualPayAmount = (BigDecimal) discountMap.get("actualAmount");//实付总额


        /* ----------8.0.查询用户优惠券，根据用户ID和设备ID ----------*/
        CouponQueryDto couponQueryDto = new CouponQueryDto();
        couponQueryDto.setMemberId(userId);    //用户ID
        couponQueryDto.setSuseLimitDevice(deviceScode); //设备编号
        couponQueryDto.setSuseLimitCommodity(commodityCodeStr);//商品编号
        couponQueryDto.setSuseLimitBrand(commodityBrandStr);//商品品牌
        couponQueryDto.setSuseLimitCategory(commodityCategoryStr);//商品分类（小类）
        couponQueryDto.setUseOrderAmount(actualPayAmount);//商品实付总额 = 商品订单总额 - 促销优惠金额

        List<CouponQueryResult> couponList = null;//调用查卷接口查询优惠券

        // 创建Rest服务客户端
        RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.QUERY_COUPON_SERVICE);
        invoke.setRequestObj(couponQueryDto);
        // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
        invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<List<CouponQueryResult>>>() {
        });
        ResponseVo<List<CouponQueryResult>> responseVo = (ResponseVo<List<CouponQueryResult>>) invoke.invoke();
        if (responseVo.isSuccess()) {
            couponList = responseVo.getData();
        }

        if (CollectionUtils.isNotEmpty(couponList)) {
            //将优惠券按照金额降序排列
            Collections.sort(couponList, new Comparator<CouponQueryResult>() {
                /**
                 *
                 * @param o1
                 * @param o2
                 * @return 负数
                 */
                @Override
                public int compare(CouponQueryResult o1, CouponQueryResult o2) {
                    if (o1.getCouponType().intValue() == BizTypeDefinitionEnum.CouponType.COMMODITY.getCode()) {
                        if (null != commodityMap.get(o1.getSuseLimitCommodity())) {
                            o1.setCouponValue(commodityMap.get(o1.getSuseLimitCommodity()));
                        } else {
                            o1.setCouponValue(BigDecimal.ZERO);
                        }
                    }
                    if (o2.getCouponType().intValue() == BizTypeDefinitionEnum.CouponType.COMMODITY.getCode()) {
                        if (null != commodityMap.get(o2.getSuseLimitCommodity())) {
                            o2.setCouponValue(commodityMap.get(o2.getSuseLimitCommodity()));
                        } else {
                            o2.setCouponValue(BigDecimal.ZERO);
                        }
                    }
                    if (null == o2.getCouponValue()) {
                        o2.setCouponValue(BigDecimal.ZERO);
                    }
                    if (null == o1.getCouponValue()) {
                        o2.setCouponValue(BigDecimal.ZERO);
                    }
                    int val = o2.getCouponValue().compareTo(o1.getCouponValue());
                    if (val == 0) {
                        return o1.getCouponExpiryDate().compareTo(o2.getCouponExpiryDate());
                    } else {
                        return val;
                    }
                }
            });

            mostCoupon = couponList.get(0);//最大优惠券金额
            if (mostCoupon.getCouponType().intValue() == BizTypeDefinitionEnum.CouponType.COMMODITY.getCode()) {
                if (null != commodityMap.get(mostCoupon.getSuseLimitCommodity())) {
                    mostCoupon.setCouponValue(commodityMap.get(mostCoupon.getSuseLimitCommodity()));
                } else {
                    mostCoupon.setCouponValue(BigDecimal.ZERO);
                }
            }
            if (mostCoupon.getCouponValue().compareTo(BigDecimal.ZERO) <= 0) {
                throw new Exception("使用券金额不正确，券编号：" + mostCoupon.getCouponCode());
            }
            couponDisCommodity = getEffectiveCouponCommodity(commodityDisList, mostCoupon);//计算商品中可用使用优惠券的商品
            if (mostCoupon.getCouponType().intValue() == BizTypeDefinitionEnum.CouponType.COMMODITY.getCode()) {
                CommodityDiscountDto tempDto = couponDisCommodity.get(0);
                if (null != tempDto) {
                    couponMoney = tempDto.getFcommodityPrice();
                }
            } else {
                couponMoney = mostCoupon.getCouponValue();//优惠券面值
            }

            //计算券的优惠金额
            Map<String, Object> discountCouponMap = calculateCommodityDiscountCoupon(commodityDiscountList, couponMoney, couponDisCommodity);//订单商品集合
            couponMoney = (BigDecimal) discountCouponMap.get("totalCoupon");
            actualPayAmount = (BigDecimal) discountCouponMap.get("actualAmount");//实付总额

            commodityDiscountList = (List<CommodityDiscountDto>) discountMap.get("commodityDisList");
        }


        //actualPayAmount = PriceUtil.sub(commodityTotalPrice, PriceUtil.add(userFirstOrder, PriceUtil.add(promotionMoney, couponMoney)));//订单实付金额
        fdiscountAmount = PriceUtil.sub(commodityTotalPrice, actualPayAmount); //订单优惠总额

        /* ----------9.返回对象赋值 ----------*/
        orderDiscount.setCommodityDisList(commodityDiscountList);//订单商品
        orderDiscount.setFtotalAmount(commodityTotalPrice);//订单总额
        orderDiscount.setFfirstDiscountAmount(userFirstOrder);//订单首单优惠金额
        orderDiscount.setFpromotionDiscountAmount(promotionMoney);//订单促销活动优惠金额
        orderDiscount.setFcouponDeductionAmount(couponMoney);//订单优惠券抵用金额
        orderDiscount.setSusedCouponCode(mostCoupon.getCouponCode());//订单优惠券编号
        orderDiscount.setSusedCouponId(mostCoupon.getCouponId());//订单优惠券ID
        orderDiscount.setFactualPayAmount(actualPayAmount);//订单实付金额
        orderDiscount.setFpointDiscountAmount(new BigDecimal(0)); //订单积分优惠金额
        orderDiscount.setFotherDiscountAmount(new BigDecimal(0));//订单其他优惠金额
        orderDiscount.setFdiscountAmount(fdiscountAmount); //订单优惠总额
        orderDiscount.setFtotalCostAmount(orderCostAmountPrice);    //订单中商品的总成本价

        if (logger.isInfoEnabled()) {
            logger.info(
                    "订单的优惠已经计算完...",
                    new Object[]{
                            orderDisDto.getSmemberName(),
                            orderDisDto.getScode(),
                            orderDisDto.getSdeviceCode(),
                            orderDisDto.getCommodityDisList().toString()
                    });
        }

        return ResponseVo.getSuccessResponse(orderDiscount);
    }

    /**
     * 比较订单金额与优惠金额
     * 如果商品金额小于首单金额，首单金额 = 商品金额，促销金额 = 0，优惠卷金额 = 0
     * 如果商品金额小于首单金额加促销金额，首单金额 = 首单金额，促销金额 = 商品金额 - 首单金额，优惠卷金额 = 0
     * 如果商品金额小于首单金额加促销金额加优惠券金额，首单金额 = 首单金额，促销金额 = 促销金额，优惠卷金额 = 商品金额 - 首单加促销金额
     * 其他情况，首单金额 = 首单金额，促销金额 = 促销金额，优惠券金额 = 优惠券金额
     *
     * @param userFirstOrder      首单金额
     * @param promotionMoney      促销金额
     * @param couponMoney         券使用金额
     * @param commodityTotalPrice 总的商品金额
     * @return
     */
    private Map<String, BigDecimal> compareOrderMoenyToPromotionMoney(BigDecimal userFirstOrder, BigDecimal promotionMoney, BigDecimal couponMoney, BigDecimal commodityTotalPrice) {
        Map<String, BigDecimal> map = new HashMap<>();
        BigDecimal firstOrderAndPromotionMoney = PriceUtil.add(userFirstOrder, promotionMoney); // 首单和促销金额
        BigDecimal firstOrderAndPromotionAndCouponMoney = PriceUtil.add(firstOrderAndPromotionMoney, couponMoney); // 首单，促销和优惠券金额

        // 比较商品总金额与各种优惠金额
        if (PriceUtil.compareTo(userFirstOrder, commodityTotalPrice)) { // 首单促销金额 > 商品总金额
            map.put("userFirstOrder", commodityTotalPrice); // 首单金额 = 商品金额
            map.put("promotionMoney", new BigDecimal(0));
            map.put("couponMoney", new BigDecimal(0));
        }
        if (PriceUtil.compareTo(firstOrderAndPromotionMoney, commodityTotalPrice)) { // 商品金额小于首单金额加促销金额
            map.put("userFirstOrder", userFirstOrder);
            map.put("promotionMoney", PriceUtil.sub(commodityTotalPrice, userFirstOrder)); // 促销金额 = 商品金额 - 首单金额
            map.put("couponMoney", new BigDecimal(0));
        } else if (PriceUtil.compareTo(firstOrderAndPromotionAndCouponMoney, commodityTotalPrice)) { // 商品金额小于首单金额加促销金额加优惠券金额
            map.put("userFirstOrder", userFirstOrder);
            map.put("promotionMoney", promotionMoney);
            map.put("couponMoney", PriceUtil.sub(commodityTotalPrice, firstOrderAndPromotionMoney));
        } else {     // 商品金额大于首单，促销，优惠券金额
            map.put("userFirstOrder", userFirstOrder);
            map.put("promotionMoney", promotionMoney);
            map.put("couponMoney", couponMoney);
        }
        return map;
    }

    /**
     * 计算每种优惠商品的
     *
     * @param commodityDisList      订单中所有商品集合
     * @param userFirstOrder        用户首单减免金额
     * @param firstDisCommodity     首单金额适用商品集合
     * @param promotionMoney        促销活动减免金额
     * @param promotionDisCommodity 促销活动适用商品集合
     * @return 计算优惠后的商品集合
     */
    private Map<String, Object> calculateCommodityDiscount(List<CommodityDiscountDto> commodityDisList, BigDecimal userFirstOrder, List<CommodityDiscountDto> firstDisCommodity,
                                                           BigDecimal promotionMoney, List<CommodityDiscountDto> promotionDisCommodity) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, BigDecimal> firstOrderMap = calculateDiscount(userFirstOrder, firstDisCommodity);//首单优惠商品记录
        Map<String, BigDecimal> promotionMap = calculateDiscount(promotionMoney, promotionDisCommodity);//促销优惠商品记录
        BigDecimal tempFirstOrder = new BigDecimal(0);//临时首单金额
        BigDecimal tempPromotion = new BigDecimal(0);//临时促销金额
        BigDecimal tempCommodityDiscount = new BigDecimal(0);//商品优惠金额=首单+促销+优惠券


        BigDecimal totalFirstOrder = new BigDecimal(0);//订单商品首单金额
        BigDecimal totalPromotion = new BigDecimal(0);//订单商品促销金额
        BigDecimal actualAmount = new BigDecimal(0);//订单商品实付总额

        //循环商品，set首单，促销
        for (CommodityDiscountDto commodity : commodityDisList) {
            String commodityCode = commodity.getScommodityCode();
            if (!firstOrderMap.isEmpty() && firstOrderMap.containsKey(commodityCode)) {
                tempFirstOrder = firstOrderMap.get(commodityCode);
                if (tempFirstOrder.compareTo(commodity.getFcommodityAmount()) > 0) {
                    tempFirstOrder = commodity.getFcommodityAmount();
                }
                commodity.setFfirstDiscountAmount(tempFirstOrder);
            } else {
                commodity.setFfirstDiscountAmount(new BigDecimal(0));
            }
            if (!promotionMap.isEmpty() && promotionMap.containsKey(commodityCode)) {
                tempPromotion = promotionMap.get(commodityCode);
                if ((PriceUtil.add(tempPromotion, commodity.getFfirstDiscountAmount())).compareTo(commodity.getFcommodityAmount()) > 0) {
                    tempPromotion = PriceUtil.sub(commodity.getFcommodityAmount(), commodity.getFfirstDiscountAmount());
                }
                commodity.setFpromotionDiscountAmount(tempPromotion);
            } else {
                commodity.setFpromotionDiscountAmount(new BigDecimal(0));
            }
            commodity.setIpoint(BigDecimal.ZERO);
            commodity.setFcouponDeductionAmount(BigDecimal.ZERO);
            tempCommodityDiscount = PriceUtil.addDown(commodity.getFfirstDiscountAmount(), PriceUtil.addDown(commodity.getFpromotionDiscountAmount(), commodity.getFcouponDeductionAmount()));
            commodity.setFdiscountAmount(tempCommodityDiscount);//该类商品优惠金额
            commodity.setFactualAmount(PriceUtil.subUp(commodity.getFcommodityAmount(), tempCommodityDiscount));//该类商品实付金额

            //计算总的优惠
            totalFirstOrder = PriceUtil.add(totalFirstOrder, commodity.getFfirstDiscountAmount());
            totalPromotion = PriceUtil.add(totalPromotion, commodity.getFpromotionDiscountAmount());
            actualAmount = PriceUtil.add(actualAmount, commodity.getFactualAmount());
        }

        map.put("commodityDisList", commodityDisList);
        map.put("totalFirstOrder", totalFirstOrder);
        map.put("totalPromotion", totalPromotion);
        map.put("actualAmount", actualAmount);
        return map;
    }


    /**
     * 计算每种优惠商品的
     *
     * @param commodityDisList   订单中所有商品集合
     * @param couponMoney        券 优惠金额
     * @param couponDisCommodity 优惠券适用商品集合
     * @return 计算优惠后的商品集合
     */
    private Map<String, Object> calculateCommodityDiscountCoupon(List<CommodityDiscountDto> commodityDisList, BigDecimal couponMoney, List<CommodityDiscountDto> couponDisCommodity) throws Exception {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, BigDecimal> couponMap = calculateDiscount(couponMoney, couponDisCommodity);//优惠券优惠商品记录
        BigDecimal tempCoupon = new BigDecimal(0);


        BigDecimal actualAmount = new BigDecimal(0);//商品的实付总额
        BigDecimal totalCoupon = new BigDecimal(0);//总的用券金额

        //循环商品，set首单，促销
        for (CommodityDiscountDto commodity : commodityDisList) {
            String commodityCode = commodity.getScommodityCode();
            totalCoupon = PriceUtil.add(totalCoupon, commodity.getFcouponDeductionAmount());
            if (!couponMap.isEmpty() && couponMap.containsKey(commodityCode)) {
                tempCoupon = couponMap.get(commodityCode);
                if ((PriceUtil.add(PriceUtil.add(commodity.getFpromotionDiscountAmount(), commodity.getFfirstDiscountAmount()), tempCoupon)).compareTo(commodity.getFcommodityAmount()) > 0) {
                    tempCoupon = PriceUtil.sub(PriceUtil.sub(commodity.getFcommodityAmount(), commodity.getFfirstDiscountAmount()), commodity.getFpromotionDiscountAmount());
                }
                commodity.setFcouponDeductionAmount(tempCoupon);
            } else {
                commodity.setFcouponDeductionAmount(new BigDecimal(0));
            }
            commodity.setFdiscountAmount(PriceUtil.addDown(commodity.getFdiscountAmount(), commodity.getFcouponDeductionAmount()));//该类商品用券优惠金额
            commodity.setFactualAmount(PriceUtil.subUp(commodity.getFactualAmount(), commodity.getFcouponDeductionAmount()));//该类商品实付金额

            //计算总的优惠
            actualAmount = PriceUtil.add(actualAmount, commodity.getFactualAmount());
            totalCoupon = PriceUtil.add(totalCoupon, commodity.getFcouponDeductionAmount());
        }


        map.put("commodityDisList", commodityDisList);
        map.put("totalCoupon", totalCoupon);
        map.put("actualAmount", actualAmount);
        return map;
    }

    /**
     * 计算每种优惠商品的优惠金额
     *
     * @param discountMoney 优惠金额
     * @param commodityList 优惠商品
     * @return 商品编号->优惠金额
     * @throws Exception
     */
    private Map<String, BigDecimal> calculateDiscount(BigDecimal discountMoney, List<CommodityDiscountDto> commodityList) throws Exception {
        BigDecimal tempMoney = new BigDecimal("0");//临时存储前n-1个商品的优惠金额
        Map<String, BigDecimal> discountMap = new HashMap<String, BigDecimal>();//返回的map
        BigDecimal tempTotalPrice = new BigDecimal(0);//有效商品总金额
        if (1 == commodityList.size()) {
            CommodityDiscountDto tempCommodity = commodityList.get(0);
            discountMap.put(tempCommodity.getScommodityCode(), discountMoney);
        } else if (CollectionUtils.isNotEmpty(commodityList) && commodityList.size() > 1 && !PriceUtil.ltOrEqZero(discountMoney)) {
            //商品种类大于1，计算每一种商品的优惠
            for (CommodityDiscountDto commodity : commodityList) {
                BigDecimal productPrice = commodity.getFcommodityAmount();//某类商品总价
                tempTotalPrice = PriceUtil.add(tempTotalPrice, productPrice);//累加所有商品总价
            }
            BigDecimal discountRate = PriceUtil.divide(discountMoney, tempTotalPrice);//每类商品优惠百分比例 = 优惠金额/商品总价
            for (int i = 0; i < commodityList.size(); i++) {
                if (i != (commodityList.size() - 1)) {       //前面n-1中商品做乘法
                    BigDecimal tempDiscountMoney = PriceUtil.multiply(commodityList.get(i).getFcommodityAmount(), discountRate);//该商品优惠金额
                    discountMap.put(commodityList.get(i).getScommodityCode(), tempDiscountMoney);
                    tempMoney = PriceUtil.add(tempMoney, tempDiscountMoney);      //累加订单中的前n-1个商品的优惠金额
                } else {//最后一种商品做减法
                    discountMap.put(commodityList.get(i).getScommodityCode(), PriceUtil.sub(discountMoney, tempMoney));//最后一种商品 = 优惠总额 - 前（n-1）中商品优惠金额
                }
            }
        }
        return discountMap;
    }

    /**
     * 计算出可以使用的优惠券商品
     *
     * @param commodityDisList
     * @param coupon
     * @return
     */
    private List<CommodityDiscountDto> getEffectiveCouponCommodity(List<CommodityDiscountDto> commodityDisList, CouponQueryResult coupon) {

        //商品分类
        List<CommodityDiscountDto> tempCommodity = new ArrayList<CommodityDiscountDto>();
        if (StringUtil.isNotBlank(coupon.getSuseLimitCategory())) {
            for (CommodityDiscountDto commodity : commodityDisList) {
                if (coupon.getSuseLimitCategory().contains(commodity.getSsmallCategoryId())) {
                    tempCommodity.add(commodity);
                }
            }
        } else {
            tempCommodity.addAll(commodityDisList);
        }

        //商品编号
        List<CommodityDiscountDto> tempCommodity1 = new ArrayList<CommodityDiscountDto>();
        if (StringUtil.isNotBlank(coupon.getSuseLimitCommodity())) {
            for (CommodityDiscountDto commodity : tempCommodity) {
                if (coupon.getSuseLimitCommodity().contains(commodity.getScommodityCode())) {
                    tempCommodity1.add(commodity);
                }
            }
        } else {
            tempCommodity1.addAll(tempCommodity);
        }

        //商品品牌
        List<CommodityDiscountDto> tempCommodity2 = new ArrayList<CommodityDiscountDto>();
        if (StringUtil.isNotBlank(coupon.getSuseLimitBrand())) {
            for (CommodityDiscountDto commodity : tempCommodity1) {
                if (coupon.getSuseLimitBrand().contains(commodity.getSbrandId())) {
                    tempCommodity2.add(commodity);
                }
            }
        } else {
            tempCommodity2.addAll(tempCommodity1);
        }

        return tempCommodity2;
    }

    /**
     * 计算促销优惠金额
     *
     * @param activityVo          活动信息
     * @param commodityTotalPrice 商品总价
     * @param commodityNum        商品总数量
     * @param someDisCommodity    有效商品集合
     * @return 优惠金额
     * @throws Exception
     */
    private BigDecimal calculatePromotionInfo(ActivityVo activityVo, BigDecimal commodityTotalPrice, BigDecimal commodityNum, List<CommodityDiscountDto> someDisCommodity) throws Exception {
        BigDecimal promotionAmount = null;//优惠金额

        if (BizTypeDefinitionEnum.ActivityDiscountWay.DISCOUT.getCode() == activityVo.getIdiscountWay()) {/*--优惠类型（大类），20：打折，--*/
            BigDecimal discountTemp = null;//临时折扣
            BigDecimal discount = new BigDecimal("10");//最小折扣 (1-10的数字) 默认不折扣
            //遍历活动优惠信息
            for (ActivityDiscountDetailVo acDetail : activityVo.getDiscountDetailList()) {
                if ((BizTypeDefinitionEnum.ActivityDiscountType.DISCOUNT_MONEY.getCode() == acDetail.getIdiscountType() &&
                        PriceUtil.compareTo(commodityTotalPrice, acDetail.getFtargetMoney())) || //20 打折满X元
                        (BizTypeDefinitionEnum.ActivityDiscountType.DISCOUNT_COUNT.getCode() == acDetail.getIdiscountType() &&
                                PriceUtil.compareTo(commodityNum, acDetail.getFtargetNum())) || //30 打折满X件
                        (BizTypeDefinitionEnum.ActivityDiscountType.DISCOUNT_MONTY_COUNT.getCode() == acDetail.getIdiscountType() &&
                                PriceUtil.compareTo(commodityTotalPrice, acDetail.getFtargetMoney()) &&
                                PriceUtil.compareTo(commodityNum, new BigDecimal(acDetail.getFtargetNum())))) {//40 打折满X元且满X件
                    discountTemp = acDetail.getFdiscount();
                    if (discountTemp != null && discountTemp.doubleValue() > 0) {//折扣满足
                        if (discount == null) {
                            discount = discountTemp;
                        } else if (discount.compareTo(discountTemp) > 0) { // 折扣数字越低，优惠金额越大
                            discount = discountTemp;
                        }
                    }
                }
            }
            // 优惠金额 = 商品总价 * （（10-优惠折扣）* 0.1）               折扣为1-10的数字
            //优惠金额 = 商品总价 - （商品总价*优惠折扣*0.1）//
            promotionAmount = PriceUtil.sub(commodityTotalPrice, PriceUtil.multiplyRoundUp(commodityTotalPrice, discount.multiply(new BigDecimal("0.1"))));//折扣方式中最后优惠最大的金额
        } else if (BizTypeDefinitionEnum.ActivityDiscountWay.FULLREDUCE.getCode() == activityVo.getIdiscountWay()) {/*--优惠类型（大类），30：满减，--*/
            BigDecimal promotionAmountTemp;//临时优惠金额
            BigDecimal fullReduceTime = null;//临时每满次数
            //遍历活动优惠信息
            for (ActivityDiscountDetailVo acDetail : activityVo.getDiscountDetailList()) {
                promotionAmountTemp = BigDecimal.ZERO;
                if (BizTypeDefinitionEnum.ActivityDiscountType.FULLREDUCE_MONEY.getCode() == acDetail.getIdiscountType()) { //50:满减满X元
                    if (PriceUtil.compareTo(commodityTotalPrice, acDetail.getFtargetMoney())) {
                        promotionAmountTemp = acDetail.getFdiscountMoney();
                    }
                } else if (BizTypeDefinitionEnum.ActivityDiscountType.FULLREDUCE_EVERY_MONEY.getCode() == acDetail.getIdiscountType()) { //60:满减每满Y元
                    if (PriceUtil.compareTo(commodityTotalPrice, acDetail.getFtargetMoney())) {
                        fullReduceTime = PriceUtil.divideToInt(commodityTotalPrice, acDetail.getFtargetMoney());  //满减次数 = 商品总价 / 目标金额
                        promotionAmountTemp = PriceUtil.multiplyRoundUp(acDetail.getFdiscountMoney(), fullReduceTime);      //满减的金额 = 优惠金额 * 满减次数
                        //判断满减优惠金额 是否大于优惠上限  是：满减优惠金额=优惠上限
                        if (null != acDetail.getFdiscountLimit() && acDetail.getFdiscountLimit().doubleValue() > 0) {//优惠上限 必须大于0
                            promotionAmountTemp = PriceUtil.compareTo(promotionAmountTemp, acDetail.getFdiscountLimit()) ? acDetail.getFdiscountLimit() : promotionAmountTemp;
                        }
                    }
                } else if (BizTypeDefinitionEnum.ActivityDiscountType.FULLREDUCE_COUNT.getCode() == acDetail.getIdiscountType()) { //70：满减满X件
                    //计算有效商品最小单价 有效商品已排序取第一个商品
                    if (PriceUtil.compareTo(commodityNum, acDetail.getFtargetNum())) {
                      /*  Collections.sort(someDisCommodity);*/
                        Collections.sort(someDisCommodity, new Comparator<CommodityDiscountDto>() {
                            @Override
                            public int compare(CommodityDiscountDto o1, CommodityDiscountDto o2) {
                                return o1.getFcommodityPrice().compareTo(o2.getFcommodityPrice());
                            }
                        });
                        promotionAmountTemp = someDisCommodity.get(0).getFcommodityPrice();
                    }
                } else if (BizTypeDefinitionEnum.ActivityDiscountType.FULLREDUCE_MONEY_COUNT.getCode() == acDetail.getIdiscountType()) { //80：满减满X元且满Y件
                    if (PriceUtil.compareTo(commodityNum, acDetail.getFtargetNum()) && PriceUtil.compareTo(commodityTotalPrice, acDetail.getFtargetMoney())) {
                        promotionAmountTemp = acDetail.getFdiscountMoney();
                    }
                }
                if (promotionAmountTemp != null && promotionAmountTemp.doubleValue() > 0) {//优惠金额满足
                    if (promotionAmount == null) {
                        promotionAmount = promotionAmountTemp;
                    } else if (promotionAmountTemp.compareTo(promotionAmount) > 0) {
                        promotionAmount = promotionAmountTemp;
                    }
                }
            }
        }

        return promotionAmount;
    }


    /**
     * 判断活动参与次数限制
     *
     * @param activityVo     活动信息
     * @param sdeviceCode    设备编号
     * @param userTotalMap   有效活动用户总参与次数集合
     * @param userDayMap     有效活动用户当天总参与次数集合
     * @param deviceTotalMap 有效活动设备总参与次数集合
     * @param deviceMap      有效活动设备当天参与次数集合
     * @return true 可以参与活动 false 不能参与活动
     * @throws Exception
     */
    private boolean countLimit(ActivityVo activityVo, String sdeviceCode, Map<String, Integer> userTotalMap,
                               Map<String, Integer> userDayMap, Map<String, Integer> deviceTotalMap, Map<String, Integer> deviceMap) throws Exception {

        //活动总次数
        if (activityVo.getIallCount().compareTo(activityVo.getIjoinNum()) > 0) {
            return true;
        }
        //根据活动次数限制类型 判断次数是否成立
        if (BizTypeDefinitionEnum.ActivityCountType.DURING_ACTIVITY.getCode() == activityVo.getIcountType()) {//活动期间
            if (userTotalMap == null || userTotalMap.get(sdeviceCode) == null) {//如果用户总次数为0 或者是未空
                return true;
            }
            if (userTotalMap.get(sdeviceCode).intValue() < activityVo.getIuserAllCount() &&
                    (userDayMap == null ||
                            userDayMap.get(sdeviceCode) == null ||
                            userDayMap.get(sdeviceCode).intValue() < activityVo.getIuserDayCount().intValue())) {//如果用户总次数小于  并且 当天参与次数有效
                return true;
            }
        } else if (BizTypeDefinitionEnum.ActivityCountType.DURING_ACTIVITY_EQUIPMENT.getCode() == activityVo.getIcountType()) {//活动期间设备
            if (deviceTotalMap == null || deviceTotalMap.get(sdeviceCode) == null) {//如果设备总次数为0 或者是未空
                return true;
            }
            if (deviceTotalMap.get(sdeviceCode).intValue() < activityVo.getIuserAllCount() &&
                    (deviceMap == null ||
                            deviceMap.get(sdeviceCode) == null ||
                            deviceMap.get(sdeviceCode).intValue() < activityVo.getIuserDayCount().intValue())) {//如果设备总次数小于  并且 当天参与次数有效
                return true;
            }
        }
        return false;
    }


    /**
     * 判断活动应用设备范围
     *
     * @param activityVo       活动信息
     * @param commodityDisList 商品信息
     * @param sdeviceCode      设备编号
     * @return flag true能参与活动 false 不能参与活动  someDisCommodity 参与活动有效商品集合
     * @throws Exception
     */
    private Map<String, Object> activityRange(ActivityVo activityVo, List<CommodityDiscountDto> commodityDisList, String sdeviceCode) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        boolean flag = false;
        ActivityUseRangeVo acUseRange = activityVo.getAcUseRange();
        if (BizTypeDefinitionEnum.ActivityRangeType.ALL.getCode() == acUseRange.getIrangeType()) {//设备不限制
            flag = true;
        } else if (BizTypeDefinitionEnum.ActivityRangeType.SOME_EQUIPMENT.getCode() == acUseRange.getIrangeType()) {//部分设备
            if (StringUtil.isNotBlank(acUseRange.getSdeviceCode()) && acUseRange.getSdeviceCode().contains(sdeviceCode)) {
                flag = true;
            }
        } else if (BizTypeDefinitionEnum.ActivityRangeType.SOME_COMMODITY.getCode() == acUseRange.getIrangeType()) {//部分商品
            //获取有效商品集合
            List<CommodityDiscountDto> someDisCommodity = getEffectiveCommodity(commodityDisList, acUseRange.getScommodityCode());
            if (someDisCommodity.size() > 0) {
                commodityDisList = someDisCommodity;
                flag = true;
            }
        } else if (BizTypeDefinitionEnum.ActivityRangeType.SOMEE_QUIPMENT_COMMODITY.getCode() == acUseRange.getIrangeType()) {//部分设备部分商品
            //获取有效商品集合
            List<CommodityDiscountDto> someDisCommodity = getEffectiveCommodity(commodityDisList, acUseRange.getScommodityCode());
            if (StringUtil.isNotBlank(acUseRange.getSdeviceCode()) && acUseRange.getSdeviceCode().contains(sdeviceCode) && someDisCommodity.size() > 0) {
                commodityDisList = someDisCommodity;
                flag = true;
            }
        }

        map.put("someDisCommodity", commodityDisList);
        map.put("flag", flag);
        return map;
    }


    /**
     * 根据应用范围商品编号信息获取有效商品集合
     *
     * @param commodityDisList 订单中的商品集合
     * @param commodityCodeStr 应用范围中的商品编号拼接字符串
     * @return 促销的有效商品集合
     */
    private List<CommodityDiscountDto> getEffectiveCommodity(List<CommodityDiscountDto> commodityDisList, String commodityCodeStr) {
        List<CommodityDiscountDto> someDisCommodity = new ArrayList<CommodityDiscountDto>();
        for (CommodityDiscountDto com : commodityDisList) {
            if (commodityCodeStr.contains(com.getScommodityCode())) {
                someDisCommodity.add(com);
            }
        }
        return someDisCommodity;
    }

    /**
     * 生成订单
     *
     * @param orderCommDto 订单Dto
     * @return ResponseVo<List<CreatOrderResult>>
     */
    @Override
    public /*ResponseVo<List<CreatOrderResult>>*/  Map generateOrder(OrderCommDto orderCommDto) throws Exception {
        logger.info("################生成订单开始！参数为：{}################", orderCommDto);
        //计算订单优惠，订单详情Dto
        List<CommodityDiscountDto> commodityDiscountDtoList = assembleOrderDiscount(orderCommDto);
        //订单表Dto
        OrderDto orderDto = orderCommDto.getOrderDto();
        //支付方式
        int isourceClientType = orderDto.getIsourceClientType();
        ResponseVo<List<CreatOrderResult>> responseVo = null;
        //微信支付
        if (BizTypeDefinitionEnum.ClientType.WECHAT.getCode() == isourceClientType) {
            responseVo = insertOrder(orderDto, commodityDiscountDtoList, BizTypeDefinitionEnum.ClientType.WECHAT.getCode());
            //支付宝支付
        } else if (BizTypeDefinitionEnum.ClientType.ALIPAY.getCode() == isourceClientType) {
            responseVo = insertOrder(orderDto, commodityDiscountDtoList, BizTypeDefinitionEnum.ClientType.ALIPAY.getCode());
        }
        Map map = new HashMap();
        map.put("responseVo", responseVo);
        map.put("commodityDiscountDtoList", commodityDiscountDtoList);
        return map;
      /*  return responseVo;*/
    }


    /**
     * 组装订单优惠参数
     *
     * @param
     * @return ResponseVo<List<OrderRecord>>
     */
    private List<CommodityDiscountDto> assembleOrderDiscount(OrderCommDto orderCommDto) throws InvocationTargetException, IllegalAccessException {
        //订单表Dto
        OrderDto orderDto = orderCommDto.getOrderDto();
        //订单详情Dto
        List<OrderCommodityDto> orderCommodityDtoList = orderCommDto.getComms();
        logger.info("开始组装订单优惠参数");
        OrderDiscountInfoDto orderDiscountInfoDto = new OrderDiscountInfoDto();
        orderDiscountInfoDto.setId(orderDto.getSmemberId());
        orderDiscountInfoDto.setScode(orderDto.getSmemberCode());
        orderDiscountInfoDto.setSmemberName(orderDto.getSmemberName());
        orderDiscountInfoDto.setSdeviceCode(orderDto.getSdeviceCode());
        List<CommodityDiscountDto> commodityDiscountDtoList = new ArrayList<>();
        for (OrderCommodityDto orderCommodityDto : orderCommodityDtoList) {
            CommodityDiscountDto commodityDiscountDto = new CommodityDiscountDto();
            BeanUtils.copyProperties(commodityDiscountDto, orderCommodityDto);//复制对象
           /* commodityDiscountDto.setScommodityCode(orderCommodityDto.getScommodityCode());//商品编号
            commodityDiscountDto.setScommodityId(orderCommodityDto.getScommodityId());//商品Id
            commodityDiscountDto.setScommodityName(orderCommodityDto.getScommodityName());//商品名
            commodityDiscountDto.setFcommodityPrice(orderCommodityDto.getFcommodityPrice());//商品单价
            commodityDiscountDto.setForderCount(orderCommodityDto.getForderCount());//商品数量
            commodityDiscountDto.setSsmallCategoryId(orderCommodityDto.getSsmallCategoryId());//小类Id
            commodityDiscountDto.setSbrandId(orderCommodityDto.getSbrandId());//商品品牌ID
            commodityDiscountDto.setFcostAmount(orderCommodityDto.getFcostAmount());//商品成本价
            commodityDiscountDto.setFtaxPoint(orderCommodityDto.getFtaxPoint());//商品进价税点*/
            commodityDiscountDtoList.add(commodityDiscountDto);
        }
        orderDiscountInfoDto.setCommodityDisList(commodityDiscountDtoList);
        logger.info("组装订单优惠参数完成 :" + orderDiscountInfoDto);
        //调用计算订单优惠服务
        ResponseVo<OrderDiscountInfoResult> responseVo = null;
        try {
            responseVo = calculateOrderDiscount(orderDiscountInfoDto);
        } catch (Exception e) {
            logger.error("调用计算优惠服务失败", e);
            throw new ServiceException("组装订单优惠参数异常!");
        }
        OrderDiscountInfoResult orderDiscountInfoResult = responseVo.getData();
        orderDto.setFtotalAmount(orderDiscountInfoResult.getFtotalAmount());//订单总额
        orderDto.setFtotalCostAmount(orderDiscountInfoResult.getFtotalCostAmount());//订单总成本
        orderDto.setFdiscountAmount(orderDiscountInfoResult.getFdiscountAmount());//订单优惠总额
        orderDto.setFfirstDiscountAmount(orderDiscountInfoResult.getFfirstDiscountAmount());//订单首单优惠金额
        orderDto.setFpromotionDiscountAmount(orderDiscountInfoResult.getFpromotionDiscountAmount());//订单促销优惠金额
        orderDto.setFcouponDeductionAmount(orderDiscountInfoResult.getFcouponDeductionAmount());//订单优惠券抵扣金额
        orderDto.setFactualPayAmount(orderDiscountInfoResult.getFactualPayAmount());//实付金额
        orderDto.setFpointDiscountAmount(orderDiscountInfoResult.getFpointDiscountAmount());//订单积分优惠金额
        orderDto.setFotherDiscountAmount(orderDiscountInfoResult.getFotherDiscountAmount());//订单其他优惠金额
        orderDto.setSacId(orderDiscountInfoResult.getSacId());/* 活动ID 多个，隔开  */
        orderDto.setSacCode(orderDiscountInfoResult.getSacCode());/* 活动编号 多个，隔开 */
        orderDto.setSusedCouponCode(orderDiscountInfoResult.getSusedCouponCode());	/* 券使用编号 */
        orderDto.setSusedCouponId(orderDiscountInfoResult.getSusedCouponId());	/* 券使用ID */
        commodityDiscountDtoList = responseVo.getData().getCommodityDisList();
        return commodityDiscountDtoList;
    }

    /**
     * 获取支付宝商户配置数据
     *
     * @param merchantCode 商户编号
     * @param type         类型 1 生活号 2 支付宝支付
     * @return 支付宝商户配置数据
     * @throws Exception
     */
    public MerchantConf getAlipayMerchantConf(String merchantCode, Integer type) throws Exception {
        MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
        if (clientConf == null) {
            return null;
        }
        int typeVal = -1;
        if (type.intValue() == 1) {
            typeVal = clientConf.getIisConfAlipayShh().intValue();//没有配置支付宝生活号
        } else if (type.intValue() == 2) {
            typeVal = clientConf.getIisConfAlipay().intValue();//没有配置支付宝支付
        }
        if (typeVal == 0) {//没有配置获取默认商户编号
            merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        }
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_ALIPAY_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
        return conf;
    }

    /**
     * 获取微信支付商户配置数据
     *
     * @param merchantCode 商户编号
     * @param type         类型 1 公众号 2 微信支付
     * @return 微信支付商户配置数据
     * @throws Exception
     */
    public MerchantConf getWechatMerchantConf(String merchantCode, Integer type) throws Exception {
        MerchantClientConf clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
        if (clientConf == null) {
            return null;
        }
        int typeVal = -1;
        if (type.intValue() == 1) {
            typeVal = clientConf.getIisConfWechatGzh().intValue();//没有配置微信公众号
        } else if (type.intValue() == 2) {
            typeVal = clientConf.getIisConfWechat().intValue();//没有配置微信支付
        }
        if (typeVal == 0) {
            merchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
        }
        String json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + merchantCode);
        if (StringUtil.isBlank(json)) {
            return null;
        }
        MerchantConf conf = JSONObject.parseObject(json, MerchantConf.class);
        return conf;
    }

    /**
     * 生成订单
     *
     * @param orderDto                 订单Dto
     * @param commodityDiscountDtoList 订单商品Dto
     * @param merchantConfType         商户客户端类型 10:微信  20:支付宝
     * @return ResponseVo<List<OrderRecord>>
     */
    private ResponseVo<List<CreatOrderResult>> insertOrder(OrderDto orderDto, List<CommodityDiscountDto> commodityDiscountDtoList, Integer merchantConfType) throws Exception {
        ResponseVo<OrderRecord> recordResponseVo = new ResponseVo<>();
      /*  MerchantClientConf merchantClientConf = merchantClientConfService.selectByPrimaryKey(orderDto.getSmerchantId());*/
        MerchantClientConf merchantClientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + orderDto.getSmerchantCode());
        if (null == merchantClientConf) {
            return ErrorCodeEnum.ERROR_COMMON_SYSTEM
                    .getResponseVo("商户客户端配置不存在！");
        }
       /* 查看商户客户端配置支持支付类型
        1.仅代扣 :代扣
        2.仅手扣 : 手扣
        3.全部 查看用户是否开通免密 (1)开通:代扣 (2)未开通:手扣*/
        int payWay = merchantClientConf.getIsupportPayWay();
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderDto.getSmemberId());
        int isOpen = 0;
        //查看用户是否开通(微信、支付宝)免密
        if (BizTypeDefinitionEnum.ClientType.WECHAT.getCode() == merchantConfType) {
           MerchantConf merchantConfTemp = getWechatMerchantConf(orderDto.getSmerchantCode(), 2);
            if (null != merchantConfTemp.getIwechatWithholdType() && 10 == merchantConfTemp.getIwechatWithholdType()) {
                isOpen = memberInfo.getIwechatOpen();
            } else if (null != merchantConfTemp.getIwechatWithholdType() && 20 == merchantConfTemp.getIwechatWithholdType()) {
                isOpen = memberInfo.getIwechatPointOpen();
            }
        } else if (BizTypeDefinitionEnum.ClientType.ALIPAY.getCode() == merchantConfType) {
            isOpen = memberInfo.getIaipayOpen();
        }
        List<CreatOrderResult> creatOrderResultList = new ArrayList();
        //订单总金额
        BigDecimal orderTotal = orderDto.getFtotalAmount();//订单总额
        //仅手扣 or  全部且未开通免密 则为主动扣款
        if (30 == payWay || (10 == payWay && 0 == isOpen)) {
            logger.info("开始生成普通订单.....");
            orderDto.setIchargebackWay(BizTypeDefinitionEnum.ChargebackWay.ACTIVE_PAYMENT.getCode());
            OrderRecord orderRecord = new OrderRecord();
            recordResponseVo = insertOrderRecord(orderRecord, orderDto,/* map,*/ 0, "");
            CreatOrderResult creatOrderResult = new CreatOrderResult();
            List<OrderCommodity> orderCommodityList = new ArrayList<>();
            creatOrderResult.setOrderRecord(recordResponseVo.getData());
            for (int i = 0; i < commodityDiscountDtoList.size(); i++) {
                CommodityDiscountDto commodityDiscountDto = commodityDiscountDtoList.get(i);
                OrderCommodity orderCommodity = new OrderCommodity();
                ResponseVo<OrderCommodity> orderCommodityResponseVo = insertOrderCommodity(orderCommodity, commodityDiscountDto, orderRecord.getId(), orderRecord.getSorderCode());
                orderCommodityList.add(orderCommodityResponseVo.getData());
            }
            creatOrderResult.setOrderCommodityList(orderCommodityList);
            creatOrderResultList.add(creatOrderResult);
        } else if (20 == payWay || (10 == payWay && 1 == isOpen)) {
           /* MerchantConf merchantConf = new MerchantConf();
            merchantConf.setSmerchantId(orderDto.getSmerchantId());
            merchantConf.setItype(merchantConfType);
            merchantConf.setIdelFlag(0);
            merchantConf = merchantConfService.selectByIdType(merchantConf);*/
           /* 10:微信  20:支付宝*/
           /* MerchantConf merchantConf = null;

            String json = null;
            if (10 == merchantConfType) {
                merchantConf = getWechatMerchantConf(orderDto.getSmerchantCode(),2);
                json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_WECHAT_CONFIG + orderDto.getSmerchantCode());
            } else if (20 == merchantConfType) {
                json = (String) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_ALIPAY_CONFIG + orderDto.getSmerchantCode());
            }
            if (StringUtil.isBlank(json)) {//异常情况返回空
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM
                        .getResponseVo("商户支付配置信息不存在！");
            }
            MerchantConf merchantConf = JSONObject.parseObject(json, MerchantConf.class);
            if (null == merchantConf) {//异常情况返回空
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM
                        .getResponseVo("商户支付配置信息不存在！");
            }*/
            /* 10:微信  20:支付宝*/
            MerchantConf merchantConf = null;
            if (10 == merchantConfType) {
                merchantConf = getWechatMerchantConf(orderDto.getSmerchantCode(), 2);
            } else if (20 == merchantConfType) {
                merchantConf = getAlipayMerchantConf(orderDto.getSmerchantCode(), 2);
            }
            if (null == merchantConf) {//异常情况返回空
                return ErrorCodeEnum.ERROR_COMMON_SYSTEM
                        .getResponseVo("商户支付配置信息不存在！");
            }
            //如果订单总价小于等于限制金额，则直接生成订单
            if (orderTotal.compareTo(merchantConf.getFpayLimitSingle()) <= 0) {
                orderDto.setIchargebackWay(BizTypeDefinitionEnum.ChargebackWay.WITHHOLDING.getCode());
                OrderRecord orderRecord = new OrderRecord();
                recordResponseVo = insertOrderRecord(orderRecord, orderDto, 0, "");
                CreatOrderResult creatOrderResult = new CreatOrderResult();
                creatOrderResult.setOrderRecord(recordResponseVo.getData());
                List<OrderCommodity> orderCommodityList = new ArrayList<>();
                for (int i = 0; i < commodityDiscountDtoList.size(); i++) {
                    CommodityDiscountDto commodityDiscountDto = commodityDiscountDtoList.get(i);
                    OrderCommodity orderCommodity = new OrderCommodity();
                    ResponseVo<OrderCommodity> orderCommodityResponseVo = insertOrderCommodity(orderCommodity, commodityDiscountDto, orderRecord.getId(), orderRecord.getSorderCode());
                    orderCommodityList.add(orderCommodityResponseVo.getData());
                }
                creatOrderResult.setOrderCommodityList(orderCommodityList);
                creatOrderResultList.add(creatOrderResult);
            } else {
                //总价格大于限制金额 拆单 生成拆单编号
                String sdismantlingCode = CoreUtils.newCode(MemberConstants.GenerateCode.SDISMANTLING_CODE);
                logger.info("开始计算拆单....拆单编号:" + sdismantlingCode);
                orderDto.setIchargebackWay(BizTypeDefinitionEnum.ChargebackWay.WITHHOLDING.getCode());
                //按照商品总金额排序 大到小
                Collections.sort(commodityDiscountDtoList, new Comparator<CommodityDiscountDto>() {
                    /**
                     *
                     * @param o1
                     * @param o2
                     * @return
                     */
                    @Override
                    public int compare(CommodityDiscountDto o1, CommodityDiscountDto o2) {
                        return o2.getFcommodityAmount().compareTo(o1.getFcommodityAmount());
                    }
                });
                //如果总额最大的一类商品总金额大于限制金额，则直接生成那一笔商品订单
                if (commodityDiscountDtoList.get(0).getFcommodityAmount().compareTo(merchantConf.getFpayLimitSingle()) > 0) {
                    for (int y = 0; y < commodityDiscountDtoList.size(); y++) {
                        OrderRecord orderRecord = new OrderRecord();
                        if (commodityDiscountDtoList.get(y).getFcommodityAmount().compareTo(merchantConf.getFpayLimitSingle()) > 0) {
                            //直接生成订单getFpromotionDiscountAmount
                            orderDto.setFtotalAmount(commodityDiscountDtoList.get(y).getFcommodityAmount());//订单总额
                            orderDto.setFdiscountAmount(commodityDiscountDtoList.get(y).getFdiscountAmount());//订单优惠总额
                            orderDto.setFfirstDiscountAmount(commodityDiscountDtoList.get(y).getFfirstDiscountAmount());//订单首单优惠金额
                            orderDto.setFpromotionDiscountAmount(commodityDiscountDtoList.get(y).getFpromotionDiscountAmount());//订单促销优惠金额
                            orderDto.setFcouponDeductionAmount(commodityDiscountDtoList.get(y).getFcouponDeductionAmount());//订单优惠券抵扣金额
                            orderDto.setFactualPayAmount(commodityDiscountDtoList.get(y).getFactualAmount());//实付金额
                            orderDto.setFpointDiscountAmount(commodityDiscountDtoList.get(y).getFpointDiscountAmount());//订单积分优惠金额
                            orderDto.setFotherDiscountAmount(commodityDiscountDtoList.get(y).getFotherDiscountAmount());//订单其他优惠金额
                            recordResponseVo = insertOrderRecord(orderRecord, orderDto,/* map,*/ 1, sdismantlingCode);
                            CreatOrderResult creatOrderResult = new CreatOrderResult();
                            List<OrderCommodity> orderCommodityList = new ArrayList<>();
                            creatOrderResult.setOrderRecord(recordResponseVo.getData());
                            ResponseVo<OrderCommodity> orderCommodityResponseVo = insertOrderCommodity(new OrderCommodity(), commodityDiscountDtoList.get(y), orderRecord.getId(), orderRecord.getSorderCode());
                            orderCommodityList.add(orderCommodityResponseVo.getData());
                            creatOrderResult.setOrderCommodityList(orderCommodityList);
                            creatOrderResultList.add(creatOrderResult);
                        } else {
                            commodityDiscountDtoList = commodityDiscountDtoList.subList(y, commodityDiscountDtoList.size());
                            creatOrderResultList = insertOtherData(commodityDiscountDtoList, merchantConf, sdismantlingCode, recordResponseVo, orderDto, creatOrderResultList);
                            break;
                        }
                    }
                } else {
                    creatOrderResultList = insertOtherData(commodityDiscountDtoList, merchantConf, sdismantlingCode, recordResponseVo, orderDto, creatOrderResultList);
                }
            }
        }
        return ResponseVo.getSuccessResponse(creatOrderResultList);
    }

    /**
     * 生成余下订单
     *
     * @param commodityDiscountDtoList
     * @param merchantConf
     * @param sdismantlingCode         拆单编号
     * @param recordResponseVo
     * @param orderDto
     * @param list
     * @return List<OrderRecord>
     */
    private List<CreatOrderResult> insertOtherData(List<CommodityDiscountDto> commodityDiscountDtoList, MerchantConf merchantConf, String sdismantlingCode, ResponseVo<OrderRecord> recordResponseVo, OrderDto orderDto, List<CreatOrderResult> list) {
        //订单总金额
        BigDecimal orderTotal = new BigDecimal(BigInteger.ZERO);
        //订单优惠总额
        BigDecimal fdiscountAmount = BigDecimal.ZERO;
        //订单首单优惠金额
        BigDecimal ffirstDiscountAmount = BigDecimal.ZERO;
        //订单促销优惠金额
        BigDecimal fpromotionDiscountAmount = BigDecimal.ZERO;
        //订单优惠券抵扣金额
        BigDecimal fcouponDeductionAmount = BigDecimal.ZERO;
        //订单积分优惠金额
        //  BigDecimal fpointDiscountAmount = BigDecimal.ZERO;
        //订单其他优惠金额
        //  BigDecimal fotherDiscountAmount = BigDecimal.ZERO;
        //实付金额
        BigDecimal factualAmount = BigDecimal.ZERO;
        //订单成本总额
        BigDecimal ftotalCostAmount = BigDecimal.ZERO;
        int num = 0;
        for (int i = 0; i < commodityDiscountDtoList.size(); i++) {
            orderTotal = orderTotal.add(commodityDiscountDtoList.get(i).getFcommodityAmount());
            fdiscountAmount = fdiscountAmount.add(commodityDiscountDtoList.get(i).getFdiscountAmount());
            ffirstDiscountAmount = ffirstDiscountAmount.add(commodityDiscountDtoList.get(i).getFfirstDiscountAmount());
            fpromotionDiscountAmount = fpromotionDiscountAmount.add(commodityDiscountDtoList.get(i).getFpromotionDiscountAmount());
            fcouponDeductionAmount = fcouponDeductionAmount.add(commodityDiscountDtoList.get(i).getFcouponDeductionAmount());
         /*   fpointDiscountAmount = fpointDiscountAmount.add(commodityDiscountDtoList.get(i).getFpointDiscountAmount());
            fotherDiscountAmount = fotherDiscountAmount.add(commodityDiscountDtoList.get(i).getFotherDiscountAmount());*/
            factualAmount = factualAmount.add(commodityDiscountDtoList.get(i).getFactualAmount());
            ftotalCostAmount = ftotalCostAmount.add(commodityDiscountDtoList.get(i).getFcostAmount());
            //如果订单支付总金额大于商户单笔支付限额,则拆单
            if (orderTotal.compareTo(merchantConf.getFpayLimitSingle()) > 0) {
                orderDto.setFtotalAmount(orderTotal.subtract(commodityDiscountDtoList.get(i).getFcommodityAmount()));
                orderDto.setFdiscountAmount(fdiscountAmount.subtract(commodityDiscountDtoList.get(i).getFdiscountAmount()));
                orderDto.setFfirstDiscountAmount(ffirstDiscountAmount.subtract(commodityDiscountDtoList.get(i).getFfirstDiscountAmount()));
                orderDto.setFpromotionDiscountAmount(fpromotionDiscountAmount.subtract(commodityDiscountDtoList.get(i).getFpromotionDiscountAmount()));
                orderDto.setFcouponDeductionAmount(fcouponDeductionAmount.subtract(commodityDiscountDtoList.get(i).getFcouponDeductionAmount()));
                // orderDto.setFpointDiscountAmount(fpointDiscountAmount.subtract(commodityDiscountDtoList.get(i).getFpointDiscountAmount()));
                // orderDto.setFotherDiscountAmount(fotherDiscountAmount.subtract(commodityDiscountDtoList.get(i).getFotherDiscountAmount()));
                orderDto.setFactualPayAmount(factualAmount.subtract(commodityDiscountDtoList.get(i).getFactualAmount()));
                orderDto.setFtotalCostAmount(ftotalCostAmount.subtract(commodityDiscountDtoList.get(i).getFcostAmount()));
                //插入商品订单记录表
                OrderRecord orderRecord = new OrderRecord();
                CreatOrderResult creatOrderResult = new CreatOrderResult();
                List<OrderCommodity> orderCommodityLis = new ArrayList<>();
                recordResponseVo = insertOrderRecord(orderRecord, orderDto, 1, sdismantlingCode);
                creatOrderResult.setOrderRecord(recordResponseVo.getData());
                //插入订单商品明细表
                for (int x = num; x < i; x++) {
                    CommodityDiscountDto commodityDiscountDto = commodityDiscountDtoList.get(x);
                    OrderCommodity orderCommodity = new OrderCommodity();
                    ResponseVo<OrderCommodity> responseVo = insertOrderCommodity(orderCommodity, commodityDiscountDto, orderRecord.getId(), orderRecord.getSorderCode());
                    orderCommodityLis.add(responseVo.getData());
                }
                creatOrderResult.setOrderCommodityList(orderCommodityLis);
                list.add(creatOrderResult);
                num = i;
                i = i - 1;
                //订单总金额
                orderTotal = BigDecimal.ZERO;
                //订单优惠总额
                fdiscountAmount = BigDecimal.ZERO;
                //订单首单优惠金额
                ffirstDiscountAmount = BigDecimal.ZERO;
                //订单促销优惠金额
                fpromotionDiscountAmount = BigDecimal.ZERO;
                //订单优惠券抵扣金额
                fcouponDeductionAmount = BigDecimal.ZERO;
                //订单积分优惠金额
                // fpointDiscountAmount = BigDecimal.ZERO;
                //订单其他优惠金额
                //fotherDiscountAmount = BigDecimal.ZERO;
                //实付金额
                factualAmount = BigDecimal.ZERO;
                //订单成本总额
                ftotalCostAmount = BigDecimal.ZERO;
            } else {
                if (i == commodityDiscountDtoList.size() - 1) {
                    orderDto.setFtotalAmount(orderTotal);
                    orderDto.setFdiscountAmount(fdiscountAmount);
                    orderDto.setFfirstDiscountAmount(ffirstDiscountAmount);
                    orderDto.setFpromotionDiscountAmount(fpromotionDiscountAmount);
                    orderDto.setFcouponDeductionAmount(fcouponDeductionAmount);
                    // orderDto.setFpointDiscountAmount(fpointDiscountAmount.subtract(commodityDiscountDtoList.get(i).getFpointDiscountAmount()));
                    // orderDto.setFotherDiscountAmount(fotherDiscountAmount.subtract(commodityDiscountDtoList.get(i).getFotherDiscountAmount()));
                    orderDto.setFactualPayAmount(factualAmount);
                    orderDto.setFtotalCostAmount(ftotalCostAmount);
                    //插入商品订单记录表
                    OrderRecord orderRecord = new OrderRecord();
                    CreatOrderResult creatOrderResult = new CreatOrderResult();
                    List<OrderCommodity> orderCommodityLis = new ArrayList<>();
                    recordResponseVo = insertOrderRecord(orderRecord, orderDto, 1, sdismantlingCode);
                    creatOrderResult.setOrderRecord(recordResponseVo.getData());
                    for (int x = num; x < i + 1; x++) {
                        CommodityDiscountDto commodityDiscountDto = commodityDiscountDtoList.get(x);
                        OrderCommodity orderCommodity = new OrderCommodity();
                        ResponseVo<OrderCommodity> orderCommodityResponseVo = insertOrderCommodity(orderCommodity, commodityDiscountDto, orderRecord.getId(), orderRecord.getSorderCode());
                        orderCommodityLis.add(orderCommodityResponseVo.getData());
                    }
                    creatOrderResult.setOrderCommodityList(orderCommodityLis);
                    list.add(creatOrderResult);
                }
            }
        }
        return list;
    }

    /**
     * 生成订单主表
     *
     * @param orderRecord      订单表
     * @param orderDto         订单Dto
     * @param isDismantling    是否是拆单订单 0:否  1:是
     * @param sdismantlingCode 拆单订单编号
     * @return ResponseVo<OrderRecord>
     */
    /*@param  isDirectGeneration 是否直接生成订单（非拆单）true:是  false:否*/
    private ResponseVo<OrderRecord> insertOrderRecord(OrderRecord orderRecord, OrderDto orderDto, Integer isDismantling, String sdismantlingCode) {
        try {
            BeanUtils.copyProperties(orderRecord, orderDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("Order对象转换失败：{}", e);
            throw new ServiceException("Order对象转换失败!");
        }
        try {
            String orderCode = CoreUtils.newCode(EntityTables.OM_ORDER_RECORD);
            orderRecord.setFactualRefundAmount(BigDecimal.ZERO);//退款金额默认值
            orderRecord.setSorderCode(orderCode);
            if (null == orderRecord.getIpoint()) {
                orderRecord.setIpoint(BigDecimal.ZERO);//默认抵扣积分
            }
            orderRecord.setTaddTime(DateUtils.getCurrentDateTime());
            orderRecord.setTupdateTime(DateUtils.getCurrentDateTime());
            orderRecord.setTorderTime(DateUtils.getCurrentDateTime());
            //如果实付金额小于等于0,则订单状态为已支付,否则为待付款
            if (orderRecord.getFactualPayAmount().compareTo(BigDecimal.ZERO) <= 0) {
                orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PAYMENT_SUCCESS.getCode());

                orderRecord.setTpayCompleteTime(DateUtils.getCurrentDateTime());//支付完成时间
            } else {
                orderRecord.setIstatus(BizTypeDefinitionEnum.OrderStatus.PENDING_PAYMENT.getCode());
            }
            orderRecord.setIdelFlag(0);
            orderRecord.setIisDismantling(isDismantling);//是否拆单
            if (StringUtil.isNotBlank(sdismantlingCode)) {
                orderRecord.setSdismantlingCode(sdismantlingCode);//拆单编号
                orderRecord.setIdismantlingType(10);//拆单类型 10:自动拆单 20:手动拆单
            }
            if (null != orderRecord.getIsourceClientType()) {
                if (BizTypeDefinitionEnum.ClientType.WECHAT.getCode() == orderRecord.getIsourceClientType()) {
                    orderRecord.setIpayType(BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());
                } else if (BizTypeDefinitionEnum.ClientType.ALIPAY.getCode() == orderRecord.getIsourceClientType()) {
                    orderRecord.setIpayType(BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());
                }
            }
            //如果订单为商户代扣,则支付类型为来源客户端类型
            if (BizTypeDefinitionEnum.ChargebackWay.WITHHOLDING.getCode() == orderRecord.getIchargebackWay()) {
                /*if (BizTypeDefinitionEnum.ClientType.WECHAT.getCode() == orderRecord.getIsourceClientType()) {
                    orderRecord.setIpayType(BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode());
                } else if (BizTypeDefinitionEnum.ClientType.ALIPAY.getCode() == orderRecord.getIsourceClientType()) {
                    orderRecord.setIpayType(BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode());
                }*/
                orderRecord.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());//支付类型中的支付方式为代扣
            }
            orderRecordDao.insert(orderRecord);
        } catch (Exception insertException) {
            logger.error("插入商品订单记录表异常：{}", insertException.toString());
            throw new ServiceException("插入商品订单记录表异常!");
        }
        return ResponseVo.getSuccessResponse(orderRecord);
    }

    /**
     * 生成订单从表
     *
     * @param orderCommodity       订单商品明细表
     * @param commodityDiscountDto 订单商品表
     * @param orderId              订单主表Id
     * @param orderCode            订单主表编号
     * @return ResponseVo
     */
    private ResponseVo<OrderCommodity> insertOrderCommodity(OrderCommodity orderCommodity, CommodityDiscountDto commodityDiscountDto, String orderId, String orderCode) {
        try {
            BeanUtils.copyProperties(orderCommodity, commodityDiscountDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("OrderCommodity对象转换失败：{}", e);
            throw new ServiceException("OrderCommodity对象转换失败!");
        }
        if (null == orderCommodity.getFtaxPoint()) {
            orderCommodity.setFtaxPoint(BigDecimal.ZERO);//税点默认值
        }
        if (null == orderCommodity.getFpointDiscountAmount()) {
            orderCommodity.setFpointDiscountAmount(BigDecimal.ZERO);//订单积分优惠金额默认值
        }
        if (null == orderCommodity.getFotherDiscountAmount()) {
            orderCommodity.setFotherDiscountAmount(BigDecimal.ZERO);//订单其他优惠金额默认值
        }
        orderCommodity.setFrefundCount(0);
        orderCommodity.setFrefundAmount(BigDecimal.ZERO);
        orderCommodity.setSorderId(orderId);
        orderCommodity.setTaddTime(DateUtils.getCurrentDateTime());
        orderCommodity.setTupdateTime(DateUtils.getCurrentDateTime());
        orderCommodity.setSorderCode(orderCode);
        try {
            orderCommodityDao.insert(orderCommodity);
        } catch (Exception insertComditityException) {
            logger.error("插入订单商品明细表异常：{}", insertComditityException);
            throw new ServiceException("插入订单商品明细表异常!");
        }
        return ResponseVo.getSuccessResponse(orderCommodity);
    }

    /**
     * 调用更新优惠券状态服务
     *
     * @param orderDto 商品订单表
     * @param orderVo
     */
    @Override
    public void upCoupon(final OrderDto orderDto, final ResponseVo<List<CreatOrderResult>> orderVo) {

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    UpCouponDto upCouponDto = new UpCouponDto();
                    int isDis = orderVo.getData().size();//订单是否拆单(订单数量大于1为拆单订单)
                    if (isDis > 1) {
                        String sdismantlingCode = orderVo.getData().get(0).getOrderRecord().getSdismantlingCode();//拆单编号
                        upCouponDto.setTargetCode(sdismantlingCode);
                        upCouponDto.setTargetInstruction("拆单订单使用,拆单编号:" + sdismantlingCode);
                    } else {
                        String orderId = orderVo.getData().get(0).getOrderRecord().getId();//订单Id
                        String orderCode = orderVo.getData().get(0).getOrderRecord().getSorderCode();//订单Code
                        upCouponDto.setTargetId(orderId);
                        upCouponDto.setTargetCode(orderCode);
                        upCouponDto.setTargetInstruction("订单下单使用,使用订单编号:" + orderCode);
                    }
                    upCouponDto.setCouponDeductionAmount(orderDto.getFcouponDeductionAmount());//优惠券抵扣金额
                    upCouponDto.setCouponUserId(orderDto.getSusedCouponId());//使用优惠券ID
                    logger.info("################组装下订单更新优惠券状态服务数据完成！参数为：{}################", upCouponDto);

                    //会员ID  upCouponDto.setMemberId(orderDto.getSmemberId());
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(ActivityServicesDefine.UP_COUPON);
                    invoke.setRequestObj(upCouponDto); // post 参数
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo>() {
                    });
                    ResponseVo responseVo = (ResponseVo) invoke.invoke();
                    if (null != responseVo && responseVo.isSuccess()) {
                        logger.info("更新优惠券状态接口服务成功");
                    } else {
                        logger.error("更新优惠券状态接口服务失败");
                        throw new ServiceException("更新优惠券状态接口服务失败");
                    }
                } catch (Exception e) {
                    logger.error("更新优惠券状态接口服务失败", e);
                    throw new ServiceException("更新优惠券状态接口服务失败");
                }
            }
        });
    }


    /***
     * 创建实时订单服务
     * @param orderDto 订单参数
     * @return
     */
    @Override
    public ResponseVo<RealTimeOrderResult> createRealTimeOrder(RealTimeOrderDto orderDto) throws Exception {
        ResponseVo<RealTimeOrderResult> responseVo = ResponseVo.getSuccessResponse();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(orderDto.getDeviceId());
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", orderDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderDto.getMemberId());
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        //1、库存对比 计算商品差
        Map<String, Object> map = deviceStockService.calcCommodityDiffByType(orderDto.getCommodityVos(), deviceInfo, memberInfo);
        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");
        RealTimeOrderResult result = null;
        if (null != subVoList && subVoList.size() > 0) {
            result = new RealTimeOrderResult();
            logger.info("开始组装订单优惠参数");
            OrderDiscountInfoDto orderDiscountInfoDto = new OrderDiscountInfoDto();
            orderDiscountInfoDto.setId(memberInfo.getId());
            orderDiscountInfoDto.setScode(memberInfo.getScode());
            orderDiscountInfoDto.setSmemberName(memberInfo.getSmemberName());
            orderDiscountInfoDto.setSdeviceCode(deviceInfo.getScode());
            List<CommodityDiscountDto> commodityDiscountDtoList = new ArrayList<>();
            for (CommodityDiffVo diffVo : subVoList) {
                CommodityDiscountDto commodityDiscountDto = new CommodityDiscountDto();
                BeanUtils.copyProperties(commodityDiscountDto, diffVo);//复制对象
                commodityDiscountDto.setForderCount(diffVo.getNumber());//订单商品数量
                commodityDiscountDtoList.add(commodityDiscountDto);
            }
            orderDiscountInfoDto.setCommodityDisList(commodityDiscountDtoList);
            logger.info("组装订单优惠参数完成 :" + orderDiscountInfoDto);
            ResponseVo<OrderDiscountInfoResult> resultResponseVo = calculateOrderDiscount(orderDiscountInfoDto);
            if (resultResponseVo.isSuccess()) {//创建优惠成功
                OrderDiscountInfoResult discountInfoResult = resultResponseVo.getData();
                BeanUtils.copyProperties(result, discountInfoResult);//复制对象
                List<RealTimeCommodityResult> results = new ArrayList<RealTimeCommodityResult>();
                RealTimeCommodityResult commodityResult = null;
                for (CommodityDiscountDto dto : discountInfoResult.getCommodityDisList()) {
                    commodityResult = new RealTimeCommodityResult();
                    BeanUtils.copyProperties(commodityResult, dto);//复制对象
                    commodityResult.setNumber(dto.getForderCount());//订单商品数量
                    results.add(commodityResult);
                }
                result.setResults(results);
            } else {
                //计算优惠失败  返回数据
                BigDecimal totalAmount = new BigDecimal("0");
                List<RealTimeCommodityResult> results = new ArrayList<RealTimeCommodityResult>();
                RealTimeCommodityResult commodityResult = null;
                for (CommodityDiffVo diffVo : subVoList) {
                    commodityResult = new RealTimeCommodityResult();
                    BeanUtils.copyProperties(commodityResult, diffVo);//复制对象
                    results.add(commodityResult);
                    totalAmount = totalAmount.add((commodityResult.getFcommodityPrice().multiply(new BigDecimal(commodityResult.getNumber()))));//计算总金额
                }
                result.setFactualPayAmount(totalAmount);
                result.setFcouponDeductionAmount(BigDecimal.ZERO);
                result.setFdiscountAmount(BigDecimal.ZERO);
                result.setFfirstDiscountAmount(BigDecimal.ZERO);
                result.setFotherDiscountAmount(BigDecimal.ZERO);
                result.setFpointDiscountAmount(BigDecimal.ZERO);
                result.setFpromotionDiscountAmount(BigDecimal.ZERO);
                result.setFtotalAmount(totalAmount);
                result.setResults(results);
            }
        }
        responseVo.setData(result);
        return responseVo;
    }

    /**
     * 创建支付订单
     *
     * @param merchantCode 商户编号
     * @param orderRecords 订单集合
     */
    @Override
    public void createPayOrder(final String merchantCode, final List<CreatOrderResult> orderRecords) {
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                logger.info("创建免密支付订单,参数：{}", orderRecords.toArray());
                OrderRecord orderRecord = null;
                FreePaymentDto paymentDto = null;

                MerchantClientConf clientConf = null;
                String smerchantCode = merchantCode;
                try {
                    clientConf = (MerchantClientConf) iCached.hget(RedisConst.MERCHANT_INFO, RedisConst.SH_CLIENT_CONFIG + merchantCode);
                    if (null == clientConf || clientConf.getIisConfAlipay().intValue() == 0) {//没有配置获取默认商户编号
                        smerchantCode = GrpParaUtil.getDetailForName(CoreConstant.DEFAULT_MERCHANT_CONFIG, "default_merchant_code").getSvalue();
                    }
                } catch (Exception e) {
                    logger.error("获取商户客户端配置异常：{}", e);
                    return;
                }
                MerchantInfo merchantInfo = merchantInfoService.selectByCode(smerchantCode);
                if (null == merchantInfo) {
                    logger.error("收款商户异常，商户编号：{}", smerchantCode);
                    return;
                }
                RestServiceInvoker invoke = null;
                ResponseVo<FreePaymentResult> resVO = null;
                for (CreatOrderResult orderResult : orderRecords) {
                    orderRecord = orderResult.getOrderRecord();
                    if (!merchantCode.equals(orderRecord.getSmerchantCode())) {
                        logger.error("商户订单异常，商户编号：{}", merchantCode + "===========" + orderRecord.getSmemberCode());
                        continue;
                    }
                    if (orderRecord.getIstatus().intValue() == 100 && orderRecord.getIchargebackWay().intValue() == 10) {//待支付  商户代扣
                        //创建支付
                        paymentDto = new FreePaymentDto();
                        paymentDto.setSsubject(merchantInfo.getSname() + "-" + orderRecord.getSorderCode());//商户明细+订单编号
                        paymentDto.setSorderId(orderRecord.getId());
                        paymentDto.setSmerchantCode(smerchantCode);
                        paymentDto.setSmemberId(orderRecord.getSmemberId());
                        paymentDto.setSmemberName(orderRecord.getSmemberName());
                        paymentDto.setSmemberCode(orderRecord.getSmemberCode());
                        paymentDto.setIpayWay(BizTypeDefinitionEnum.PayWay.WITHHOLDING.getCode());//代扣
                        paymentDto.setIisIgnoreStatus(0);
                        String url = "";
                        if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_WECHAT.getCode()) {//微信代扣服务
                            url = FreeServicesDefine.WECHAT_PAYMENT;
                        } else if (orderRecord.getIpayType().intValue() == BizTypeDefinitionEnum.PayType.PAY_ALIPAY.getCode()) {//支付宝代扣服务
                            url = FreeServicesDefine.ALIPAY_PAYMENT;
                        }
                        if (StringUtil.isNotBlank(url)) {
                            try {
                                invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(url);// 服务名称
                                // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                                invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<FreePaymentResult>>() {
                                });
                                invoke.setRequestObj(paymentDto); // post 参数
                                resVO = (ResponseVo<FreePaymentResult>) invoke.invoke();
                                if (resVO.isSuccess()) {
                                    logger.info("订单代扣支付成功:{}", resVO.getData());
                                } else {
                                    logger.error("订单代扣支付异常:{}", resVO);
                                }
                            } catch (Exception e) {
                                logger.error("商户订单创建支付异常，收款商户编号===========订单编号：{}", smerchantCode + "===========" + orderRecord.getId());
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * 创建补货实时订单服务
     *
     * @param orderDto 订单参数
     * @return
     */
    @Override
    public ResponseVo<ReplenishRealTimeOrderResult> createReplenishRealTimeOrder(ReplenishRealTimeOrderDto orderDto) {
        ResponseVo<ReplenishRealTimeOrderResult> responseVo = ResponseVo.getSuccessResponse();
        ReplenishRealTimeOrderResult replenishRealTimeOrderResult = new ReplenishRealTimeOrderResult();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(orderDto.getDeviceId());
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", orderDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderDto.getMemberId());
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }

        // 库存对比 计算商品差（上架商品与下架商品）
        Map<String, Object> map = deviceStockService.calcCommodityDiffByType(orderDto.getCommodityVos(), deviceInfo, memberInfo);

        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList"); // 下架的商品
        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList"); // 上架的商品

        List<ReplenishRealTimeCommodityResult> subCommodityList = new ArrayList<>();
        List<ReplenishRealTimeCommodityResult> addCommodityList = new ArrayList<>();
        List<ReplenishRealTimeCommodityResult> stockCommodityList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(subVoList)) {    // 组装下架商品集合
            ReplenishRealTimeCommodityResult repSub = null;
            for (CommodityDiffVo comSub : subVoList) {
                repSub = new ReplenishRealTimeCommodityResult();
                repSub.setScommodityId(comSub.getScommodityId());
                repSub.setNumber(comSub.getNumber());
                repSub.setScommodityCode(comSub.getScommodityCode());
                repSub.setScommodityName(comSub.getScommodityName());
                repSub.setScommodityFullName(comSub.getScommodityFullName());
                repSub.setScommodityUrl(comSub.getScommodityImg());
                subCommodityList.add(repSub);
            }
        }

        if (CollectionUtils.isNotEmpty(addVoList)) {    // 组装上架商品集合
            ReplenishRealTimeCommodityResult repAdd = null;
            for (CommodityDiffVo comAdd : addVoList) {
                repAdd = new ReplenishRealTimeCommodityResult();
                repAdd.setScommodityId(comAdd.getScommodityId());
                repAdd.setScommodityName(comAdd.getScommodityName());
                repAdd.setScommodityFullName(comAdd.getScommodityFullName());
                repAdd.setScommodityCode(comAdd.getScommodityCode());
                repAdd.setNumber(comAdd.getNumber());
                repAdd.setScommodityUrl(comAdd.getScommodityImg());
                addCommodityList.add(repAdd);
            }
        }

        // 组装补货之前库存商品集合
        List<StockVo> stockVoList = deviceStockService.selectStockByDeviceId(orderDto.getDeviceId());
        if (CollectionUtils.isNotEmpty(stockVoList)) {
            ReplenishRealTimeCommodityResult repStock = null;
            for (StockVo stock : stockVoList) {
                repStock = new ReplenishRealTimeCommodityResult();
                repStock.setScommodityId(stock.getId());
                repStock.setScommodityCode(stock.getScode());
                repStock.setScommodityName(stock.getSname());
                repStock.setScommodityFullName(stock.getSbrandName() + stock.getSname() + stock.getStaste() + stock.getIspecWeight() + stock.getSspecUnit() + "/" + stock.getSpackageUnit());
                repStock.setNumber(stock.getIstock());
                repStock.setScommodityUrl(stock.getScommodityImg());
                stockCommodityList.add(repStock);
            }
        }

        replenishRealTimeOrderResult.setShelfCommoditys(addCommodityList);
        replenishRealTimeOrderResult.setDropOffCommoditys(subCommodityList);
        replenishRealTimeOrderResult.setResults(stockCommodityList);
        responseVo.setData(replenishRealTimeOrderResult);
        return responseVo;
    }

   /* *//**
     * 创建补货实时订单服务
     *
     * @param gravityCloseDto 订单参数
     * @return
     *//*
   @Override
    public ResponseVo<ReplenishRealTimeOrderResult> closeGravityCommodityDeal(GravityCloseDto gravityCloseDto) {
        ResponseVo<ReplenishRealTimeOrderResult> responseVo = ResponseVo.getSuccessResponse();
        ReplenishRealTimeOrderResult replenishRealTimeOrderResult = new ReplenishRealTimeOrderResult();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(gravityCloseDto.getDeviceId());
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", gravityCloseDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(gravityCloseDto.getMemberId());
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }

        // 库存对比 计算商品差（上架商品与下架商品）
        Map<String, Object> map = deviceStockService.calcCommodityDiffByType(gravityCloseDto.getCommodityVoList(), deviceInfo, memberInfo);

        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList"); // 下架的商品
        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList"); // 上架的商品

        int result = gravityCloseDto.getStockTotalWeight().compareTo(gravityCloseDto.getTotalWeight());
        if (0 == result) {  //库存重力等于盘货重力    -->重力不变
            if ((!subVoList.isEmpty()) || (subVoList.isEmpty() && addVoList.isEmpty())) {
                //视觉减少  || 视觉不变     --> 不出订单
            } else if (!addVoList.isEmpty()) {
                //视觉变多,出警报
            }
        } else if (result < 0) { //库存重力小于盘货重力  -->重力变多
            //出警报
        } else {  //库存重力大于盘货重力  -->重力变少

        }


        List<ReplenishRealTimeCommodityResult> subCommodityList = new ArrayList<>();
        List<ReplenishRealTimeCommodityResult> addCommodityList = new ArrayList<>();
        List<ReplenishRealTimeCommodityResult> stockCommodityList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(subVoList)) {    // 组装下架商品集合
            ReplenishRealTimeCommodityResult repSub = null;
            for (CommodityDiffVo comSub : subVoList) {
                repSub = new ReplenishRealTimeCommodityResult();
                repSub.setScommodityId(comSub.getScommodityId());
                repSub.setNumber(comSub.getNumber());
                repSub.setScommodityCode(comSub.getScommodityCode());
                repSub.setScommodityName(comSub.getScommodityName());
                repSub.setScommodityFullName(comSub.getScommodityFullName());
                repSub.setScommodityUrl(comSub.getScommodityImg());
                subCommodityList.add(repSub);
            }
        }

        if (CollectionUtils.isNotEmpty(addVoList)) {    // 组装上架商品集合
            ReplenishRealTimeCommodityResult repAdd = null;
            for (CommodityDiffVo comAdd : addVoList) {
                repAdd = new ReplenishRealTimeCommodityResult();
                repAdd.setScommodityId(comAdd.getScommodityId());
                repAdd.setScommodityName(comAdd.getScommodityName());
                repAdd.setScommodityFullName(comAdd.getScommodityFullName());
                repAdd.setScommodityCode(comAdd.getScommodityCode());
                repAdd.setNumber(comAdd.getNumber());
                repAdd.setScommodityUrl(comAdd.getScommodityImg());
                addCommodityList.add(repAdd);
            }
        }

        // 组装补货之前库存商品集合
        List<StockVo> stockVoList = deviceStockService.selectStockByDeviceId(gravityCloseDto.getDeviceId());
        if (CollectionUtils.isNotEmpty(stockVoList)) {
            ReplenishRealTimeCommodityResult repStock = null;
            for (StockVo stock : stockVoList) {
                repStock = new ReplenishRealTimeCommodityResult();
                repStock.setScommodityId(stock.getId());
                repStock.setScommodityCode(stock.getScode());
                repStock.setScommodityName(stock.getSname());
                repStock.setScommodityFullName(stock.getSbrandName() + stock.getSname() + stock.getStaste() + stock.getIspecWeight() + stock.getSspecUnit() + "/" + stock.getSpackageUnit());
                repStock.setNumber(stock.getIstock());
                repStock.setScommodityUrl(stock.getScommodityImg());
                stockCommodityList.add(repStock);
            }
        }

        replenishRealTimeOrderResult.setShelfCommoditys(addCommodityList);
        replenishRealTimeOrderResult.setDropOffCommoditys(subCommodityList);
        replenishRealTimeOrderResult.setResults(stockCommodityList);
        responseVo.setData(replenishRealTimeOrderResult);
        return responseVo;
    }*/

    /**
     * 根据商品差创建实时订单
     *
     * @param orderDto
     * @return
     */
    @Override
    public ResponseVo<RealTimeOrderResult> createRealTimeOrderByCommodityDiff(RealTimeCommodityDiffOrderDto orderDto) throws Exception {
        String deviceId = orderDto.getDeviceId();
        String memberId = orderDto.getMemberId();
        Integer isourceClientType = orderDto.getIsourceClientType();
        List<InventoryCommodityDiffVo> commodityDiffVoList = orderDto.getInventoryCommodityDiffVoList();

        ResponseVo<RealTimeOrderResult> responseVo = ResponseVo.getSuccessResponse();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(deviceId);
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", orderDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(memberId);
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }
        //1、库存对比 计算商品差
        Map<String, Object> map = deviceStockService.calcIncrementsAndDecrements(commodityDiffVoList, deviceInfo, memberInfo);
//        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList");
        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");

        RealTimeOrderResult result = null;
        if (null != subVoList && subVoList.size() > 0) {
            result = new RealTimeOrderResult();
            logger.info("开始组装订单优惠参数");
            OrderDiscountInfoDto orderDiscountInfoDto = new OrderDiscountInfoDto();
            orderDiscountInfoDto.setId(memberInfo.getId());
            orderDiscountInfoDto.setScode(memberInfo.getScode());
            orderDiscountInfoDto.setSmemberName(memberInfo.getSmemberName());
            orderDiscountInfoDto.setSdeviceCode(deviceInfo.getScode());
            List<CommodityDiscountDto> commodityDiscountDtoList = new ArrayList<>();
            for (CommodityDiffVo diffVo : subVoList) {
                CommodityDiscountDto commodityDiscountDto = new CommodityDiscountDto();
                BeanUtils.copyProperties(commodityDiscountDto, diffVo);//复制对象
                commodityDiscountDto.setForderCount(diffVo.getNumber());//订单商品数量
                commodityDiscountDtoList.add(commodityDiscountDto);
            }
            orderDiscountInfoDto.setCommodityDisList(commodityDiscountDtoList);
            logger.info("组装订单优惠参数完成 :" + orderDiscountInfoDto);
            ResponseVo<OrderDiscountInfoResult> resultResponseVo = calculateOrderDiscount(orderDiscountInfoDto);
            if (resultResponseVo.isSuccess()) {//创建优惠成功
                OrderDiscountInfoResult discountInfoResult = resultResponseVo.getData();
                BeanUtils.copyProperties(result, discountInfoResult);//复制对象
                List<RealTimeCommodityResult> results = new ArrayList<RealTimeCommodityResult>();
                RealTimeCommodityResult commodityResult = null;
                for (CommodityDiscountDto dto : discountInfoResult.getCommodityDisList()) {
                    commodityResult = new RealTimeCommodityResult();
                    BeanUtils.copyProperties(commodityResult, dto);//复制对象
                    commodityResult.setNumber(dto.getForderCount());//订单商品数量
                    results.add(commodityResult);
                }
                result.setResults(results);
            } else {
                //计算优惠失败  返回数据
                BigDecimal totalAmount = new BigDecimal("0");
                List<RealTimeCommodityResult> results = new ArrayList<RealTimeCommodityResult>();
                RealTimeCommodityResult commodityResult = null;
                for (CommodityDiffVo diffVo : subVoList) {
                    commodityResult = new RealTimeCommodityResult();
                    BeanUtils.copyProperties(commodityResult, diffVo);//复制对象
                    results.add(commodityResult);
                    totalAmount = totalAmount.add((commodityResult.getFcommodityPrice().multiply(new BigDecimal(commodityResult.getNumber()))));//计算总金额
                }
                result.setFactualPayAmount(totalAmount);
                result.setFcouponDeductionAmount(BigDecimal.ZERO);
                result.setFdiscountAmount(BigDecimal.ZERO);
                result.setFfirstDiscountAmount(BigDecimal.ZERO);
                result.setFotherDiscountAmount(BigDecimal.ZERO);
                result.setFpointDiscountAmount(BigDecimal.ZERO);
                result.setFpromotionDiscountAmount(BigDecimal.ZERO);
                result.setFtotalAmount(totalAmount);
                result.setResults(results);
            }
        }
        responseVo.setData(result);
        return responseVo;
    }

    /**
     * 根据商品差创建实时补货单服务
     *
     * @param orderDto
     * @return
     */
    @Override
    public ResponseVo<ReplenishRealTimeOrderResult> createReplenishRealTimeOrderByCommodityDiff(RealTimeCommodityDiffOrderDto orderDto) {

        ResponseVo<ReplenishRealTimeOrderResult> responseVo = ResponseVo.getSuccessResponse();
        ReplenishRealTimeOrderResult replenishRealTimeOrderResult = new ReplenishRealTimeOrderResult();
        List<InventoryCommodityDiffVo> commodityDiffVoList = orderDto.getInventoryCommodityDiffVoList();
        //验证参数数据
        DeviceInfo deviceInfo = deviceInfoService.selectByPrimaryKey(orderDto.getDeviceId());
        if (null == deviceInfo || deviceInfo.getIstatus().intValue() != 20 ||
                deviceInfo.getIoperateStatus().intValue() == 20) {
            logger.error("设备数据异常，设备ID：{}", orderDto.getDeviceId());
            logger.error("设备数据异常，设备数据：{}", deviceInfo);
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，设备数据异常");
        }
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderDto.getMemberId());
        if (null == memberInfo || !memberInfo.getSmerchantId().equals(deviceInfo.getSmerchantId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("系统错误，会员数据异常");
        }

        // 库存对比 计算商品差（上架商品与下架商品）
        Map<String, Object> map = deviceStockService.calcIncrementsAndDecrements(commodityDiffVoList, deviceInfo, memberInfo);
        List<CommodityDiffVo> addVoList = (List<CommodityDiffVo>) map.get("addVoList");
        List<CommodityDiffVo> subVoList = (List<CommodityDiffVo>) map.get("subVoList");

        List<ReplenishRealTimeCommodityResult> subCommodityList = new ArrayList<>();
        List<ReplenishRealTimeCommodityResult> addCommodityList = new ArrayList<>();
        List<ReplenishRealTimeCommodityResult> stockCommodityList = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(subVoList)) {    // 组装下架商品集合
            ReplenishRealTimeCommodityResult repSub = null;
            for (CommodityDiffVo comSub : subVoList) {
                repSub = new ReplenishRealTimeCommodityResult();
                repSub.setScommodityId(comSub.getScommodityId());
                repSub.setNumber(comSub.getNumber());
                repSub.setScommodityCode(comSub.getScommodityCode());
                repSub.setScommodityName(comSub.getScommodityName());
                repSub.setScommodityFullName(comSub.getScommodityFullName());
                repSub.setScommodityUrl(comSub.getScommodityImg());
                subCommodityList.add(repSub);
            }
        }

        if (CollectionUtils.isNotEmpty(addVoList)) {    // 组装上架商品集合
            ReplenishRealTimeCommodityResult repAdd = null;
            for (CommodityDiffVo comAdd : addVoList) {
                repAdd = new ReplenishRealTimeCommodityResult();
                repAdd.setScommodityId(comAdd.getScommodityId());
                repAdd.setScommodityName(comAdd.getScommodityName());
                repAdd.setScommodityFullName(comAdd.getScommodityFullName());
                repAdd.setScommodityCode(comAdd.getScommodityCode());
                repAdd.setNumber(comAdd.getNumber());
                repAdd.setScommodityUrl(comAdd.getScommodityImg());
                addCommodityList.add(repAdd);
            }
        }

        // 组装补货之前库存商品集合
        List<StockVo> stockVoList = deviceStockService.selectStockByDeviceId(orderDto.getDeviceId());
        if (CollectionUtils.isNotEmpty(stockVoList)) {
            ReplenishRealTimeCommodityResult repStock = null;
            for (StockVo stock : stockVoList) {
                repStock = new ReplenishRealTimeCommodityResult();
                repStock.setScommodityId(stock.getId());
                repStock.setScommodityCode(stock.getScode());
                repStock.setScommodityName(stock.getSname());
                repStock.setScommodityFullName(stock.getSbrandName() + stock.getSname() + stock.getStaste() + stock.getIspecWeight() + stock.getSspecUnit() + "/" + stock.getSpackageUnit());
                repStock.setNumber(stock.getIstock());
                repStock.setScommodityUrl(stock.getScommodityImg());
                stockCommodityList.add(repStock);
            }
        }

        replenishRealTimeOrderResult.setShelfCommoditys(addCommodityList);
        replenishRealTimeOrderResult.setDropOffCommoditys(subCommodityList);
        replenishRealTimeOrderResult.setResults(stockCommodityList);
        responseVo.setData(replenishRealTimeOrderResult);
        logger.debug("生产补货单完成");
        return responseVo;
    }

    /**
     * 生成审核订单
     *
     * @param exceptionOrderDto 商品订单表
     * @return ResponseVo
     */
    public ResponseVo generateExceptionOrder(ExceptionOrderDto exceptionOrderDto) {
        //生成审核订单主表
        OrderAudit orderAudit = new OrderAudit();
        try {
            BeanUtils.copyProperties(orderAudit, exceptionOrderDto);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("OrderAudit对象转换失败：{}", e);
            throw new ServiceException("OrderAudit审核订单主表对象转换失败!");
        }
        String sorderCode = CoreUtils.newCode(EntityTables.OM_ORDER_AUDIT);//审核订单编号
        orderAudit.setSorderCode(sorderCode);
        orderAudit.setTaddTime(DateUtils.getCurrentDateTime());
        orderAudit.setTupdateTime(DateUtils.getCurrentDateTime());
        orderAudit.setIdelFlag(0);
        orderAudit.setIstatus(10);//待处理
        orderAudit.setIversion(1);
        orderAudit.setShandlerRemark(exceptionOrderDto.getShandlerRemark());
        orderAuditService.insert(orderAudit);
        logger.info("生成审核订单主表成功,主表Id:", orderAudit.getId());
        List<ExceptionOrderCommodityDto> exceptionOrderCommodityDtoList = exceptionOrderDto.getExceptionOrderCommodityDtoList();
        if (null != exceptionOrderCommodityDtoList) {
            //生成审核订单从表
            for (ExceptionOrderCommodityDto exceptionOrderCommodityDto : exceptionOrderCommodityDtoList) {
                OrderAuditCommodity orderAuditCommodity = new OrderAuditCommodity();
                try {
                    BeanUtils.copyProperties(orderAuditCommodity, exceptionOrderCommodityDto);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    logger.error("OrderAuditCommodity审核订单从表对象转换失败：{}", e);
                    throw new ServiceException("OrderAuditCommodity审核订单从表对象转换失败!");
                }
                orderAuditCommodity.setSorderId(orderAudit.getId());
                orderAuditCommodity.setSorderCode(orderAudit.getSorderCode());
                orderAuditCommodity.setTaddTime(DateUtils.getCurrentDateTime());
                orderAuditCommodity.setTupdateTime(DateUtils.getCurrentDateTime());
                orderAuditCommodityService.insert(orderAuditCommodity);
            }
        }
        //异步解约单次免密
        //获取用户的支付宝免密数据
        MemberInfo memberInfo = memberInfoService.selectByPrimaryKey(orderAudit.getSmemberId());
        FreeData freeData = freeDataService.selectByMemberId(memberInfo.getId(), memberInfo.getSmerchantId());
        if (null != freeData && StringUtils.isNotBlank(freeData.getSproductCode()) && "ONE_TIME_AUTH_P".equals(freeData.getSproductCode())) {
            rescissionFree(memberInfo);
        }
        return ResponseVo.getSuccessResponse(orderAudit.getId());
    }

    public static void rescissionFree(final MemberInfo memberInfo) {

        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    UnsignDto unsignDto = new UnsignDto();
                    unsignDto.setSmerchantCode(memberInfo.getSmerchantCode());
                    unsignDto.setSmemberId(memberInfo.getId());
                    unsignDto.setSmemberMerchantCode(memberInfo.getSmerchantCode());
                    RestServiceInvoker invoke = RestServiceInvokeBuilder.newBuilder().newInvoker(FreeServicesDefine.ALIPAY_UNSIGN);// 服务名称
                    // 返回对象中含有泛型，则需要设置返回类型，否则无需设置
                    invoke.setParameterizedTypeReference(new ParameterizedTypeReference<ResponseVo<String>>() {
                    });
                    invoke.setRequestObj(unsignDto); // post 参数
                    ResponseVo<String> resVO = (ResponseVo<String>) invoke.invoke();
                } catch (Exception e) {
                    logger.error("购物生成异常订单异步解约会员单词代扣异常：{}", memberInfo.getScode());
                }
            }
        });
    }


}
