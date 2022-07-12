package com.cloud.cang.mgr.sys.dao;


import java.util.List;
import java.util.Map;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sys.domain.MenuDomain;
import com.cloud.cang.model.sys.Menu;
import com.github.pagehelper.Page;

/** 后台菜单管理表(SYS_MENU) **/
public interface MenuDao extends GenericDao<Menu, String> {

    /**
     * 根据用户ID 商户ID 查询用户权限范围内的菜单
     * @param param 查询参数 用户ID 商户ID
     * @return
     */
    List<Menu> queryByMap(Map<String, Object> param);
    /**
     * 判断菜单名是否存在
     * @param param
     * @return
     */
    Integer isNameExist(Menu param);
    void deletePurviewByMenuId(String id);
    void deleteRolePurviewMenuId(String id);
    /**
     * 分页查询所有菜单记录
     * @param menu 查询参数
     * @return
     */
    Page<Menu> queryAllMenu(MenuDomain menu);
    /**
     * 获取父菜单
     * @return
     */
    List<Menu> selectByParentMenu();
    /**
     * 获取商户所有菜单
     * @param merchantId 商户ID
     * @return
     */
    List<Menu> selectByMerchantId(String merchantId);

    /**
     * 删除菜单商户权限码关联表
     * @param menuId
     */
    void deletePurviewMerchantByMenuId(String menuId);
}