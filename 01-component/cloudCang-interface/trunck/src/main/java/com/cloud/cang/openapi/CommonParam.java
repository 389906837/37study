package com.cloud.cang.openapi;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: 37cang-云平台
 * @description: 公共请求参数
 * @author: qzg
 * @create: 2019-11-18 09:58
 **/
@Data
public class CommonParam implements Serializable {
    //37 仓分配的开发者应用 ID(加密数据)
    private String appId;

    //接口名称 (加密数据)
    private String methodName;

    //发送请求时间  yyyyMMddHHmmss
    private String timestamp;

    //请求使用的编码
    private String charset = "UTF-8";

    //商户生成签名字符串所使用的签名算法类型，
    // 目前支持 RSA2 和 RSA，推荐使用 RSA2
    private String signType="RSA2";

    //商户请求参数的签名串
    private String sign;

    //调用的接口版本，固定为：1.0.0
    private String version;

    private String format = "json";

    //37 号仓服务器主动通 知商户服务器里指定 的页面 http/https 路径
    private String notifyUrl;

    private String encryptType;

    // 请求参数 (加密数据)
    private String bizContent;
}
