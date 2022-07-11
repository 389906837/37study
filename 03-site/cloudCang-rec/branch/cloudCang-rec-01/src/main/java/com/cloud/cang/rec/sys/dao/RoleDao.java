package com.cloud.cang.rec.sys.dao;

import com.cloud.cang.generic.GenericDao;

import com.cloud.cang.rec.sys.domain.PurviewDomain;
import com.cloud.cang.rec.sys.domain.RoleDomain;
import com.cloud.cang.rec.sys.vo.RoleVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Role;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

/** 角色表(SYS_ROLE) **/
public interface RoleDao extends GenericDao<Role, String> {

    /**
     * 根据用户id查角色信息
     * @param userId
     * @return
     */
    public List<Role> selectByUserId(String userId);

    /**
     * 根据用户ID查询用户没有的角色
     * @param userId
     * @return
     */
    public List<Role> selectOperatorNoCheckRole(String userId);
    /**
     * 查询所有角色
     * @param roleDomain
     * @return
     */
    public Page<RoleVo> selectByDomainWhere(RoleDomain roleDomain);

    /**
     *查询用户商会下所有角色
     * @param operator
     * @return
     */
    public  List<Role> selectAllRole(Operator operator);
    /**
     *查询用户商会下所有角色
     * @param operator
     * @return
     */
     List<RoleVo> selectOperatorRole(Operator operator);
    /**
     * 获取商户权限
     * @param paramMap
     * @return
     */
    List<PurviewDomain> selectPurviewByMerchant(Map<String, Object> paramMap);

}