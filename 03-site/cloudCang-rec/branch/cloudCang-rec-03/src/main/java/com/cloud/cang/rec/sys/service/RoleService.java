package com.cloud.cang.rec.sys.service;

import com.cloud.cang.generic.GenericService;

import com.cloud.cang.rec.sys.domain.PurviewDomain;
import com.cloud.cang.rec.sys.domain.RoleDomain;
import com.cloud.cang.rec.sys.vo.RoleVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Role;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface RoleService extends GenericService<Role, String> {

    /**
     * 根据ID集合删除Role
     * @param checkboxId
     */
    public void delete(String[] checkboxId);

    /**
     * 分派权限，
     * @param checkPurviewId 选中的权限ID 用","隔开
     * @param roleId
     */
    public void saveRolePurview(String checkPurviewId, String roleId);

    /**
     * 根据用户ID查询用户没有的角色
     * @param userId
     * @return
     */
    public List<Role> selectOperatorNoCheckRole(String userId);

    /**
     * 根据用户id查角色信息
     * @param userId
     * @return
     */
    public List<Role> selectByUserId(String userId);


    public Page<RoleVo> selectPageByDomainWhere(Page<RoleVo> page, RoleDomain roleDomain);
    /**
     * 查询用户角色上商户下所有角色
     */
    public List<Role> selectAllRole(Operator operator);
    /**
     * 获取商户权限
     * @param paramMap
     * @return
     */
    List<PurviewDomain> selectPurviewByMerchant(Map<String, Object> paramMap);

    /***
     * 更新角色权限
     * @param role 角色信息
     * @param purviewIds 权限Id集合
     * @throws Exception
     */
    void updateRoleAuth(Role role, String[] purviewIds) throws Exception;

    /**
     * 查询用户角色（已有角色和未有角色）
     * @param operator
     * @return
     */
    public  List<RoleVo> selectOperatorRole(Operator operator);
}