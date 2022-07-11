package com.cloud.cang.pay.ws;

import com.alipay.api.internal.util.AlipaySignature;
import com.cloud.cang.core.common.AlipayConfigure;
import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.hy.service.MemberInfoService;
import com.cloud.cang.pay.om.service.OrderRecordService;
import com.cloud.cang.pay.sh.service.MerchantInfoService;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * 支付宝异步通知类
 * Created by zhouhong on 2017/11/9.
 */
@Controller
@RequestMapping("/alipayCallback")
public class AliPayCallBackController {

    @Autowired
    private MerchantInfoService merchantInfoService;
    @Autowired
    private OrderRecordService orderRecordService;
    @Autowired
    private MemberInfoService memberInfoService;


    private static final Logger logger = LoggerFactory.getLogger(AliPayCallBackController.class);

    /**
     * 支付宝通知网关地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/gateway")
    public String gateway(HttpServletRequest request) {
        logger.info("支付宝网关回调开始...");
        try {
            //1. 解析请求参数
            Map<String, String> params = getAlipayRequestParams(request);
            //打印本次请求日志，开发者自行决定是否需要
            logger.info("支付宝网关回调开始请求串:{}", params.toString());
            //获取商户配置信息
            MerchantConf conf = merchantInfoService.getDefaultAlipayMerchantConf();
            //2. 验证签名
            AlipaySignature.rsaCheckV1(params, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
            logger.info("网关回调返回数据：{}", params);
            //执行业务逻辑
            String notifyType = params.get("notify_type");
            if (StringUtil.isNotBlank(notifyType)) {
                params.put("sip", RequestUtils.getIpAddr(request));
                if (notifyType.equals("dut_user_sign")) {//网关通知免密签约
                    memberInfoService.dealwithAlipaySign(conf.getSmerchantCode(), params, conf);
                } else if (notifyType.equals("dut_user_unsign")) {//支付宝端免密协议解约
                    memberInfoService.dealwithAlipayUnsign(conf.getSmerchantCode(), params, conf);
                }
            }
            return "success";
        } catch (Exception exception) {
            logger.error("网关回调处理异常：{}",exception);
        }
        return "false";
    }

    /**
     * 支付宝手动支付通知地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/paymentNotify")
    public String paymentNotify(HttpServletRequest request) {
        //获取支付宝返回的信息
        try {
            logger.info("支付宝支付结果通知回调。。。");
            Map<String, String> map = getAlipayRequestParams(request);
            logger.debug("获得的参数="+ map);
            //获取商户配置信息
            //MerchantConf conf = merchantInfoService.getAlipayMerchantConf(merchantCode, 2);
            MerchantConf conf = merchantInfoService.getDefaultAlipayMerchantConf();
            //验证签名
            boolean flag = AlipaySignature.rsaCheckV1(map, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
            if (flag) {//验证签名成功
                logger.info("支付宝支付回调成功：{}", map);
                orderRecordService.dealwithAlipayPaymentNotify(conf, map);
                return "success";
            }
        } catch (Exception e) {
            logger.error("回调订单处理异常：{}",e);
        }
        return "false";
    }
    /**
     * 支付宝免密支付通知地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/freePaymentNotify")
    public String freePaymentNotify(HttpServletRequest request) {
        //获取支付宝返回的信息
        try {
            logger.info("支付宝免密支付结果通知回调。。。");
            Map<String, String> map = getAlipayRequestParams(request);
            logger.debug("获得的参数="+ map);
            //获取商户配置信息
            MerchantConf conf = merchantInfoService.getDefaultAlipayMerchantConf();
            //验证签名
            boolean flag = AlipaySignature.rsaCheckV1(map, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
            if (flag) {//验证签名成功
                logger.info("支付宝免密支付回调成功：{}", map);
                orderRecordService.dealwithAlipayFreePaymentNotify(conf, map);
                return "success";
            }
        } catch (Exception e) {
            logger.error("回调付款订单处理异常：{}",e);
        }
        return "false";
    }

    /**
     * 支付宝免密支付并签约通知地址
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/freePaymentAndSignNotify")
    public String freePaymentAndSignNotify(HttpServletRequest request) {
        //获取支付宝返回的信息
        try {
            logger.info("支付宝免密支付并签约结果通知回调。。。");
            Map<String, String> map = getAlipayRequestParams(request);
            logger.debug("获得的参数="+ map);
            //获取商户配置信息
            MerchantConf conf = merchantInfoService.getDefaultAlipayMerchantConf();
            //验证签名
            boolean flag = AlipaySignature.rsaCheckV1(map, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
            if (flag) {//验证签名成功
                logger.info("支付宝免密支付并签约回调成功：{}", map);
                orderRecordService.dealwithAlipayFreePaymentAndSignNotify(conf, map);
                return "success";
            }
        } catch (Exception e) {
            logger.error("回调付款订单并签约处理异常：{}",e);
        }
        return "false";
    }
    /***
     * 支付宝签约成功回调通知
     * @param request
     */
    @ResponseBody
    @RequestMapping("/signNotify")
    public String signNotify(HttpServletRequest request) {
        logger.info("支付宝免密签约回调开始...");
        try {
            Map<String, String> map = getAlipayRequestParams(request);
            logger.debug("获得的参数=" + map);
            //获取商户配置信息
            MerchantConf conf = merchantInfoService.getDefaultAlipayMerchantConf();
            //验证签名
            boolean flag = AlipaySignature.rsaCheckV1(map, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
            if (flag) {//验证签名成功
                logger.info("支付宝签约回调成功：{}", map);
                map.put("sip", RequestUtils.getIpAddr(request));
                memberInfoService.dealwithAlipaySign(conf.getSmerchantCode(), map, conf);


                return "success";
            }
            logger.error("支付宝验证签名失败");
        } catch (Exception e) {
            logger.error("支付宝签约回调异常：{}", e);
        }
        return "false";
    }

    /***
     * 支付宝签约成功回调通知
     * @param request
     */
    @ResponseBody
    @RequestMapping("/unsignNotify")
    public String unsignNotify(HttpServletRequest request) {
        logger.info("支付宝免密解约回调开始...");
        try {
            Map<String, String> map = getAlipayRequestParams(request);
            logger.debug("获得的参数=" + map);
            //获取商户配置信息
            MerchantConf conf = merchantInfoService.getDefaultAlipayMerchantConf();
            //验证签名
            boolean flag = AlipaySignature.rsaCheckV1(map, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
            if (flag) {//验证签名成功
                logger.info("支付宝解约回调成功：{}", map);
                map.put("sip", RequestUtils.getIpAddr(request));
                memberInfoService.dealwithAlipayUnsign(conf.getSmerchantCode(), map, conf);
                return "success";
            }
            logger.error("支付宝验证签名失败");
        } catch (Exception e) {
            logger.error("支付宝签约回调异常：{}", e);
        }
        return "false";
    }

    /***
     * 支付宝交易订单关闭回调通知
     * @param request
     */
    @ResponseBody
    @RequestMapping("/closePaymentNotify")
    public String closePaymentNotify(HttpServletRequest request) {
        logger.info("支付宝交易订单关闭回调开始...");
        try {
            Map<String, String> map = getAlipayRequestParams(request);
            logger.debug("获得的参数=" + map);
            //获取商户配置信息
            MerchantConf conf = merchantInfoService.getDefaultAlipayMerchantConf();
            //验证签名
            boolean flag = AlipaySignature.rsaCheckV1(map, conf.getSpublicKey(), AlipayConfigure.charset, AlipayConfigure.sign_type);
            if (flag) {//验证签名成功
                logger.info("支付宝交易订单关闭回调成功：{}", map);
                memberInfoService.dealwithAlipayClosePayment(conf.getSmerchantCode(), map, conf);
                return "success";
            }
            logger.error("支付宝验证签名失败");
        } catch (Exception e) {
            logger.error("支付宝交易订单关闭回调异常：{}", e);
        }
        return "false";
    }

    private Map<String, String> getAlipayRequestParams(HttpServletRequest request) {
        Map<String, String> map = new HashMap<String, String>();
        // 获取组装参数
        Map paramsMap = request.getParameterMap();
        for (Iterator iter = paramsMap.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) paramsMap.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            map.put(name,valueStr);
        }
        return map;
    }

}
