package com.cloud.cang.message.sms.impl;

import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.message.exception.SMSException;
import	com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.message.sms.ISmsSender;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import com.jianzhou.sdk.BusinessService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 建短信调用
 * @author zhouhong
 * @version 1.0
 */
@Service
public class JianZhouSmsSender implements ISmsSender {
    
    private final String jianzhouUrl = "http://vip.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService";
    
    private static Logger LOGGER = LoggerFactory
		.getLogger(JianZhouSmsSender.class);
    
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
		    sbStr.append(lockMobiles.get(totalCount - 1)+";");
		    if (sbStr != null && sbStr.toString().endsWith(";")) {
			String mobileStr = sbStr.substring(0,
				sbStr.length() - 1);
			String rtnValue = sendMessage(content,mobileStr, currentCount);
			returnStr.append(rtnValue+"|");
			
		    }
		    currentCount = 0;
		    sbStr.setLength(0);
		    sbStr = new StringBuilder(1500);
		} else {
		    sbStr.append(lockMobiles.get(totalCount - 1)+";");
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
	      LOGGER.info("jianzhou sms send message before mobile:{},content:{},mobileCount:{}",new Object[]{mobileStr,content,currentCount});
	}
	SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_JIANZHAOU_CODE);
	if (supplierInfo == null) {
		LOGGER.error("=============> supplier is disabled ");
		return "-99";
		
	}
	content =content + Constrants.MESSAGE_SIGNATURE ;
    	BusinessService businessService = new BusinessService();
    	businessService.setWebService(jianzhouUrl);
    	Integer returnValue = businessService.sendBatchMessage(supplierInfo.getSaccName(), supplierInfo.getSaccPassword(), mobileStr, content);
    	if (LOGGER.isInfoEnabled()) {
    	    LOGGER.info("jianzhou sms send message end mobile:{},return value:{}",new Object[]{mobileStr,returnValue});
	  }  
    	return String.valueOf(returnValue);
	
    }

    @Override
    public String queryRemainMessageCount() {
	SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_JIANZHAOU_CODE);
	BusinessService businessService = new BusinessService();
	businessService.setWebService(jianzhouUrl);
	String returnXML = businessService.getUserInfo(supplierInfo.getSaccName(), supplierInfo.getSaccPassword());
	if (StringUtils.isNotBlank(returnXML)) {
	    Document doc = null;
	    try {
		doc = DocumentHelper.parseText(returnXML);
		Element root = doc.getRootElement();
		return root.element("remainFee").getText();
	    } catch (DocumentException e) {
		LOGGER.error("", e);
	    }
	   
	}
	return "-1";
	
    }
  

}
