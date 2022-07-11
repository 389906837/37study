package com.cloud.cang.wap.common.utils;

import com.cloud.cang.zookeeper.utils.EvnUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;


/**
 * read config from  zookeeper 
 * @author zhouhong
 * @version 1.0
 */
@Component
public class EnviornConfig {
    
    
    public  String getEnvValue(){
        return EvnUtils.getValue("send.env");
        
    }
   
    /**
     * 1.插入到数据库中，但是不发送
     * @return
     */
    public boolean isTest(){
        return !StringUtils.isEmpty(getEnvValue())&& "test".equals(getEnvValue());
    }
    
    /**
     * 线上环境
     * @return
     */
    public boolean isProduct(){
        return !StringUtils.isEmpty(getEnvValue())&& "product".equals(getEnvValue());
    }
    
    

}
