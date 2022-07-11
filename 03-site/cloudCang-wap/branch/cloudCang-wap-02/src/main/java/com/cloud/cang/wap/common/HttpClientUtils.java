package com.cloud.cang.wap.common;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * Created by YLF on 2019/11/21.
 */
public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    public static String doPost(String methodurl, String data) {
        HttpURLConnection connection = null;
        OutputStream dataout = null;
        BufferedReader reader = null;
        String line = null;
        try {
            URL url = new URL(methodurl);
            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
            connection.setDoOutput(true);// 设置是否向connection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true,默认情况下是false
            connection.setDoInput(true); // 设置是否从connection读入，默认情况下是true;
            connection.setRequestMethod("POST");// 设置请求方式为post,默认GET请求
            connection.setUseCaches(false);// post请求不能使用缓存设为false
            connection.setConnectTimeout(3000);// 连接主机的超时时间
            connection.setReadTimeout(3000);// 从主机读取数据的超时时间
            connection.setInstanceFollowRedirects(true);// 设置该HttpURLConnection实例是否自动执行重定向
            connection.setRequestProperty("connection", "Keep-Alive");// 连接复用
            connection.setRequestProperty("charset", "utf-8");

            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer 66cb225f1c3ff0ddfdae31rae2b57488aadfb8b5e7");
            connection.connect();// 建立TCP连接,getOutputStream会隐含的进行connect,所以此处可以不要

            dataout = new DataOutputStream(connection.getOutputStream());// 创建输入输出流,用于往连接里面输出携带的参数
            String body = data;
            dataout.write(body.getBytes());
            dataout.flush();
            dataout.close();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));//
                }
                return result.toString();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));//
                }
                return result.toString();
            }
        } catch (IOException e) {
            logger.error("发送Post请求异常：{}", e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                logger.error("发送Post请求,关闭流异常：{}", e);
            }
            connection.disconnect();
        }
        return "";
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


    /**
     * 向指定 URL 发送POST方法的请求
     *
     * @param url 发送请求的 URL
     * @param map 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String, Object> map) {
        String param = urlencode(map, "");
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;

    }


    public static String urlencode(Object params, String key) {
        String res = "";
        if (params instanceof Map) {
            Map<String, Object> _params = (Map<String, Object>) params;
            for (String i : _params.keySet()) {
                String k = key.isEmpty() ? i : (key + "[" + i + "]");
                String encodeValue = urlencode(_params.get(i), k);
                if (!encodeValue.isEmpty()) {
                    res += '&' + encodeValue;
                }
            }
        } else if (params instanceof List) {
            List<Object> _params = (List<Object>) params;
            for (Integer i = 0; i < _params.size(); i++) {
                String k = key.isEmpty() ? i.toString() : (key + "[" + i.toString() + "]");
                String encodeValue = urlencode(_params.get(i), k);
                if (!encodeValue.isEmpty()) {
                    res += '&' + encodeValue;
                }
            }
        } else if (params.getClass().isArray()) {
            Object[] _params;
            if (params instanceof Object[]) {
                _params = (Object[]) params;
            } else if (params instanceof String[]) {
                _params = (String[]) params;
            } else if (params instanceof int[]) {
                _params = ArrayUtils.toObject((int[]) params);
            } else if (params instanceof double[]) {
                _params = ArrayUtils.toObject((double[]) params);
            } else {
                _params = new Object[]{};
            }
            for (Integer i = 0; i < _params.length; i++) {
                String k = key.isEmpty() ? i.toString() : (key + "[" + i.toString() + "]");
                String encodeValue = urlencode(_params[i], k);
                if (!encodeValue.isEmpty()) {
                    res += '&' + encodeValue;
                }
            }
        } else if (params instanceof String) {
            String _params = (String) params;
            try {
                res += '&' + URLEncoder.encode(key, "UTF-8") + '=' + URLEncoder.encode(_params, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else if (params instanceof Number) {
            Number _params = (Number) params;
            try {
                res += '&' + URLEncoder.encode(key, "UTF-8") + '=' + URLEncoder.encode(_params.toString(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            return "";
        }
        return res.substring(1);
    }


}
