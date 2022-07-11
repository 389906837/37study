package com.cloud.cang.core.sys.service;

import java.util.Date;
import java.util.List;

public interface HolidayService {
    
    List<String> selectListString();
   
    /**
     * 是否是休息日
     * @param date 日期
     * @return
     */
    boolean isRestDay(Date date);

}
