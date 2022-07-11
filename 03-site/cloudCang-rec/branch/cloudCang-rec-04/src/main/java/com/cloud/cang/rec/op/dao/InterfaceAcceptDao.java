package com.cloud.cang.rec.op.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.AppManage;
import com.cloud.cang.model.op.InterfaceAccept;
import com.cloud.cang.rec.op.domain.AppManageDomain;
import com.cloud.cang.rec.op.domain.InterfaceAcceptDomain;
import com.cloud.cang.rec.op.vo.InterfaceAcceptVo;
import com.github.pagehelper.Page;

/**
 * 平台接口业务受理信息表(OP_INTERFACE_ACCEPT)
 **/
public interface InterfaceAcceptDao extends GenericDao<InterfaceAccept, String> {


    Page<InterfaceAcceptVo> selectPageByDomainWhere(InterfaceAcceptDomain interfaceAcceptDomain);
}