package com.cloud.cang.mgr.cs.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sys.domain.CurrenttradedateDomain;
import com.cloud.cang.model.cs.Currenttradedate;
import com.github.pagehelper.Page;

/** 节日管理(CS_CURRENTTRADEDATE) **/
public interface CurrenttradedateDao extends GenericDao<Currenttradedate, String> {



    //查询列表
    Page<Currenttradedate> selectPageByDomainWhere(CurrenttradedateDomain currenttradedateDomain);
    //初始化
    void batchDeleteDay(String string);
}