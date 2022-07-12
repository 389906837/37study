package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.model.sb.GroupRelationship;
import com.cloud.cang.generic.GenericService;

public interface GroupRelationshipService extends GenericService<GroupRelationship, String> {


    /**
     * 设备和分组信息入设备分组关系表
     * @param deviceIds
     * @param groupId
     */
    void insertDeviceIds(String deviceIds, String groupId) throws Exception;

    /**
     * 根据设备ID查询设备分组关系
     * @param deviceId 设备ID
     * @return 设备分组关系
     */
    GroupRelationship selectByDevieId(String deviceId);


}