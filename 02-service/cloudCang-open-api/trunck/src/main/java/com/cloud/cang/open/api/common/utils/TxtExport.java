package com.cloud.cang.open.api.common.utils;

import java.io.*;

/**
 * @version 1.0
 * @Description: txt 文件操作
 * @Author: zhouhong
 * @Date: 2018/11/30 15:06
 */
public class TxtExport {

    /**
     * 创建文件
     * @param fileName
     * @return
     */
    public static boolean createFile(File fileName)throws Exception{
        boolean flag=false;
        try{
            if(!fileName.exists()){
                fileName.createNewFile();
                flag = true;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 读TXT文件内容
     * @param fileName
     * @return
     */
    public static String readTxtFile(File fileName)throws Exception{
        String result=null;
        FileReader fileReader=null;
        BufferedReader bufferedReader=null;
        try{
            fileReader=new FileReader(fileName);
            bufferedReader=new BufferedReader(fileReader);
            String read=null;
            while((read=bufferedReader.readLine()) != null){
                result = result+read+"\r\n";
            }

        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if(bufferedReader!=null){
                bufferedReader.close();
            }
            if(fileReader!=null){
                fileReader.close();
            }
        }
        return result;
    }


    public static boolean writeTxtFile(String content,File  fileName)throws Exception{
        boolean flag = false;
        FileOutputStream o = null;
        try {
            o = new FileOutputStream(fileName);
            o.write(content.getBytes("GBK"));
            o.close();
            flag=true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(o != null) {
                o.close();
            }
        }
        return flag;
    }
}
