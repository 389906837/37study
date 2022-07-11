package com.cloud.cang.core.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.utils.SpringContext;
import com.cloud.cang.zookeeper.ZookeeperFactoryBean;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 消息发送频率限制：
 *
 * 每个机器人每分钟最多发送20条。消息发送太频繁会严重影响群成员的使用体验;
 * 大量发消息的场景 (譬如系统监控报警) 可以将这些信息进行整合，通过markdown消息以摘要的形式发送到群里。
 *
 * @program: 37cang-云平台
 * @description:
 * @author: qzg
 * @create: 2019-11-29 12:32
 **/
public class DingTalkUtils {
    private static Boolean isProduction = null;
    public static final String baseUrl = "https://oapi.dingtalk.com/robot/send";

    // 默认测试环境配置
    public static  String access_token = "ff9f95412c5ed77c29e6ea85d1b8a92ec30ce9cf72fee043e675707f2ca1dcd7";
    public static  String secret = "SEC79cbdc48e0648b22c02ae7179ec2f6e09648a54b8a92f128fb727fcc46037aed";

    private static final Logger logger = LoggerFactory.getLogger(DingTalkUtils.class);

    public static void sendText(final String content, final String ...atMobiles){
        ExecutorManager.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String serverUrl = getUrl();
                    DingTalkClient client = new DefaultDingTalkClient(serverUrl);
                    OapiRobotSendRequest request = new OapiRobotSendRequest();
                    request.setMsgtype("text");

                    OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();

                    text.setContent("【"+projectName() + "】" + content);
                    request.setText(text);

                    OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
                    // todo 动态配置
                    //  @默认用户
                    ArrayList<String> defaultMobiles =
                            CollUtil.newArrayList( "18670346997");
                    if(atMobiles != null){
                        defaultMobiles.addAll(Arrays.asList(atMobiles));
                    }
                    at.setAtMobiles(defaultMobiles);
                    request.setAt(at);
                    OapiRobotSendResponse response = client.execute(request);
                   if(!response.isSuccess()){
                       logger.error("钉钉预警消息,发送失败：{}", JSONUtil.toJsonStr(response));
                   }
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 签名
     * @param timestamp
     * @param secret
     * @return
     */
    private static String sign(long timestamp,String secret){
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            return URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取URL, 生产或者测试
     *  todo 后期放在zookeeper配置
     * @return
     */
    private static String getUrl(){
        // 生成环境
        if(DingTalkUtils.isProduction()){
            access_token = "e0dfc447465d064e230948ac6c646ef8b74fee1eb1bbfe287f3c9bad5cc41677";
            secret = "SEC4961b1a7f49576af381107b1f04c0ef6c73de1eb78a1b129e7c0c25c791e82f4";
        }
        Long timestamp = System.currentTimeMillis();
        String sign = sign(timestamp, secret);
        String url = baseUrl +"?access_token="+ access_token +"&timestamp="+timestamp+"&sign="+sign;
        return url;
    }

    /**
     * 是否生产环境
     * @return
     */
    private static boolean isProduction(){
        try {
            if(isProduction !=null ){
                return isProduction;
            }
            Environment environment = SpringContext.getBean(Environment.class);
            String activeProfile = environment.getActiveProfiles()[0];
            if(StrUtil.equals(activeProfile,"production")){
                isProduction = new Boolean(true);
                return true;
            }
            isProduction = new Boolean(false);
            return false;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private static String projectName(){
        ZookeeperFactoryBean bean = SpringContext.getBean(ZookeeperFactoryBean.class);
        return bean.getProjectName();
    }


    public static void main(String[] args){
         DingTalkUtils.sendText("121212");
    }
}
