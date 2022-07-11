package com.cang.os.mgr.sys.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.cang.os.common.dao.BaseMongoDao;
import com.cang.os.bean.sys.Purview;
import com.cang.os.mgr.sys.dao.PurviewDao;
import com.cang.os.security.service.SecPurviewService;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;






import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.cang.os.common.service.BaseServiceImpl;


import com.cang.os.bean.sys.Operatorrole;
import com.cang.os.bean.sys.Rolepurview;
import com.cang.os.mgr.sys.service.OperatorroleService;
import com.cang.os.mgr.sys.service.PurviewService;
import com.cang.os.mgr.sys.service.RolepurviewService;
import com.cang.os.security.vo.PurviewVO;

@Service
public class PurviewServiceImpl extends BaseServiceImpl<Purview> implements
		PurviewService, SecPurviewService {

	@Autowired
    PurviewDao purviewDao;

	@Autowired
	OperatorroleService operatorroleService;

	@Autowired
	RolepurviewService rolepurviewService;

	@Override
	public BaseMongoDao<Purview> getDao() {
		return purviewDao;
	}

	@Override
	public List<PurviewVO> queryAll() {
		List<Purview> dbPur = this.purviewDao.findAll();
		if (dbPur == null)
			return null;
		List<PurviewVO> secPurList = new ArrayList<PurviewVO>();
		for (Purview pur : dbPur) {
			PurviewVO secPur = new PurviewVO();
			secPur.setPurviewCode(pur.getSpurCode());
			secPur.setPurviewUrl(pur.getSurlPath());
			secPurList.add(secPur);
		}
		return secPurList;
	}

	@Override
	public List<PurviewVO> queryByUserId(String userId) {
		Query query = new Query(Criteria.where("soperatorId").is(userId));
		List<Operatorrole> userRoles = operatorroleService.find(query);
		List<String> roleids = new ArrayList<String>();
		for (Operatorrole operatorrole : userRoles) {
			roleids.add(operatorrole.getSroleId());
		}
		Query rolepurviewQuery = new Query(Criteria.where("sroleId")
				.in(roleids));
		List<Rolepurview> rplist = rolepurviewService.find(rolepurviewQuery);

		List<String> purviewIds = new ArrayList<String>();
		for (Rolepurview rolepurview : rplist) {
			if (!purviewIds.contains(rolepurview.getSpurviewId())) {
				purviewIds.add(rolepurview.getSpurviewId());
			}
		}

		Query purviewQuery = new Query(Criteria.where("id").in(purviewIds));
		List<PurviewVO> voArr = new ArrayList<PurviewVO>();
		List<Purview> entity = purviewDao.find(purviewQuery);
		if (entity != null) {
			for (Purview r : entity) {
				PurviewVO vo = new PurviewVO();
				vo.setPurviewCode(r.getSpurCode());
				vo.setPurviewId(r.getId());
				vo.setPurviewUrl(r.getSurlPath());
				voArr.add(vo);
			}
		}
		return voArr;
	}

}