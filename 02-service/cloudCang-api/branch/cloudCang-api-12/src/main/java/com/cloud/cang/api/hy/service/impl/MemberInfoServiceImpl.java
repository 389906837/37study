package com.cloud.cang.api.hy.service.impl;

import com.cloud.cang.api.hy.dao.MemberInfoDao;
import com.cloud.cang.api.hy.service.MemberInfoService;
import com.cloud.cang.api.sb.dao.AiInfoDao;
import com.cloud.cang.api.sb.dao.DeviceRegisterDao;
import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.core.common.ErrorCodeEnum;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.model.sb.AiInfo;
import com.cloud.cang.model.sb.DeviceRegister;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberInfoServiceImpl extends GenericServiceImpl<MemberInfo, String> implements
		MemberInfoService {

	@Autowired
	MemberInfoDao memberInfoDao;

	@Autowired
	DeviceRegisterDao deviceRegisterDao;

	@Autowired
	AiInfoDao aiInfoDao;

	
	@Override
	public GenericDao<MemberInfo, String> getDao() {
		return memberInfoDao;
	}


	/**
	 * 根据手机尾号查找用户是否存在
	 *
	 * @param deviceId 设备ID
	 * @param key      通信密钥
	 * @param tailNum  尾号
	 * @return 查询到匹配用户时，返回userId拼接字符串
	 */
	@Override
	public ResponseVo<String> searchUser(String deviceId, String key, String tailNum) {
		StringBuffer userIds = new StringBuffer();
		// 查找设备
		DeviceRegister deviceRegister = new DeviceRegister();
		deviceRegister.setSdeviceId(deviceId);
		deviceRegister.setSsecurityKey(key);
		List<DeviceRegister> deviceRegisterList = deviceRegisterDao.selectByEntityWhere(deviceRegister);
		AiInfo aiInfo = aiInfoDao.selectByPrimaryKey(deviceId);


		if (CollectionUtils.isNotEmpty(deviceRegisterList) && null != aiInfo) {
			DeviceRegister deviceRegister1 = deviceRegisterList.get(0);
			if (40 == deviceRegister1.getIstatus() && 20 == aiInfo.getIstatus()) { //10 待审核 20  审核通过  30 审核拒绝 40 已注册

				// 根据手机号查找匹配的用户
				MemberInfo memberInfoVo = new MemberInfo();
				memberInfoVo.setSmerchantId(aiInfo.getSmerchantId());
				memberInfoVo.setSmobile(tailNum);
				List<MemberInfo> memberInfoList = memberInfoDao.selectByTailNum(memberInfoVo);
				if (CollectionUtils.isNotEmpty(memberInfoList)) {
					for (MemberInfo m : memberInfoList) {
						userIds.append(m.getId());
						userIds.append(",");
					}
					return new ResponseVo<>(true, 200, "匹配到用户", userIds.toString());
				} else {
					return new ResponseVo<>(false, 300, "没有匹配到对应用户");
				}
			} else {
				return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("设备状态不正常");
			}
		} else {
			return ErrorCodeEnum.ERROR_COMMON_SYSTEM.getResponseVo("未查询到相关设备信息");
		}


	}

}