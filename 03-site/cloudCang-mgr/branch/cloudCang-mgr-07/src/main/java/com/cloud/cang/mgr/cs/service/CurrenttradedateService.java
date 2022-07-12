package com.cloud.cang.mgr.cs.service;

import com.cloud.cang.mgr.sys.domain.CurrenttradedateDomain;
import com.cloud.cang.model.cs.Currenttradedate;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.Map;

public interface CurrenttradedateService extends GenericService<Currenttradedate, String> {

    //查询列表
    Page<Currenttradedate> selectPageByDomainWhere( Page<Currenttradedate> page,CurrenttradedateDomain currenttradedateVo);

    //初始化
    void initFestivalDay(String year);

    void setWork(String id);

    void setRest(String id);
}