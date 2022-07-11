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
import com.cloud.cang.model.cr.ServerList;
import com.cloud.cang.rec.cr.domain.OpenServerListDomain;
import com.github.pagehelper.Page;

/**
 * GPU识别服务器列表(CR_SERVER_LIST)
 **/
public interface ServerListDao extends GenericDao<ServerList, String> {


    /**
     * 查询列表
     *
     * @param serverList
     * @return
     */
    Page<ServerList> selectPageByDomainWhere(ServerList serverList);
}