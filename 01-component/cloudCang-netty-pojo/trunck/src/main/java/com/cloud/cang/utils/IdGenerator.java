package com.cloud.cang.utils;

import java.util.UUID;

/**
 *
 * @ClassName: IdGenerator
 * @Description: 生产32位编码 系统流水ID生成
 * @Author: zhouhong
 * @Date: 2016年2月23日 下午5:13:12
 * @version 1.0
 */
public class IdGenerator {

    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:14:02
     * @return String UUID
     */
    public static String randomUUID(int length){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,length);
    }


    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:14:02
     * @return String UUID
     */
    public static String randomUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     *
     * @Copyright (C) 2016 hurbao All rights reserved
     * @Author: zhouhong
     * @Date: 2016年2月23日 下午5:14:25
     * @return String
     * @Description:32位字符串 用于des加密解密
     */
    public static String randomUUID8(){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,8);
    }
}
