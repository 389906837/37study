package com.cloud.cang.pay.common.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * 网络请求工具类
 * @author zhouhong
 * @version 1.0
 */
public class HttpUtils {

    public static HttpResponse executeGet(String url) throws ClientProtocolException, IOException{
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,5000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,5000);
        HttpGet httpGet = new HttpGet(url);
        return httpClient.execute(httpGet);
    }
    
    public static HttpResponse executePost(String urlStr) throws ClientProtocolException, IOException, URISyntaxException{
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,5000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,5000);
        
		URL url = new URL(urlStr); 
		URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
        HttpPost httpPost = new HttpPost(uri);
        return httpClient.execute(httpPost);
    }

    public static InputStream getInputStream(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5 * 1000);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("CHARSET", "UTF-8");
        conn.connect();
        return conn.getInputStream();
    }

}
