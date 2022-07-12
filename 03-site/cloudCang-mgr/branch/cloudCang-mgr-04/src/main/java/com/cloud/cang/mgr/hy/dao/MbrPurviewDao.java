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
import com.cloud.cang.mgr.hy.vo.MbrPurviewVo;
import com.cloud.cang.model.hy.MbrPurview;
import com.cloud.cang.model.hy.MbrRole;
import com.github.pagehelper.Page;

/** 权限码管理(HY_MBR_PURVIEW) **/
public interface MbrPurviewDao extends GenericDao<MbrPurview, String> {

    Page<MbrPurview> selectPageAll(MbrPurviewVo mbrPurviewVo);

    List<Map<String,String>> selectAllByRole(MbrPurviewVo mbrPurviewVo);

    MbrPurview selectByExist(MbrPurview mbrPurview);

    List<MbrPurviewVo> selectMbrRole(MbrRole mbrRole);
}