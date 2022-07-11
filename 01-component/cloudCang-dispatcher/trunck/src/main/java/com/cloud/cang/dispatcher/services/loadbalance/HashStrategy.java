/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.cloud.cang.dispatcher.services.loadbalance;


import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.InstanceProvider;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This strategy 基于参数进行Hash的服务路由
 */
public class HashStrategy implements ProviderStrategy {
	public static Logger logger = LoggerFactory
			.getLogger(HashStrategy.class);
    private String paramName="";
    public HashStrategy(String paramName){
        this.paramName=paramName;
    }

    @Override
    public ServiceInstance getInstance(InstanceProvider instanceProvider , String localIp, String method, Object[] args) throws Exception {
    	if (logger.isDebugEnabled()) {
    		logger.debug("方法：{}，参数：{} 请求基于Hash发现服务", method,paramName);
    	}
    	
    	Object requestObj = null;
    	
    	if (args != null && args.length > 0) {
    		requestObj = args[0];
    	}
    	
        List<ServiceInstance> instances = instanceProvider.getInstances();
        int serviceSize=instances.size();
       
        String value= method;
        if (requestObj != null && StringUtils.isNotBlank(paramName)) {
        	if (requestObj instanceof Map) {
        		value =((Map) requestObj).get(paramName).toString();
        	}else if (!isPrimitive(requestObj.getClass())){
        		value= BeanUtilsBean2.getInstance().getProperty(requestObj, paramName);
        	}
        	
        }
        int hashcode=Math.abs(value.hashCode());
        if (logger.isDebugEnabled()) {
    		logger.debug("参数值：{}，Hashcode：{}", new Object[]{value,hashcode});
    	}
        return instances.get(hashcode%serviceSize);
    }
    
    private static boolean isPrimitive(Class<?> cls) {
        return cls.isPrimitive() || cls == Boolean.class || cls == Byte.class
                || cls == Character.class || cls == Short.class || cls == Integer.class
                || cls == Long.class || cls == Float.class || cls == Double.class
                || cls == String.class || cls == Date.class || cls == Class.class;
    }
}
