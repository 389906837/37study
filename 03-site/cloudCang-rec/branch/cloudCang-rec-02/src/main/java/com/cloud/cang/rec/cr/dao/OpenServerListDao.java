package com.cloud.cang.rec.cr.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.cr.OpenServerList;
import com.cloud.cang.rec.cr.domain.OpenServerListDomain;
import com.cloud.cang.rec.cr.vo.OpenServerListVo;
import com.github.pagehelper.Page;

/**
 * 开放平台API服务器管理(CR_OPEN_SERVER_LIST)
 **/
public interface OpenServerListDao extends GenericDao<OpenServerList, String> {


    /**
     * 查询列表
     *
     * @param openServerListDomain
     * @return
     */
    Page<OpenServerList> selectPageByDomainWhere(OpenServerListDomain openServerListDomain);

    /**
     * 进入编辑页面查询API服务和GPU服务关联
     *
     * @param id
     * @return
     */
    OpenServerListVo selectOpenGpuServer(String id);
}