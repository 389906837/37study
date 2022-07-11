package com.cloud.cang.zookeeper.utils;

import com.cloud.cang.zookeeper.confclient.config.PropertiesConfigurationFactoryBean;

/**
 * 静态工具类，取得zookeeper的配置数据
 * @author zhouhong
 * @version 1.0
 */
public class EvnUtils {

    public static String getValue(String key){
      return   PropertiesConfigurationFactoryBean.getPropertiesConfiguration().getString(key,"");
    }
}
