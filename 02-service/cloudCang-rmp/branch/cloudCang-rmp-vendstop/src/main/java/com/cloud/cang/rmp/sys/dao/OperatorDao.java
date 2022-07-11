package com.cloud.cang.rmp.sys.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.Operator;
import org.apache.ibatis.annotations.Param;

/** 后台用户表(SYS_OPERATOR) **/
public interface OperatorDao extends GenericDao<Operator, String> {
    /**
     * 根据手机号 获取系统用户
     * @param smemberName 会员手机号
     * @return
     */
    Operator selectByMoblie(@Param("smemberName") String smemberName, @Param("smerchantId") String smerchantId);


}