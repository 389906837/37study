package com.cloud.cang.open.sdk.common;

import com.cloud.cang.open.sdk.response.BaseResponse;
import com.cloud.cang.open.sdk.util.BaseHashMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.impl.Jdk14Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;

/**
 * @version 1.0
 * @ClassName: cloudCangBranch
 * @Description:
 * @Author: zhouhong
 * @Date: 2018/9/3 17:19
 */
public class CloudCangLogger {

    private static final Log clog = LogFactory.getLog("sdk.comm.err");
    private static final Log blog = LogFactory.getLog("sdk.biz.err");
    private static String osName = System.getProperties().getProperty("os.name");
    private static String ip = null;
    private static boolean needEnableLogger = true;

    public CloudCangLogger() {
    }

    public static void setNeedEnableLogger(boolean needEnableLogger) {
        needEnableLogger = needEnableLogger;
    }

    public static String getIp() {
        if(ip == null) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (Exception var1) {
                var1.printStackTrace();
            }
        }

        return ip;
    }

    public static void setIp(String ip) {
        ip = ip;
    }

    public static void logCommError(Exception e, HttpURLConnection conn, String appKey, String method, byte[] content) {
        if(needEnableLogger) {
            String contentString = null;

            try {
                contentString = new String(content, "UTF-8");
                logCommError(e, conn, appKey, method, contentString);
            } catch (Exception var7) {
                var7.printStackTrace();
            }

        }
    }

    public static void logCommError(Exception e, String url, String appKey, String method, byte[] content) {
        if(needEnableLogger) {
            String contentString = null;

            try {
                contentString = new String(content, "UTF-8");
                logCommError(e, url, appKey, method, contentString);
            } catch (UnsupportedEncodingException var7) {
                var7.printStackTrace();
            }

        }
    }

    public static void logCommError(Exception e, HttpURLConnection conn, String appKey, String method, Map<String, String> params) {
        if(needEnableLogger) {
            _logCommError(e, conn, (String)null, appKey, method, params);
        }
    }

    public static void logCommError(Exception e, String url, String appKey, String method, Map<String, String> params) {
        if(needEnableLogger) {
            _logCommError(e, (HttpURLConnection)null, url, appKey, method, params);
        }
    }

    private static void logCommError(Exception e, HttpURLConnection conn, String appKey, String method, String content) {
        Map<String, String> params = parseParam(content);
        _logCommError(e, conn, (String)null, appKey, method, params);
    }

    private static void logCommError(Exception e, String url, String appKey, String method, String content) {
        Map<String, String> params = parseParam(content);
        _logCommError(e, (HttpURLConnection)null, url, appKey, method, params);
    }

    private static void _logCommError(Exception e, HttpURLConnection conn, String url, String appKey, String method, Map<String, String> params) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String sdkName = "alipay-sdk-java-dynamicVersionNo";
        String urlStr = null;
        String rspCode = "";
        if(conn != null) {
            try {
                urlStr = conn.getURL().toString();
                rspCode = "HTTP_ERROR_" + conn.getResponseCode();
            } catch (IOException var11) {
                ;
            }
        } else {
            urlStr = url;
            rspCode = "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(df.format(new Date()));
        sb.append("~-~");
        sb.append(method);
        sb.append("~-~");
        sb.append(appKey);
        sb.append("~-~");
        sb.append(getIp());
        sb.append("~-~");
        sb.append(osName);
        sb.append("~-~");
        sb.append(sdkName);
        sb.append("~-~");
        sb.append(urlStr);
        sb.append("~-~");
        sb.append(rspCode);
        sb.append("~-~");
        sb.append((e.getMessage() + "").replaceAll("\r\n", " "));
        clog.error(sb.toString());
    }

    private static Map<String, String> parseParam(String contentString) {
        Map<String, String> params = new HashMap();
        if(contentString != null && !contentString.trim().equals("")) {
            String[] paramsArray = contentString.split("\\&");
            if(paramsArray != null) {
                String[] var3 = paramsArray;
                int var4 = paramsArray.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    String param = var3[var5];
                    String[] keyValue = param.split("=");
                    if(keyValue != null && keyValue.length == 2) {
                        params.put(keyValue[0], keyValue[1]);
                    }
                }
            }

            return params;
        } else {
            return params;
        }
    }

    public static void logBizDebug(String rsp) {
        if(needEnableLogger) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            StringBuilder sb = new StringBuilder();
            sb.append(df.format(new Date()));
            sb.append("~-~");
            sb.append(rsp);
            if(blog.isDebugEnabled()) {
                blog.debug(sb.toString());
            }

        }
    }

    public static void logBizError(String rsp) {
        if(needEnableLogger) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            StringBuilder sb = new StringBuilder();
            sb.append(df.format(new Date()));
            sb.append("~-~");
            sb.append(rsp);
            blog.error(sb.toString());
        }
    }

    public static void logBizError(Throwable t) {
        if(needEnableLogger) {
            blog.error(t);
        }
    }

    public static void logErrorScene(Map<String, Object> rt, BaseResponse tRsp, String appSecret) {
        if(needEnableLogger) {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            StringBuilder sb = new StringBuilder();
            sb.append("ErrorScene");
            sb.append("~-~");
            sb.append(tRsp.getCode());
            sb.append("~-~");
            sb.append(tRsp.getSubCode());
            sb.append("~-~");
            sb.append(ip);
            sb.append("~-~");
            sb.append(osName);
            sb.append("~-~");
            sb.append(df.format(new Date()));
            sb.append("~-~");
            sb.append("ProtocalMustParams:");
            appendLog((BaseHashMap)rt.get("protocalMustParams"), sb);
            sb.append("~-~");
            sb.append("ProtocalOptParams:");
            appendLog((BaseHashMap)rt.get("protocalOptParams"), sb);
            sb.append("~-~");
            sb.append("ApplicationParams:");
            appendLog((BaseHashMap)rt.get("textParams"), sb);
            sb.append("~-~");
            sb.append("Body:");
            sb.append((String)rt.get("rsp"));
            blog.error(sb.toString());
        }
    }

    private static void appendLog(BaseHashMap map, StringBuilder sb) {
        boolean first = true;
        Set<Map.Entry<String, String>> set = map.entrySet();

        Map.Entry entry;
        for(Iterator var4 = set.iterator(); var4.hasNext(); sb.append((String)entry.getKey()).append("=").append((String)entry.getValue())) {
            entry = (Map.Entry)var4.next();
            if(!first) {
                sb.append("&");
            } else {
                first = false;
            }
        }

    }

    public static Boolean isBizDebugEnabled() {
        return Boolean.valueOf(blog.isDebugEnabled());
    }

    public static void setJDKDebugEnabled(Boolean isEnabled) {
        if(blog instanceof Jdk14Logger) {
            Jdk14Logger logger = (Jdk14Logger)blog;
            if(isEnabled.booleanValue()) {
                logger.getLogger().setLevel(Level.FINE);
                Handler consoleHandler = new ConsoleHandler();
                consoleHandler.setLevel(Level.FINE);
                logger.getLogger().addHandler(consoleHandler);
            } else {
                logger.getLogger().setLevel(Level.INFO);
            }
        }

    }
}
