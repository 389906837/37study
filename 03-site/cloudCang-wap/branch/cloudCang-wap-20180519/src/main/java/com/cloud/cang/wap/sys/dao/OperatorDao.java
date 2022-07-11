package com.cloud.cang.wap.sys.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.Operator;

/** 后台用户表(SYS_OPERATOR) **/
public interface OperatorDao extends GenericDao<Operator, String> {
    /**
     * 获取系统用户
     * @param userName 会员名
     * @return
     */
    Operator selectByMemberName(String userName);
}