package com.cloud.cang.mgr.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cloud.cang.mgr.sh.domain.PurviewDomain;
import com.cloud.cang.mgr.sh.service.MerchantInfoService;
import com.cloud.cang.mgr.sys.service.MerchantPurviewService;
import com.cloud.cang.mgr.sys.service.OperatorService;
import com.cloud.cang.mgr.sys.web.OperatorController;
import com.cloud.cang.model.sh.MerchantInfo;
import com.cloud.cang.model.sys.MerchantPurview;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.security.service.SecPurviewService;
import com.cloud.cang.security.utils.SessionUserUtils;
import com.cloud.cang.security.vo.PurviewVO;
import com.cloud.cang.utils.StringUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sys.dao.PurviewDao;
import com.cloud.cang.model.sys.Purview;
import com.cloud.cang.mgr.sys.service.PurviewService;

@Service
public class PurviewServiceImpl extends GenericServiceImpl<Purview, String> implements
		PurviewService,SecPurviewService {

	@Autowired
	private PurviewDao purviewDao;
	private static final Logger logger = LoggerFactory.getLogger(PurviewServiceImpl.class);
	@Override
	public GenericDao<Purview, String> getDao() {
		return purviewDao;
	}


	@Override
	public List<PurviewVO> queryAll() {
		/*//获取所有权限 根据商户ID
		String merchantId = (String) SessionUserUtils.getSessionAttribute(SessionUserUtils.SESSION_KEY_SMERCHANT_ID);
		if (StringUtil.isBlank(merchantId)) {
			logger.error("Session商户不存在,商户ID：{}",merchantId);
			return null;
		}
		List<Purview> dbPur= this.purviewDao.selectByAllPurviewByMerchantId(merchantId);*/
		List<Purview> dbPur= this.purviewDao.selectByEntityWhere(new Purview());
		if(dbPur==null)return null;
		List<PurviewVO> secPurList=new ArrayList<PurviewVO>();
		for(Purview pur:dbPur){
			PurviewVO secPur=new PurviewVO();
			secPur.setPurviewCode(pur.getSpurCode());
			secPur.setPurviewUrl(pur.getSurlPath());
			secPur.setPurviewId(pur.getId());
			secPurList.add(secPur);
		}
		return secPurList;
	}

	@Override
	public List<PurviewVO> queryByUserId(String userId) {
		List<PurviewVO> voArr=new ArrayList<PurviewVO>();
		Operator operator = purviewDao.selectOperatorByPrimaryKey(userId);
		if (operator == null) {
			logger.error("获取权限用户不存在,用户ID：{}",userId);
			return null;
		}
		if (operator.getBisAdmin().intValue() == 1) {
			//是超级管理员
			logger.info("超级管理员获取所有权限");
			return this.queryMerchantAll(operator.getSmerchantId());
		}
		List<Purview> entity= this.purviewDao.selectByUserId(userId);
		if(entity!=null){
			for(Purview r:entity){
				PurviewVO vo=new PurviewVO();
				vo.setPurviewCode(r.getSpurCode());
				vo.setPurviewId(r.getId());
				vo.setPurviewUrl(r.getSurlPath());
				voArr.add(vo);
			}
		}
		return voArr;
	}
	@Override
	public boolean isNameExist(Purview param) {
		int count =  purviewDao.isNameExist(param);
		if (count > 0) return true;
		return false;
	}
	@Override
	public void delete(String[] checkboxId) {
		for (String id:checkboxId) {
			//删除权限商户关联表
			purviewDao.deletePurviewMerchantByPurviewId(id);
			//删除后台权限角色关联表
			purviewDao.deleteRolePurviewByPurviewId(id);
			//删除权限码
			purviewDao.deleteByPrimaryKey(id);
		}
	}

	@Override
	public List<PurviewVO> queryMerchantAll(String merchantId) {
		List<Purview> dbPur= this.purviewDao.selectByAllPurviewByMerchantId(merchantId);
		if(dbPur==null)return null;
		List<PurviewVO> secPurList=new ArrayList<PurviewVO>();
		for(Purview pur:dbPur){
			PurviewVO secPur=new PurviewVO();
			secPur.setPurviewCode(pur.getSpurCode());
			secPur.setPurviewUrl(pur.getSurlPath());
			secPur.setPurviewId(pur.getId());
			secPurList.add(secPur);
		}
		return secPurList;
	}

	/**
	 * 获取商户权限
	 * @param paramMap
	 * @return
	 */
	@Override
	public List<PurviewDomain> selectPurviewByMerchant(Map<String, Object> paramMap) {
		return 	purviewDao.selectPurviewByMerchant(paramMap);
	}


	@Override
	public List<Purview> selectMerchantPurview(String merchantId){
		return 	purviewDao.selectMerchantPurview(merchantId);
	}

	@Override
	/**
	 * 查询用户所有角色下的权限
	 */
	public List<String> selectOperatorPurview(String operatorId) {
		return purviewDao.selectOperatorPurview(operatorId);
	}
	/**
	 * 分页查询权限数据
	 * @param page 分页数据
	 * @param map 查询参数
	 * @return
	 */
	@Override
	public Page<Purview> selectPageByMap(Page<Purview> page, Map<String, Object> map) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return purviewDao.selectPageByMap(map);
	}
}