package com.cloud.cang.act.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.MemberInfo;

import java.util.Date;

/** 会员基础信息表(HY_MEMBER_INFO) **/
public interface MemberInfoDao extends GenericDao<MemberInfo, String> {


    public Date selectRegisterDate(String id);

}