package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.mgr.hy.domain.ThirdAuthoriseInfoDomain;
import com.cloud.cang.mgr.hy.vo.ThirdAuthoriseInfoVo;
import com.cloud.cang.model.hy.ThirdAuthorise;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

public interface ThirdAuthoriseService extends GenericService<ThirdAuthorise, String> {

    /**
     * 会员第三方授权信息查询接口
     * @param page
     * @param thirdAuthoriseInfoVo
     * @return
     */
    Page<ThirdAuthoriseInfoDomain> selectThirdAuthorise(Page<ThirdAuthoriseInfoDomain> page, ThirdAuthoriseInfoVo thirdAuthoriseInfoVo);
    
}