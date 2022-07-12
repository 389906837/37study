package com.cloud.cang.mgr.sb.service.impl;

import java.util.List;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.sb.dao.GroupRelationshipDao;
import com.cloud.cang.mgr.sb.domain.GroupManageDomain;
import com.cloud.cang.mgr.sb.vo.GroupManageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.sb.dao.GroupManageDao;
import com.cloud.cang.model.sb.GroupManage;
import com.cloud.cang.mgr.sb.service.GroupManageService;

@Service
public class GroupManageServiceImpl extends GenericServiceImpl<GroupManage, String> implements
		GroupManageService {

	@Autowired
	GroupManageDao groupManageDao;

	@Autowired
	GroupRelationshipDao groupRelationshipDao;

	
	@Override
	public GenericDao<GroupManage, String> getDao() {
		return groupManageDao;
	}


	@Override
	public Page<GroupManageDomain> selectPageByDomainWhere(Page<GroupManageDomain> page, GroupManageVo groupManageVo) {

		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return (Page<GroupManageDomain>) groupManageDao.selectByDomainWhere(groupManageVo);
	}

	@Override
	public List<GroupManage> selectAll() {

		return groupManageDao.selectAll();
	}

	@Override
	public int updateBySelectiveVo(GroupManage groupManage) {
		return groupManageDao.updateByIdSelectiveVo(groupManage);
	}

	/**
	 * 查询该设备所属商户的分组信息
	 * @param merchantId
	 * @return
	 */
	@Override
	public List<GroupManage> selectDeviceGroup(String merchantId) {
		return groupManageDao.selectDeviceGroup(merchantId);
	}

	/**
	 * 删除设备分组信息
	 *
	 * @param checkboxId
	 */
	@Override
	public void deleteByGroupId(String[] checkboxId) {
		for (String id : checkboxId) {
			groupManageDao.deleteByPrimaryKey(id);
			groupRelationshipDao.deleteByGroupId(id);
		}
	}


}