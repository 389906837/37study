package com.cloud.cang.open.api.op.service.impl;

import java.util.Date;

import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.open.api.common.utils.IPUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.open.api.op.dao.TransferLogDao;
import com.cloud.cang.model.op.TransferLog;
import com.cloud.cang.open.api.op.service.TransferLogService;

@Service
public class TransferLogServiceImpl extends GenericServiceImpl<TransferLog, String> implements
		TransferLogService {

	private final static Logger logger = LoggerFactory.getLogger(TransferLogServiceImpl.class);
	@Autowired
	TransferLogDao transferLogDao;

	
	@Override
	public GenericDao<TransferLog, String> getDao() {
		return transferLogDao;
	}

	/**
	 * 新增接口调用日志记录
	 * @param clientIp 请求IP
     * @param batchNo 业务编号
	 * @param app 应用信息
	 * @param interfaceInfo 接口信息
	 * @param itype 日志类型
	 * @param operType 操作类型
	 * @param content 操作内容
	 */
	@Override
	public void addTransferLog(String clientIp, String batchNo, AppManage app, InterfaceInfo interfaceInfo, int itype, String operType, String content) {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				logger.info("新增接口调用日志");
				//新增接口操作日志
				TransferLog log = new TransferLog();
				log.setSbusinessCode(batchNo);
				log.setScode(CoreUtils.newCode("op_transfer_log"));
				log.setSuserId(app.getSuserId());
				log.setSuserCode(app.getSuserCode());
				log.setSappCode(app.getScode());
				log.setSappId(app.getSappId());
				log.setSinterfaceCode(interfaceInfo.getScode());
				log.setItype(10);
				log.setIoperType("image_recognition");
				log.setToperTime(new Date());
				log.setScontent(content);
				log.setIdelFlag(0);
				log.setSoperIp(clientIp);
				transferLogDao.insert(log);
			}
		});
	}
}