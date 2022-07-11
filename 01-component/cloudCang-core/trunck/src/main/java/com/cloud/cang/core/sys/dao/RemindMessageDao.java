package com.cloud.cang.core.sys.dao;


import com.cloud.cang.core.sys.domain.RemindMessageDomain;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.RemindMessage;
import com.github.pagehelper.Page;

/** 消息提醒配置(SYS_REMIND_MESSAGE) **/
public interface RemindMessageDao extends GenericDao<RemindMessage, String> {


    //查询列表
    Page<RemindMessage> selectPageByDomainWhere(RemindMessageDomain remindMessageDomain);
}