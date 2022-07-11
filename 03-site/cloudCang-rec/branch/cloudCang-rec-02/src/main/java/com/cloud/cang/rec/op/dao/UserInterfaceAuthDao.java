package com.cloud.cang.rec.op.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.UserInterfaceAuth;
import com.cloud.cang.rec.op.domain.UserInterfaceAuthDomain;
import com.cloud.cang.rec.op.vo.UserInterfaceAuthVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

/**
 * 用户接口权限表(OP_USER_INTERFACE_AUTH)
 **/
public interface UserInterfaceAuthDao extends GenericDao<UserInterfaceAuth, String> {

    Page<UserInterfaceAuthVo> selectPageByDomainWhere(UserInterfaceAuthDomain userInterfaceAuthDomain);

    UserInterfaceAuth selectByAppIdAndInterfaceCodeAndUserId(@Param("appId") String appId, @Param("interfaceCode") String interfaceCode, @Param("userId") String userId);

    int selectUserAuthNum(String userId);
}