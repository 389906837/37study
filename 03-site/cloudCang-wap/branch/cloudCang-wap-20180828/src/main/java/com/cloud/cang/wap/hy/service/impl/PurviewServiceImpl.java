package com.cloud.cang.wap.hy.service.impl;

import com.cloud.cang.core.common.CoreConstant;
import com.cloud.cang.core.utils.GrpParaUtil;
import com.cloud.cang.model.hy.MbrPurview;
import com.cloud.cang.security.service.SecPurviewService;
import com.cloud.cang.security.vo.PurviewVO;
import com.cloud.cang.wap.hy.dao.MbrPurviewDao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PurviewServiceImpl implements SecPurviewService {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PurviewServiceImpl.class);
	
	@Autowired
	private MbrPurviewDao mbrPurviewDao;

	@Override
	public List<PurviewVO> queryAll() {
		LOGGER.debug("#################加载web站权限queryAll开始#################");
		List<MbrPurview> purviewList = mbrPurviewDao.selectPurviewBySiteName(GrpParaUtil.getValue(CoreConstant.CANG_SITE_DATA_CODE, CoreConstant.CANG_WAP_SITE_CODE));
		List<PurviewVO> _PurviewVOList = new ArrayList<PurviewVO>();
		if (purviewList != null && purviewList.size() > 0) {
			for (MbrPurview mbrPurview:purviewList) {
				PurviewVO purviewVO = new PurviewVO();
				purviewVO.setPurviewCode(mbrPurview.getSpurCode());
				purviewVO.setPurviewId(mbrPurview.getId());
				purviewVO.setPurviewUrl(mbrPurview.getSurlPath());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("----->{},{},{}", new Object[]{purviewVO.getPurviewId(),purviewVO.getPurviewCode(),purviewVO.getPurviewUrl()});
				}
				_PurviewVOList.add(purviewVO);
			}
		}
		LOGGER.debug("#################加载web站权限queryAll完成#################");
		return _PurviewVOList;
	}
	

	@Override
	public List<PurviewVO> queryByUserId(String userId) {
		LOGGER.debug("#################加载web站用户权限#################");
		List<MbrPurview> purviewList = mbrPurviewDao.selectPurviewByUserId(userId);
		List<PurviewVO> _PurviewVOList = new ArrayList<PurviewVO>();
		if (purviewList != null && purviewList.size() > 0) {
			for (MbrPurview mbrPurview:purviewList) {
				PurviewVO purviewVO = new PurviewVO();
				purviewVO.setPurviewCode(mbrPurview.getSpurCode());
				purviewVO.setPurviewId(mbrPurview.getId());
				purviewVO.setPurviewUrl(mbrPurview.getSurlPath());
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("----->{},{},{}", new Object[]{purviewVO.getPurviewId(),purviewVO.getPurviewCode(),purviewVO.getPurviewUrl()});
				}
				_PurviewVOList.add(purviewVO);
			}
		}
	   LOGGER.debug("#################加载web站用户权限完成#################");
		return _PurviewVOList;
		
	}

}
