package com.cloud.cang.mgr.hy.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.dao.MbrRoleDao;
import com.cloud.cang.mgr.hy.service.MbrPurviewService;
import com.cloud.cang.mgr.hy.service.MbrRolePurService;
import com.cloud.cang.mgr.hy.service.MbrRoleService;
import com.cloud.cang.mgr.hy.vo.MbrRoleVo;
import com.cloud.cang.model.hy.MbrPurview;
import com.cloud.cang.model.hy.MbrRole;
import com.cloud.cang.model.hy.MbrRolePur;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class MbrRoleServiceImpl extends GenericServiceImpl<MbrRole, String> implements
		MbrRoleService {

	@Autowired
	MbrRoleDao mbrRoleDao;

	@Autowired
	MbrPurviewService mbrPurviewService;

	@Autowired
	MbrRolePurService mbrRolePurService;
	
	@Override
	public GenericDao<MbrRole, String> getDao() {
		return mbrRoleDao;
	}

	/**
	 * 会员分配权限
	 * @param page
	 * @param mbrRoleVo
	 * @return
	 */
	@Override
	public Page<MbrRole> selectPageAllRole(Page<MbrRole> page, MbrRoleVo mbrRoleVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return mbrRoleDao.selectPageAllRole(mbrRoleVo);
	}

	/**
	 * 修改会员角色
	 * @param mbrRole
	 * @return
	 */
	@Override
	public MbrRole selectByExist(MbrRole mbrRole) {
		return mbrRoleDao.selectByExist(mbrRole);
	}

	/**
	 * 分配会员角色
	 * @param purIds
	 * @param mid
	 */
	@Override
	public void saveOperatorRole(String[] purIds, String mid) {
		// 删除roleId下所有权限
		mbrRolePurService.deleteByRoleId(mid);
		if (StringUtils.isNotBlank(mid)){
			for (String purId:purIds) {
				MbrRolePur mbrRolePur = new MbrRolePur();
				mbrRolePur.setSroleId(mid);
				mbrRolePur.setSpurviewId(purId);
				mbrRolePurService.insert(mbrRolePur);
			}
		}
	}


	/**
	 *  根据ID数据删除会员角色表
	 */
	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId)
		{
			MbrRole mbrRole  = mbrRoleDao.selectByPrimaryKey(id);
			if (mbrRole != null)
			{
				//物理删除
				mbrRoleDao.deleteByPrimaryKey(mbrRole.getId());
				//操作日志
				String logText= MessageFormat.format("删除会员角色,名称{0}",mbrRole.getSroleName() );
				LogUtil.addOPLog(LogUtil.AC_DELETE,logText, null);

			}
		}
	}


}