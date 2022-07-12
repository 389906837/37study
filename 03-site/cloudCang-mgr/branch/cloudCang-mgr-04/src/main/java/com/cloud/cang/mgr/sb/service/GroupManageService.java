package com.cloud.cang.mgr.sb.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.mgr.sb.domain.GroupManageDomain;
import com.cloud.cang.mgr.sb.vo.GroupManageVo;
import com.cloud.cang.model.sb.GroupManage;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;

public interface GroupManageService extends GenericService<GroupManage, String> {


    /**
     *
     * @param page
     * @param groupManageVo
     * @return
     */
    Page<GroupManageDomain> selectPageByDomainWhere(Page<GroupManageDomain> page, GroupManageVo groupManageVo);


    /**
     * 查询所有分组
     * @return
     */
    List<GroupManage> selectAll();


    /**
     * 自定义修改model方法
     * @param groupManage
     * @return
     */
    int updateBySelectiveVo(GroupManage groupManage);

    /**
     * 查询该设备所属商户的分组信息
     * @param merchantId
     * @return
     */
    List<GroupManage> selectDeviceGroup(String merchantId);

    /**
     * 删除设备分组信息
     * @param checkboxId
     */
    void deleteByGroupId(String[] checkboxId);

}