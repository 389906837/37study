package com.cloud.cang.mgr.hy.service;

import com.cloud.cang.mgr.hy.vo.MbrRoleVo;
import com.cloud.cang.model.hy.MbrRole;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface MbrRoleService extends GenericService<MbrRole, String> {

    /**
     * 会员角色表列表数据
     */
    Page<MbrRole> selectPageAllRole(Page<MbrRole> page, MbrRoleVo mbrRoleVo);

    /**
     *  根据ID数据删除会员角色表
     */
   void delete(String[] checkboxId);

    MbrRole selectByExist(MbrRole mbrRole);

    /**
     * 分配权限码
     * @param roleIds
     * @param mid
     */
    void saveOperatorRole(String [] roleIds,String mid);

}