package com.cloud.cang.eye.sdk.socket;


import com.cloud.cang.eye.sdk.CAvatarEye;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Socket收发器：通过Socket发送数据，并使用新线程监听Socket接收到的数据
 */
public abstract class SocketTransceiver implements Runnable {

	protected Socket socket;
	protected InetAddress addr;
	protected DataInputStream in;
	protected DataOutputStream out;
	private boolean runFlag;
	private boolean connectFlag = false;

	public boolean getConnectFlag() {
		return connectFlag;
	}

	public void setConnectFlag(boolean connectFlag) {
		this.connectFlag = connectFlag;
	}

	/**
	 * 实例化
	 */
	public SocketTransceiver(Socket socket) {
		this.socket = socket;
		this.addr = socket.getInetAddress();
	}

	/**
	 * 获取连接到的Socket地址
	 */
	public InetAddress getInetAddress() {
		return addr;
	}

	/**
	 * 开启Socket收发，如果开启失败，会断开连接并回调{@code onDisconnect()}
	 */
	public void start() {
		runFlag = true;
		connectFlag = true;
		new Thread(this).start();
	}

	/**
	 * 断开连接(主动)，连接断开后，会回调{@code onDisconnect()}
	 */
	public void stop() {
		runFlag = false;
		connectFlag =false;
		try {
			socket.shutdownInput();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送字符串
	 */
	public boolean send(String s) {
		if (out != null) {
			try {
				out.write(s.getBytes("utf-8"), 0, s.length());
				//out.writeUTF(s);
				out.flush();
				CAvatarEye.LOG("client sending: " + s,1);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public boolean send(Packet p) {
		if (out != null) {
			try {
				CAvatarEye.LOG("TCPClient sending Packet: " + p.getCNO(),1);
				p.write(out);
				out.flush();
				return true;
			} catch (Exception e) {
				connectFlag = false;
				CAvatarEye.LOG("TCPClient & TCPServer connection error.",2);
				//e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 监听Socket接收的数据(新线程中运行)
	 */
	@Override
	public void run() {
		try {
			in = new DataInputStream(this.socket.getInputStream());
			out = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			runFlag = false;
		}
		while (runFlag) {
			try {
				//final String s = in.readUTF();
				
				int a = in.available();
				if (a <= 0)
					continue;
				byte[] recv = new byte[a];
				in.read(recv);
				this.onReceive(addr, recv);
			} catch (IOException e) {
				// 连接被断开(被动)
				CAvatarEye.LOG("TCPServer Disconnect……",2);
				runFlag = false;
			}
		}
		// 断开连接
		try {
			in.close();
			out.close();
			socket.close();
			in = null;
			out = null;
			socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.onDisconnect(addr);
	}

	/**
	 * 接收到数据，此回调是在新线程中执行的
	 */
	public abstract void onReceive(InetAddress addr, String s);
	
	public abstract void onReceive(InetAddress addr, byte[] bytes);

	/**
	 * 连接断开，此回调是在新线程中执行的
	 */
	public abstract void onDisconnect(InetAddress addr);
}
