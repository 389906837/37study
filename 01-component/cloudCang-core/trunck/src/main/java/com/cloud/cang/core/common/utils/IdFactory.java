package com.cloud.cang.core.common.utils;

import com.cloud.cang.core.utils.CoreUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 各id生成器
 * @author zhouhong
 * @version 1.0
 */
public class IdFactory {
    private static Object initLock = new Object(); // 用于初始化一个生成随机数的空间对象
    private static Random randGen = null; // 随机数对象
    private static char numbersAndLetters[] = null; // 随机数生成的字符范围
    private static Object outSerialLock = new Object(); // 用于初始化一个生成随机数的空间对象
    private static String SYS_CODE=null;
    private static AtomicLong count = new AtomicLong(0L); 
    /**
     * 用于成生外部流水号，返回32位数字 yyMMddhhmmssSSS+17位随机数
     * @return
     */
    public static String getOutRandomSerialNumber() {
    	
    	if(SYS_CODE==null){
    		synchronized (outSerialLock) {
    			if(SYS_CODE==null){
    				SYS_CODE= CoreUtils.newCode("out_serial_num");
    			}
    		}
    	}
    	String sequence = new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
    	
    	return SYS_CODE+sequence+count.getAndIncrement();
    }
    /**
     * 获取一个20位随机流水号：年月日时分秒毫秒+5位随机数,
     * 
     * @return
     */
    public static String getRandomSerialNumber() {
        String sequence = new SimpleDateFormat("yyMMddHHmmssSSS")
                .format(new Date());
        return sequence + RandomStringUtils.randomNumeric(5);
    }
    /**
     * 获取一个32位随机流水号
     * 
     * @return
     */
    public static String getUUIDSerialNumber() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
    /**
     * 读取随机数字,线程安全
     * 
     * @param i
     *            <int>(取值个数)
     * @return String
     */
    public static final String randomString(int i) {

        if (i < 1)
            return null;
        if (randGen == null)
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    numbersAndLetters = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                            .toCharArray();
                }
            }
        char ac[] = new char[i];
        for (int j = 0; j < ac.length; j++)
            ac[j] = numbersAndLetters[randGen.nextInt(71)];

        return new String(ac);
    }

    /**
     * 读取随机数字,线程安全的
     * 
     * @param i
     *            <int>(取值个数)
     * @return String
     */
    public static final String randomNum(int i) {
        if (i < 1)
            return null;
        if (randGen == null)
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random();
                    numbersAndLetters = "0123456789".toCharArray();
                }
            }
        char ac[] = new char[i];
        for (int j = 0; j < ac.length; j++)
            ac[j] = numbersAndLetters[randGen.nextInt(9)];
        return new String(ac);
    }

    public static void main(String[] args) {
    	System.out.println(count.getAndIncrement());
    	System.out.println(count.getAndIncrement());
    	System.out.println(count.getAndIncrement());
    	System.out.println(count.getAndIncrement());
    	System.out.println(count.getAndIncrement());
    	System.out.println(count.getAndIncrement());
	}
   

    

}
