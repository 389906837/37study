package com.cloud.cang.mgr.sb.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sb.domain.GroupManageDomain;
import com.cloud.cang.mgr.sb.vo.GroupManageVo;
import com.cloud.cang.model.sb.GroupManage;
import com.github.pagehelper.Page;

/** 设备分组管理(SB_GROUP_MANAGE) **/
public interface GroupManageDao extends GenericDao<GroupManage, String> {
    /**
     * 分页查询
     * @param groupManageVo
     * @return
     */
    Page<GroupManageDomain> selectByDomainWhere(GroupManageVo groupManageVo);

    /**
     * 查询所有分组
     * @return 分组List
     */
    List<GroupManage> selectAll();

    /**
     * 自定义修改方法
     * @param groupManage
     * @return
     */
    int updateByIdSelectiveVo(GroupManage groupManage);

    /**
     * 查询该设备所属商户的分组信息
     * @param merchantId
     * @return
     */
    List<GroupManage> selectDeviceGroup(String merchantId);



    /** codegen **/
}