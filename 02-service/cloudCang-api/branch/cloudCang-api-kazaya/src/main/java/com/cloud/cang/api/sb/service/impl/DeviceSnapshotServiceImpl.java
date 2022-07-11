package com.cloud.cang.api.sb.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.cloud.cang.api.antbox.dto.CustomerDto;
import com.cloud.cang.api.antbox.util.AntboxUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.api.sb.dao.DeviceSnapshotDao;
import com.cloud.cang.model.sb.DeviceSnapshot;
import com.cloud.cang.api.sb.service.DeviceSnapshotService;

@Service
public class DeviceSnapshotServiceImpl extends GenericServiceImpl<DeviceSnapshot, String> implements
		DeviceSnapshotService {

	@Autowired
	DeviceSnapshotDao deviceSnapshotDao;

	
	@Override
	public GenericDao<DeviceSnapshot, String> getDao() {
		return deviceSnapshotDao;
	}


	@Override
	public DeviceSnapshot getDeviceSnapshotByBoxId(Long boxId) {
		DeviceSnapshot snapshot = new DeviceSnapshot();
		snapshot.setSboxSerialNumber(String.valueOf(boxId));
		List<DeviceSnapshot> list = deviceSnapshotDao.selectByEntityWhere(snapshot);
		if(list !=null && list.size() >0 ){
			return list.get(0);
		}
		return null;
	}

	@Override
	public void updateCurrentShopper(Long boxId, CustomerDto customerDto) {
		DeviceSnapshot entity = this.getDeviceSnapshotByBoxId(boxId);
		// 新增
		if(entity == null ){
			entity = new DeviceSnapshot();
			entity.setSboxSerialNumber(String.valueOf(boxId));
			entity.setSdeviceId(customerDto.getDeviceId());
			entity.setCurrentCustomer(AntboxUtil.GSON.toJson(customerDto));
			entity.setTaddTime(new Date());
			deviceSnapshotDao.insert(entity);
		// 更新
		}else{
			entity.setCurrentCustomer(AntboxUtil.GSON.toJson(customerDto));
			deviceSnapshotDao.updateByIdSelective(entity);
		}
	}

	@Override
	public void resetCurrentShopper(Long boxId) {
		DeviceSnapshot entity = this.getDeviceSnapshotByBoxId(boxId);
		if(entity !=null){
			entity.setCurrentCustomer("");
			deviceSnapshotDao.updateByIdSelective(entity);
		}
	}

	@Override
	public void updateLastInventoryInfo(Long boxId, Set<String> lables, CustomerDto customerDto) {
		DeviceSnapshot entity = this.getDeviceSnapshotByBoxId(boxId);
		if(entity !=null){
			entity.setLastInventoryRfid(AntboxUtil.GSON.toJson(lables));
			if(customerDto !=null){
				entity.setLastInventoryCustomer(AntboxUtil.GSON.toJson(customerDto));
			}
			deviceSnapshotDao.updateByIdSelective(entity);
		}
	}
}