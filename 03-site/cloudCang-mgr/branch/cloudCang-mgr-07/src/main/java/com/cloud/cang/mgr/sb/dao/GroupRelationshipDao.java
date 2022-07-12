package com.cloud.cang.mgr.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sb.GroupRelationship;

/**
 * 设备分组关系(SB_GROUP_RELATIONSHIP)
 **/
public interface GroupRelationshipDao extends GenericDao<GroupRelationship, String> {

    /**
     * 根据设备ID查询设备分组关系
     * @param deviceId 设备ID
     * @return 设备分组关系
     */
    GroupRelationship selectByDevieId(String deviceId);

    /**
     * 根据分组ID删除设备分组关联关系表对应数据
     * @param id
     */
    void deleteByGroupId(String id);



    /** codegen **/
}