package com.cloud.cang.mgr.sys.service;

import com.cloud.cang.mgr.sys.domain.MenuDomain;
import com.cloud.cang.mgr.sys.vo.MenuDto;
import com.cloud.cang.model.sys.Menu;
import com.cloud.cang.generic.GenericService;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface MenuService extends GenericService<Menu, String> {

    /**
     * 根据用户ID 商户ID 查询用户权限范围内的菜单
     * @param param
     * @return
     */
    List<Menu> queryByMap(Map<String, Object> param);

    /**
     * 获取商户所有菜单
     * @param merchantId 商户ID
     * @return
     */
    List<Menu> selectByMerchantId(String merchantId);
    /**
     * 判断菜单名称是否存在
     * @param domain
     * @return
     */
    boolean isExistName(Menu domain);

    /**
     * 保存菜单
     * @param menu
     * @param id
     */
    void save(Menu menu,String id);


    /**
     * 删除菜单
     * @param checkboxId 选中的菜单集合
     */
    void delete(String[] checkboxId);

    /**
     * 分页查询所有菜单记录
     * @param page 分页参数
     * @param menu 查询参数
     * @return
     */
    Page<MenuDto> queryAllMenu(Page<MenuDto> page, MenuDomain menu);

    /**
     * 获取父菜单
     * @return
     */
    List<Menu> selectByParentMenu();

    /**
     * 获取所有菜单列表
     * @return
     */
    List<Menu> queryAllMenu();
}