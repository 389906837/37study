package com.cloud.cang.mgr.common.utils;

import com.cloud.cang.core.utils.FtpParamUtil;
import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.utils.DateUtils;
import com.cloud.cang.utils.FtpUtils;
import com.cloud.cang.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;

/**
 * Created by YLF on 2020/6/5.
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 处理上传图片
     *
     * @param file     图片文件
     * @param pathName 路径
     * @return
     */
    public static String uploadHome(MultipartFile file, String pathName) {
        if (file == null) {
            throw new ServiceException("没有找到上传文件");
        }
        String filName = file.getOriginalFilename();//文件原名
        String[] fileNameSplit = filName.split("\\.");
        String exp = fileNameSplit[fileNameSplit.length - 1];//获取后缀名====>jpg|png|bmp
        //图片类型限制
        if (!exp.toLowerCase().equals("jpg") && !exp.toLowerCase().equals("png") && !exp.toLowerCase().equals("bmp")) {
            throw new ServiceException("文件类型错误，上传失败");
        }
        try {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(pathName).append("/").append(DateUtils.dateParseShortString(new Date())).append("/");
            String serverPath = stringBuffer.toString();//------>pathName/2018-03-07/

            String tempName = DateUtils.getCurrentSTimeNumber() + "." + exp;//文件名=="时间"+"."+"原图片名后缀"
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(file.getInputStream(), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer1 = new StringBuffer();
                stringBuffer1.append("/").append(serverPath).append(tempName);// "/" + serverPath + tempName
                return stringBuffer1.toString();// 路径为------> /commodityInfo/2018-03-07/20180307151504492.jpg
            }
        } catch (IOException e) {
            logger.error("上传文件出现IOException异常：{}", e);
        }
        return null;
    }

    /**
     * 文件上传
     *
     * @param apkFile  apk文件
     * @param pathName 路径
     * @return
     */
    public static String uploadHome(File apkFile, String pathName, String dataStr) {
        try {
            if (apkFile == null || !apkFile.exists() || apkFile.length() <= 0) {
                logger.info("上传文件异常，文件为空");
                return "";
            }
            String serverPath = pathName + "/";
            if (StringUtil.isNotBlank(dataStr)) {
                serverPath = serverPath + dataStr + "/";
            }
            String tempName = apkFile.getName();
            // 返回URL地址
            if (FtpUtils.uploadToFtpServer(new FileInputStream(apkFile), serverPath, tempName, FtpParamUtil.getFtpUser())) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("/").append(serverPath).append(tempName);
                return stringBuffer.toString();
            }
        } catch (IOException e) {
            logger.error("上传文件出现IOException异常：{}", e);
        }
        return null;
    }

    /**
     * 复制网络图片
     *
     * @param inurl  网络图片地址
     * @param outUrl 输出图片文件路径
     * @return
     * @throws Exception
     */
    public static boolean copyImg(String inurl, String outUrl) {
        OutputStream os = null;
        InputStream is = null;
        try {
            File file = new File(outUrl);
            os = new FileOutputStream(file);
            URL url = new URL(inurl);
            is = url.openStream();
            byte[] buff = new byte[1024];
            while (true) {
                int readed = is.read(buff);
                if (readed == -1) {
                    break;
                }
                byte[] temp = new byte[readed];
                System.arraycopy(buff, 0, temp, 0, readed);
                os.write(temp);
            }
            return true;
        } catch (Exception e) {
            logger.error("复制网络图片异常：{}", e);
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error("关闭输入流异常：{}", e);
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    logger.error("关闭输出流异常：{}", e);
                }
            }
        }
        return false;
    }
}
