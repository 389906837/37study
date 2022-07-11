package com.cloud.cang.bitmain;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.cloud.cang.bitmain.bean.*;
import com.cloud.cang.bitmain.socket.Connection;
import com.cloud.cang.bitmain.socket.Packet;
import com.cloud.cang.bitmain.socket.TCPClient;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


//1.4.0 由于模型初始化时间较长，将init超时时间改为30秒
//1.4.1 添加时间的统计、添加单实例运行的处理
//1.4.2 解决连接中断导致空指针的问题
public class CAvatarEye {


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
				System.out.println("[sdk debug log] " + info);
			} else if(level == 1) {
				System.out.println("[sdk info log] " + info);
			} else if(level == 2) {
				System.err.println("[sdk error log] " + info);
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

	//	初始化，此操作内部执行二个操作，建立SOCKET和初始化模型
	public String init(String configParam) {		
		CAvatarEye.LOG("CAvatarEye.init(), configParam:" + configParam,0);
		commStart = System.currentTimeMillis();
		ComplexResultBean result = new ComplexResultBean();

		//Object转json
		List<HostBean> param;
		try {
			param = (new Gson()).fromJson(configParam, new TypeToken<List<HostBean>>(){}.getType());
		} catch (JsonSyntaxException e) {
			CAvatarEye.LOG("CAvatarEye.init(), fromJson error: " + e.getMessage(),2);
			param = null;
		}

		//参数判断
		if (param == null || param.size() <= 0) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.init(), return:" + strResult,1);
			return strResult;
		}		

		//获取存在的链接,如果链接存在则获取以存在的链接
		boolean hasBadConnection = false;
		ArrayList<Connection> tempConns = new ArrayList<Connection>();
		for (HostBean host: param) {
			Connection conn = connsMap.get(host.getServerCode());
			if(null != conn && conn.isConnected()) {//判断服务是否连接
				/*if(!conn.isHasInit()) {//判读视觉SDK是否初始化
					tempConns.add(conn);
				}
				continue;*/
				if(conn.isHasInit()) {
					conn.getClient().disconnect();
				}
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
		result.setCodeMsg(EnumError.UNKNOWN_ERROR);
		//recieve data from server
		Connection tempConn = null;
		ArrayList<Connection> resultConns = new ArrayList<Connection>();
		for (Connection conn: tempConns) {
			tempConn = connsMap.get(conn.getServerCode());
			dealwithInitRecieve(tempConn, result);
			connsMap.put(conn.getServerCode(), tempConn);	
			resultConns.add(tempConn);
		}
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.init(), return:" + strResult,0);
		return strResult;
	}

	//处理init的返回结果
	private ResultBean dealwithInitRecieve(Connection conn, ResultBean result) {
		for(int i=1; i<=3000; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					break;
				}
				String s = new String(p.getData());
				if(null != s && s.equals(EnumError.SUCCESS.getCode())) {
					result.setCodeMsg(EnumError.SUCCESS);
					conn.setHasInit(true);
					break;
				} else {
					result.setCodeMsg(EnumError.SDK_INIT_ERROR);
					conn.setHasInit(false);
					break;
				}
			}
			delay(10);
		}
		return result;
	}

	//反初始化，释放链接和模型
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
			Packet p = new Packet((byte)0x01, (byte)0x14, serverStr.getBytes());
			conn.getClient().getTransceiver().send(p);
			
			tempConns.add(conn);
		}
		

		result.setCodeMsg(EnumError.UNKNOWN_ERROR);
		for (Connection conn: tempConns) {
			for(int i=1; i<=100; i++)  {
				if (conn.getClient().hasNewData()) {
					Packet p = conn.getClient().getNewData();
					if (p == null || p.getData() == null || p.getData().length == 0) {
						result.setCodeMsg(EnumError.UNKNOWN_ERROR);
						break;
					}
					String s = new String(p.getData());
					CAvatarEye.LOG("CAvatarEye.uninit(), result: " + s,0);
					if (s == null || !s.equals(EnumError.SUCCESS.getCode())) {
						result.setCodeMsg(EnumError.UNKNOWN_ERROR);
						break;
					}
					result.setCodeMsg(EnumError.SUCCESS);
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
		Packet packet = new Packet((byte)0x01, (byte)0x28, input.getBytes());
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

	/**
	 * 根据图片路径识别
	 * @param conn 识别服务器连接信息
	 * @param modeParam 图片数据
	 * @return
	 */
	public String imagePathInventory(Connection conn, String modeParam) {
		CAvatarEye.LOG("CAvatarEye.imagePathInventory(), modeParam:" + modeParam,0);
		commStart = System.currentTimeMillis();
		InventoryResultBeanPos result = new InventoryResultBeanPos();

		ImageCellListBean param = null;
		try {
			param = (new Gson()).fromJson(modeParam, ImageCellListBean.class);
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEye.imagePathInventory(), fromJson error: " + e.getMessage(),2);
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.imagePathInventory(), invalid parameter!",1);
			return strResult;
		}
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.imagePathInventory(), invalid parameter error connection is empty!",1);
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
			CAvatarEye.LOG("CAvatarEye.imagePathInventory(), invalid parameter error imgs is empty!",1);
			return strResult;
		}
		
		String input = (new Gson()).toJson(param.getImgs());
		CAvatarEye.LOG("CAvatarEye.imagePathInventory(), socket input:" + input,1);

		//send packet to server
		Packet packet = new Packet((byte)0x01, (byte)0x26, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			conn.setConnected(true);
		}
		conn.getClient().getTransceiver().send(packet);
		
		//recieve data from server,merge cells from multi module

		result.setCodeMsg(EnumError.UNKNOWN_ERROR);
		for(int i=1; i<=240; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					break;
				}
				String s = new String(p.getData());
				CAvatarEye.LOG("CAvatarEye.imagePathInventory(), from socket server:" + s,2);

				ArrayList<CellGoodsBeanPos> data;
				try {
					data = (new Gson()).fromJson(s, new TypeToken<ArrayList<CellGoodsBeanPos>>(){}.getType());
				} catch (Exception e) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					CAvatarEye.LOG("CAvatarEye.imagePathInventory(), fromJson error:" + e.getMessage(),2);
					break;
				}
				result.setCodeMsg(EnumError.SUCCESS);
				result.setData(data);
				break;
			}
			delay(50);
		}
		
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.imagePathInventory(), return:" + strResult,0);
		return strResult;
	}


	/**
	 * 获取网络摄像头照片识别图片
	 * @param conn 识别服务器连接信息
	 * @param modeParam 识别参数
	 * @return
	 */
	public String inventory(Connection conn, String modeParam) {
		CAvatarEye.LOG("CAvatarEye.inventory(), modeParam:" + modeParam,0);
		commStart = System.currentTimeMillis();
		InventoryResultBean result = new InventoryResultBean();

		ImageCellListBean param = null;
		try {
			param = (new Gson()).fromJson(modeParam, ImageCellListBean.class);
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEye.inventory(), fromJson error: " + e.getMessage(),2);
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.inventory(), invalid parameter!",1);
			return strResult;
		}
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.inventory(), invalid parameter error connection is empty!",1);
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
			CAvatarEye.LOG("CAvatarEye.inventory(), invalid parameter error imgs is empty!",1);
			return strResult;
		}

		String input = (new Gson()).toJson(param.getImgs());
		CAvatarEye.LOG("CAvatarEye.inventory(), socket input:" + input,1);

		//send packet to server
		Packet packet = new Packet((byte)0x01, (byte)0x24, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			conn.setConnected(true);
		}
		conn.getClient().getTransceiver().send(packet);

		//recieve data from server,merge cells from multi module

		result.setCodeMsg(EnumError.UNKNOWN_ERROR);
		for(int i=1; i<=240; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					break;
				}
				String s = new String(p.getData());
				if(!s.startsWith("[")){
					continue;
				}
				CAvatarEye.LOG("CAvatarEye.inventory(), from socket server:" + s,2);

				ArrayList<CellGoodsBean> data;
				try {
					data = (new Gson()).fromJson(s, new TypeToken<ArrayList<CellGoodsBean>>(){}.getType());
				} catch (Exception e) {
					result.setCodeMsg(EnumError.DATA_FORMAT_ERROR);
					CAvatarEye.LOG("CAvatarEye.inventory(), fromJson error:" + e.getMessage(),2);
					break;
				}
				result.setCodeMsg(EnumError.SUCCESS);
				result.setData(data);
				break;
			}
			delay(50);
		}

		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.inventory(), return:" + strResult,0);
		return strResult;
	}


	/**
	 * 获取网络摄像头照片识别图片 新版
	 * @param conn 识别服务器连接信息
	 * @param modeParam 识别参数
	 * @return
	 */
	public String inventoryNew(Connection conn, String modeParam) {
		CAvatarEye.LOG("CAvatarEye.inventory(), modeParam:" + modeParam,0);
		commStart = System.currentTimeMillis();
		InventoryResultBean result = new InventoryResultBean();

		ImageInfoBean param = null;
		try {
			param = (new Gson()).fromJson(modeParam, ImageInfoBean.class);
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEye.inventoryNew(), fromJson error: " + e.getMessage(),2);
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.inventoryNew(), invalid parameter!",1);
			return strResult;
		}
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.inventoryNew(), invalid parameter error connection is empty!",1);
			return strResult;
		}
		result.setServerCode(conn.getServerCode());
		result.setScode(param.getScode());
		result.setSextends(param.getSextends());

		String verifyStr = verify(conn, 2);
		if(null != verifyStr) {
			return verifyStr;
		}

		if (null == param.getParam() || param.getParam().isEmpty()) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.inventoryNew(), invalid parameter error imgs is empty!",1);
			return strResult;
		}

		String input = (new Gson()).toJson(param);
		CAvatarEye.LOG("CAvatarEye.inventoryNew(), socket input:" + input,1);

		//send packet to server
		Packet packet = new Packet((byte)0x01, (byte)0x30, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			conn.setConnected(true);
		}
		conn.getClient().getTransceiver().send(packet);

		//recieve data from server,merge cells from multi module

		result.setCodeMsg(EnumError.UNKNOWN_ERROR);
		for(int i=1; i<=240; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					break;
				}
				String s = new String(p.getData());
				if(!s.startsWith("[")){
					continue;
				}
				CAvatarEye.LOG("CAvatarEye.inventoryNew(), from socket server:" + s,2);

				ArrayList<CellGoodsBean> data;
				try {
					data = (new Gson()).fromJson(s, new TypeToken<ArrayList<CellGoodsBean>>(){}.getType());
				} catch (Exception e) {
					result.setCodeMsg(EnumError.DATA_FORMAT_ERROR);
					CAvatarEye.LOG("CAvatarEye.inventoryNew(), fromJson error:" + e.getMessage(),2);
					break;
				}
				result.setCodeMsg(EnumError.SUCCESS);
				result.setData(data);
				break;
			}
			delay(50);
		}

		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.inventoryNew(), return:" + strResult,0);
		return strResult;
	}



	/**
	 * 获取网络摄像头照片识别图片
	 * @param conn 识别服务器连接信息
	 * @param modeParam 识别参数
	 * @return
	 */
	public String inventoryPos(Connection conn, String modeParam) {
		CAvatarEye.LOG("CAvatarEye.inventoryPos(), modeParam:" + modeParam,0);
		commStart = System.currentTimeMillis();
		InventoryResultBeanPos result = new InventoryResultBeanPos();

		ImageCellListBean param = null;
		try {
			param = (new Gson()).fromJson(modeParam, ImageCellListBean.class);
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEye.inventoryPos(), fromJson error: " + e.getMessage(),2);
		}

		if (param == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.inventoryPos(), invalid parameter!",1);
			return strResult;
		}
		if (conn == null) {
			result.setCodeMsg(EnumError.INVALID_PARAM);
			commEnd = System.currentTimeMillis();
			result.setCommTime(Long.toString(commEnd-commStart));
			strResult = (new Gson()).toJson(result);
			CAvatarEye.LOG("CAvatarEye.inventoryPos(), invalid parameter error connection is empty!",1);
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
			CAvatarEye.LOG("CAvatarEye.inventoryPos(), invalid parameter error imgs is empty!",1);
			return strResult;
		}

		String input = (new Gson()).toJson(param.getImgs());
		CAvatarEye.LOG("CAvatarEye.inventoryPos(), socket input:" + input,1);

		//send packet to server
		Packet packet = new Packet((byte)0x01, (byte)0x24, input.getBytes());
		if (conn.getClient() == null || !conn.getClient().isConnected()) {
			conn.getClient().connectOnce();
			conn.setConnected(true);
		}
		conn.getClient().getTransceiver().send(packet);

		//recieve data from server,merge cells from multi module

		result.setCodeMsg(EnumError.UNKNOWN_ERROR);
		for(int i=1; i<=240; i++)  {
			if (conn.getClient().hasNewData()) {
				Packet p = conn.getClient().getNewData();
				if (p == null || p.getData() == null || p.getData().length == 0) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					break;
				}
				String s = new String(p.getData());
				CAvatarEye.LOG("CAvatarEye.inventoryPos(), from socket server:" + s,2);

				ArrayList<CellGoodsBeanPos> data;
				try {
					data = (new Gson()).fromJson(s, new TypeToken<ArrayList<CellGoodsBeanPos>>(){}.getType());
				} catch (Exception e) {
					result.setCodeMsg(EnumError.UNKNOWN_ERROR);
					CAvatarEye.LOG("CAvatarEye.inventoryPos(), fromJson error:" + e.getMessage(),2);
					break;
				}
				result.setCodeMsg(EnumError.SUCCESS);
				result.setData(data);
				break;
			}
			delay(50);
		}

		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.inventoryPos(), return:" + strResult,0);
		return strResult;
	}

	/**
	 * 模型更新
	 * @param featureParam 更新参数
	 * @return
	 */
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
		
		UpdateBean param = null;
		try {
			param = (new Gson()).fromJson(featureParam, UpdateBean.class);
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), fromJson error: " + e.getMessage(),2);
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
			//conn.setUpdating(true);
			//connsMap.put(serverCode, conn);
			String input = featureParam;
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), socket input:" + input,0);
			if (null != param.getAddressType() && param.getAddressType().equals("10")) {
				p = new Packet((byte) 0x01, (byte) 0x18, input.getBytes());
			} else {
				p = new Packet((byte) 0x01, (byte) 0x16, input.getBytes());
			}
			conn.getClient().getTransceiver().send(p);

			//接收返回消息
			for(int i=1; i<=(600*3); i++)  {
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
							/*conn.setHasInit(false);
							conn.setBusying(false);
							conn.setUpdating(false);
							connsMap.put(conn.getServerCode(), conn);*/
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
			//conn.setUpdating(false);
			connsMap.put(serverCode, conn);
		}
				
		result.setCodeMsg(EnumError.SUCCESS);
		commEnd = System.currentTimeMillis();
		result.setCommTime(Long.toString(commEnd-commStart));
		strResult = (new Gson()).toJson(result);
		CAvatarEye.LOG("CAvatarEye.updateFeatures(), return:" + strResult,0);
		return strResult;
	}

	/**
	 * 模型更新
	 * @param featureParam 更新参数
	 * @param conn 连接信息
	 * @return
	 */
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

		UpdateBean param = null;
		try {
			param = (new Gson()).fromJson(featureParam, UpdateBean.class);
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEye.updateFeatures(), fromJson error: " + e.getMessage(),2);
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
		//conn.setUpdating(true);
		//connsMap.put(conn.getServerCode(), conn);
		String input = featureParam;
		CAvatarEye.LOG("CAvatarEye.updateFeatures(), socket input:" + input,0);
		if (null != param.getAddressType() && param.getAddressType().equals("10")) {
			p = new Packet((byte) 0x01, (byte) 0x18, input.getBytes());
		} else {
			p = new Packet((byte) 0x01, (byte) 0x16, input.getBytes());
		}
		conn.getClient().getTransceiver().send(p);

		//接收消息
		for(int i=1; i<=(600*3); i++)  {
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
						/*conn.setHasInit(false);
						conn.setBusying(false);
						conn.setUpdating(false);
						connsMap.put(conn.getServerCode(), conn);*/
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
		/*conn.setUpdating(false);
		connsMap.put(conn.getServerCode(), conn);*/

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

		UpdateBean param = null;
		try {
			param = (new Gson()).fromJson(serverParam, UpdateBean.class);
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEye.updateServer(), fromJson error: " + e.getMessage(),2);
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
			/*conn.setUpdating(true);
			connsMap.put(serverCode, conn);*/
			String input = serverParam;
			CAvatarEye.LOG("CAvatarEye.updateServer(), socket input:" + input,0);
			if (null != param.getAddressType() && param.getAddressType().equals("10")) {
				p = new Packet((byte) 0x01, (byte) 0x22, input.getBytes());
			} else {
				p = new Packet((byte) 0x01, (byte) 0x20, input.getBytes());
			}
			conn.getClient().getTransceiver().send(p);

			//接收返回消息
			for(int i=1; i<=(600*3); i++)  {
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
							resultBean.setType(10);
							/*conn.setHasInit(false);
							conn.setBusying(false);
							conn.setUpdating(false);
							connsMap.put(conn.getServerCode(), conn);*/
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
			/*conn.setUpdating(false);
			connsMap.put(serverCode, conn);*/
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

		UpdateBean param = null;
		try {
			param = (new Gson()).fromJson(serverParam, UpdateBean.class);
		} catch (Exception e) {
			CAvatarEye.LOG("CAvatarEye.updateServer(), fromJson error: " + e.getMessage(),2);
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
		/*conn.setUpdating(true);
		connsMap.put(conn.getServerCode(), conn);*/
		String input = serverParam;
		CAvatarEye.LOG("CAvatarEye.updateServer(), socket input:" + input,0);
		if (null != param.getAddressType() && param.getAddressType().equals("10")) {
			p = new Packet((byte) 0x01, (byte) 0x22, input.getBytes());
		} else {
			p = new Packet((byte) 0x01, (byte) 0x20, input.getBytes());
		}
		conn.getClient().getTransceiver().send(p);

		//接收消息
		for(int i=1; i<=(600*3); i++)  {
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
						resultBean.setType(10);
						/*conn.setHasInit(false);
						conn.setBusying(false);
						conn.setUpdating(false);
						connsMap.put(conn.getServerCode(), conn);*/
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
		/*conn.setUpdating(false);
		connsMap.put(conn.getServerCode(), conn);*/

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
		} catch (JsonSyntaxException e) {
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
