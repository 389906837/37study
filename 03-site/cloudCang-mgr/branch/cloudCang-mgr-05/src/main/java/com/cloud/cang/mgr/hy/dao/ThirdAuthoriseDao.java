package com.cloud.cang.mgr.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.hy.domain.ThirdAuthoriseInfoDomain;
import com.cloud.cang.mgr.hy.vo.ThirdAuthoriseInfoVo;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.github.pagehelper.Page;

/** 第三方授权信息表(HY_THIRD_AUTHORISE) **/
public interface ThirdAuthoriseDao extends GenericDao<ThirdAuthorise, String> {

    Page<ThirdAuthoriseInfoDomain> selectThirdAuthorise(ThirdAuthoriseInfoVo thirdAuthoriseInfoVo);
}