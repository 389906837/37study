package com.cloud.cang.api.antbox.util;


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IpUtil {

    /**
     * 获取服务地址(IP)
     */
    public static String getServerAddress(String port, String env) {
        return getLocalIp(env) + ":" + port;
    }

    /**
     * 获取服务地址(hostname)
     */
    public static String getHostAddress(String port) {
        return getHostName() + ":" + port;
    }

    /**
     * 获取本地ip
     */
    public static String getLocalIp(String env) {

        String ip = "127.0.0.1";
        String networkInterfaceName="";

        /*switch (env) {
            case Config.ENV_DEV:
                networkInterfaceName = "enp2s0"; // 开发机器 networkInterfaceName
                break;
            case Config.ENV_PROD:
            case Config.ENV_UAT:
                networkInterfaceName = "eth0";// 生产环境、uat环境 机器 networkInterfaceName
                break;
            default:
                return ip;
        }*/
        Enumeration<NetworkInterface> en = null;
        try {
            en = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        if (en == null) {
            throw new RuntimeException("get networkInterface is null");
        }

        NetworkInterface networkInterface = null;
        while (en.hasMoreElements()) {
            networkInterface = en.nextElement();
            if (networkInterface.getDisplayName().equals(networkInterfaceName)) {
                break;
            }
        }
        if (networkInterface == null) {
            throw new RuntimeException("get networkInterface is null");
        }

        Enumeration<InetAddress> inetAddressEnumeration = networkInterface.getInetAddresses();
        while (inetAddressEnumeration.hasMoreElements()) {
            InetAddress inetAddress = inetAddressEnumeration.nextElement();
            ip = inetAddress.getHostAddress();
        }
        return ip;
    }

    public static String getHostName() {
        try {
            return (InetAddress.getLocalHost()).getHostName();
        } catch (UnknownHostException uhe) {
            String host = uhe.getMessage(); // host = "hostname: hostname"
            if (host != null) {
                int colon = host.indexOf(':');
                if (colon > 0) {
                    return host.substring(0, colon);
                }
            }
            return "";
        }
    }

    /**
     * 检验是否ip地址
     *
     * @param addr
     * @return
     */
    public static boolean validateIpAddress(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }

        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(addr);
        boolean ipAddress = mat.find();
        return ipAddress;
    }

}
