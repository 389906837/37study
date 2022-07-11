package com.cloud.cang.message.xx.dao;


import com.cloud.cang.message.xx.domain.SelectAvailSenderTaskDto;
import com.cloud.cang.message.xx.domain.SysUseInfo;
import com.cloud.cang.model.xx.MsgTask;

import java.util.List;
import java.util.Map;

public interface MsgTaskExtDao {
    /**
     * 根据条件查询待发送的任务
     * @param dto
     * @return
     */
    List<MsgTask> selectAvailSenderTask(SelectAvailSenderTaskDto dto);
    
    /**
     * 根据权限查询系统用户
     * @param purCode
     * @return
     */
    List<SysUseInfo> selectSysInfosByPurCode(String purCode);/**
     * 根据权限码和商户Id查询系统用户
     * @param map
     * @return
     */
    List<SysUseInfo> selectSysInfosByPurCodeAndMerchantId(Map<String,String > map);

}
