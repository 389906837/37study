package com.cloud.cang.wap.sys.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.sys.Operator;
import org.apache.ibatis.annotations.Param;

/** 后台用户表(SYS_OPERATOR) **/
public interface OperatorDao extends GenericDao<Operator, String> {
    /**
     * 获取系统用户
     * @param userName 会员名
     * @param merchantCode 商户编号
     * @return
     */
    Operator selectByMemberName(@Param("userName") String userName, @Param("merchantCode") String merchantCode);
    /**
     * 查询补货员信息
     * @param paramMap 查询参数
     * @return
     */
    Operator selectByMap(Map<String, Object> paramMap);
}