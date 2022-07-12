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
import com.cloud.cang.model.hy.MbrRoleConf;

/** 会员角色关系表(HY_MBR_ROLE_CONF) **/
public interface MbrRoleConfDao extends GenericDao<MbrRoleConf, String> {


    /**
     * 根据角色ID和用户Id删除
     *
     * @param map
     * @return
     */
    void delByMbrIdRoleId (Map<String,String> map);
}