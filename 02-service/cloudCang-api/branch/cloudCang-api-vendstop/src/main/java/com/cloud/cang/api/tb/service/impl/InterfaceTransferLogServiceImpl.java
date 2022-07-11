package com.cloud.cang.api.tb.service.impl;

import java.util.Date;
import java.util.List;

import com.cloud.cang.concurrent.ExecutorManager;
import com.cloud.cang.core.utils.CoreUtils;
import com.cloud.cang.model.EntityTables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.tb.dao.InterfaceTransferLogDao;
import com.cloud.cang.model.tb.InterfaceTransferLog;
import com.cloud.cang.api.tb.service.InterfaceTransferLogService;

@Service
public class InterfaceTransferLogServiceImpl extends GenericServiceImpl<InterfaceTransferLog, String> implements
		InterfaceTransferLogService {

	private final static Logger logger = LoggerFactory.getLogger(InterfaceTransferLogServiceImpl.class);

	@Autowired
	InterfaceTransferLogDao interfaceTransferLogDao;

	
	@Override
	public GenericDao<InterfaceTransferLog, String> getDao() {
		return interfaceTransferLogDao;
	}


	/**
	 * 设备第三方接口调用日志记录
	 *
	 * @param deviceId         设备ID
	 * @param deviceCode       设备编号
	 * @param userId           用户ID
	 * @param userType         用户类型 10 普通用户，20 管理员
	 * @param thirdCode        第三方编号
	 * @param thirdName        第三方名称
	 * @param interfaceType    接口类型 10 请求接口，20 返回接口
	 * @param interfaceAction  接口动作
	 * @param interfaceName    接口名称
	 * @param reqParams        请求参数
	 * @param currentDateTime  请求时间
	 * @param respParams       返回参数
	 * @param currentDateTime1 响应时间
	 * @param remarks          备注
	 * @param currentDateTime2 添加时间
	 */
	@Override
	public void insertLog(String deviceId, String deviceCode, String userId, Integer userType, String thirdCode, String thirdName, Integer interfaceType, String interfaceAction, String interfaceName, String reqParams, Date currentDateTime, String respParams, Date currentDateTime1, String remarks, Date currentDateTime2) {
		ExecutorManager.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				String code = CoreUtils.newCode(EntityTables.TB_INTERFACE_TRANSFER_LOG);
				logger.info("新增设备第三方接口调用日志记录调用日志，编号：{}", code);
				logger.debug("设备编号：" + deviceCode + "用户类型:" + userType + "第三方编号:" + thirdCode + "第三方名称:" + thirdName + "接口动作:" + interfaceAction + "接口名称:"
						+ interfaceName + "请求参数:" + reqParams + "响应参数:" + respParams + "请求时间" + currentDateTime + "响应时间" + currentDateTime1);
				//新增接口操作日志
				InterfaceTransferLog interfaceTransferLog = new InterfaceTransferLog();
				interfaceTransferLog.setScode(code);                                // 记录编号
				interfaceTransferLog.setSdeviceId(deviceId);					// 设备ID
				interfaceTransferLog.setSdeviceCode(deviceCode);				// 设备编号
				interfaceTransferLog.setSuserId(userId);						// 用户ID
				interfaceTransferLog.setIuserType(userType);					// 用户类型
				interfaceTransferLog.setSthirdCode(thirdCode);					// 第三方编号
				interfaceTransferLog.setSthirdName(thirdName);					// 第三方名称
				interfaceTransferLog.setIinterfaceType(interfaceType.toString());			// 接口类型
				interfaceTransferLog.setSinterfaceAction(interfaceAction);		// 接口动作
				interfaceTransferLog.setSinterfaceName(interfaceName);			// 接口名称
				interfaceTransferLog.setSrequestData(reqParams);				// 请求参数
				interfaceTransferLog.setTrequestTime(currentDateTime);			// 请求时间
				interfaceTransferLog.setSresponseData(respParams);				// 响应参数
				interfaceTransferLog.setTresponseTime(currentDateTime1);		// 响应时间
				interfaceTransferLog.setSremark(remarks);						// 备注
				interfaceTransferLog.setTaddTime(currentDateTime2);				// 添加时间
				interfaceTransferLogDao.insert(interfaceTransferLog);
			}
		});
	}
}