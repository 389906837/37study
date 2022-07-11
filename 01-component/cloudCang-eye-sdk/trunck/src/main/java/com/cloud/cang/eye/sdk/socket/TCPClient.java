package com.cloud.cang.eye.sdk.socket;


import com.cloud.cang.eye.sdk.CAvatarEye;

import java.net.InetAddress;
import java.net.Socket;

/**
 * TCP Socket客户端
 */
public class TCPClient implements Runnable {

	private int port;
	private String hostIP;
	private SocketTransceiver transceiver;
	
	private boolean hasNewData = false;
	private Packet p;
	CAvatarEye eye;
	
	public TCPClient (CAvatarEye eye) {
		this.eye = eye;
	}

	/**
	 * 建立连接，连接的建立将在新线程中进行：
	 * 连接建立成功，回调{@code onConnect()}
	 * 连接建立失败，回调{@code onConnectFailed()}
	 */
	public void connect(String hostIP, int port) {
		this.hostIP = hostIP;
		this.port = port;
		new Thread(this).start();
	}
	
	public void connectOnce() {
		try {
			Socket socket = new Socket(hostIP, port);
			transceiver = new SocketTransceiver(socket) {

				@Override
				public void onReceive(InetAddress addr, String s) {					
					CAvatarEye.LOG("client receive Packet: " + s,1);
					TCPClient.this.onReceive(this, s);
				}
				
				@Override
				public void onReceive(InetAddress addr, byte[] bytes) {
					TCPClient.this.onReceive(this, bytes);
				}

				@Override
				public void onDisconnect(InetAddress addr) {
					TCPClient.this.onDisconnect(this);
				}				
			};
			transceiver.start();
			this.onConnect(transceiver);
		} catch (Exception e) {
			this.onConnectFailed();
		}
	}

	@Override
	public void run() {
		connectOnce();
	}

	/**
	 * 断开连接。连接断开，回调{@code onDisconnect()}
	 */
	public void disconnect() {
		if (transceiver != null) {
			transceiver.stop();
			transceiver = null;
		}
	}

	/**
	 * 判断是否连接
	 */
	public boolean isConnected() {
		return (null == transceiver) ? false : transceiver.getConnectFlag();
	}

	/**
	 * 获取Socket收发器
	 */
	public SocketTransceiver getTransceiver() {
		return isConnected() ? transceiver : null;
	}

	/**
	 * 连接建立
	 */
	public void onConnect(SocketTransceiver transceiver) {
		CAvatarEye.LOG("TCPClient onConnect()",1);
	}

	/**
	 * 连接建立失败
	 */
	public void onConnectFailed() {
		CAvatarEye.LOG("TCPClient onConnectFailed()",2);
	}

	/**
	 * 接收到数据，此回调在新线程中执行
	 */
	public void onReceive(SocketTransceiver st, String s) {
		CAvatarEye.LOG("TCPClient onReceive(), String: " + s,1);
		hasNewData = true;
	}
	
	public boolean hasNewData() {
		return hasNewData;
	}
	
	public Packet getNewData() {
		hasNewData = false;
		return p;
	}
	
	public void onReceive(SocketTransceiver st, byte[] bytes) {
		CAvatarEye.LOG("TCPClient onReceive(), " + bytes.length + " bytes: ",1);
		hasNewData = true;
		p = new Packet(bytes);
	}

	/**
	 * 连接断开，此回调在新线程中执行
	 */
	public void onDisconnect(SocketTransceiver st) {
		CAvatarEye.LOG("TCPClient onDisconnect()",1);
	}
}
