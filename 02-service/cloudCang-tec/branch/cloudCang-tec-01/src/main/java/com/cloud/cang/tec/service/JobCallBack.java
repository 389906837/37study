/*
 * Copyright (C) 2016 hurbao All rights reserved
 * Author: zhouhong
 * Date: 2016年3月12日
 * Description:JobCallBack.java 
 */
package com.cloud.cang.tec.service;

/**
 * @author zhouhong
 * @version 1.0
 */
public interface JobCallBack {
	
	 /**
     * 
     * @return 执行结果
     * @throws Exception
     */
    String doInJob() throws Exception;

}
