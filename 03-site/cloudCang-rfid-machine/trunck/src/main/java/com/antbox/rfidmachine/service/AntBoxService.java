package com.antbox.rfidmachine.service;

import java.util.List;

/**
 * 读写器接口
 * 
 * @author yong.ma
 * @since 0.0.1
 *
 */
public interface AntBoxService {

	/**
	 * 读写器是否处理打开状态
	 */
	public boolean isOpened();

	/**
	 * 打开串口
	 */
	void open();

	/**
	 * 关闭串口
	 */
	void close();

	/**
	 * 标签盘点
	 */
	List<String> inventory(String selectMachine);

	/**
	 * 设置读写器盘点溢出时间（秒）
	 */
	void setInventoryTimeSeconds(int seconds);
}
