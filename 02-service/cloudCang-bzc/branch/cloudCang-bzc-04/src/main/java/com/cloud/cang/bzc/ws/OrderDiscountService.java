package com.cloud.cang.bzc.ws;

import com.cloud.cang.bzc.common.PriceUtil;
import com.cloud.cang.bzc.om.service.OrderRecordService;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.dispatcher.annotation.RegisterRestResource;
import com.cloud.cang.jy.CommodityDiscountDto;
import com.cloud.cang.jy.OrderDiscountInfoDto;
import com.cloud.cang.jy.OrderDiscountInfoResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @version 1.0
 * @ClassName: OrderDiscountService
 * @Description: 订单服务
 * @Author: zengzexiong
 * @Date: 2017年12月28日17:14:12
 */
@RestController
@RequestMapping("/orderDiscountService")
@RegisterRestResource
public class OrderDiscountService {
    private static final Logger logger = LoggerFactory.getLogger(OrderDiscountService.class);

    @Autowired
    private OrderRecordService orderDiscountCalcService;

    /**
     * 订单优惠服务
     * @param orderDisDto 订单参数
     * @return 订单优惠计算结果
     */
    @SuppressWarnings({"rawtypes", "unchecked", "static-access"})
    @RequestMapping(value = "/orderDiscount", method = RequestMethod.POST)
    public ResponseVo<OrderDiscountInfoResult> orderDiscount(@RequestBody OrderDiscountInfoDto orderDisDto) {
        logger.debug("生成订单优惠服务开始...");
        ResponseVo<OrderDiscountInfoResult> responseVo = ResponseVo.getSuccessResponse();
        try {
            //校验基础参数
            ResponseVo<String> validateResult = validateCreateParam(orderDisDto);
            if (!validateResult.isSuccess()) {
                logger.error("生成订单优惠参数校验失败：{}", validateResult.getMsg());
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo(validateResult.getMsg());
            }

            //返回订单优惠计算结果
            ResponseVo<OrderDiscountInfoResult> orderDiscount = orderDiscountCalcService.calculateOrderDiscount(orderDisDto);
            return orderDiscount;
        } catch (Exception e) {
            responseVo.setSuccess(false);
            responseVo.setErrorCode(-1000);
            responseVo.setMsg("生成订单优惠服务异常");
            logger.error("生成订单优惠服务异常：", e);
        }

        return responseVo;
    }

    /**
     * 验证订单参数是否有效
     * @param orderDisDto 订单参数
     * @return 成功返回success，否则返回false
     */
    private ResponseVo<String> validateCreateParam(OrderDiscountInfoDto orderDisDto) {
        logger.debug("创建订单校验参数开始.....参数：{}", orderDisDto);
        if (StringUtils.isBlank(orderDisDto.getId())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户ID不能为空");
        }
        if (StringUtils.isBlank(orderDisDto.getScode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户编号不能为空");
        }
        if (StringUtils.isBlank(orderDisDto.getSmemberName())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("用户名（手机号）不能为空");
        }
        if (StringUtils.isBlank(orderDisDto.getSdeviceCode())) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("设备编号不能为空");
        }

        //校验订单中的商品参数
        List<CommodityDiscountDto> commodityList = orderDisDto.getCommodityDisList();
        if (CollectionUtils.isEmpty(commodityList)) {
            return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("订单中商品列表项不能为空");
        }
        for (CommodityDiscountDto p : commodityList) {
            if (StringUtils.isBlank(p.getScommodityCode())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品编号不能为空");
            }
            if (StringUtils.isBlank(p.getSsmallCategoryId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品小类ID不能为空");
            }
            if (StringUtils.isBlank(p.getSbrandId())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品商品品牌ID不能为空");
            }
            if (0 >= p.getForderCount().intValue()) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品数量应该大于0");
            }
            if (null == p.getFcommodityPrice() || PriceUtil.ltOrEqZero(p.getFcommodityPrice())) {
                return ErrorCodeEnum.ERROR_COMMON_PARAM.getResponseVo("商品价格不能为空或小于等于0");
            }
        }
        logger.debug("订单优惠校验参数成功.....");
        return ResponseVo.getSuccessResponse();

    }
}
