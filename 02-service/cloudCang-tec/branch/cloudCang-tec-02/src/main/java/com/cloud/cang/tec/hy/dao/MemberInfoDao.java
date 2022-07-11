package com.cloud.cang.tec.hy.dao;

import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.MemberInfo;

/** 会员基础信息表(HY_MEMBER_INFO) **/
public interface MemberInfoDao extends GenericDao<MemberInfo, String> {
    /**
     * 查询所有单次代扣且开通免密的人
     * @return
     */
    List<MemberInfo> selectByAlipaySign();

}