package com.cloud.cang.api.hy.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.MemberInfo;

/**
 * 会员基础信息表(HY_MEMBER_INFO)
 **/
public interface MemberInfoDao extends GenericDao<MemberInfo, String> {

    /**
     * 根据手机尾号查找对应用户
     *
     * @param memberInfoVo
     * @return
     */
    List<MemberInfo> selectByTailNum(MemberInfo memberInfoVo);


    /** codegen **/
}