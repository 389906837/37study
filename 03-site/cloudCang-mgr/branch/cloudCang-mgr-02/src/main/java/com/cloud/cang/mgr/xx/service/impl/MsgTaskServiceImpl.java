package com.cloud.cang.mgr.xx.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.xx.dao.MsgTaskDao;
import com.cloud.cang.mgr.xx.service.MsgTaskService;
import com.cloud.cang.mgr.xx.vo.MsgTaskVo;
import com.cloud.cang.model.xx.MsgTask;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MsgTaskServiceImpl extends GenericServiceImpl<MsgTask, String> implements
		MsgTaskService {

	@Autowired
	MsgTaskDao msgTaskDao;

	
	@Override
	public GenericDao<MsgTask, String> getDao() {
		return msgTaskDao;
	}

	/**
	 * 消息任务表列表数据
	 */
	@Override
	public Page<Map<String,String>> selectAllSendMsg(Page<Map<String,String>> page, MsgTaskVo msgTaskVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return msgTaskDao.selectAllSendMsg(msgTaskVo);
	}
}