package com.cloud.cang.mgr.sys.dao;

import com.cloud.cang.generic.GenericDao;
import com.cloud.cang.mgr.sys.vo.MenuVo;
import com.cloud.cang.model.sys.FastMenu;

import java.util.List;
import java.util.Map;

/**
 * 运营快捷菜单配置(SYS_FAST_MENU)
 **/
public interface FastMenuDao extends GenericDao<FastMenu, String> {
    /**
     * 查看用户可添加的菜单权限
     *
     * @param
     */
    List<MenuVo> selectUserMenu(String id);

    String selectMenuPathById(String id);

    void deleteByOperatorId(String operator);

    void updateByName(FastMenu fastMenu);

    void deleteByName(String name);

    /**
     * 是否已选择该快捷菜单
     *
     * @param map 菜单名,商户Id
     */
    int isSelect(Map<String, String> map);
    /**
     * 查询快捷菜单数据
     * @param operId 系统用户ID
     * @return
     */
    List<FastMenu> selectByOperId(String operId);

    /**
     * 商户是否有该权限
     *
     * @param map 权限码,商户Id
     */
    int isMerchantPurview(Map<String, String> map);
}