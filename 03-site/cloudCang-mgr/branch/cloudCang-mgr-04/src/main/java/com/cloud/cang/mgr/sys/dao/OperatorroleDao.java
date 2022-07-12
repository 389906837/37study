package com.cloud.cang.mgr.sys.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.Operatorrole;

/** 用户角色表(SYS_OPERATORROLE) **/
public interface OperatorroleDao extends GenericDao<Operatorrole, String> {

    /**
     * 根据用户ID删除角色
     * @param operatorId
     */
    void deleteByOperatorId(String operatorId);


    /** codegen **/
}