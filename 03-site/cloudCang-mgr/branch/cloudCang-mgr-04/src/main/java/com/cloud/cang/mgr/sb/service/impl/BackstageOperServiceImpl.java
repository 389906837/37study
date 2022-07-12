package com.cloud.cang.mgr.sb.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;
import com.cloud.cang.mgr.sb.dao.BackstageOperDao;
import com.cloud.cang.mgr.sb.domain.BackstageOperDomain;
import com.cloud.cang.mgr.sb.service.BackstageOperService;
import com.cloud.cang.mgr.sb.vo.BackstageOperVo;
import com.cloud.cang.model.sb.BackstageOper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BackstageOperServiceImpl extends GenericServiceImpl<BackstageOper, String> implements
		BackstageOperService {

	@Autowired
	BackstageOperDao backstageOperDao;

	
	@Override
	public GenericDao<BackstageOper, String> getDao() {
		return backstageOperDao;
	}


	/**
	 * 后台设备操作记录分页查询
	 *
	 * @param page
	 * @param backstageOperVo
	 * @return
	 */
	@Override
	public Page<BackstageOperDomain> selectPageByDomainWhere(Page<BackstageOperDomain> page, BackstageOperVo backstageOperVo) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        String sname = backstageOperVo.getSaddUser();
        if (StringUtils.isNotBlank(sname)) {
            char[] chars = sname.toCharArray();
            backstageOperVo.setSaddUser(StringUtils.join(chars, '%'));
        }
        return backstageOperDao.selectPageByDomainWhere(backstageOperVo);
    }
}