package com.cloud.cang.message.sms.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.message.exception.SMSException;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.message.sms.ISmsSender;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 漫道短信发送
 * @author zhouhong
 * @version 1.0
 */
@Service
public class ManDaoSmsSender implements ISmsSender {
    
    private static Logger LOGGER = LoggerFactory
		.getLogger(ManDaoSmsSender.class);
    @Autowired
    private SupplierInfoService supplierInfoService;
    
    //手机批量发送锁
    private Lock lock = new ReentrantLock(); 

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
    private String sendMessage(String content,
	    String mobileStr,Integer currentCount) throws Exception {
	if (LOGGER.isInfoEnabled()) {
	      LOGGER.info("maodao sms send message before mobile:{},content:{},mobileCount:{}",new Object[]{mobileStr,content,currentCount});
	}
	SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_MANDAO_CODE);
	if (supplierInfo == null) {
		LOGGER.error("=============> supplier is disabled ");
		return "-99";
		
	}
	content =content + Constrants.MESSAGE_SIGNATURE ;
	content = URLEncoder.encode(content, "utf-8");
	ManDaoClient client = new ManDaoClient(supplierInfo.getSaccName(), supplierInfo.getSaccPassword());
	String result_mt = client.mdsmssend(mobileStr, content, "", "", "", "");
    	if (LOGGER.isInfoEnabled()) {
    	    LOGGER.info("mandao sms send message end mobile:{},return value:{}",new Object[]{mobileStr,result_mt});
	  }
    	return result_mt;

    }

    @SuppressWarnings("unchecked")
	@Override
    public String queryRemainMessageCount() {
	SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_MANDAO_CODE);
	try {
	   ManDaoClient client = new ManDaoClient(supplierInfo.getSaccName(), supplierInfo.getSaccPassword());
	   String returnJOSN = client.mdgetSninfo();
	  if(StringUtils.isNotBlank(returnJOSN)) {
	    Map<String,Object> map = JSON.parseObject(returnJOSN, Map.class);
	    return String.valueOf(map.get("Balance"));
	  }
	} catch (UnsupportedEncodingException e) {
	   LOGGER.error("", e);
	}
	return "-1";
    }
    
    

}
