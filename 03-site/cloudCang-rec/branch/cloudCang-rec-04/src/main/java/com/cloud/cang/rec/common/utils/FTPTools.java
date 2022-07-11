package com.cloud.cang.rec.common.utils;

import com.cloud.cang.utils.FtpUser;
import com.cloud.cang.utils.FtpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import sun.net.ftp.FtpClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * @program: cloudCang-vis
 * @description: FTPTools
 * @author: qzg
 * @create: 2020-08-26 18:06
 **/
public class FTPTools {
    private static Log log = LogFactory.getLog(FTPTools.class);

    //设置私有不能实例化
    private FTPTools() {

    }

    /**
     * 上传
     * @return
     */
    public static boolean upload(InputStream inputStream, String serverPath, String saveName, FtpUser config) {
        boolean flag = false;
        FTPClient ftpClient = new FTPClient();
        String serverUrl = config.getIp();
        Integer port =Integer.parseInt(config.getPort()) ;
        String userName = config.getUserName();
        String passWord = config.getPassword();
        System.out.println("serverPath:" + serverPath);
        System.out.println("saveName:" + saveName);
        //1 测试连接
        if (connect(ftpClient, serverUrl, port, userName, passWord)) {
            try {
                //2 检查工作目录是否存在

                if (!ftpClient.changeWorkingDirectory(serverPath)) {
                    try {
                        System.out.println(6666);
                        ftpClient.makeDirectory(serverPath);
                    } catch (Exception var5) {
                        log.info("创建目录失败！");
                        var5.printStackTrace();
                        Exception e = null;
                        return false;
                    }
                }else {
                    System.out.println("切换成功！");
                }

                ftpClient.changeWorkingDirectory(serverPath);
                // 3 检查是否上传成功
                if (storeFile(ftpClient, saveName, inputStream)) {
                    flag = true;
                }

            } catch (Exception e) {
                log.info("上传失败！");
                e.printStackTrace();

            }finally {
                //关闭资源
                try {
                    if(null != inputStream){
                        inputStream.close();
                    }
                    //退出
                    ftpClient.logout();
                    //断开连接
                    disconnect(ftpClient);
                }catch (IOException io){
                    log.error("ERROR:"+io.getMessage());
                }
            }
        }
        return flag;
    }

    public static boolean isDirExist(FTPClient ftpClient, String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }


    /**
     * 断开连接
     *
     * @param ftpClient
     * @throws Exception
     */
    public static void disconnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
                log.info("已关闭连接");
            } catch (IOException e) {
                log.info("没有关闭连接");
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试是否能连接
     *
     * @param ftpClient
     * @param hostname  ip或域名地址
     * @param port      端口
     * @param username  用户名
     * @param password  密码
     * @return 返回真则能连接
     */
    public static boolean connect(FTPClient ftpClient, String hostname, int port, String username, String password) {
        boolean flag = false;
        try {
            //ftp初始化的一些参数
            ftpClient.connect(hostname, port);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.setControlEncoding("UTF-8");
            if (ftpClient.login(username, password)) {
                log.info("连接ftp成功");
                flag = true;
            } else {
                log.info("连接ftp失败，可能用户名或密码错误");
                try {
                    disconnect(ftpClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            log.info("连接失败，可能ip或端口错误");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param saveName
     * @param fileInputStream 要上传的文件流
     * @return
     */
    public static boolean storeFile(FTPClient ftpClient, String saveName, InputStream fileInputStream) {
        boolean flag = false;
        try {
            if (ftpClient.storeFile(saveName, fileInputStream)) {
                flag = true;
                log.info("上传成功");
                disconnect(ftpClient);
            }
        } catch (IOException e) {
            log.info("上传失败");
            disconnect(ftpClient);
            e.printStackTrace();
        }
        return flag;
    }
}

