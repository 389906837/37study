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

import java.util.List;

import com.cloud.cang.dispatcher.services.ServiceInstance;
import com.cloud.cang.dispatcher.services.manager.InstanceProvider;

/**
 * This strategy 基于方法指定IP
 */
public class IPStrategy implements ProviderStrategy {
    private String remoteIp;
    private String remotePort;
    public IPStrategy(String remoteIp,String remotePort){
        this.remoteIp=remoteIp;
        this.remotePort=remotePort;
    }

    @Override
    public ServiceInstance getInstance(InstanceProvider instanceProvider , String localIp, String method, Object[] args) throws Exception {
        System.out.println("方法："+method+"，指定Ip访问："+localIp+" 请求基发现服务"+"远程IP与端口"+remoteIp+remotePort);
        List<ServiceInstance> instances = instanceProvider.getInstances();
        for(ServiceInstance instance:instances){
            if(instance.getIpAddress().equals(remoteIp) && instance.getPort().toString().equals(remotePort))
                return instance;
        }
        return null;
    }
}
