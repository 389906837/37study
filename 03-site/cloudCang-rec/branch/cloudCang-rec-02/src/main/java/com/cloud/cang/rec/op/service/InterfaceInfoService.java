package com.cloud.cang.rec.op.service;

import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.rec.op.domain.InterfaceInfoDomain;
import com.cloud.cang.rec.op.domain.UserInfoDomain;
import com.cloud.cang.rec.op.vo.UserInfoVo;
import com.github.pagehelper.Page;

public interface InterfaceInfoService extends GenericService<InterfaceInfo, String> {


    /**
     * 查询列表
     *
     * @param page
     * @param interfaceInfoDomain
     * @return
     */
    Page<InterfaceInfo> selectPageByDomainWhere(Page<InterfaceInfo> page, InterfaceInfoDomain interfaceInfoDomain);

    /**
     * 删除平台应用
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    /**
     * 添加平台接口
     *
     * @param interfaceInfo
     * @return
     */
    InterfaceInfo saveInterface(InterfaceInfo interfaceInfo);

    /**
     * 修改平台接口
     *
     * @param interfaceInfo
     */
    void upInterface(InterfaceInfo interfaceInfo);
}