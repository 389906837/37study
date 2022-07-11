package com.cloud.cang.rec.op.service;

import com.cloud.cang.common.ResponseVo;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.rec.op.domain.InterfaceAcceptDomain;
import com.cloud.cang.rec.op.domain.InterfaceInfoDomain;
import com.cloud.cang.rec.op.vo.InterfaceAcceptVo;
import com.github.pagehelper.Page;

import java.io.IOException;

public interface InterfaceAcceptService extends GenericService<InterfaceAccept, String> {

    /**
     * 查询列表
     *
     * @param page
     * @param interfaceAcceptDomain
     * @return
     */
    Page<InterfaceAcceptVo> selectPageByDomainWhere(Page<InterfaceAcceptVo> page, InterfaceAcceptDomain interfaceAcceptDomain);

    /**
     * 删除平台接口业务受理信息
     *
     * @param checkboxId
     */
    void delete(String[] checkboxId);

    /**
     * 主动通知
     * @param id
     * @return
     */
    ResponseVo activeNotify(String id) throws IOException;
}