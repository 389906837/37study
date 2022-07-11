/**        
 */
package com.cloud.cang.zookeeper.confclient.config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 */
public class FileUtils {

	/**
	 * 备份数据到本地
	 */
	public static void saveData(String projCode, Properties data) {
		String userHome = System.getProperty("user.home");
		String dirStr = userHome + File.separator + projCode;
		File dir = new File(dirStr);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(dirStr + File.separator + "data.properties");
		// 如果配置文件已经存在，那么删除
		if (file.exists())
			file.delete();
		try {
			// /保存属性到b.properties文件
			FileOutputStream oFile = new FileOutputStream(file, true);// true表示追加打开
			data.store(oFile, "项目配置属性文件信息");
			oFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Properties readConfigFromLocal(String projCode)
			throws IOException {
		String userHome = System.getProperty("user.home");
		String dirStr = userHome + File.separator + projCode;
		File dir = new File(dirStr);
		if (!dir.exists())
			dir.mkdirs();
		File file = new File(dirStr + File.separator + "data.properties");
		Properties prop = new Properties();   
		try {
			
			// 读取属性文件a.properties
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			prop.load(in); // /加载属性列表
			in.close();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
