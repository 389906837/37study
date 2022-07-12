package com.cloud.cang.mgr.hy.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.hy.vo.MbrRoleVo;
import com.cloud.cang.model.hy.MbrRole;
import com.github.pagehelper.Page;

/** 会员角色表(HY_MBR_ROLE) **/
public interface MbrRoleDao extends GenericDao<MbrRole, String> {


    Page<MbrRole> selectPageAllRole(MbrRoleVo mbrRoleVo);

    MbrRole selectByExist(MbrRole mbrRole);

}