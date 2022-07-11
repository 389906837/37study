/**
 * 
 */
package com.cloud.cang.message.sms.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.message.common.utli.http.HttpClientResult;
import com.cloud.cang.message.common.utli.http.HttpClientUtils;
import com.cloud.cang.message.exception.SMSException;
import com.cloud.cang.message.sms.ISmsSender;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import com.cloud.cang.model.xx.SupplierInfo;
import com.ctc.smscloud.json.JSONHttpClient;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author zhouhong
 *
 */
@Service
public class TwoFactorSmsSender implements ISmsSender {
	private static Logger LOGGER = LoggerFactory.getLogger(TwoFactorSmsSender.class);
	@Autowired
    private SupplierInfoService supplierInfoService;
    private Lock lock = new ReentrantLock();
	private static final String SENDER_ID="VNDSTP";
	private static final String TEMPLATE_NAME = "SMSTemplateOTP1";
	private static final String  INTERFACE_URL="http://2factor.in/API/V1/{}/ADDON_SERVICES/SEND/TSMS";
	private static final String  INTERFACE_BALANCE_URL="http://2factor.in/API/V1/{}/ADDON_SERVICES/BAL/TRANSACTIONAL_SMS";

	@Override
	public String send(String mobile, String content) throws SMSException {
		if (StringUtils.isBlank(mobile)) {
		    throw new SMSException("手机号不能为空");
		}
		try {
		    return sendMessage(content, mobile, 1);
		} catch (Exception e) {
		    LOGGER.error("", e);
		    throw new SMSException(e);
		}
	}

	@Override
	public String batchSendSameContent(List<String> mobiles, String content)
			throws SMSException {
		if (mobiles == null || mobiles.size() == 0) {
		    throw new SMSException("手机号不能为空");
		}
		//返回信息
		StringBuffer returnStr = new StringBuffer();
		try {
		    lock.lock();
		    List<String> lockMobiles = mobiles.subList(0, mobiles.size());
		    // 当前记录数
		    int currentCount = 0;
		    StringBuilder sbStr = new StringBuilder(1500);
		    // 总记录数
		    int totalCount = 0;
		    for (int i = 0; i < lockMobiles.size(); i++) {
				currentCount++;
				totalCount++;
				if (currentCount == 100
					|| (currentCount < 100 && totalCount == lockMobiles.size())) {
					sbStr.append(lockMobiles.get(totalCount - 1)+",");
					if (sbStr != null && sbStr.toString().endsWith(",")) {
					String mobileStr = sbStr.substring(0,
						sbStr.length() - 1);
					String rtnValue = sendMessage(content,mobileStr, currentCount);
					returnStr.append(rtnValue+"|");

					}
					currentCount = 0;
					sbStr.setLength(0);
					sbStr = new StringBuilder(1500);
				} else {
					sbStr.append(lockMobiles.get(totalCount - 1)+",");
				}
		    }
		} catch (Exception e) {
		    LOGGER.error("",e);
		    throw new SMSException(e);
		} finally {
		    lock.unlock();
		}
		if (returnStr != null) {
		    if (returnStr.toString().endsWith("|")) {
			return returnStr.substring(0,
				returnStr.length() - 1);
		    }
		    return returnStr.toString();
		}
		return "";
	}
	
    /**
     * 发送短信
     * @param content
     * @param mobile
     * @return
     * @throws Exception
     */
	private String sendMessage(String content,
	    String mobile,Integer currentCount) throws Exception {
		if (LOGGER.isInfoEnabled()) {
			  LOGGER.info("2factor sms send message before mobile:{},content:{},mobileCount:{}",new Object[]{mobile,content,currentCount});
		}
		SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_TWO_FACTORY_CODE);
		if (supplierInfo == null) {
			LOGGER.error("=============> supplier is disabled ");
			return "-99";
		}
		//短信供应商扩展字段
		Map<String, String> extInfoMap = JSON.parseObject(supplierInfo.getSextInfo(), Map.class);
		//构建请求参数
		Map <String, String> param = buildRequestParam(content, mobile);
		//拼接请求地址
		String url = INTERFACE_URL.replace("{}",extInfoMap.get("apiKey"));
		//{"Status":"Success","Details":"a28658b8-553d-4e1d-91e5-ab24854e5d425ea5e43959.12701435"}
		//{"Status":"Error","Details":""}
		HttpClientResult result = HttpClientUtils.doPost(url,param);
		if(result ==null || result.getCode() != 200){
			return "-99";
		}
		Map<String,String> sendResultMap = JSON.parseObject(result.getContent(), Map.class);
		if(!"Success".equals(sendResultMap.get("Status"))){
			return "-99";
		}
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info("2factor sms send message end mobile:{},return value:{}",new Object[]{mobile,result});
		}
		return sendResultMap.get("Details");
    }

	@Override
	public String queryRemainMessageCount() {
		SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_TWO_FACTORY_CODE);
		if (supplierInfo == null) {
			LOGGER.error("=============> supplier is disabled ");
			return "-99";
		}
		String extInfo = supplierInfo.getSextInfo();
		Map<String, String> extInfoMap = JSON.parseObject(extInfo, Map.class);
		try {
		    //拼接请求地址
            String url = INTERFACE_BALANCE_URL.replace("{}",extInfoMap.get("apiKey"));
			//{ "Status": "Success", "Details": "2039"}
			HttpClientResult result = HttpClientUtils.doGet(url);
			if(result == null || result.getCode() != 200){
				return "-99";
			}
			Map<String,String> sendResultMap = JSON.parseObject(result.getContent(), Map.class);
			if("Success".equals(sendResultMap.get("Status"))){
				return result.getContent();
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "-99";
	}

	private Map <String, String> buildRequestParam(String content, String mobile) {
		Map<String,String> param = new HashMap <>();
		param.put("From",SENDER_ID);
		param.put("To",mobile);
		param.put("TemplateName",TEMPLATE_NAME);
		param.put("VAR1",content);
		return param;
	}
}
