package com.cloud.cang.mgr.hy.service.impl;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import com.cloud.cang.mgr.common.utils.LogUtil;
import com.cloud.cang.mgr.hy.vo.MbrPurviewVo;
import com.cloud.cang.model.hy.MbrRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import com.cloud.cang.exception.ServiceException;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.datasource.DataSource;

import com.cloud.cang.mgr.hy.dao.MbrPurviewDao;
import com.cloud.cang.model.hy.MbrPurview;
import com.cloud.cang.mgr.hy.service.MbrPurviewService;

@Service
public class MbrPurviewServiceImpl extends GenericServiceImpl<MbrPurview, String> implements
		MbrPurviewService {

	@Autowired
	MbrPurviewDao mbrPurviewDao;

	
	@Override
	public GenericDao<MbrPurview, String> getDao() {
		return mbrPurviewDao;
	}


	@Override
	public Page<MbrPurview> selectPageAll(Page<MbrPurview> page, MbrPurviewVo mbrPurviewVo) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return mbrPurviewDao.selectPageAll(mbrPurviewVo);
	}

	@Override
	public List<Map<String, String>> selectAllByRole(MbrPurviewVo mbrPurviewVo) {
		return mbrPurviewDao.selectAllByRole(mbrPurviewVo);
	}

	@Override
	public MbrPurview selectByExist(MbrPurview mbrPurview) {
		return mbrPurviewDao.selectByExist(mbrPurview);
	}

	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId) {
			MbrPurview mbrPurview = mbrPurviewDao.selectByPrimaryKey(id);
			if (mbrPurview != null){
				mbrPurviewDao.deleteByPrimaryKey(mbrPurview.getId());
				//操作日志
				String logText= MessageFormat.format("删除会员权限码，名称{0},编号{1}",mbrPurview.getSpurName(),mbrPurview.getSpurNo() );
				LogUtil.addOPLog(LogUtil.AC_DELETE, logText, null);
			}
		}
	}

	@Override
	public List<MbrPurviewVo> selectMbrRole(MbrRole mbrRole) {
		return mbrPurviewDao.selectMbrRole(mbrRole);
	}
}