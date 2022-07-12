package com.cloud.cang.mgr.sys.service;

import com.cloud.cang.mgr.sys.vo.MenuVo;
import com.cloud.cang.model.sys.FastMenu;
import com.cloud.cang.generic.GenericService;
import com.cloud.cang.model.sys.Menu;

import java.util.List;

public interface FastMenuService extends GenericService<FastMenu, String> {

    /**
     * 查看用户可添加的菜单权限
     *
     * @param id 用户Id
     */
    List<MenuVo> selectUserMenu(String id);

    /**
     * 保存用户选择的菜单
     *
     * @param RoleIds    用户选择的数据字典明细ID
     * @param operatorId 用户Id
     */
    void saveFastMenu(String[] RoleIds, String operatorId);

    /**
     * 根据Id查询菜单路径
     *
     * @param id 快捷菜单数字字典明细Id
     */
    String selectMenuPathById(String id);

    /**
     * 根据name修改
     *
     * @param
     */
    void updateByName(FastMenu fastMenu);

    /**
     * 根据name删除
     *
     * @param
     */
    void deleteByName(String name);

    /**
     * 是否已选择该快捷菜单
     *
     * @param name: 快捷菜单名
     */
    boolean isSelect(String name);


    /**
     * 查询快捷菜单数据
     *
     * @param operId 系统用户ID
     * @return
     */
    List<FastMenu> selectByOperId(String operId);

    /**
     * 商户是否有该权限
     *
     * @param spurCode: 权限码
     */
    boolean isMerchantPurview(String spurCode);
}