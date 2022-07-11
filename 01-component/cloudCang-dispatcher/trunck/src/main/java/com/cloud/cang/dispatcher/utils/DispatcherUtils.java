package com.cloud.cang.dispatcher.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

public class DispatcherUtils {

	public static Logger logger = LoggerFactory
			.getLogger(DispatcherUtils.class);
	
	 static Pattern pattern = Pattern.compile( "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$" );

	private static String localIP;
	
	//上下文
	private static String appContext;
	
	//端口号
	private static Integer port;

	public static String getLocalAddress() throws SocketException {
		if (!StringUtils.isEmpty(localIP)) {
			return localIP;
		}
		if (StringUtils.isEmpty(localIP)) {
			// 取第一个ip
			Collection<InetAddress> ips = getAllLocalAddresses();
			if (ips.size() > 0) {
				localIP = ips.iterator().next().getHostAddress();
			}
		}
		return localIP;
	}

	public static String getIpByHostName(String hostName) throws SocketException {
		Collection<InetAddress> ips = getAllLocalAddresses();
		for (InetAddress ip : ips) {
			if(ip.getHostName().equals(hostName)){
				return ip.getHostAddress();
			}
		}
		return "";
	}

	public static String getAppContext() {
		return appContext;
	}

	public static void setAppContext(String appContext) {
		DispatcherUtils.appContext = appContext;
	}

	public static Integer getPort() {
		return port==null?8080:port;
	}

	public static void setPort(Integer port) {
		DispatcherUtils.port = port;
	}

	public static Integer getSslPort() {
		return 0;
	}

	public static Collection<InetAddress> getAllLocalAddresses()
			throws SocketException {
		List<InetAddress> listAdr = Lists.newArrayList();
		Enumeration<NetworkInterface> nifs = NetworkInterface
				.getNetworkInterfaces();
		if (nifs == null)
			return listAdr;

		while (nifs.hasMoreElements()) {
			NetworkInterface nif = nifs.nextElement();
			Enumeration<InetAddress> adrs = nif.getInetAddresses();
			while (adrs.hasMoreElements()) {
				InetAddress adr = adrs.nextElement();
				if ((adr != null) && !adr.isLoopbackAddress()
						&& (nif.isPointToPoint() || !adr.isLinkLocalAddress())
						&& !nif.isVirtual() && nif.getParent() == null && !pattern.matcher(adr.getHostName()).matches()) {
					if(adr.getHostAddress().indexOf(":")!=-1)continue;
					listAdr.add(adr);
				}
			}
		}
		return listAdr;
	}

	public static String getIntallDir() {
		Properties properties = System.getProperties();
		String sdir = properties.getProperty("user.dir");
		int npos;

		if ((npos = sdir.indexOf("bin")) != -1) {
			sdir = sdir.substring(0, npos);
		}
		return sdir;
	}

	public static String getCurrentVersion() {
		Class callClass = getCallClass();
		String version = null;
		if (callClass != null) {
			version = callClass.getPackage().getImplementationVersion();
			if (StringUtils.isEmpty(version)) {
				version = callClass.getPackage().getSpecificationVersion();
			}
		}
		if (StringUtils.isEmpty(version)) {
			version = "unknow";
		}
		return version;
	}

	public static Class getCallClass() {
		try {
			StackTraceElement[] elements = new Exception().getStackTrace();
			for (StackTraceElement e : elements) {
				String className = e.getClassName();
				if (className
						.indexOf("com.fable.insightview.monitor.dispatcher") < 0
						&& className.indexOf("com.fable.insightview.") >= 0) {
					return Class.forName(className);
				}
			}
		} catch (Exception e) {
			logger.error("获取调用类失败！", e);
		}
		return null;
	}

	public static String getProcessID() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		return name.split("@")[0];
	}

	public static String getServerName() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		String[] n = name.split("@");
		if (n.length > 1) {
			return n[1];
		}
		return null;
	}

	public static void main(String[] args) throws SocketException {
		// DispatcherUtils.getCurrentVersion();
		System.out.print(DispatcherUtils.getAllLocalAddresses());
	}
}
