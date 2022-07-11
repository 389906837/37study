package com.cloud.cang.rec.sys.dao;

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