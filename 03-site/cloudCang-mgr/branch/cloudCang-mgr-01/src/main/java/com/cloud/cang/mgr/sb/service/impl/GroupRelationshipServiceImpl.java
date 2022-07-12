package com.cloud.cang.mgr.sb.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sb.dao.GroupRelationshipDao;
import com.cloud.cang.model.sb.GroupRelationship;
import com.cloud.cang.mgr.sb.service.GroupRelationshipService;

@Service
public class GroupRelationshipServiceImpl extends GenericServiceImpl<GroupRelationship, String> implements
		GroupRelationshipService {

	@Autowired
	GroupRelationshipDao groupRelationshipDao;

	
	@Override
	public GenericDao<GroupRelationship, String> getDao() {
		return groupRelationshipDao;
	}


	@Override
	public void insertDeviceIds(String deviceIds, String groupId) throws Exception{
		String[] deviceArray = deviceIds.split(",");//分割设备ID拼接的字符串“，”
		if (ArrayUtils.isNotEmpty(deviceArray)) {
			for (int i = 0;i<deviceArray.length;i++) {
				String deviceId = deviceArray[i];
				Map<String, String> map = new HashMap<>();
				map.put("sdeviceId", deviceId);
				List<GroupRelationship> groupRelationshipList = groupRelationshipDao.selectByMapWhere(map);//查询是否已经存在分组
				if (CollectionUtils.isNotEmpty(groupRelationshipList)) {//存在分组信息，修改分组ID
					GroupRelationship groupRelationship1 = groupRelationshipList.get(0);
					groupRelationship1.setSgroupId(groupId);
					groupRelationshipDao.updateByIdSelective(groupRelationship1);
				} else {//不存在分组ID，插入数据
					GroupRelationship groupRelationship = new GroupRelationship();
					groupRelationship.setSgroupId(groupId);
					groupRelationship.setSdeviceId(deviceId);
					groupRelationshipDao.insert(groupRelationship);
				}
			}
		}
	}

	@Override
	public GroupRelationship selectByDevieId(String deviceId) {
		return groupRelationshipDao.selectByDevieId(deviceId);
	}
}