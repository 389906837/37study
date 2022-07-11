package com.cloud.cang.pay.ws;



import com.cloud.cang.core.common.RequestUtils;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.model.sh.MerchantConf;
import com.cloud.cang.pay.hy.service.MemberInfoService;
import com.cloud.cang.pay.om.service.OrderRecordService;
import com.cloud.cang.pay.sh.service.MerchantInfoService;
import com.cloud.cang.pay.wechat.common.Signature;
import com.cloud.cang.pay.wechat.common.XMLParser;
import com.cloud.cang.pay.wechat.notify.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;


@Controller
@RequestMapping("/wechatCallback")
public class WechatCallBackController {
	@Autowired
	private MerchantInfoService merchantInfoService;
	@Autowired
	private MemberInfoService memberInfoService;
	@Autowired
	private OrderRecordService orderRecordService;
	private static final Logger logger = LoggerFactory.getLogger(WechatCallBackController.class);
	/**
	 * 微信网关通知地址
	 * @param request
	 */
	@ResponseBody
	@RequestMapping(value = "/gateway")
	public String gateway(HttpServletRequest request) {
		logger.info("微信网关回调开始...");
		ResponseData responseData = new ResponseData();
		try {
			//1. 解析请求参数
			String paramsXml = getCallbackParamsByXml(request);
			logger.info("微信网关回调开始请求串:{}", paramsXml.toString());
			//获取商户配置信息
			final MerchantConf conf = merchantInfoService.getDefaultWechatMerchantConf();
			final String sip = RequestUtils.getIpAddr(request);
			/*if(Signature.checkIsSignValidFromResponseString(paramsXml, conf)) {
				UnsignNotifyTemplate unsignNotifyTemplate = new UnsignNotifyTemplate(paramsXml);
				return unsignNotifyTemplate.execute(new UnsignSuccessCallBack() {
					@Override
					public void onSuccess(UnsignNotifyData unsignNotifyData) throws Exception {
						logger.info("微信端解约回调成功：{}", unsignNotifyData);
						//处理付款回调通知
						memberInfoService.dealwithWechatUnsign(sip, unsignNotifyData, conf);
					}
				});
			} else {
				throw new ServiceException("签名异常");
			}*/
			UnsignNotifyTemplate unsignNotifyTemplate = new UnsignNotifyTemplate(paramsXml);
			return unsignNotifyTemplate.execute(new UnsignSuccessCallBack() {
				@Override
				public void onSuccess(UnsignNotifyData unsignNotifyData) throws Exception {
					logger.info("微信端解约回调成功：{}", unsignNotifyData);
					//处理付款回调通知
					memberInfoService.dealwithWechatUnsign(sip, unsignNotifyData, conf);
				}
			});
		} catch (Exception exception) {
			logger.error("网关回调处理异常：{}",exception);
			responseData.setReturn_code("FAIL");
			responseData.setReturn_msg("网关回调处理异常");
		}
		return XMLParser.toXML(responseData);
	}

	/***
	 * 微信签约成功回调通知
	 * @param request
	 */
	@ResponseBody
	@RequestMapping("/signNotify")
	public String signNotify(HttpServletRequest request) {
		logger.info("微信签约成功回调通知开始...");
		ResponseData responseData = new ResponseData();
		try {
			//1. 解析请求参数
			String paramsXml = getCallbackParamsByXml(request);
			logger.info("微信签约成功回调请求串:{}", paramsXml.toString());
			final String sip = RequestUtils.getIpAddr(request);
			//获取商户配置信息
			final MerchantConf conf = merchantInfoService.getDefaultWechatMerchantConf();
			/*if(Signature.checkIsSignValidFromResponseString(paramsXml, conf)) {
				SignNotifyTemplate signNotify = new SignNotifyTemplate(paramsXml);
				return signNotify.execute(new SignSuccessCallBack() {
					@Override
					public void onSuccess(SignNotifyData signNotifyData) throws Exception {
						logger.info("微信签约成功回调参数：{}", signNotifyData);
						//处理微信签约回调通知
						memberInfoService.dealwithWechatSign(signNotifyData, conf, sip);
					}
				});
			} else {
				throw new ServiceException("签名异常");
			}*/
			SignNotifyTemplate signNotify = new SignNotifyTemplate(paramsXml);
			return signNotify.execute(new SignSuccessCallBack() {
				@Override
				public void onSuccess(SignNotifyData signNotifyData) throws Exception {
					logger.info("微信签约成功回调参数：{}", signNotifyData);
					//处理微信签约回调通知
					memberInfoService.dealwithWechatSign(signNotifyData, conf, sip);
				}
			});
		} catch (Exception exception) {
			logger.error("微信签约成功回调处理异常：{}",exception);
			responseData.setReturn_code("FAIL");
			responseData.setReturn_msg("微信签约成功回调处理异常");
		}
		return XMLParser.toXML(responseData);
	}


