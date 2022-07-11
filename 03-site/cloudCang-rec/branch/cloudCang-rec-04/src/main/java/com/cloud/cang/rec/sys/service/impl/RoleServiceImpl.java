package com.cloud.cang.rec.sys.service.impl;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.generic.GenericServiceImpl;

import com.cloud.cang.rec.sys.dao.RoleDao;
import com.cloud.cang.rec.sys.dao.RolepurviewDao;
import com.cloud.cang.rec.sys.domain.PurviewDomain;
import com.cloud.cang.rec.sys.domain.RoleDomain;
import com.cloud.cang.rec.sys.service.RoleService;
import com.cloud.cang.rec.sys.vo.RoleVo;
import com.cloud.cang.model.sys.Operator;
import com.cloud.cang.model.sys.Role;
import com.cloud.cang.model.sys.Rolepurview;
import com.cloud.cang.security.service.SecRoleSevice;
import com.cloud.cang.security.vo.RoleVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoleServiceImpl extends GenericServiceImpl<Role, String> implements
        RoleService, SecRoleSevice {

    @Autowired
    RoleDao roleDao;
    @Autowired
    private RolepurviewDao rolepurviewDao;

    @Override
    public GenericDao<Role, String> getDao() {
        return roleDao;
    }


    @Override
    public List<RoleVO> queryAllRole() {
        return null;
    }

    @Override
    public List<RoleVO> queryRoleByUserId(String userId) {
        List<RoleVO> voArr = new ArrayList<RoleVO>();
        List<Role> entity = this.roleDao.selectByUserId(userId);
        if (entity != null) {
            for (Role r : entity) {
                RoleVO vo = new RoleVO();
                vo.setRoleId(r.getId());
                vo.setRoleCode(r.getSroleName());
                vo.setRoleName(r.getSroleName());
                voArr.add(vo);
            }
        }
        return voArr;
    }

    @Override
    public void delete(String[] checkboxId) {
        for (String id : checkboxId) {
            rolepurviewDao.deleteByRoleId(id);
            roleDao.deleteByPrimaryKey(id);
        }
    }

    @Override
    public void saveRolePurview(String checkPurviewId, String roleId) {
        //删除已经存在的权限
        rolepurviewDao.deleteByRoleId(roleId);
        if (StringUtils.isNotBlank(checkPurviewId)) {
            String[] purIds = checkPurviewId.split(",");
            if (purIds != null && purIds.length > 0) {
                for (String purId : purIds) {
                    Rolepurview rolepurview = new Rolepurview();
                    rolepurview.setSpurviewId(purId);
                    rolepurview.setSroleId(roleId);
                    rolepurviewDao.insert(rolepurview);
                }

            }
        }

    }

    @Override
    public List<Role> selectOperatorNoCheckRole(String userId) {
        return roleDao.selectOperatorNoCheckRole(userId);
    }

    @Override
    public List<Role> selectByUserId(String userId) {
        return roleDao.selectByUserId(userId);
    }

    @Override
    public Page<RoleVo> selectPageByDomainWhere(Page<RoleVo> page, RoleDomain roleDomain) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return (Page<RoleVo>) roleDao.selectByDomainWhere(roleDomain);
    }

    @Override
    public List<Role> selectAllRole(Operator operator) {
        return roleDao.selectAllRole(operator);
    }

    /**
     * 查询用户角色
     *
     * @param operator
     * @return
     */
    public List<RoleVo> selectOperatorRole(Operator operator) {
        return roleDao.selectOperatorRole(operator);
    }

    /**
     * 获取商户权限
     *
     * @param paramMap
     * @return
     */
    @Override
    public List<PurviewDomain> selectPurviewByMerchant(Map<String, Object> paramMap) {
        return roleDao.selectPurviewByMerchant(paramMap);
    }

    /***
     * 更新角色权限
     * @param role 角色信息
     * @param purviewIds 权限Id集合
     * @throws Exception
     */
    @Override
    public void updateRoleAuth(Role role, String[] purviewIds) throws Exception {
        //删除角色权限
        rolepurviewDao.deleteByRoleId(role.getId());
        //重新添加角色权限
        Rolepurview rolepurview = null;
        Map<String, Rolepurview> map = new HashMap<String, Rolepurview>();
        for (String id : purviewIds) {//新增商户菜单权限
            if (id.indexOf("#//#") == -1) {
                continue;
            }
            if (map.get(id.split("#//#")[0]) == null) {//判断父菜单是否添加  没有则添加父菜单
                rolepurview = new Rolepurview();
                rolepurview.setSpurviewId(id.split("#//#")[0]);
                rolepurview.setSroleId(role.getId());
                rolepurviewDao.insert(rolepurview);
                map.put(id.split("#//#")[0], rolepurview);
            }

            rolepurview = new Rolepurview();
            rolepurview.setSpurviewId(id.split("#//#")[1]);
            rolepurview.setSroleId(role.getId());
            rolepurviewDao.insert(rolepurview);
        }
    }
}