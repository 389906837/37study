package com.cloud.cang.mgr.xx.service;

import com.cloud.cang.generic.GenericService;
import com.cloud.cang.mgr.xx.vo.MsgTaskVo;
import com.cloud.cang.model.xx.MsgTask;
import com.github.pagehelper.Page;

import java.util.Map;

public interface MsgTaskService extends GenericService<MsgTask, String> {

    /**
     * @description 消息任务表 服务
     * @author ChangTanchang
     * @time 2018-01-19 10:00:08
     * @fileName MsgTaskService.java
     * @version 1.0
     */
    Page<Map<String,String>> selectAllSendMsg(Page<Map<String,String>> page, MsgTaskVo msgTaskVo);
}