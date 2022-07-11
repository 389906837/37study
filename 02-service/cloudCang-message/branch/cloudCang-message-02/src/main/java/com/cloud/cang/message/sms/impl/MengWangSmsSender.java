package com.cloud.cang.message.sms.impl;

import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.core.utils.ValidateUtils;
import com.cloud.cang.message.exception.SMSException;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.message.sms.ISmsSender;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import com.cloud.cang.dispatcher.support.RestClientHttpRequest;
import com.cloud.cang.utils.FreemarkUtils;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 梦网短信发送
 * @author zhouhong
 * @version 1.0
 */
@Service
public class MengWangSmsSender implements ISmsSender {
    
   @Autowired
   private SupplierInfoService supplierInfoService;
    /**
     * 发送短信HTTP地址
     */
    private String HTTP_ADDRESS ="http://61.130.7.220:8023/MWGate/wmgw.asmx/MongateSendSubmit?userId=${userId}&password=${password}&pszMobis=${pszMobis}&pszMsg=${pszMsg}&iMobiCount=${iMobiCount}&pszSubPort=${pszSubPort}";

    /**
     * 查询短信余额HTTP地址
     */
    private String HTTP_REMAIN_ADDRESS ="http://61.130.7.220:8023/MWGate/wmgw.asmx/MongateQueryBalance?userId=${userId}&password=${password}";
    private static Logger LOGGER = LoggerFactory
		.getLogger(MengWangSmsSender.class);
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
	    String rtnValue = sendMessage(content, 1, mobile);
	    if (StringUtils.isNotBlank(rtnValue)) {
		Document doc = DocumentHelper.parseText(rtnValue);
		Element el = doc.getRootElement();
		return el.getText();
	    }
	} catch (Exception e) {
	    LOGGER.error("",e);
	    throw new SMSException(e);
	}
	return "";
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
			String rtnValue = sendMessage(content, currentCount,
				mobileStr);
			
			if (StringUtils.isNotBlank(rtnValue)) {
			    Document doc = DocumentHelper.parseText(rtnValue);
			    Element el = doc.getRootElement();
			    returnStr.append(el.getText()+"|");
			}

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
     * @param currentCount
     * @param mobileStr
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    private String sendMessage(String content, int currentCount,
	    String mobileStr) throws IOException, TemplateException {
	if (LOGGER.isInfoEnabled()) {
	      LOGGER.info("mengwang sms send message before mobile:{},content:{},mobileCount:{}",new Object[]{mobileStr,content,currentCount});
	  }
	SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_MENGWANG_CODE);
	if (supplierInfo == null) {
		LOGGER.error("=============> supplier is disabled ");
		return "-99";
		
	}
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("userId", supplierInfo.getSaccName());
	map.put("password", supplierInfo.getSaccPassword());
	map.put("pszMobis", mobileStr);
	map.put("pszMsg", content);
	map.put("iMobiCount", currentCount + "");
	map.put("pszSubPort", "*");
	String httpAddress = FreemarkUtils.replaceParameters(
		map, HTTP_ADDRESS);
	String rtnValue = new RestClientHttpRequest().getRestTemplate()
		.getForObject(httpAddress, String.class);
	
	if (LOGGER.isInfoEnabled()) {
	    LOGGER.info("mengwang sms send message end mobile:{},return value:{}",new Object[]{mobileStr,rtnValue});
	  }  
	return rtnValue;
    }
    
    public static void main(String[] args) {
	/*String html ="<?xml version=\"1.0\" encoding=\"utf-8\"?><string xmlns=\"http://tempuri.org/\">2361822722482673356</string>";
	Document doc = null;
	try {
	    doc = DocumentHelper.parseText(html);
	} catch (DocumentException e) {
	    e.printStackTrace();
	}
	Element el = doc.getRootElement();
	System.out.println(el.getText());*/
	  ArrayList<String> mobiles = new ArrayList<String>();
	  mobiles.add("15121061739");
	  mobiles.add("18229113605");
	  mobiles.add("18115180016");
	 List<String> lockMobiles = mobiles;
	 System.out.println(mobiles==lockMobiles);
	 System.out.println(lockMobiles.toString());
	
    }

    @Override
    public String queryRemainMessageCount() {
	SupplierInfo supplierInfo = supplierInfoService
		.getSupplierInfoByCode(Constrants.SMS_MENGWANG_CODE);
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("userId", supplierInfo.getSaccName());
	map.put("password", supplierInfo.getSaccPassword());
	String httpAddress = "";
	try {
	    httpAddress = FreemarkUtils.replaceParameters(map,
		    HTTP_REMAIN_ADDRESS);

	    String returnHTML = new RestClientHttpRequest().getRestTemplate().getForObject(
		    httpAddress, String.class);
	    if (StringUtils.isNotBlank(returnHTML)) {
		Document doc = DocumentHelper.parseText(returnHTML);
		Element el = doc.getRootElement();
		return el.getText();
	    }
	} catch (Exception e) {
	    LOGGER.error("", e);
	}
	return "-1";
    }

}
