package com.cloud.cang.rec.cr.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.cr.OpenGpuServer;

/**
 * API服务和GPU服务关联表(CR_OPEN_GPU_SERVER)
 **/
public interface OpenGpuServerDao extends GenericDao<OpenGpuServer, String> {

    /**
     * 根据OpenServerId删除 API服务和GPU服务关联表
     *
     * @param openServerId
     */
    void deleteByOpenServerId(String openServerId);
}