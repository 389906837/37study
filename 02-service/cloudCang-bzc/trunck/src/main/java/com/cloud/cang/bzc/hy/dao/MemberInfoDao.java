package com.cloud.cang.bzc.hy.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.model.hy.MemberInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 会员基础信息表
 * （HY_MEMBER_INFO）
 *
 * @Author: zengzexiong
 * @Date: 2017年12月27日
 * @version 1.0
 */
public interface MemberInfoDao extends GenericDao<MemberInfo,String>{


    /**
     * 会员角色关系表
     * （HY_MBR_ROLE_CONF）
     * @param id
     * @return
     */
    Integer addRoleForUser(String id);


    /**
     * 根据推荐编号查询用户
     * @param recomendCode
     * @return
     */
    String selectMemberInfoByRecommedCode( @Param("recomendCode") String recomendCode, @Param("merchantCode") String merchantCode);
}
