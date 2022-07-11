package com.cloud.cang.message.sms.impl;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.message.common.Constrants;
import com.cloud.cang.message.exception.SMSException;
import com.cloud.cang.model.xx.SupplierInfo;
import com.cloud.cang.message.sms.IEmailSender;
import com.cloud.cang.message.xx.service.SupplierInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Map;


/**
 * 邮件发送服务
 * @author zhouhong
 * @version 1.0
 * {host:"",from:""}
 */
@Service
public class EmailSenderImpl implements IEmailSender {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSenderImpl.class);
    
    @Autowired
    private SupplierInfoService supplierInfoService;

   
    
    public static void main(String[] args) {

	      try {
	          HtmlEmail email = new HtmlEmail();
	          email.setCharset("UTF-8");  
	          email.setHostName("smtp.exmail.qq.com");
	          email.addTo("820627489@qq.com");
	          email.setFrom("ht@hurbao.com","互融宝");
	          email.setSubject("互融宝合同测试发送456789");
	          email.setAuthentication("ht@hurbao.com", "P2p58066815");
	          email.setHtmlMsg("尊敬的用户，您已成功投资[互融宝项目-1]理财项目。请查阅附件投资协议<span style=\"color:red\">(协议查看密码为身份证后六位)</span>。感谢您对投资吧的支持。您可登录投资吧网站或客户端查看投资详情。");  
	          EmailAttachment attachment = new EmailAttachment();
	         // attachment.setPath("http://static.tziba.com/advertis/20160205092606741.jpg");
	          attachment.setURL(new URL("http://static.tziba.com/advertis/20160205092606741.jpg"));
                  attachment.setDisposition(EmailAttachment.ATTACHMENT); 
                  email.attach(attachment);
	        System.out.println(  email.send());
	         
	         
	      } catch (Exception e) {
	          e.printStackTrace();
	      }
	}

    @SuppressWarnings("unchecked")
    @Override
    public String send(String receive, String subject, String content,
	    String attachment) throws SMSException {
	if (LOGGER.isInfoEnabled()) {
	      LOGGER.info("email send  before receive:{},subject:{},content:{},attachment:{}",new Object[]{receive,subject,content,attachment});
	}
	//供应商
	SupplierInfo supplierInfo = supplierInfoService.getSupplierInfoByCode(Constrants.SMS_EMAIL_CODE);
	String extInfo = supplierInfo.getSextInfo();
	Map<String,String> extInfoMap = JSON.parseObject(extInfo, Map.class);
	
	  try {
	       HtmlEmail email = new HtmlEmail();
	      email.setCharset("UTF-8");  
	      email.setHostName(extInfoMap.get("host"));
	      email.addTo(receive);
	      email.setFrom(extInfoMap.get("from"),"互融宝");
	      email.setSubject(subject);
	      email.setAuthentication(supplierInfo.getSaccName(), supplierInfo.getSaccPassword());
	      email.setHtmlMsg(content);  
	      if (StringUtils.isNotBlank(attachment)) {
	          String [] attStrs = attachment.split(",");
	          for (String att:attStrs) {
	              EmailAttachment emailAttachment = new EmailAttachment();
		          //emailAttachment.setURL(new URL(att));
		          emailAttachment.setPath(att);
		          emailAttachment.setDisposition(EmailAttachment.ATTACHMENT); 
		          email.attach(emailAttachment);
	          }
	         
	      }
	      email.send();
	} catch (EmailException e) {
	  LOGGER.error("", e);
	  return e.getMessage();
	}
	
	return "success";
    }

}
