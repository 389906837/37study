package com.cloud.cang.message.sms;

import com.cloud.cang.message.exception.SMSException;

import java.util.List;

public interface ISmsSender {
    
    
    /**
     * 发送单条短信
     * @param mobile
     * @param content
     */
    String send(String mobile, String content) throws SMSException;


    /**
     * 批量发送相同
     * @param mobiles  目标字符串
     * @param content
     */
    String batchSendSameContent(List<String> mobiles, String content) throws SMSException;
    
    
    /**
     * 查询短信剩余条数
     * @return
     */
    String queryRemainMessageCount();
    
   
    

}
