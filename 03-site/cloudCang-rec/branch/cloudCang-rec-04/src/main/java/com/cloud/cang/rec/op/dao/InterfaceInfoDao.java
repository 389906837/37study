package com.cloud.cang.rec.op.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.InterfaceInfo;
import com.cloud.cang.rec.op.domain.InterfaceInfoDomain;
import com.cloud.cang.rec.op.domain.UserInfoDomain;
import com.cloud.cang.rec.op.vo.UserInfoVo;
import com.github.pagehelper.Page;

/**
 * 平台接口信息管理表(OP_INTERFACE_INFO)
 **/
public interface InterfaceInfoDao extends GenericDao<InterfaceInfo, String> {


    Page<InterfaceInfo> selectPageByDomainWhere(InterfaceInfoDomain interfaceInfoDomain);
}