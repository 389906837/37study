package com.cloud.cang.eye.sdk;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import com.cloud.cang.eye.sdk.bean.*;
import com.cloud.cang.eye.sdk.socket.Connection;
import com.cloud.cang.eye.sdk.socket.Packet;
import com.cloud.cang.eye.sdk.socket.TCPClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//1.4.0 由于模型初始化时间较长，将init超时时间改为30秒
//1.4.1 添加时间的统计、添加单实例运行的处理
//1.4.2 解决连接中断导致空指针的问题
public class CAvatarEye {

	private final static Logger logger = LoggerFactory.getLogger(CAvatarEye.class);

	//SDK log level
	private static int logLevel = LogLevel.NOT_SET_LOG_LEVEL;
	
	//SDK version
	private String version = "1.4.2";
	
	//SDK publish time
	private String pubtime = "2018-07-04 12:00:00";	
	
	//connections to goods inventory module
	private ConcurrentMap<String, Connection> connsMap;
	
	//实例化通知接口
	private INotifier notifier = null;
	
	//function return value(string)
	private String strResult;
	
	private long commStart;
	
	private long commEnd;
	
	public CAvatarEye() {
		connsMap = new ConcurrentHashMap<String, Connection>(); 
	}
	
	public CAvatarEye(int logLevel) {
		CAvatarEye.logLevel = logLevel;
		connsMap = new ConcurrentHashMap<String, Connection>(); 
	}
	
	public static void LOG(String info, int level) {
		//level 0 debug 1 info 2 error
		if (CAvatarEye.logLevel < LogLevel.NOT_SET_LOG_LEVEL) {
			if(level == 0) {
				logger.info("[sdk debug log] " + info);
			} else if(level == 1) {
				logger.info("[sdk info log] " + info);
			} else if(level == 2) {
				logger.error("[sdk error log] " + info);
			}
		}
	}
	
	private String verify(Connection conn,int level) {
		ResultBean result = new ResultBean();
		result.setServerCode(conn.getServerCode());
		if (conn.isUpdating()) {
			result.setCodeMsg(EnumError.BUSY_UPDATING);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.verify.isUpdating(), return:" + strResult,1);
			return strResult;
		}
		if(level == 2) {
			if (!conn.isHasInit()) {
				result.setCodeMsg(EnumError.SDK_NOT_INIT);
				
				commEnd = System.currentTimeMillis();
				result.setCommTime(Long.toString(commEnd-commStart));
				strResult = (new Gson()).toJson(result);
				CAvatarEye.LOG("CAvatarEye.verify.isHasInit(), return:" + strResult,1);
				return strResult;
			}
			if (conn.isBusying()) {
				result.setCodeMsg(EnumError.BUSY_CALLING);
				
				commEnd = System.currentTimeMillis();
				result.setCommTime(Long.toString(commEnd-commStart));
				strResult = (new Gson()).toJson(result);
				CAvatarEye.LOG("CAvatarEye.verify.isBusying(), return:" + strResult,1);
				return strResult;
			}
		}
		return null;
	}
	
	
	public String init(String configParam) {		
		CAvatarEye.LOG("CAvatarEye.init(), configParam:" + configParam,0);
		commStart = System.currentTimeMillis();
		ComplexResultBean result = new ComplexResultBean();
		
		List<HostBean> param;
		try {
			param = (new Gson()).fromJson(configParam, new TypeToken<List<HostBean>>(){}.getType());
		} catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.init(), fromJson error: " + e.getMessage(),2);
			param = null;
		}
		
