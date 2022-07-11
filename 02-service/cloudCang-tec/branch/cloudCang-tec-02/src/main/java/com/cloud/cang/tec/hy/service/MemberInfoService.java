package com.cloud.cang.tec.hy.service;

import com.cloud.cang.model.hy.MemberInfo;
import com.cloud.cang.generic.GenericService;

import java.util.List;

public interface MemberInfoService extends GenericService<MemberInfo, String> {


    /**
     * 查询所有单次代扣且开通免密的人
     * @return
     */
    List<MemberInfo> selectByAlipaySign();
}