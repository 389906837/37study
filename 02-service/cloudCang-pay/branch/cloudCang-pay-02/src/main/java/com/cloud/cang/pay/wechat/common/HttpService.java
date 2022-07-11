package com.cloud.cang.pay.wechat.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import com.cloud.cang.core.common.WechatConfigure;
import com.cloud.cang.model.sh.MerchantConf;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpService 初始化需要在Configure指定密钥的为止，以及密码
 * Created by hupeng on 2015/7/28.
 */
@SuppressWarnings("deprecation")
public class HttpService {

    private MerchantConf conf;
    private CloseableHttpClient httpClient;
    public HttpService(MerchantConf conf) {
        this.conf = conf;
        httpClient = buildHttpClient();
    }
    private  final Logger logger = LoggerFactory.getLogger(HttpService.class);
    


    private  int socketTimeout = 5000;

    private  int connectTimeout = 5000;

    private  int requestTimeout = 5000;

	public CloseableHttpClient buildHttpClient() {

        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(new File(conf.getSwechatCertUrl()));
            try {
                keyStore.load(instream, conf.getSwechatCertPwd().toCharArray());
            } finally {
                instream.close();
            }


            // Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
                    .loadKeyMaterial(keyStore, conf.getSwechatCertPwd().toCharArray())
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[]{"TLSv1"},
                    null,
                    SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(requestTimeout)
                    .setSocketTimeout(socketTimeout).build();

            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setSSLSocketFactory(sslsf)
                    .build();

            return httpClient;
        } catch (Exception e) {
            throw new RuntimeException("error create httpclient......", e);
        }
    }


    public  String doGet(String requestUrl) throws Exception {
        HttpGet httpget = new HttpGet(requestUrl);
        try {


            logger.debug("Executing request " + httpget.getRequestLine());
            // Create a custom response handler
            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final HttpResponse response) throws ClientProtocolException, IOException {
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {
                        HttpEntity entity = response.getEntity();
                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };

            return httpClient.execute(httpget, responseHandler);
        } finally {
            httpget.releaseConnection();
        }
    }

    public  String doPost(String url, Object object2Xml) {

        String result = null;

        HttpPost httpPost = new HttpPost(url);

        String postDataXML = XMLParser.toXML(object2Xml);

        logger.info("API POST DATA:");
        logger.info(postDataXML);

        StringEntity postEntity = new StringEntity(postDataXML, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        logger.info("executing request" + httpPost.getRequestLine());

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            logger.error("http get throw ConnectionPoolTimeoutException(wait time out)", e);

        } catch (ConnectTimeoutException e) {
            logger.error("http get throw ConnectTimeoutException", e);

        } catch (SocketTimeoutException e) {
            logger.error("http get throw SocketTimeoutException", e);

        } catch (Exception e) {
            logger.error("http get throw Exception", e);

        } finally {
            httpPost.releaseConnection();
        }

        return result;
    }
}
