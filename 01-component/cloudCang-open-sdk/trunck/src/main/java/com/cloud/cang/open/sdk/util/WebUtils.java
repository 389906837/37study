package com.cloud.cang.open.sdk.util;

import com.cloud.cang.open.sdk.common.CloudCangLogger;
import com.cloud.cang.open.sdk.mapping.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @version 1.0
 * @Description: http请求util
 * @Author: zhouhong
 * @Date: 2018/9/4 11:09
 */
public abstract class WebUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_GET = "GET";
    private static SSLContext ctx = null;
    private static HostnameVerifier verifier = null;
    private static SSLSocketFactory socketFactory = null;

    private WebUtils() {
    }

    public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException {
        return doPost(url, params, "UTF-8", connectTimeout, readTimeout);
    }

    public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout) throws IOException {
        String ctype = "application/x-www-form-urlencoded;charset=" + charset;
        String query = buildQuery(params, charset);
        byte[] content = new byte[0];
        if(query != null) {
            content = query.getBytes(charset);
        }

        return doPost(url, ctype, content, connectTimeout, readTimeout);
    }

    public static String doPost(String url, String ctype, byte[] content, int connectTimeout, int readTimeout) throws IOException {
        HttpURLConnection conn = null;
        OutputStream out = null;
        String rsp = null;

        try {
            Map map;
            try {
                conn = getConnection(new URL(url), "POST", ctype);
                conn.setConnectTimeout(connectTimeout);
                conn.setReadTimeout(readTimeout);
            } catch (IOException var15) {
                map = getParamsFromUrl(url);
                CloudCangLogger.logCommError(var15, url, (String)map.get("appId"), (String)map.get("methodName"), content);
                throw var15;
            }

            try {
                out = conn.getOutputStream();
                out.write(content);
                rsp = getResponseAsString(conn);
            } catch (IOException var14) {
                map = getParamsFromUrl(url);
                CloudCangLogger.logCommError(var14, conn, (String)map.get("appId"), (String)map.get("methodName"), content);
                throw var14;
            }
        } finally {
            if(out != null) {
                out.close();
            }

            if(conn != null) {
                conn.disconnect();
            }

        }

        return rsp;
    }


    private static byte[] getTextEntry(String fieldName, String fieldValue, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\"\r\nContent-Type:text/plain\r\n\r\n");
        entry.append(fieldValue);
        return entry.toString().getBytes(charset);
    }

    private static byte[] getFileEntry(String fieldName, String fileName, String mimeType, String charset) throws IOException {
        StringBuilder entry = new StringBuilder();
        entry.append("Content-Disposition:form-data;name=\"");
        entry.append(fieldName);
        entry.append("\";filename=\"");
        entry.append(fileName);
        entry.append("\"\r\nContent-Type:");
        entry.append(mimeType);
        entry.append("\r\n\r\n");
        return entry.toString().getBytes(charset);
    }

    public static String doGet(String url, Map<String, String> params) throws IOException {
        return doGet(url, params, "UTF-8");
    }

    public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
        HttpURLConnection conn = null;
        String rsp = null;

        try {
            String ctype = "application/x-www-form-urlencoded;charset=" + charset;
            String query = buildQuery(params, charset);

            Map map;
            try {
                conn = getConnection(buildGetUrl(url, query), "GET", ctype);
            } catch (IOException var14) {
                map = getParamsFromUrl(url);
                CloudCangLogger.logCommError(var14, url, (String)map.get("appId"), (String)map.get("methodName"), params);
                throw var14;
            }

            try {
                rsp = getResponseAsString(conn);
            } catch (IOException var13) {
                map = getParamsFromUrl(url);
                CloudCangLogger.logCommError(var13, conn, (String)map.get("appId"), (String)map.get("methodName"), params);
                throw var13;
            }
        } finally {
            if(conn != null) {
                conn.disconnect();
            }

        }

        return rsp;
    }

    private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
        HttpURLConnection conn = null;
        if("https".equals(url.getProtocol())) {
            HttpsURLConnection connHttps = (HttpsURLConnection)url.openConnection();
            connHttps.setSSLSocketFactory(socketFactory);
            connHttps.setHostnameVerifier(verifier);
            conn = connHttps;
        } else {
            conn = (HttpURLConnection)url.openConnection();
        }

        ((HttpURLConnection)conn).setRequestMethod(method);
        ((HttpURLConnection)conn).setDoInput(true);
        ((HttpURLConnection)conn).setDoOutput(true);
        ((HttpURLConnection)conn).setRequestProperty("Accept", "text/xml,text/javascript,text/html");
        ((HttpURLConnection)conn).setRequestProperty("User-Agent", "aop-sdk-java");
        ((HttpURLConnection)conn).setRequestProperty("Content-Type", ctype);
        return (HttpURLConnection)conn;
    }

    private static URL buildGetUrl(String strUrl, String query) throws IOException {
        URL url = new URL(strUrl);
        if(StringUtils.isEmpty(query)) {
            return url;
        } else {
            if(StringUtils.isEmpty(url.getQuery())) {
                if(strUrl.endsWith("?")) {
                    strUrl = strUrl + query;
                } else {
                    strUrl = strUrl + "?" + query;
                }
            } else if(strUrl.endsWith("&")) {
                strUrl = strUrl + query;
            } else {
                strUrl = strUrl + "&" + query;
            }

            return new URL(strUrl);
        }
    }

    public static String buildQuery(Map<String, String> params, String charset) throws IOException {
        if(params != null && !params.isEmpty()) {
            StringBuilder query = new StringBuilder();
            Set<Map.Entry<String, String>> entries = params.entrySet();
            boolean hasParam = false;
            Iterator var5 = entries.iterator();

            while(var5.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry)var5.next();
                String name = (String)entry.getKey();
                String value = (String)entry.getValue();
                if(StringUtils.areNotEmpty(new String[]{name, value})) {
                    if(hasParam) {
                        query.append("&");
                    } else {
                        hasParam = true;
                    }

                    query.append(name).append("=").append(URLEncoder.encode(value, charset));
                }
            }

            return query.toString();
        } else {
            return null;
        }
    }

    protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
        String charset = getResponseCharset(conn.getContentType());
        InputStream es = conn.getErrorStream();
        if(es == null) {
            return getStreamAsString(conn.getInputStream(), charset);
        } else {
            String msg = getStreamAsString(es, charset);
            if(StringUtils.isEmpty(msg)) {
                throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
            } else {
                throw new IOException(msg);
            }
        }
    }

    private static String getStreamAsString(InputStream stream, String charset) throws IOException {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
            StringWriter writer = new StringWriter();
            char[] chars = new char[256];
            boolean var5 = false;

            int count;
            while((count = reader.read(chars)) > 0) {
                writer.write(chars, 0, count);
            }

            String var6 = writer.toString();
            return var6;
        } finally {
            if(stream != null) {
                stream.close();
            }

        }
    }

    private static String getResponseCharset(String ctype) {
        String charset = "UTF-8";
        if(!StringUtils.isEmpty(ctype)) {
            String[] params = ctype.split(";");
            String[] var3 = params;
            int var4 = params.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String param = var3[var5];
                param = param.trim();
                if(param.startsWith("charset")) {
                    String[] pair = param.split("=", 2);
                    if(pair.length == 2 && !StringUtils.isEmpty(pair[1])) {
                        charset = pair[1].trim();
                    }
                    break;
                }
            }
        }

        return charset;
    }

    public static String decode(String value) {
        return decode(value, "UTF-8");
    }

    public static String encode(String value) {
        return encode(value, "UTF-8");
    }

    public static String decode(String value, String charset) {
        String result = null;
        if(!StringUtils.isEmpty(value)) {
            try {
                result = URLDecoder.decode(value, charset);
            } catch (IOException var4) {
                throw new RuntimeException(var4);
            }
        }

        return result;
    }

    public static String encode(String value, String charset) {
        String result = null;
        if(!StringUtils.isEmpty(value)) {
            try {
                result = URLEncoder.encode(value, charset);
            } catch (IOException var4) {
                throw new RuntimeException(var4);
            }
        }

        return result;
    }

    private static Map<String, String> getParamsFromUrl(String url) {
        Map<String, String> map = null;
        if(url != null && url.indexOf(63) != -1) {
            map = splitUrlQuery(url.substring(url.indexOf(63) + 1));
        }

        if(map == null) {
            map = new HashMap();
        }

        return (Map)map;
    }

    public static Map<String, String> splitUrlQuery(String query) {
        Map<String, String> result = new HashMap();
        String[] pairs = query.split("&");
        if(pairs != null && pairs.length > 0) {
            String[] var3 = pairs;
            int var4 = pairs.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String pair = var3[var5];
                String[] param = pair.split("=", 2);
                if(param != null && param.length == 2) {
                    result.put(param[0], param[1]);
                }
            }
        }

        return result;
    }

    private static String buildHiddenFields(Map<String, String> parameters) {
        if(parameters != null && !parameters.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            Set<String> keys = parameters.keySet();
            Iterator var3 = keys.iterator();

            while(var3.hasNext()) {
                String key = (String)var3.next();
                String value = (String)parameters.get(key);
                if(key != null && value != null) {
                    sb.append(buildHiddenField(key, value));
                }
            }

            String result = sb.toString();
            return result;
        } else {
            return "";
        }
    }

    private static String buildHiddenField(String key, String value) {
        StringBuffer sb = new StringBuffer();
        sb.append("<input type=\"hidden\" name=\"");
        sb.append(key);
        sb.append("\" value=\"");
        String a = value.replace("\"", "&quot;");
        sb.append(a).append("\">\n");
        return sb.toString();
    }

    static {
        try {
            ctx = SSLContext.getInstance("TLS");
            ctx.init(new KeyManager[0], new TrustManager[]{new WebUtils.DefaultTrustManager()}, new SecureRandom());
            ctx.getClientSessionContext().setSessionTimeout(15);
            ctx.getClientSessionContext().setSessionCacheSize(1000);
            socketFactory = ctx.getSocketFactory();
        } catch (Exception var1) {
        }

        verifier = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return false;
            }
        };
    }

    private static class DefaultTrustManager implements X509TrustManager {
        private DefaultTrustManager() {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    }
}
