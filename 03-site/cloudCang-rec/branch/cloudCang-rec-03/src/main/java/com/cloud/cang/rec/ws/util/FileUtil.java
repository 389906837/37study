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
     * �ж��ļ����Ƿ���ڣ�����������򴴽�
     */
    public static void judeDirExists(File file) {
        if (file.exists()) {
            
        } else {
            file.mkdirs();
        }
    }
    
    /**
     * �ж��ļ��Ƿ����,����������򴴽�
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
     * ��ĳ���ļ���׷������
     *
     * @param fileName
     * @param content
     */
    public static void appendStringToFile(String fileName, String content) {
        try {
            //�ж��ļ��Ƿ����
            File file = new File(fileName);
            judeFileExists(file);
            // ��һ����������ļ���������д��ʽ
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // �ļ����ȣ��ֽ���
            long fileLength = randomFile.length();
            // ��д�ļ�ָ���Ƶ��ļ�β��
            randomFile.seek(fileLength);
            randomFile.write((content + "\r\n").getBytes());
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
   
    /**
     * �ļ��������,û���ļ��򴴽�
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
