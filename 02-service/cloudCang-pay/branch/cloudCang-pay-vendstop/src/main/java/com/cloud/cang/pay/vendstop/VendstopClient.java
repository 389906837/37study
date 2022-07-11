package com.cloud.cang.pay.vendstop;

import com.alibaba.fastjson.JSON;
import com.cloud.cang.pay.vendstop.http.HttpClientResult;
import com.cloud.cang.pay.vendstop.http.HttpClientUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: 37cang
 * @description: 请求vendstop地址
 * @author: qzg
 * @create: 2019-08-08 13:51
 **/
public class VendstopClient {
    public static final String head_name = "apiKey";
    private static final Logger logger = LoggerFactory.getLogger(VendstopClient.class);
    /**
     * 发送请求封装
     * @param url
     * @return
     */
    public static VendstopResponse invoke(String url, Map<String,Object> param) throws Exception{
        logger.debug("====请求地址：{}",url);
        logger.debug("====请求参数：{}", param);
        Map<String,String> header = new HashMap();
        header.put(head_name,VendstopConstant.API_KEY);
        HttpClientResult result = HttpClientUtils.doPost(url,header,param);
        //请求返回状态码 200
        if(result.getCode() == HttpStatus.SC_OK){
            Map<String,Object> resultMap = JSON.parseObject(result.getContent(), Map.class);
            logger.debug("====返回结果{}:",resultMap);
            return parseVenstopResponse(resultMap);
        }
        logger.debug("====请求异常{}:",result);
        return null;
    }

    /**
     * success : true,
     * data:{
     *     "name":"jack",
     *     "age": 11
     * },
     * error:{
     *
     * }
     * 封装VendstopResponse
     * @param resultMap
     * @return
     */
    private static VendstopResponse parseVenstopResponse( Map<String,Object> resultMap){
        VendstopResponse response = new VendstopResponse();
        response.setSuccess(Boolean.parseBoolean(resultMap.get("success")+""));
        response.setData(resultMap.get("data")+"");
        response.setError(resultMap.get("error")+"");
        return response;
    }
}
