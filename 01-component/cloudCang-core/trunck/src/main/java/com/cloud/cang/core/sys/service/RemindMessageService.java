package com.cloud.cang.core.sys.service;


import com.cloud.cang.core.sys.domain.RemindMessageDomain;
import com.cloud.cang.model.sys.RemindMessage;
import com.github.pagehelper.Page;

public interface RemindMessageService {
	
	/**
	 * 根据编号获取消息提醒
	 * @param code
	 * @return
	 */
	RemindMessage getRemindMessageByCode(String code);


	/**
	 * 消息提醒配置列表数据
	 */
	Page<RemindMessage>  selectPageByDomainWhere(Page<RemindMessage> page, RemindMessageDomain remindMessageDomain);

	/**
	 *  根据ID数据删除消息提醒配置
	 */
	void delete(String[] checkboxId);

	RemindMessage selectByPrimaryKey(String id);

	void insert(RemindMessage remindMessage);

	void updateBySelective(RemindMessage remindMessage);
}