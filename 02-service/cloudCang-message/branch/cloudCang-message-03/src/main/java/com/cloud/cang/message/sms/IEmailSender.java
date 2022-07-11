package com.cloud.cang.message.sms;

import com.cloud.cang.message.exception.SMSException;

public interface IEmailSender {
    
    /**
     * 发送邮件
     * @param receive 接收人
     * @param subject 主题
     * @param content 内容
     * @param attachment 附件
     * @return
     * @throws SMSException
     */
    String send(String receive, String subject, String content, String attachment) throws SMSException;

}
