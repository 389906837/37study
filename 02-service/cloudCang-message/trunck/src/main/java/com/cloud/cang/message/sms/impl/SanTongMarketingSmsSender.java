/**
 * 
 */
package com.cloud.cang.message.sms.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.message.exception.SMSException;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.message.sms.ISmsSender;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import com.ctc.smscloud.json.JSONHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author zhouhong
 *
 */
@Service
public class SanTongMarketingSmsSender implements ISmsSender {
	
	@Autowired
    private SupplierInfoService supplierInfoService;
	
	 //手机批量发送锁
    private Lock lock = new ReentrantLock(); 
	
	  private static Logger LOGGER = LoggerFactory
				.getLogger(SanTongMarketingSmsSender.class);

	/* (non-Javadoc)
	 * @see com.hurbao.message.sms.ISmsSender#send(java.lang.String, java.lang.String)
	 */
	@Override
	public String send(String mobile, String content) throws SMSException {
		if (StringUtils.isBlank(mobile)) {
		    throw new SMSException("手机号不能为空");
		}
		if (!ValidateUtils.isMobile(mobile)) {
		    throw new SMSException("手机号格式不正确");
		}
		try {
		    return sendMessage(content, mobile, 1);
		} catch (Exception e) {
		    LOGGER.error("", e);
		    throw new SMSException(e);
		}
	}

	/* (non-Javadoc)
	 * @see com.hurbao.message.sms.ISmsSender#batchSendSameContent(java.util.List, java.lang.String)
	 */
	@SuppressWarnings("unused")
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
     * @param mobileStr
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
	private String sendMessage(String content,
	    String mobileStr,Integer currentCount) throws Exception {
	if (LOGGER.isInfoEnabled()) {
	      LOGGER.info("dahansantong sms send message before mobile:{},content:{},mobileCount:{}",new Object[]{mobileStr,content,currentCount});
	}
	
	SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_SANTONG_MARKING_CODE);
	if (supplierInfo == null) {
		LOGGER.error("=============> supplier is disabled ");
		return "-99";
		
	}
	String extInfo = supplierInfo.getSextInfo();
	Map<String,String> extInfoMap = JSON.parseObject(extInfo, Map.class);
	JSONHttpClient jsonHttpClient = JSONHttpClient.getInstance("wt.3tong.net");
	String sendRes = jsonHttpClient.sendSms(supplierInfo.getSaccName(), supplierInfo.getSaccPassword(), mobileStr, content, Constrants.MESSAGE_SIGNATURE, extInfoMap.get("subcode"));
    if (LOGGER.isInfoEnabled()) {
    	    LOGGER.info("dahansantong sms send message end mobile:{},return value:{}",new Object[]{mobileStr,sendRes});
	}
	Map<String,String> sendResultMap = JSON.parseObject(sendRes, Map.class);
    return sendResultMap.get("result");
	
    }

	/* (non-Javadoc)
	 * @see com.hurbao.message.sms.ISmsSender#queryRemainMessageCount()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryRemainMessageCount() {
		JSONHttpClient jsonHttpClient = JSONHttpClient.getInstance("wt.3tong.net");
		SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_SANTONG_MARKING_CODE);
		if (supplierInfo == null) {
			LOGGER.error("=============> supplier is disabled ");
			return "-99";
			
		}
		String balanceRes = jsonHttpClient.getBalance(supplierInfo.getSaccName(), supplierInfo.getSaccPassword());
		 if (LOGGER.isInfoEnabled()) {
			 LOGGER.info("query result:{}",balanceRes);
		 }
		 if (StringUtils.isNotBlank(balanceRes)){
				Map<String,Object> sendResultMap = JSON.parseObject(balanceRes, Map.class);
				String result = (String)sendResultMap.get("result");
				if (result != null && result.equals("0")){
					JSONObject smsBalance =(JSONObject)sendResultMap.get("smsBalance");
					return smsBalance.getString("number");
					
				}
			}
		return "-99";
	}

}
