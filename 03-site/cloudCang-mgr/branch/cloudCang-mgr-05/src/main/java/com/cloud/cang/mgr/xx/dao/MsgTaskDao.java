package com.cloud.cang.mgr.xx.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.xx.vo.MsgTaskVo;
import com.cloud.cang.model.xx.MsgTask;
import com.github.pagehelper.Page;

import java.util.Map;

/** 消息任务表(XX_MSG_TASK) **/
public interface MsgTaskDao extends GenericDao<MsgTask, String> {

    Page<Map<String,String>> selectAllSendMsg(MsgTaskVo msgTaskVo);
}