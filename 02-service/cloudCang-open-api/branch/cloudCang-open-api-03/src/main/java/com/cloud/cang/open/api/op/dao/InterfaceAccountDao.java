package com.cloud.cang.open.api.op.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.op.InterfaceAccount;

/** 接口账户记录表(OP_INTERFACE_ACCOUNT) **/
public interface InterfaceAccountDao extends GenericDao<InterfaceAccount, String> {

    /**
     * 根据查询参数获取接口账户
     * @param paramMap 查询参数
     * @return
     */
    InterfaceAccount selectByMap(Map<String, Object> paramMap);

    /**
     * 锁住接口账户信息 行锁
     * @param id 账户ID
     * @return
     */
    InterfaceAccount selectByPrimaryKeySync(String id);
    /**
     * 根据查询参数获取接口账户数据
     * @param paramMap 查询参数
     * @return
     */
    InterfaceAccount selectByParamsMap(Map<String, Object> paramMap);
}