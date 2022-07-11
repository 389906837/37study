package com.cloud.cang.rec.ws.util;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipOutputStream;
public class FileUtil {
	/**
     * 判断文件夹是否存在，如果不存在则创建
     */
    public static void judeDirExists(File file) {
        if (file.exists()) {
            
        } else {
            file.mkdirs();
        }
    }
    
    /**
     * 判断文件是否存在,如果不存在则创建
     */
    public static void judeFileExists(File file) {
        if (file.exists()) {
        } else {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 在某个文件中追加内容
     *
     * @param fileName
     * @param content
     */
    public static void appendStringToFile(String fileName, String content) {
        try {
            //判断文件是否存在
            File file = new File(fileName);
            judeFileExists(file);
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.write((content + "\r\n").getBytes());
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    /**
     * 文件内容清空,没有文件则创建
     *
     * @param filePath
     */
    public static void clearFile(String filePath) {
        File file = new File(filePath);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