	@ResponseBody
	@RequestMapping(value = "paymentNotify")
	public String paymentNotify(HttpServletRequest request) {
		ResponseData responseData = new ResponseData();
		try {
			//1. 解析请求参数
			String paramsXml = getCallbackParamsByXml(request);
			logger.info("微信手动支付成功回调请求串:{}", paramsXml.toString());
			//获取商户配置信息
			final MerchantConf conf = merchantInfoService.getDefaultWechatMerchantConf();
			PayNotifyTemplate payNotify = new PayNotifyTemplate(paramsXml);
			return payNotify.execute(new PaySuccessCallBack() {
				@Override
				public void onSuccess(PayNotifyData payNotifyData) throws Exception {
					logger.info("手动付款回调成功：{}", payNotifyData);
					//处理付款回调通知
					orderRecordService.dealwithWechatPaymentNotify(conf, payNotifyData);
				}
			});
			/*if(Signature.checkIsSignValidFromResponseString(paramsXml, conf)) {
				PayNotifyTemplate payNotify = new PayNotifyTemplate(paramsXml);
				return payNotify.execute(new PaySuccessCallBack() {
					@Override
					public void onSuccess(PayNotifyData payNotifyData) throws Exception {
						logger.info("手动付款回调成功：{}", payNotifyData);
						//处理付款回调通知
						orderRecordService.dealwithWechatPaymentNotify(conf, payNotifyData);
					}
				});
			} else {
				throw new ServiceException("手动付款签名异常");
			}*/
		} catch (ServiceException e) {
			logger.error("手动付款通知异常：{}", e);
			responseData.setReturn_code("FAIL");
			responseData.setReturn_msg(e.getMessage());
		} catch (Exception e) {
			logger.error("微信手动付款通知异常：{}", e);
			responseData.setReturn_code("FAIL");
			responseData.setReturn_msg("微信手动付款通知异常");
		}
	    return XMLParser.toXML(responseData);
	}


	@ResponseBody
	@RequestMapping(value = "freePaymentNotify")
	public String freePaymentNotify(HttpServletRequest request) {
		ResponseData responseData = new ResponseData();
		try {
			//1. 解析请求参数
			String paramsXml = getCallbackParamsByXml(request);
			logger.info("微信免密支付成功回调请求串:{}", paramsXml.toString());
			//获取商户配置信息
			final MerchantConf conf = merchantInfoService.getDefaultWechatMerchantConf();
			/*if(Signature.checkIsSignValidFromResponseString(paramsXml, conf)) {
				FreePayNotifyTemplate payNotify = new FreePayNotifyTemplate(paramsXml);
				return payNotify.execute(new FreePaySuccessCallBack() {
					@Override
					public void onSuccess(FreePayNotifyData freePayNotifyData) throws Exception {
						logger.info("免密付款回调成功：{}", freePayNotifyData);
						//处理付款回调通知
						orderRecordService.dealwithWechatFreePaymentNotify(conf, freePayNotifyData);
					}
				});
			} else {
				throw new ServiceException("免密付款签名异常");
			}*/
			FreePayNotifyTemplate payNotify = new FreePayNotifyTemplate(paramsXml);
			return payNotify.execute(new FreePaySuccessCallBack() {
				@Override
				public void onSuccess(FreePayNotifyData freePayNotifyData) throws Exception {
					logger.info("免密付款回调成功：{}", freePayNotifyData);
					//处理付款回调通知
					orderRecordService.dealwithWechatFreePaymentNotify(conf, freePayNotifyData);
				}
			});
		} catch (ServiceException e) {
			logger.error("免密付款通知异常：{}", e);
			responseData.setReturn_code("FAIL");
			responseData.setReturn_msg(e.getMessage());
		} catch (Exception e) {
			logger.error("微信免密付款通知异常：{}", e);
			responseData.setReturn_code("FAIL");
			responseData.setReturn_msg("微信免密付款通知异常");
		}
		return XMLParser.toXML(responseData);
	}
	/**
	 * 获取请求参数
	 * @param @param request
	 * @param @throws Exception
	 * @return Map<String,String>
	 * @throws
	 */
	public String getCallbackParamsByXml(HttpServletRequest request)
			throws Exception {
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		inStream.close();
		String result = new String(outSteam.toByteArray(), "utf-8");
		logger.debug("参数接口成功：{}", result);
		return result;
	}


}
