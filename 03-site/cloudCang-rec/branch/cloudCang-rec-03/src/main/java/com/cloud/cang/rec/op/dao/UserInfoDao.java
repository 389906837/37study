package com.cloud.cang.rec.op.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.UserInfo;
import com.cloud.cang.rec.op.domain.UserInfoDomain;
import com.cloud.cang.rec.op.vo.UserInfoVo;
import com.cloud.cang.rec.op.vo.UserInterfaceAuthVo;
import com.github.pagehelper.Page;

/**
 * 开放平台用户信息表(OP_USER_INFO)
 **/
public interface UserInfoDao extends GenericDao<UserInfo, String> {


    Page<UserInfoVo> selectPageByDomainWhere(UserInfoDomain userInfoDomain);

    UserInfoVo selectVoById(String id);

    List<UserInterfaceAuthVo> selectUserInterfaceAuthVoList(String sid);

}