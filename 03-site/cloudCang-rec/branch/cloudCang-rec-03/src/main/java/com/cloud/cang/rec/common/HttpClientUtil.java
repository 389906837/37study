package com.cloud.cang.rec.common;


import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author : chenlinyan
 * @version : 2.0
 * @date : 2019/9/27 9:40
 */
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    /**
     * 通过post方式调用http接口
     *
     * @param url       url路径
     * @param jsonParam json格式的参数
     * @param reSend    重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostByJson(String url, String jsonParam, int reSend) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime = System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime = 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            Header header = new BasicHeader("Accept-Encoding", null);
            httpPost.setHeader(header);
            // 设置报文和通讯格式
            StringEntity stringEntity = new StringEntity(jsonParam, HttpConstant.UTF8_ENCODE);
            stringEntity.setContentEncoding(HttpConstant.UTF8_ENCODE);
            stringEntity.setContentType(HttpConstant.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            logger.info("请求{}接口的参数为{}", url, jsonParam);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            logger.error("请求{}接口出现异常", url, e);
            if (reSend > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发", url);
                result = sendPostByJson(url, jsonParam, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常", e);
            }
        }
        //请求接口的响应时间
        endTime = System.currentTimeMillis();
        logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒", url);
        return result;

    }


    /**
     * 通过post方式调用http接口
     *
     * @param url    url路径
     * @param map    json格式的参数
     * @param reSend 重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostByForm(String url, Map<String, String> map, int reSend) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime = System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime = 0L;
        HttpEntity httpEntity = null;
        UrlEncodedFormEntity entity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
            }
            entity = new UrlEncodedFormEntity(list, HttpConstant.UTF8_ENCODE);
            httpPost.setEntity(entity);
            logger.info("请求{}接口的参数为{}", url, map);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            logger.error("请求{}接口出现异常", url, e);
            if (reSend > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发", url, e.getMessage());
                result = sendPostByForm(url, map, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常", e);
            }
        }
        //请求接口的响应时间
        endTime = System.currentTimeMillis();
        logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒");
        return result;

    }

    /**
     * 通过post方式调用http接口
     *
     * @param url      url路径
     * @param xmlParam json格式的参数
     * @param reSend   重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostByXml(String url, String xmlParam, int reSend) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime = System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime = 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            StringEntity stringEntity = new StringEntity(xmlParam, HttpConstant.UTF8_ENCODE);
            stringEntity.setContentEncoding(HttpConstant.UTF8_ENCODE);
            stringEntity.setContentType(HttpConstant.TEXT_XML);
            httpPost.setEntity(stringEntity);
            logger.info("请求{}接口的参数为{}", url, xmlParam);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity, HttpConstant.UTF8_ENCODE);
        } catch (Exception e) {
            logger.error("请求{}接口出现异常", url, e);
            if (reSend > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发", url, e.getMessage());
                result = sendPostByJson(url, xmlParam, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常", e);
            }
            //请求接口的响应时间
            endTime = System.currentTimeMillis();
            logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒", url);
            return result;
        }

    }

    /**
     * 获取token
     *
     * @param url
     * @param jsonParam
     * @param reSend
     * @return
     */
    public static String sendPostGetToken(String url, String jsonParam, int reSend, String tokenName) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime = System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime = 0L;
        HttpEntity httpEntity = null;
        String subjectToken = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            Header header = new BasicHeader("Accept-Encoding", null);
            httpPost.setHeader(header);
            // 设置报文和通讯格式
            StringEntity stringEntity = new StringEntity(jsonParam, HttpConstant.UTF8_ENCODE);
            stringEntity.setContentEncoding(HttpConstant.UTF8_ENCODE);
            stringEntity.setContentType(HttpConstant.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            //设置超时时间
            RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
            requestConfigBuilder.setConnectionRequestTimeout(1000).setMaxRedirects(1);
            httpPost.setConfig(requestConfigBuilder.build());

            logger.info("请求{}接口的参数为{}", url, jsonParam);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            Header[] token = httpResponse.getHeaders(tokenName);
            if (token != null && token.length > 0) {
                subjectToken = token[0].getValue();
            }
          /*  httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);*/

        } catch (Exception e) {
            logger.error("请求{}接口出现异常", url, e);
            if (reSend > 0) {
                //logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发",url,e.getMessage(),(HttpConstant.REQ_TIMES-reSend +1));
                result = sendPostGetToken(url, jsonParam, reSend - 1, tokenName);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常", e);
            }
        }
        //请求接口的响应时间
        endTime = System.currentTimeMillis();
        logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒", url);
        //  return result;
        return subjectToken;

    }

    /**
     * 发送POST请求添加请求头
     *
     * @param url
     * @param jsonParam
     * @param reSend
     * @param headerName
     * @param headerValue
     * @return
     */
    public static String sendPostAddHeader(String url, String jsonParam, int reSend, String headerName, String headerValue) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime = System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime = 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            Header header = new BasicHeader("Accept-Encoding", null);
            httpPost.setHeader(header);
            Header header2 = new BasicHeader(headerName, headerValue);
            httpPost.setHeader(header2);


            // 设置报文和通讯格式
            if (StringUtils.isNotBlank(jsonParam)) {
                StringEntity stringEntity = new StringEntity(jsonParam, HttpConstant.UTF8_ENCODE);
                stringEntity.setContentEncoding(HttpConstant.UTF8_ENCODE);
                stringEntity.setContentType(HttpConstant.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
            }
            logger.info("请求{}接口的参数为{}", url, jsonParam);
            //执行发送，获取相应结果
            //请求超时
/*            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
            //读取超时
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,3000);*/
            RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
            requestConfigBuilder.setConnectionRequestTimeout(1000).setMaxRedirects(1);
            httpPost.setConfig(requestConfigBuilder.build());

            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            logger.error("请求{}接口出现异常", url, e);
            if (reSend > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发", url);
                result = sendPostByJson(url, jsonParam, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常", e);
            }
        }
        //请求接口的响应时间
        endTime = System.currentTimeMillis();
        logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒", url);
        return result;

    }

    /**
     * 发送POST请求添加请求头
     *
     * @param url
     * @param jsonParam
     * @param reSend
     * @param headerName
     * @param headerValue
     * @return
     */
    public static String sendPostAddHeaderAndFile(String url, String jsonParam, int reSend, String headerName, String headerValue,Map<String, MultipartFile> fileParams) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime = System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime = 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            Header header = new BasicHeader("Accept-Encoding", null);
            httpPost.setHeader(header);
            Header header2 = new BasicHeader(headerName, headerValue);
            httpPost.setHeader(header2);


            // 设置报文和通讯格式
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.setCharset(Charset.forName("utf-8"));
            builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);//加上此行代码解决返回中文乱码问题
            //    文件传输http请求头(multipart/form-data)
            if (fileParams != null && fileParams.size() > 0) {
                for (Map.Entry<String, MultipartFile> e : fileParams.entrySet()) {
                    String fileParamName = e.getKey();
                    MultipartFile file = e.getValue();
                    if (file != null) {
                        String fileName = file.getOriginalFilename();
                        builder.addBinaryBody(fileParamName, file.getInputStream(), ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
                    }
                }
            }
            //    字节传输http请求头(application/json)
          /*  ContentType contentType = ContentType.create("application/json", Charset.forName("UTF-8"));
            if (otherParams != null && otherParams.size() > 0) {
                for (Map.Entry<String, String> e : otherParams.entrySet()) {
                    String value = e.getValue();
                    if (StringUtils.isNotBlank(value)) {
                        builder.addTextBody(e.getKey(), value, contentType);// 类似浏览器表单提交，对应input的name和value
                    }
                }
            }*/
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            logger.info("请求{}接口的参数为{}", url, jsonParam);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            logger.error("请求{}接口出现异常", url, e);
            if (reSend > 0) {
                logger.info("请求{}出现异常:{}，进行重发。进行第{}次重发", url);
                result = sendPostByJson(url, jsonParam, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
                logger.error("http请求释放资源异常", e);
            }
        }
        //请求接口的响应时间
        endTime = System.currentTimeMillis();
        logger.info("请求{}接口的响应报文内容为{},本次请求API接口的响应时间为:{}毫秒", url);
        return result;

    }

    /**
     * 通过GET方式调用http接口
     *
     * @param url url路径
     * @return
     * @throws Exception
     */
    public static String sendGetAddHeader(String url, String headerName, String headerValue) {
        String str = "";
        try {
            URI uri = new URI(url);
            HttpGet httpGet = new HttpGet(uri);

//                httpGet.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//                httpGet.setHeader("Accept-Encoding", "gzip, deflate");
//                httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
//                httpGet.setHeader("Connection", "keep-alive");
//                httpGet.setHeader("Host", "api.douban.com");
            httpGet.setHeader("Accept-Charset", "utf-8");
            httpGet.setHeader(headerName, headerValue);
            httpGet.setHeader("contentType", "utf-8");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:37.0) Gecko/20100101 Firefox/37.0");

            HttpResponse response = new DefaultHttpClient().execute(httpGet);
            str = EntityUtils.toString(response.getEntity());
            System.out.println("--->" + str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

}