		if (param == null || param.size() <= 0) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.init(), return:" + strResult,1);
			return strResult;
		}		
		
		boolean hasBadConnection = false;
		ArrayList<Connection> tempConns = new ArrayList<Connection>();
		for (HostBean host: param) {
			Connection conn = connsMap.get(host.getServerCode());
			if(null != conn && conn.isConnected()) {//判断服务是否连接
				if(!conn.isHasInit()) {//判读视觉SDK是否初始化
					tempConns.add(conn);
				}
				continue;
			}
			
			conn = new Connection();
			conn.setServerCode(host.getServerCode());
			conn.setSip(host.getSip());
			conn.setPort(host.getPort());
			conn.setExtendAttr(host.getExtendAttr());
			conn.setConnected(false);
			
			
			TCPClient tcpclient = new TCPClient(this);
			tcpclient.connect(host.getSip(), Integer.parseInt(host.getPort()));
			for (int i=0; i<200; i++) {
				if (!tcpclient.isConnected()) {
					delay(50);
				} else {
					break;
				}
			}
			if (!tcpclient.isConnected()) {
				CAvatarEye.LOG("CAvatarEye.init(), not connect",1);
				hasBadConnection = true;
				break;
			}
			conn.setClient(tcpclient);
			conn.setConnected(tcpclient.isConnected());
			tempConns.add(conn);
		}
		
		//has bad connection, disconnect other OK connection.
		if (tempConns.size() <= 0 || hasBadConnection) {
			for (Connection conn: tempConns) {
				if (conn.getClient().isConnected()) {
					conn.getClient().disconnect();
				}
			}
			tempConns.clear();
			result.setCodeMsg(EnumError.CONNECT_INVENTORY_ERROR);
			
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.init(), return:" + strResult,1);
			return strResult;
		}
		//循环添加到集合中
		for (Connection conn: tempConns) {
			connsMap.put(conn.getServerCode(), conn);
		}
		
		result.setCodeMsg(EnumError.SUCCESS);
		
		//send packet to server
		for (Connection conn: tempConns) {
			HostBean hb = new HostBean();
			hb.setSip(conn.getSip());
			hb.setPort(conn.getPort());
			hb.setServerCode(conn.getServerCode());
			hb.setExtendAttr(conn.getExtendAttr());
			String ipport = (new Gson()).toJson(hb);
			Packet p = new Packet((byte)0x01, (byte)0x12, ipport.getBytes());
			conn.getClient().getTransceiver().send(p);			
		}
		
		//recieve data from server
		Connection tempConn = null;
		ArrayList<Connection> resultConns = new ArrayList<Connection>();
		for (Connection conn: tempConns) {
			tempConn = connsMap.get(conn.getServerCode());
			dealwithInitRecieve(tempConn, null);
			connsMap.put(conn.getServerCode(), tempConn);	
			resultConns.add(tempConn);
		}
		//strResult = (new Gson()).toJson(resultConns, new TypeToken<ArrayList<Connection>>(){}.getType());
		//result.setConns(strResult);
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.init(), return:" + strResult,0);
		return strResult;
	}
	
	
	public String initModel(Connection conn) {		
		commStart = System.currentTimeMillis();
		ResultBean result = new ResultBean();
		
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.initModel(), invalid parameter error!",1);
			return strResult;
		}
		result.setServerCode(conn.getServerCode());
		CAvatarEye.LOG("CAvatarEye.initModel(), conn:" + conn.toString(),0);
		String verifyStr = verify(conn, 1);
		if(null != verifyStr) {
			return verifyStr;
		}
		
		if(!conn.isConnected()) {//判断服务是否连接
			TCPClient tcpclient = new TCPClient(this);
			tcpclient.connect(conn.getSip(), Integer.parseInt(conn.getPort()));
			for (int i=0; i<200; i++) {
				if (!tcpclient.isConnected()) {
					delay(50);
				} else {
					break;
				}
			}
			if (!tcpclient.isConnected()) {
				
				result.setCodeMsg(EnumError.CONNECT_ERROR);
				commEnd = System.currentTimeMillis();
				result.setCommTime(Long.toString(commEnd-commStart));
				strResult = (new Gson()).toJson(result);
				CAvatarEye.LOG("CAvatarEye.initModel(), Can’t connect to eye server!",1);
				return strResult;
			}
			conn.setClient(tcpclient);
			conn.setConnected(tcpclient.isConnected());
			connsMap.put(conn.getServerCode(), conn);
		}
		
		result.setCodeMsg(EnumError.SUCCESS);
		
		//send packet to server
		HostBean hb = new HostBean();
		hb.setSip(conn.getSip());
		hb.setPort(conn.getPort());
		hb.setServerCode(conn.getServerCode());
		hb.setExtendAttr(conn.getExtendAttr());
		String data = (new Gson()).toJson(hb);
		Packet packet = new Packet((byte)0x01, (byte)0x12, data.getBytes());
		conn.getClient().getTransceiver().send(packet);			
		
		//recieve data from server
		result = dealwithInitRecieve(conn, result);
		
		if (result.getCode().equals("0")) {
			connsMap.put(conn.getServerCode(), conn);
		}
		
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.initModel(), return:" + strResult,0);
		return strResult;
	}
	
	
	private ResultBean dealwithInitRecieve(Connection conn, ResultBean result) {
		HostBean cb = null;
		for(int i=1; i<=3000; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				
				InitResultBean resultBean = null;
				try {
					resultBean = (new Gson()).fromJson(s, InitResultBean.class);
				} catch (Exception e) {
					CAvatarEye.LOG("CAvatarEye.initModel(), fromJson error:" + e.getMessage(),2);
					if(null != result) {
						result.setCodeMsg(EnumError.SDK_INIT_ERROR);
					}
					conn.setHasInit(false);
					break;
				}
				
				if (!resultBean.getStatus().equals(EnumError.SUCCESS.getCode())) {
					CAvatarEye.LOG("CAvatarEye.initModel(), resultBean.getStatus() is: " + resultBean.getStatus(),2);
					if(null != result) {
						result.setCodeMsg(EnumError.SDK_INIT_ERROR);
					}
					conn.setHasInit(false);
					break;
				}
				
				if (resultBean.getCells() == null || resultBean.getCells().equals("")) {
					CAvatarEye.LOG("CAvatarEye.initModel(), no cell list in module",2);
					if(null != result) {
						result.setCodeMsg(EnumError.SDK_INIT_ERROR);
					}
					conn.setHasInit(false);
					break;
				}
				
				try {
					cb = (new Gson()).fromJson(resultBean.getCells(), HostBean.class);
				} catch (Exception e) {
					CAvatarEye.LOG("CAvatarEye.initModel(), fromJson error:" + e.getMessage(),2);
					result.setCodeMsg(EnumError.SDK_INIT_ERROR);
					conn.setHasInit(false);
					break;
				}
				
				if(!cb.getServerCode().equals(conn.getServerCode())) {
					CAvatarEye.LOG("CAvatarEye.initModel(), serverCode error:" + cb.getServerCode(),2);
					if(null != result) {
						result.setCodeMsg(EnumError.SDK_INIT_ERROR);
					}
					conn.setHasInit(false);
					break;
				}
				conn.setHasInit(true);		
				break;
			}
			delay(10);
		}
		return result;
	}

	public String uninit(String serverCodes) {	
		CAvatarEye.LOG("CAvatarEye.uninit()",0);
		commStart = System.currentTimeMillis();
		ResultBean result = new ResultBean();
		
		ArrayList<Connection> conns = new ArrayList<Connection>();
		Connection tempConn = null;
		if (serverCodes != null && !serverCodes.equals("")) {
			String[] arr = serverCodes.split("#");
			if(arr.length > 0) {
				for(String serverCode : arr) {
					tempConn = connsMap.get(serverCode);
					if(null == tempConn) {
						continue;
					}
					if (null != tempConn && tempConn.isConnected() && tempConn.isHasInit()) {
						conns.add(tempConn);
					} else {
						if(null != tempConn.getClient()) {
							tempConn.getClient().disconnect();
						}
						connsMap.remove(tempConn.getServerCode());
					}
				}
			}
		}
		if (conns.size() <= 0) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.uninit(), invalid parameter connection list is empty",2);
			return strResult;
		}
		ArrayList<Connection> tempConns = new ArrayList<Connection>();
		for (Connection conn: conns) {
			String verifyStr = verify(conn, 2);
			if(null != verifyStr) {
				return verifyStr;
			}
			HostBean hb = new HostBean();
			hb.setServerCode(conn.getServerCode());
			String serverStr = (new Gson()).toJson(hb);
			Packet p = new Packet((byte)0x01, (byte)0x18, serverStr.getBytes());
			conn.getClient().getTransceiver().send(p);
			
			tempConns.add(conn);
		}
		
		result.setCodeMsg(EnumError.SUCCESS);
		
		for (Connection conn: tempConns) {

			for(int i=1; i<=100; i++)  {
				if (conn.getClient().hasNewData()) {
					Packet p = conn.getClient().getNewData();
					if (p == null || p.getData() == null || p.getData().length == 0) {
						break;
					}
					String s = new String(p.getData());
					CAvatarEye.LOG("CAvatarEye.uninit(), result: " + s,0);
					if (!s.equals(EnumError.SUCCESS.getCode())) {
						result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					}
					break;
				}
				delay(30);
			}
			
			conn.getClient().disconnect();
			connsMap.remove(conn.getServerCode());
		}
		
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.uninit(), return:" + strResult,0);
		return strResult;
	}

	public String uninit(Connection conn) {
		CAvatarEye.LOG("CAvatarEye.uninit()",0);
		commStart = System.currentTimeMillis();
		ResultBean result = new ResultBean();

		if (null == conn) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.uninit(), invalid parameter connection is empty",2);
			return strResult;
		}

		String verifyStr = verify(conn, 2);
		if(null != verifyStr) {
			return verifyStr;
		}
		HostBean hb = new HostBean();
		hb.setServerCode(conn.getServerCode());
		String serverStr = (new Gson()).toJson(hb);
		Packet packet = new Packet((byte)0x01, (byte)0x18, serverStr.getBytes());
		conn.getClient().getTransceiver().send(packet);

		result.setCodeMsg(EnumError.SUCCESS);
		for(int i=1; i<=100; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				CAvatarEye.LOG("CAvatarEye.uninit(), result: " + s,0);
				if (!s.equals(EnumError.SUCCESS.getCode())) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
				}
				break;
			}
			delay(30);
		}

		conn.getClient().disconnect();
		connsMap.remove(conn.getServerCode());

		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.uninit(), return:" + strResult,0);
		return strResult;
	}
	
	public String filePathInventoryPos(Connection conn, String modeParam) {
		CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), modeParam:" + modeParam,0);
		commStart = System.currentTimeMillis();
		InventoryResultBeanPos result = new InventoryResultBeanPos();

		ImageCellListBean param = null;
		try {
			param = (new Gson()).fromJson(modeParam, ImageCellListBean.class);
		} catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), fromJson error: " + e.getMessage(),2);
			param = null;
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), invalid parameter!",1);
			return strResult;
		}
		/*Connection conn = null;
		if(param.getServerCode() != null && !param.getServerCode().equals("")) {
			conn = connsMap.get(param.getServerCode());
		}*/
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), invalid parameter error connection is empty!",1);
			return strResult;
		}
		result.setServerCode(conn.getServerCode());
		result.setScode(param.getScode());
		result.setSextends(param.getSextends());
		
		String verifyStr = verify(conn, 2);
		if(null != verifyStr) {
			return verifyStr;
		}

		if (null == param.getImgs() || param.getImgs().isEmpty()) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), invalid parameter error imgs is empty!",1);
			return strResult;
		}
		
		String input = (new Gson()).toJson(param.getImgs());
		CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), socket input:" + input,1);

		//send packet to server
		Packet packet = new Packet((byte)0x01, (byte)0x28, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			conn.setConnected(true);
		}
		conn.getClient().getTransceiver().send(packet);
		
		//recieve data from server,merge cells from multi module
		result.setCodeMsg(EnumError.SUCCESS);
		
		for(int i=1; i<=240; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), from socket server:" + s,2);

				ArrayList<CellGoodsBeanPos> data;
				try {
					data = (new Gson()).fromJson(s, new TypeToken<ArrayList<CellGoodsBeanPos>>(){}.getType());
				} catch (Exception e) {
					result.setCodeMsg(EnumError.DATA_FORMAT_ERROR);
					CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), fromJson error:" + e.getMessage(),2);
					break;
				}
				result.setData(data);
				break;
			}
			delay(50);
		}
		
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), return:" + strResult,0);
		return strResult;
	}


	/***
	 * 获取GPU服务初始化状态
	 * @param conn 服务器连接信息
	 * @return 0 未初始化 1 已初始化
	 */
	public int getInitStatus(Connection conn) {
		Integer result = 0;
		if (conn == null) {
			return result;
		}
		String input = "";
		Packet packet = new Packet((byte)0x01, (byte)0x38, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			for (int i=0; i<200; i++) {
				if (!conn.getClient().isConnected()) {
					delay(50);
				} else {
					break;
				}
			}
			if (conn.getClient().isConnected()) {
				conn.setConnected(true);
				connsMap.put(conn.getServerCode(), conn);//重新添加到连接集合中
			} else {
				return result;
			}
		}
		conn.getClient().getTransceiver().send(packet);

		for(int i=1; i<=100; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				if (s != null && s.trim().length() > 0) {
					try {
						result = Integer.parseInt(s.trim());
					} catch (Exception e) {
						result = 0;
					}
					break;
				}
			}
			delay(50);
		}
		return result;
	}



	public String filePathInventoryPos(String modeParam) {
		CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), modeParam:" + modeParam,0);
		commStart = System.currentTimeMillis();
		InventoryResultBeanPos result = new InventoryResultBeanPos();

		ImageCellListBean param = null;
		try {
			param = (new Gson()).fromJson(modeParam, ImageCellListBean.class);
		} catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), fromJson error: " + e.getMessage(),2);
			param = null;
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), invalid parameter!",1);
			return strResult;
		}
		Connection conn = null;
		if(param.getServerCode() != null && !param.getServerCode().equals("")) {
			conn = connsMap.get(param.getServerCode());
		}
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), invalid parameter error connection is empty!",1);
			return strResult;
		}
		result.setServerCode(conn.getServerCode());
		result.setScode(param.getScode());
		result.setSextends(param.getSextends());

		String verifyStr = verify(conn, 2);
		if(null != verifyStr) {
			return verifyStr;
		}

		if (null == param.getImgs() || param.getImgs().isEmpty()) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), invalid parameter error imgs is empty!",1);
			return strResult;
		}

		String input = (new Gson()).toJson(param.getImgs());
		CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), socket input:" + input,1);

		//send packet to server
		Packet packet = new Packet((byte)0x01, (byte)0x28, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			conn.setConnected(true);
		}
		conn.getClient().getTransceiver().send(packet);

		//recieve data from server,merge cells from multi module
		result.setCodeMsg(EnumError.SUCCESS);

		for(int i=1; i<=240; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), from socket server:" + s,2);

				ArrayList<CellGoodsBeanPos> data;
				try {
					data = (new Gson()).fromJson(s, new TypeToken<ArrayList<CellGoodsBeanPos>>(){}.getType());
				} catch (Exception e) {
					result.setCodeMsg(EnumError.DATA_FORMAT_ERROR);
					CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), fromJson error:" + e.getMessage(),2);
					break;
				}
				result.setData(data);
				break;
			}
			delay(50);
		}

		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.filePathInventoryPos(), return:" + strResult,0);
		return strResult;
	}
	
	public String filePathInventory(Connection conn, String modeParam) {
		CAvatarEye.LOG("CAvatarEye.filePathInventory(), modeParam:" + modeParam,0);
		commStart = System.currentTimeMillis();
		InventoryResultBean result = new InventoryResultBean();

		ImageCellListBean param = null;
		try {
			param = (new Gson()).fromJson(modeParam, ImageCellListBean.class);
		}
		catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.filePathInventory(), fromJson error: " + e.getMessage(),2);
			param = null;
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventory(), invalid parameter!",1);
			return strResult;
		}
		/*Connection conn = null;
		if(param.getServerCode() != null && !param.getServerCode().equals("")) {
			conn = connsMap.get(param.getServerCode());
		}*/
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventory(), invalid parameter error connection is empty!",1);
			return strResult;
		}
		result.setServerCode(conn.getServerCode());
		result.setScode(param.getScode());
		result.setSextends(param.getSextends());
		
		String verifyStr = verify(conn, 2);
		if(null != verifyStr) {
			return verifyStr;
		}

		if (null == param.getImgs() || param.getImgs().isEmpty()) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventory(), invalid parameter error imgs is empty!",1);
			return strResult;
		}
		
		String input = (new Gson()).toJson(param.getImgs());
		CAvatarEye.LOG("CAvatarEye.filePathInventory(), socket input:" + input,1);

		//send packet to server
		Packet packet = new Packet((byte)0x01, (byte)0x34, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			conn.setConnected(true);
		}
		conn.getClient().getTransceiver().send(packet);
		
		//recieve data from server,merge cells from multi module
		result.setCodeMsg(EnumError.SUCCESS);
		
		for(int i=1; i<=240; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				CAvatarEye.LOG("CAvatarEye.filePathInventory(), from socket server:" + s,2);

				ArrayList<CellGoodsBean> data;
				try {
					data = (new Gson()).fromJson(s, new TypeToken<ArrayList<CellGoodsBean>>(){}.getType());
				} catch (Exception e) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					CAvatarEye.LOG("CAvatarEye.filePathInventory(), fromJson error:" + e.getMessage(),2);
					break;
				}
				result.setData(data);
				break;
			}
			delay(50);
		}
		
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.filePathInventory(), return:" + strResult,0);
		return strResult;
	}


	public String filePathInventory(String modeParam) {
		CAvatarEye.LOG("CAvatarEye.filePathInventory(), modeParam:" + modeParam,0);
		commStart = System.currentTimeMillis();
		InventoryResultBean result = new InventoryResultBean();

		ImageCellListBean param = null;
		try {
			param = (new Gson()).fromJson(modeParam, ImageCellListBean.class);
		}
		catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.filePathInventory(), fromJson error: " + e.getMessage(),2);
			param = null;
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventory(), invalid parameter!",1);
			return strResult;
		}
		Connection conn = null;
		if(param.getServerCode() != null && !param.getServerCode().equals("")) {
			conn = connsMap.get(param.getServerCode());
		}
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventory(), invalid parameter error connection is empty!",1);
			return strResult;
		}
		result.setServerCode(conn.getServerCode());
		result.setScode(param.getScode());
		result.setSextends(param.getSextends());

		String verifyStr = verify(conn, 2);
		if(null != verifyStr) {
			return verifyStr;
		}

		if (null == param.getImgs() || param.getImgs().isEmpty()) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.filePathInventory(), invalid parameter error imgs is empty!",1);
			return strResult;
		}

		String input = (new Gson()).toJson(param.getImgs());
		CAvatarEye.LOG("CAvatarEye.filePathInventory(), socket input:" + input,1);

		//send packet to server
		Packet packet = new Packet((byte)0x01, (byte)0x34, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			conn.setConnected(true);
		}
		conn.getClient().getTransceiver().send(packet);

		//recieve data from server,merge cells from multi module
		result.setCodeMsg(EnumError.SUCCESS);

		for(int i=1; i<=240; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				CAvatarEye.LOG("CAvatarEye.filePathInventory(), from socket server:" + s,2);

				ArrayList<CellGoodsBean> data;
				try {
					data = (new Gson()).fromJson(s, new TypeToken<ArrayList<CellGoodsBean>>(){}.getType());
				} catch (Exception e) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					CAvatarEye.LOG("CAvatarEye.filePathInventory(), fromJson error:" + e.getMessage(),2);
					break;
				}
				result.setData(data);
				break;
			}
			delay(50);
		}

		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.filePathInventory(), return:" + strResult,0);
		return strResult;
	}


	public String updateFeatures(String featureParam) {
		CAvatarEye.LOG("CAvatarEye.updateFeatures(), featureParam:" + featureParam,0);
		commStart = System.currentTimeMillis();
		
		ResultBean result = new ResultBean();
		
		
		if (connsMap.size() <= 0) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), invalid parameter connection list is empty",1);
			return strResult;
		}
		
		UpdateBean param;
		try {
			param = (new Gson()).fromJson(featureParam, UpdateBean.class);
		}
		catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), fromJson error: " + e.getMessage(),2);
			param = null;
		}
		
		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), return:" + strResult,0);
			return strResult;
		}
		
		//send packet to server
		Packet p = null;
		for (String serverCode : connsMap.keySet()) {
			Connection conn = connsMap.get(serverCode);
			conn.setUpdating(true);
			connsMap.put(serverCode, conn);
			String input = featureParam;
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), socket input:" + input,0);
			if (null != param.getAddressType() && param.getAddressType().equals("10")) {
				p = new Packet((byte) 0x01, (byte) 0x42, input.getBytes());
			} else {
				p = new Packet((byte) 0x01, (byte) 0x40, input.getBytes());
			}
			conn.getClient().getTransceiver().send(p);

			//接收返回消息
			for(int i=1; i<=600; i++)  {
				if (conn.getClient().hasNewData()) {
					p = conn.getClient().getNewData();
					if (p == null || p.getData() == null || p.getData().length == 0) {
						break;
					}
					String s = new String(p.getData());
					UpdateResultBean resultBean = new UpdateResultBean();
					try {
						resultBean = (new Gson()).fromJson(s, UpdateResultBean.class);
					} catch (Exception e) {
						if(null != notifier) {
							resultBean = new UpdateResultBean();
							resultBean.setServerCode(conn.getServerCode());
							resultBean.setProgress("-1");
							resultBean.setType(20);
							notifier.updateReported((new Gson()).toJson(resultBean));
						}
						CAvatarEye.LOG("CAvatarEye.updateFeatures(), fromJson error:" + e.getMessage(),2);
						break;
					}
					if(null != notifier) {
						notifier.updateReported((new Gson()).toJson(resultBean));
					}
					break;
				}
				delay(1000);
			}
			conn.setUpdating(false);
			connsMap.put(serverCode, conn);
		}
				
		result.setCodeMsg(EnumError.SUCCESS);
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.updateFeatures(), return:" + strResult,0);
		return strResult;
	}


	public String updateFeatures(String featureParam, Connection conn) {
		CAvatarEye.LOG("CAvatarEye.updateFeatures(), featureParam:" + featureParam,0);
		commStart = System.currentTimeMillis();

		ResultBean result = new ResultBean();


		if (connsMap.size() <= 0) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), invalid parameter connection list is empty",1);
			return strResult;
		}

		UpdateBean param;
		try {
			param = (new Gson()).fromJson(featureParam, UpdateBean.class);
		}
		catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), fromJson error: " + e.getMessage(),2);
			param = null;
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), return:" + strResult,0);
			return strResult;
		}

		//send packet to server
		Packet p = null;
		conn.setUpdating(true);
		connsMap.put(conn.getServerCode(), conn);
		String input = featureParam;
		CAvatarEye.LOG("CAvatarEye.updateFeatures(), socket input:" + input,0);
		if (null != param.getAddressType() && param.getAddressType().equals("10")) {
			p = new Packet((byte) 0x01, (byte) 0x42, input.getBytes());
		} else {
			p = new Packet((byte) 0x01, (byte) 0x40, input.getBytes());
		}
		conn.getClient().getTransceiver().send(p);

		//接收消息
		for(int i=1; i<=600; i++)  {
			if (conn.getClient().hasNewData()) {
				p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				UpdateResultBean resultBean = new UpdateResultBean();
				try {
					resultBean = (new Gson()).fromJson(s, UpdateResultBean.class);
				} catch (Exception e) {
					if(null != notifier) {
						resultBean = new UpdateResultBean();
						resultBean.setServerCode(conn.getServerCode());
						resultBean.setProgress("-1");
						resultBean.setType(20);
						notifier.updateReported((new Gson()).toJson(resultBean));
					}
					CAvatarEye.LOG("CAvatarEye.updateFeatures(), fromJson error:" + e.getMessage(),2);
					break;
				}
				if(null != notifier) {
					notifier.updateReported((new Gson()).toJson(resultBean));
				}
				break;
			}
			delay(1000);
		}
		conn.setUpdating(false);
		connsMap.put(conn.getServerCode(), conn);

		result.setCodeMsg(EnumError.SUCCESS);
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.updateFeatures(), return:" + strResult,0);
		return strResult;
	}


	/**
	 * 更新视觉服务
	 * @param serverParam 更新参数
	 * @return
	 */
	public String updateServer(String serverParam) {
		CAvatarEye.LOG("CAvatarEye.updateServer(), serverParam:" + serverParam,0);
		commStart = System.currentTimeMillis();

		ResultBean result = new ResultBean();


		if (connsMap.size() <= 0) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.updateServer(), invalid parameter connection list is empty",1);
			return strResult;
		}

		UpdateBean param;
		try {
			param = (new Gson()).fromJson(serverParam, UpdateBean.class);
		}
		catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.updateServer(), fromJson error: " + e.getMessage(),2);
			param = null;
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.updateServer(), return:" + strResult,0);
			return strResult;
		}

		//send packet to server
		Packet p = null;
		for (String serverCode : connsMap.keySet()) {
			Connection conn = connsMap.get(serverCode);
			conn.setUpdating(true);
			connsMap.put(serverCode, conn);
			String input = serverParam;
			CAvatarEye.LOG("CAvatarEye.updateServer(), socket input:" + input,0);
			if (null != param.getAddressType() && param.getAddressType().equals("10")) {
				p = new Packet((byte) 0x01, (byte) 0x46, input.getBytes());
			} else {
				p = new Packet((byte) 0x01, (byte) 0x44, input.getBytes());
			}
			conn.getClient().getTransceiver().send(p);

			//接收返回消息
			for(int i=1; i<=600; i++)  {
				if (conn.getClient().hasNewData()) {
					p = conn.getClient().getNewData();
					if (p == null || p.getData() == null || p.getData().length == 0) {
						break;
					}
					String s = new String(p.getData());
					CAvatarEye.LOG("CAvatarEye.updateServer(), from socket server: " + s,1);
					UpdateResultBean resultBean = new UpdateResultBean();
					try {
						resultBean = (new Gson()).fromJson(s, UpdateResultBean.class);
					} catch (Exception e) {
						if(null != notifier) {
							resultBean = new UpdateResultBean();
							resultBean.setServerCode(conn.getServerCode());
							resultBean.setProgress("-1");
							resultBean.setType(20);
							notifier.updateReported((new Gson()).toJson(resultBean));
						}
						CAvatarEye.LOG("CAvatarEye.updateServer(), fromJson error:" + e.getMessage(),2);
						break;
					}
					if(null != notifier) {
						notifier.updateReported((new Gson()).toJson(resultBean));
					}
					break;
				}
				delay(1000);
			}
			conn.setUpdating(false);
			connsMap.put(serverCode, conn);
		}

		result.setCodeMsg(EnumError.SUCCESS);
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.updateServer(), return:" + strResult,0);
		return strResult;
	}

	/**
	 * 更新视觉服务
	 * @param serverParam 更新参数
	 * @param conn 连接信息
	 * @return
	 */
	public String updateServer(String serverParam, Connection conn) {
		CAvatarEye.LOG("CAvatarEye.updateServer(), serverParam:" + serverParam,0);
		commStart = System.currentTimeMillis();

		ResultBean result = new ResultBean();


		if (connsMap.size() <= 0) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.updateServer(), invalid parameter connection list is empty",1);
			return strResult;
		}

		UpdateBean param;
		try {
			param = (new Gson()).fromJson(serverParam, UpdateBean.class);
		}
		catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.updateServer(), fromJson error: " + e.getMessage(),2);
			param = null;
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);

			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.updateServer(), return:" + strResult,0);
			return strResult;
		}

		//send packet to server
		Packet p = null;
		conn.setUpdating(true);
		connsMap.put(conn.getServerCode(), conn);
		String input = serverParam;
		CAvatarEye.LOG("CAvatarEye.updateServer(), socket input:" + input,0);
		if (null != param.getAddressType() && param.getAddressType().equals("10")) {
			p = new Packet((byte) 0x01, (byte) 0x46, input.getBytes());
		} else {
			p = new Packet((byte) 0x01, (byte) 0x44, input.getBytes());
		}
		conn.getClient().getTransceiver().send(p);

		//接收消息
		for(int i=1; i<=600; i++)  {
			if (conn.getClient().hasNewData()) {
				p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				UpdateResultBean resultBean = new UpdateResultBean();
				try {
					resultBean = (new Gson()).fromJson(s, UpdateResultBean.class);
				} catch (Exception e) {
					if(null != notifier) {
						resultBean = new UpdateResultBean();
						resultBean.setServerCode(conn.getServerCode());
						resultBean.setProgress("-1");
						resultBean.setType(20);
						notifier.updateReported((new Gson()).toJson(resultBean));
					}
					CAvatarEye.LOG("CAvatarEye.updateServer(), fromJson error:" + e.getMessage(),2);
					break;
				}
				if(null != notifier) {
					notifier.updateReported((new Gson()).toJson(resultBean));
				}
				break;
			}
			delay(1000);
		}
		conn.setUpdating(false);
		connsMap.put(conn.getServerCode(), conn);

		result.setCodeMsg(EnumError.SUCCESS);
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.updateServer(), return:" + strResult,0);
		return strResult;
	}


	public String getVersion() {	
		CAvatarEye.LOG("CAvatarEye.getVersion()",0);
		
		VersionBean result = new VersionBean();		
		result.setVersion(version);		
		result.setPubtime(pubtime);	
		
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.getVersion(), return:" + strResult,1);
		return strResult;
	}
	
	public int getVersionNumber() {
		String version = getVersion();
		
		VersionBean result;
		try {
			result = (new Gson()).fromJson(version, VersionBean.class);
		}
		catch (JsonSyntaxException e) {
			return 1;
		}
		
		try {
			String[] newv = result.getVersion().split("\\.");
			int n1 = Integer.parseInt(newv[0]);
			int n2 = Integer.parseInt(newv[1]);
			int n3 = Integer.parseInt(newv[2]);
			
			return 100*n1+10*n2+n3;
		} catch (Exception e) {
			return 1;
		}
	}
	
	static void delay(int msecond) {
		try {
			Thread.sleep(msecond);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public void setNotifier(INotifier notifier) {	
		CAvatarEye.LOG("CAvatarEye.setNotifier()",0);
		if (notifier != null) {
			this.notifier = notifier;
		}
	}

	public INotifier getNotifier() {
		return notifier;
	}

	public ConcurrentMap<String, Connection> getConnsMap() {
		return connsMap;
	}

	public void setConnsMap(ConcurrentMap<String, Connection> connsMap) {
		this.connsMap = connsMap;
	}
	
}
