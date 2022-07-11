package com.cang.os.common.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;

import com.cang.os.common.exception.ServiceException;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;


/**
 * 
 *Ftp远程文件处理功能
 * 4, 2014 liqin 创建文件，实现基本功能
 */
public class FtpUtils {
    private static Logger log = LoggerFactory.getLogger(FtpUtils.class);
	
	public static boolean uploadToFtpServer(File file, String serverPath,
			String fileName,FtpUser config) {
		try {
            return uploadToFtpServer(getLocalFileInputStream(file), serverPath, fileName, config);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		return false;
	}
	
	public static boolean uploadToFtpServer(InputStream fileStream, String serverPath,
			String fileName,FtpUser config) {
	    String serverUrl =config.getIp();
        String userName = config.getUserName();
        String passWord = config.getPassword();
		FtpClient ftpClient;
		try {
			ftpClient=FtpClient.create(serverUrl);//此为jdk1.7创建ftp连接
			// 登录到Ftp服务器
			boolean isOpenSuccess = loginToFtpServer(ftpClient, serverUrl,
					userName, passWord);
			if (isOpenSuccess) {// 以下几步顺序不能错
				
				serverPath=getFullPath(ftpClient, serverPath);
				createDir(ftpClient, serverPath);
				//String os = SysParaUtil.get(SysParaUtil._FTPOS);
				
				ftpClient.changeDirectory(serverPath);
				/*OutputStream outputStream = getFtpClientOutputStream(ftpClient,
						fileName);
				writeToFtpServer(outputStream, fileStream);
				close(ftpClient, outputStream, fileStream);// 关闭*/
				//writeToFtpServer(outputStream, inputStream);
				ftpClient.putFile(fileName, fileStream);
				close(ftpClient, null, fileStream);// 关闭
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private static String getFullPath(FtpClient ftpClient,String serverPath) throws FtpProtocolException, IOException{
		String workingdir=ftpClient.getWorkingDirectory();
		if(!serverPath.startsWith("/"))
			serverPath="/"+serverPath;
		if(!workingdir.equals("/"))
			serverPath=workingdir+serverPath;
		return serverPath;
	}
	
	/**
	 * 将字符串内容以一个文件的形式保存到ftp
	 * 
	 * @param content 字符串内容
	 * @param ftpDir 存放的ftp目录
	 * @param ftpFileName 文件名
	 */
	public static void uploadFtp(String content, String ftpDir, String ftpFileName,FtpUser config) {
        ByteArrayInputStream contentStream;
        try {
            contentStream = new ByteArrayInputStream(content.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("",e);
            throw new ServiceException(e);
        }
        FtpUtils.uploadToFtpServer(contentStream, ftpDir, ftpFileName, config);
    }

	/**
	 * 检查文件夹在当前目录下是否存在
	 * 
	 * @param dir
	 * @return
	 */
	private static boolean isDirExist(FtpClient ftpClient, String dir) {
		String pwd = "";
		try {
			pwd=ftpClient.getWorkingDirectory();
			ftpClient.changeDirectory(dir);
			ftpClient.changeDirectory(pwd);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 在当前目录下创建文件夹
	 * 
	 * @param dir
	 * @return
	 * @throws Exception
	 */
	private static boolean createDir(FtpClient ftpClient, String dir) {
		try {
			ftpClient.setAsciiType();
			StringTokenizer s = new StringTokenizer(dir, "/"); // sign
			s.countTokens();
			String pathName="";//
			//String woringdir=ftpClient.getWorkingDirectory();
			while (s.hasMoreElements()) {
				pathName = pathName + "/" + (String) s.nextElement();
				if (!isDirExist(ftpClient, pathName)) {
					try {
						ftpClient.makeDirectory(pathName);
					} catch (Exception e) {
						e.printStackTrace();
						e = null;
						return false;
					}
					ftpClient.getLastResponseString();
				}
			}
			ftpClient.setBinaryType();
			return true;
		} catch (IOException e1) {
			return false;
		} catch (FtpProtocolException e1) {
			e1.printStackTrace();
		}
		return false;
	}

	/** 从Ftp服务器上下载文件 
	 * @param out */
    public static void downloadFormFtp(OutputStream out,  String serverPath, FtpUser config) {
        String serverUrl = config.getIp();
        String userName = config.getUserName();
        String passWord = config.getPassword();
        String filepath = serverPath.substring(0, serverPath.lastIndexOf("/"));
        String filename = serverPath.substring(serverPath.lastIndexOf("/") + 1, serverPath.length());
        FtpClient ftpClient=null;
        try {
        	ftpClient=FtpClient.create(serverUrl);//此为jdk1.7创建ftp连接
        	loginToFtpServer(ftpClient, serverUrl,
					userName, passWord);
        	filepath=getFullPath(ftpClient, filepath);
            if (isDirExist(ftpClient, filepath)) {
            	//String os = SysParaUtil.get(SysParaUtil._FTPOS);
            	
				ftpClient.changeDirectory(filepath);
                InputStream inputStream = getFtpClientInputStream(ftpClient, filename);
                if(inputStream!=null)
                    writeToLocal(inputStream, out);
                if (inputStream != null)
                    inputStream.close();
               
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }finally{
        	 if (ftpClient != null)
				try {
					ftpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        }
    }
	 
	

	/** 登录到Ftp服务器 */
    private static boolean loginToFtpServer(FtpClient ftpClient, String serverUrl,
			String userName, String passWord) {
		try {
			ftpClient.login(userName, passWord.toCharArray());   
			return true;
		} catch (IOException e) {
			System.out.println("Ftp 客户端打开失败");
			return false;
		} catch (FtpProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/** 得到ftp客户端的输出流 
	 * @throws FtpProtocolException */
	public static OutputStream getFtpClientOutputStream(FtpClient ftpClient,
			String fileName) throws IOException, FtpProtocolException {
		ftpClient.setBinaryType();  
		return ftpClient.putFileStream(fileName,true);//ftpClient.put(fileName);此为jdk1.6方法 以下为jdk1.7方法
	}

	/** 得到ftp客户端的输出流 
	 * @throws FtpProtocolException */
	public static InputStream getFtpClientInputStream(FtpClient ftpClient,
			String downLoadFile) throws IOException, FtpProtocolException {
		ftpClient.setBinaryType(); 
		File file = new File(downLoadFile);
		return ftpClient.getFileStream(file.getName());
	}

    public static void batchDownFiles(OutputStream output, FtpUser config, String[] filePaths,
        String[] orlNames)
        throws IOException {
        byte[] buffer = new byte[1024];
        String serverUrl = config.getIp();
        String userName = config.getUserName();
        String passWord = config.getPassword();
        FtpClient ftpClient=null;
        ZipOutputStream out =null;
        try {
        	ftpClient=FtpClient.create(serverUrl);//此为jdk1.7创建ftp连接
           
            out = new ZipOutputStream(output);
            out.setEncoding("gbk");//指定编码为gbk，否则部署到linux下会出现乱码
            loginToFtpServer(ftpClient, serverUrl,
     				userName, passWord);
            String initdir=ftpClient.getWorkingDirectory();
            for (int i = 0; i < filePaths.length; i++) {
                try {
                	 
                    String serverPath = filePaths[i];
                    String filepath = serverPath.substring(0, serverPath.lastIndexOf("/"));
                    String filename = serverPath.substring(serverPath.lastIndexOf("/") + 1, serverPath.length());
                    //if (isDirExist(ftpClient, filepath)) {
                        // 进入服务器的指定目录
                    filepath=getFullPath(ftpClient, filepath);
                    ftpClient.changeDirectory(filepath);
    				System.out.println(ftpClient.getWorkingDirectory());
                        InputStream inputStream = getFtpClientInputStream(ftpClient, filename);
                        if(inputStream==null) continue;
                        out.putNextEntry(new ZipEntry(new String(orlNames[i])));
                        
                        // 设置压缩文件内的字符编码，不然会变成乱码
                        // out.setEncoding("GBK");
                        int len;
                        // 读入需要下载的文件的内容，打包到zip文件
                        while ((len = inputStream.read(buffer)) > 0) {
                            out.write(buffer, 0, len);
                        }
                        out.closeEntry();
                        if (inputStream != null)
                            inputStream.close();
                        ftpClient.changeDirectory(initdir);
                    //}
                } catch (Exception e) {
                	e.printStackTrace();
                }
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                out.flush();
                out.close();
            }
            output.flush();
            output.close();
            if (ftpClient != null)
                ftpClient.close();
        }
    }
	/** 得到本地文件的输入流 */
	private static InputStream getLocalFileInputStream(File file)
			throws FileNotFoundException {
		return new FileInputStream(file);
	}

	/** 向ftp服务器写入文件 */
	public static void writeToFtpServer(OutputStream outputStream,
			InputStream inputStream) throws IOException {
		byte[] bytes = new byte[1024];
		int len = 0;
		while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {                     
		    outputStream.write(bytes, 0, len);
		}
		outputStream.flush();
	}

	/** 下载文件到本地 */
	public static void writeToLocal(InputStream inputStream, OutputStream outputStream)
			throws IOException {
	    byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = inputStream.read(bytes, 0, bytes.length)) != -1) {                     
            outputStream.write(bytes, 0, len);
        }
        outputStream.flush();
	}

	/** 关闭 */
	private static void close(FtpClient ftpClient, OutputStream outputStream,
			InputStream inputStream) throws IOException {
		if (inputStream != null)
			inputStream.close();
		if (outputStream != null)
			outputStream.close();
		if (ftpClient != null)
			ftpClient.close();
	}
	
	public static void main(String[] args) {
		String serverUrl ="192.168.1.12";
        String userName ="tziba";
        String passWord ="tziba1";
        FtpUser ftpUser = new FtpUser();
        ftpUser.setUserName(userName);
        ftpUser.setPassword(passWord);
        ftpUser.setIp(serverUrl);
        FtpUtils.uploadToFtpServer(new File("E:\\11.JPG"), "article", "001.jpg", ftpUser);
        
		/*FtpClient ftpClient;
		try {
			ftpClient=FtpClient.create(serverUrl);//此为jdk1.7创建ftp连接
			// 登录到Ftp服务器
			boolean isOpenSuccess = loginToFtpServer(ftpClient, serverUrl,
					userName, passWord);
			
			createDir(ftpClient, "aa/bb/cc/dd");
		}catch(Exception ex){
			ex.printStackTrace();
		}*/
	}
	
}
